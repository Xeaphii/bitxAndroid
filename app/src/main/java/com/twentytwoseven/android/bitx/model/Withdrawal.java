package com.twentytwoseven.android.bitx.model;

public class Withdrawal {
    public String id;
    public String status;
    public String type;
    public String amount;

    public static class Type {
        public static final String NAD_EFT = "NAD_EFT";
        public static final String ZAR_EFT = "ZAR_EFT";
    }
}
