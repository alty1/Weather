package com.example.ylz.weather.Bean;

import com.google.gson.annotations.SerializedName;

public class DailyCond {
    @SerializedName("code_d")
    public String code_d;
    @SerializedName("code_n")
    public String code_n;
    @SerializedName("txt_d")
    public String txt_d;
    @SerializedName("txt_n")
    public String txt_n;
}
