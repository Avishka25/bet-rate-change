package com.ordering.bet_rate_change.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefHelper {

    private static final String PREF_NAME = "crypto_prefs";

    private static final String KEY_CHIP_BUYING = "chip_buying";
    private static final String KEY_CASH_OUT = "cash_out";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public PrefHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // ✅ SAVE DATA
    public void saveChipData(double chipBuying, double cashOut) {
        editor.putFloat(KEY_CHIP_BUYING, (float) chipBuying);
        editor.putFloat(KEY_CASH_OUT, (float) cashOut);
        editor.apply(); // async save
    }

    // ✅ GET CHIP BUYING
    public double getChipBuying() {
        return sharedPreferences.getFloat(KEY_CHIP_BUYING, 0.0f);
    }

    // ✅ GET CASH OUT
    public double getCashOut() {
        return sharedPreferences.getFloat(KEY_CASH_OUT, 0.0f);
    }

    // ✅ DELETE DATA
    public void clearChipData() {
        editor.remove(KEY_CHIP_BUYING);
        editor.remove(KEY_CASH_OUT);
        editor.apply();
    }
}
