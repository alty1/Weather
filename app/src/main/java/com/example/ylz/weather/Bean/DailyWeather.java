package com.example.ylz.weather.Bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by PIETY on 2017/5/1.
 */

public class DailyWeather {
    @SerializedName("astro")
    public DailyAstro astro;
    @SerializedName("cond")
    public DailyCond cond;
    @SerializedName("date")
    public String date;
    @SerializedName("hum")
    public String hum;
    @SerializedName("pcpn")
    public String pcpn;
    @SerializedName("pop")
    public String pop;
    @SerializedName("pres")
    public String pres;
    @SerializedName("tmp")
    public DailyTmp tmp;
    @SerializedName("uv")
    public String uv;
    @SerializedName("vis")
    public String vis;
    @SerializedName("wind")
    public Wind wind;
}
