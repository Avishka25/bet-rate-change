package com.ordering.bet_rate_change.network;

import com.ordering.bet_rate_change.model.ChipTransaction;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("api/CryptoRate/GetChipTransactions")
    Call<List<ChipTransaction>> getChipTransactions();

}
