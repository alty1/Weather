package com.example.ylz.weather.Bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by PIETY on 2017/4/30.
 */

public class NowWeather {
    @SerializedName("cond")
    public NowCond cond;        //天气状况
    @SerializedName("fl")
    public String fl;       //体感温度
    @SerializedName("hum")
    public String hum;      //湿度(%)
    @SerializedName("pcpn")
    public String pcpn;     //降雨量(mm)
    @SerializedName("pres")
    public String pres;     //气压
    @SerializedName("tmp")
    public String tmp;      //当前温度(摄氏度)
    @SerializedName("vis")
    public String vis;      //能见度(km)
    @SerializedName("wind")
    public Wind wind;    //风力状况
}
