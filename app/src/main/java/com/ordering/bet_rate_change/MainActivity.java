package com.ordering.bet_rate_change;

import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.ordering.bet_rate_change.Adapters.ImageSliderAdapter;
import com.ordering.bet_rate_change.model.ChipTransaction;
import com.ordering.bet_rate_change.utils.PrefHelper;
import com.ordering.bet_rate_change.viewmodel.CryptoViewModel;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private Handler sliderHandler = new Handler();
    private CryptoViewModel viewModel;

    private int[] images = {
            R.drawable.chaina,
            R.drawable.chaina_rate,
            R.drawable.lkr,
            R.drawable.lkr_rate
    };
    private Handler apiHandler = new Handler();
    private PrefHelper prefHelper;
    private LinearLayout rateLayout;
    private TextView txtBuyValue, txtCashValue, txtBuyTitle, txtCashTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        viewPager = findViewById(R.id.viewPager);
        prefHelper = new PrefHelper(this);

        rateLayout = findViewById(R.id.rateLayout);
        txtBuyValue = findViewById(R.id.txtBuyValue);
        txtCashValue = findViewById(R.id.txtCashValue);
        txtBuyTitle = findViewById(R.id.txtBuyTitle);
        txtCashTitle = findViewById(R.id.txtCashTitle);

        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        ImageSliderAdapter adapter = new ImageSliderAdapter(images);
        viewPager.setAdapter(adapter);

        startAutoSlide();

        viewModel = new ViewModelProvider(this).get(CryptoViewModel.class);

        startApiAutoCall();

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                if (images[position] == R.drawable.chaina_rate) {

                    rateLayout.setVisibility(View.VISIBLE);

                    txtBuyTitle.setText("购买赌场筹码");
                    txtCashTitle.setText("兑换现金");

                    updateRateUI();

                }
                else if (images[position] == R.drawable.lkr_rate) {

                    rateLayout.setVisibility(View.VISIBLE);

                    txtBuyTitle.setText("CHIPS BUYING");
                    txtCashTitle.setText("CASH OUT");

                    updateRateUI();

                }
                else {
                    rateLayout.setVisibility(View.GONE);
                }
            }
        });
    }

    private void updateRateUI() {

        int savedChip = prefHelper.getChipBuying();
        int savedCash = prefHelper.getCashOut();

        // 🔹 Chip Buying
        String chipText = "LKR. " + savedChip;
        SpannableString chipSpannable = new SpannableString(chipText);

        // Make "LKR." smaller
        chipSpannable.setSpan(
                new android.text.style.RelativeSizeSpan(0.5f), // 60% of main text
                0, 4, // "LKR."
                android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        txtBuyValue.setText(chipSpannable);

        // 🔹 Cash Out
        String cashText = "LKR. " + savedCash;
        SpannableString cashSpannable = new SpannableString(cashText);

        // Make "LKR." smaller
        cashSpannable.setSpan(
                new android.text.style.RelativeSizeSpan(0.4f),
                0, 4, // "LKR."
                android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        txtCashValue.setText(cashSpannable);
    }

    private void startApiAutoCall() {

        // 🔥 Observe ONLY ONCE
        viewModel.getTransactions().observe(this, transactions -> {

            if (transactions != null && !transactions.isEmpty()) {

                ChipTransaction data = transactions.get(0);

                int chipBuying = data.getChip_Buying();
                int cashOut = data.getCash_Out();

                // ✅ SAVE every time API returns
                prefHelper.saveChipData(chipBuying, cashOut);
            }
        });

        // 🔥 Call API every 2 seconds
        apiHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Log.d("API_CALL", "Calling API...");
                viewModel.fetchTransactions();   // <-- call API here

                apiHandler.postDelayed(this, 20000);
            }
        }, 2000);
    }



    private void startAutoSlide() {
        sliderHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                int currentItem = viewPager.getCurrentItem();
                int totalItem = viewPager.getAdapter().getItemCount();

                if (currentItem < totalItem - 1) {

                    // normal slide
                    viewPager.setCurrentItem(currentItem + 1, true);

                } else {

                    // go back to first WITHOUT backward animation
                    viewPager.setCurrentItem(0, false);

                }

                sliderHandler.postDelayed(this, 10000);

            }
        }, 10000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sliderHandler.removeCallbacksAndMessages(null);
        apiHandler.removeCallbacksAndMessages(null);
    }
}