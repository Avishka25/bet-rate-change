package com.ordering.bet_rate_change.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ordering.bet_rate_change.model.ChipTransaction;
import com.ordering.bet_rate_change.repository.CryptoRepository;

import java.util.List;

public class CryptoViewModel extends ViewModel {

    private CryptoRepository repository;
    private MutableLiveData<List<ChipTransaction>> transactionsLiveData = new MutableLiveData<>();

    public CryptoViewModel() {
        repository = new CryptoRepository();
    }

    public LiveData<List<ChipTransaction>> getTransactions() {
        return transactionsLiveData;
    }

    public void fetchTransactions() {
        repository.getChipTransactions().observeForever(data -> {
            transactionsLiveData.postValue(data);
        });
    }
}
