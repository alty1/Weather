package com.example.ylz.weather.Bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by PIETY on 2017/4/30.
 */

public class Wind {
    @SerializedName("deg")
    public String deg;
    @SerializedName("dir")
    public String dir;
    @SerializedName("sc")
    public String sc;
    @SerializedName("spd")
    public String spd;
}
