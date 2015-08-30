package com.twentytwoseven.android.bitx.model;

import java.util.List;

public class OrderBook {
    public long timestamp;
    public String currency;
    public List<SimpleOrder> asks;
    public List<SimpleOrder> bids;
}
