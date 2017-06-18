package com.example.ylz.weather.Bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by PIETY on 2017/5/2.
 */

public class AQI {
    @SerializedName("aqi")
    public String aqi;
    @SerializedName("pm10")
    public String pm10;
    @SerializedName("pm25")
    public String pm25;
    @SerializedName("qlty")
    public String qlty;
}
