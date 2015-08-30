package com.twentytwoseven.android.bitx.model;

import com.google.gson.annotations.SerializedName;

public class Ticker {
    public long timestamp;
    public String currency;
    public double bid;
    public double ask;
    public double lastTrade;
    @SerializedName("rolling_24_hour_volume")
    public double rolling24HourVolume;
}
