package com.ordering.bet_rate_change.model;

import com.google.gson.annotations.SerializedName;

public class ChipTransaction {
    @SerializedName("chip_Buying")
    private double chip_Buying;

    @SerializedName("cash_Out")
    private double cash_Out;

    public double getChip_Buying() {
        return chip_Buying;
    }

    public void setChip_Buying(double chip_Buying) {
        this.chip_Buying = chip_Buying;
    }

    public double getCash_Out() {
        return cash_Out;
    }

    public void setCash_Out(double cash_Out) {
        this.cash_Out = cash_Out;
    }
}
