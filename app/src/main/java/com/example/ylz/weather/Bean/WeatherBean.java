package com.example.ylz.weather.Bean;

import android.graphics.Bitmap;

/**
 * Created by PIETY on 2017/5/1.
 */

public class WeatherBean {
    public NowWeather now;
    public DailyWeather day1;
    public DailyWeather day2;
    public DailyWeather day3;
    public Bitmap now_icon;
    public Bitmap day1_icon;
    public Bitmap day2_icon;
    public Bitmap day3_icon;
    public AQI aqi;
    public Suggestion drsg;
    public Suggestion sport;
    public Suggestion trav;
    public String time;
    public WeatherBean(){}
}
