package com.ordering.bet_rate_change.model;

import com.google.gson.annotations.SerializedName;

public class ChipTransaction {
    @SerializedName("chip_Buying")
    private int chip_Buying;

    @SerializedName("cash_Out")
    private int cash_Out;

    public int getChip_Buying() {
        return chip_Buying;
    }

    public void setChip_Buying(int chip_Buying) {
        this.chip_Buying = chip_Buying;
    }

    public int getCash_Out() {
        return cash_Out;
    }

    public void setCash_Out(int cash_Out) {
        this.cash_Out = cash_Out;
    }
}
