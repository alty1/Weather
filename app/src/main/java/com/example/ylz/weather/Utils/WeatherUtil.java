package com.example.ylz.weather.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.google.gson.Gson;
import com.example.ylz.weather.Bean.AQI;
import com.example.ylz.weather.Bean.DailyWeather;
import com.example.ylz.weather.Bean.NowWeather;
import com.example.ylz.weather.Bean.Suggestion;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherUtil {
    public static String jsondata="";
    public WeatherUtil(){}
    public WeatherUtil(String position){
        try{
            URL url = new URL("https://free-api.heweather.com/v5/weather?city="+position.trim()+"&key=7c3a4bf7c82241b08ad337637811e8a8");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(10*1000);
            if(conn.getResponseCode()==200){
                BufferedReader buffer = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
                jsondata=buffer.readLine();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public static NowWeather getNowWeathre()
    {
        NowWeather nwb = null;
        try {
            JSONObject object = new JSONObject(jsondata);
            JSONObject obj = object.getJSONArray("HeWeather5").getJSONObject(0);
            Gson gson = new Gson();
            nwb = gson.fromJson(obj.getJSONObject("now").toString(),NowWeather.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return nwb;
    }
    public static DailyWeather getDailyWeather(int index)
    {
        DailyWeather dwb = null;
        try {
            JSONObject object = new JSONObject(jsondata);
            JSONObject obj = object.getJSONArray("HeWeather5").getJSONObject(0);
            Gson gson = new Gson();
            dwb = gson.fromJson(obj.getJSONArray("daily_forecast").getJSONObject(index).toString(),DailyWeather.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return dwb;
    }
    public static AQI getAQI(){
        AQI aqi =null;
        try {
            JSONObject object = new JSONObject(jsondata);
            JSONObject obj = object.getJSONArray("HeWeather5").getJSONObject(0);
            Gson gson = new Gson();
            aqi = gson.fromJson(obj.getJSONObject("aqi").getJSONObject("city").toString(),AQI.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return aqi;
    }
    public static Suggestion getSuggestion(String type){
        Suggestion sug = null;
        try {
            JSONObject object = new JSONObject(jsondata);
            JSONObject obj = object.getJSONArray("HeWeather5").getJSONObject(0);
            Gson gson = new Gson();
            sug = gson.fromJson(obj.getJSONObject("suggestion").getJSONObject(type).toString(),Suggestion.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return sug;
    }
    public static Bitmap getIcon(String code){
        Bitmap bitmap=null;
        try{
            URL url = new URL("https://cdn.heweather.com/cond_icon/"+code+".png");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(10*1000);
            if (conn.getResponseCode()==200){
                bitmap = BitmapFactory.decodeStream(conn.getInputStream());
                String path = Environment.getExternalStorageDirectory().getPath()+"/mWeather";
                File fPath = new File(path);
                if(!fPath.exists()){fPath.mkdirs();}
                File icon = new File(path,code+".png");
                if(!icon.exists()){
                    icon.createNewFile();
                    FileOutputStream out = new FileOutputStream(icon);
                    bitmap.compress(Bitmap.CompressFormat.PNG,100,out);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return bitmap;
    }
    public static String getUpdateTime(){
        String time="";
        try {
            JSONObject object = new JSONObject(jsondata);
            JSONObject obj = object.getJSONArray("HeWeather5").getJSONObject(0).getJSONObject("basic").getJSONObject("update");
            time = obj.getString("loc");
        }catch (Exception e){
            e.printStackTrace();
        }
        return time;
    }
}
