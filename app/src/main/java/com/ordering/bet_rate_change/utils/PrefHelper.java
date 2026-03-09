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
    public void saveChipData(int chipBuying, int cashOut) {
        editor.putInt(KEY_CHIP_BUYING, chipBuying);
        editor.putInt(KEY_CASH_OUT, cashOut);
        editor.apply(); // async save
    }

    // ✅ GET CHIP BUYING
    public int getChipBuying() {
        return sharedPreferences.getInt(KEY_CHIP_BUYING, 0);
    }

    // ✅ GET CASH OUT
    public int getCashOut() {
        return sharedPreferences.getInt(KEY_CASH_OUT, 0);
    }

    // ✅ DELETE DATA
    public void clearChipData() {
        editor.remove(KEY_CHIP_BUYING);
        editor.remove(KEY_CASH_OUT);
        editor.apply();
    }
}
