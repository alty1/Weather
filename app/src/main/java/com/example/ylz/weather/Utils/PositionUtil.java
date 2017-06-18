package com.example.ylz.weather.Utils;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class PositionUtil {
    public static ArrayList<String> getProvince(){
        ArrayList<String> list = new ArrayList<>();
        String result;
        try{
            URL url = new URL("http://guolin.tech/api/china/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(10*1000);
            if(conn.getResponseCode()==200){
                BufferedReader buffer = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
                result = buffer.readLine();
                JSONArray array = new JSONArray(result);
                for(int i=0;i<array.length();i++){
                    JSONObject obj=array.getJSONObject(i);
                    list.add(obj.getInt("id")+"-"+obj.getString("name"));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    public static ArrayList<String> getCity(String provinceId){
        ArrayList<String> list = new ArrayList<>();
        String result;
        try{
            URL url = new URL("http://guolin.tech/api/china/"+provinceId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(10*1000);
            if(conn.getResponseCode()==200){
                BufferedReader buffer = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
                result = buffer.readLine();
                JSONArray array = new JSONArray(result);
                for(int i=0;i<array.length();i++){
                    JSONObject obj=array.getJSONObject(i);
                    list.add(obj.getInt("id")+"-"+obj.getString("name"));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    public static ArrayList<String> getLocation(String provinceId,String cityId){
        ArrayList<String> list = new ArrayList<>();
        String result;
        try{
            URL url = new URL("http://guolin.tech/api/china/"+provinceId+"/"+cityId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(10*1000);
            if(conn.getResponseCode()==200){
                BufferedReader buffer = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
                result = buffer.readLine();
                JSONArray array = new JSONArray(result);
                for(int i=0;i<array.length();i++){
                    JSONObject obj=array.getJSONObject(i);
                    list.add(obj.getString("name"));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
