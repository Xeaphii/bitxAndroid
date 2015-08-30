package com.twentytwoseven.android.bitx.model;

public class Order {
    public double feeCounter;
    public String orderId;
    public long creationTimestamp;
    public double counter;
    public double limitVolume;
    public double limitPrice;
    public String state;
    public double base;
    public double feeBase;
    public String type;
    public long expirationTimestamp;

    public static class Type {
        public static final String BID = "BID";
        public static final String ASK = "ASK";
    }

    public static class State {
        public static final String PENDING = "PENDING";
        public static final String COMPLETE = "COMPLETE";
    }
}
