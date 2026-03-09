package com.ordering.bet_rate_change.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ordering.bet_rate_change.model.ChipTransaction;
import com.ordering.bet_rate_change.network.ApiService;
import com.ordering.bet_rate_change.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CryptoRepository {

    private ApiService apiService;

    public CryptoRepository() {
        apiService = RetrofitClient.getClient().create(ApiService.class);
    }

    public LiveData<List<ChipTransaction>> getChipTransactions() {

        MutableLiveData<List<ChipTransaction>> data = new MutableLiveData<>();

        apiService.getChipTransactions().enqueue(new Callback<List<ChipTransaction>>() {
            @Override
            public void onResponse(Call<List<ChipTransaction>> call, Response<List<ChipTransaction>> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ChipTransaction>> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }
}
