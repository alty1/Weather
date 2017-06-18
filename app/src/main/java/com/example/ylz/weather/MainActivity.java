package com.example.ylz.weather;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ylz.weather.Bean.WeatherBean;
import com.example.ylz.weather.Utils.WeatherUtil;

public class MainActivity extends AppCompatActivity {
    public static String position="";
    private Context mContext = this;
    private Button btn;
    private TextView tv_tmp,tv_fl,tv_txt,tv_dir,tv_sc,tv_hum,tv_vis,tv_day1Cond,tv_day2Cond,tv_day3Cond,tv_day1Tmp,tv_day2Tmp,tv_day3Tmp;
    private TextView tv_qlty,tv_aqi,tv_pm25,tv_dbrf,tv_dtxt,tv_sbrf,tv_stxt,tv_tbrf,tv_ttxt,tv_update;
    private ImageView iv_delete,iv_icon,iv_day1Icon,iv_day2Icon,iv_day3Icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //天气信息相关控件
        tv_tmp = (TextView) findViewById(R.id.tv_tmp);
        tv_fl = (TextView) findViewById(R.id.tv_fl);
        tv_txt = (TextView) findViewById(R.id.tv_txt);
        tv_dir = (TextView) findViewById(R.id.tv_dir);
        tv_sc = (TextView) findViewById(R.id.tv_sc);
        tv_hum = (TextView) findViewById(R.id.tv_hum);
        tv_vis = (TextView) findViewById(R.id.tv_vis);
        iv_icon = (ImageView) findViewById(R.id.iv_icon);
        tv_day1Cond = (TextView) findViewById(R.id.tv_day1Cond);
        tv_day2Cond = (TextView) findViewById(R.id.tv_day2Cond);
        tv_day3Cond = (TextView) findViewById(R.id.tv_day3Cond);
        tv_day1Tmp = (TextView) findViewById(R.id.tv_day1Tmp);
        tv_day2Tmp = (TextView) findViewById(R.id.tv_day2Tmp);
        tv_day3Tmp = (TextView) findViewById(R.id.tv_day3Tmp);
        iv_day1Icon = (ImageView) findViewById(R.id.iv_day1Icon);
        iv_day2Icon = (ImageView) findViewById(R.id.iv_day2Icon);
        iv_day3Icon = (ImageView) findViewById(R.id.iv_day3Icon);
        tv_qlty = (TextView) findViewById(R.id.tv_qlty);
        tv_aqi = (TextView) findViewById(R.id.tv_aqi);
        tv_pm25 = (TextView) findViewById(R.id.tv_pm25);
        tv_dbrf = (TextView) findViewById(R.id.tv_dbrf);
        tv_dtxt = (TextView) findViewById(R.id.tv_dtxt);
        tv_sbrf = (TextView) findViewById(R.id.tv_sbrf);
        tv_stxt = (TextView) findViewById(R.id.tv_stxt);
        tv_tbrf = (TextView) findViewById(R.id.tv_tbrf);
        tv_ttxt = (TextView) findViewById(R.id.tv_ttxt);
        tv_update = (TextView) findViewById(R.id.tv_update);


        //浮动button添加地址
        btn = (Button) findViewById(R.id.float_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, PositionActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (position!="") {
            btn.setText(position);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String location=position.split("-")[1];
                        WeatherUtil wutil = new WeatherUtil(location);
                        final WeatherBean wb = setWeatherBean(wutil);
                        MainActivity.position = "";
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateUI(wb);
                            }
                        });
                    }catch (Exception e){
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext,"未能找到相关的天气信息!",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }).start();
        }
    }
    private boolean checkNet(){
        try {
            ConnectivityManager conn = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo net = conn.getActiveNetworkInfo();
            if(net.getState()== NetworkInfo.State.CONNECTED){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    private WeatherBean setWeatherBean(WeatherUtil wutil){;
        WeatherBean wb = new WeatherBean();
        wb.now = wutil.getNowWeathre();
        wb.day1 = wutil.getDailyWeather(0);
        wb.day2 = wutil.getDailyWeather(1);
        wb.day3 = wutil.getDailyWeather(2);
        wb.now_icon = wutil.getIcon(wb.now.cond.code);
        wb.day1_icon = wutil.getIcon(wb.day1.cond.code_d);
        wb.day2_icon = wutil.getIcon(wb.day2.cond.code_d);
        wb.day3_icon = wutil.getIcon(wb.day3.cond.code_d);
        wb.aqi=wutil.getAQI();
        wb.drsg=wutil.getSuggestion("drsg");
        wb.sport=wutil.getSuggestion("sport");
        wb.trav=wutil.getSuggestion("trav");
        wb.time=wutil.getUpdateTime();
        return wb;
    }

    //选择地址后直接从网络获取数据更新UI
    private void updateUI(WeatherBean wb){
        tv_tmp.setText(wb.now.tmp + "°");
        tv_fl.setText("体感温度：" + wb.now.fl + "°");
        tv_txt.setText(wb.now.cond.txt);
        tv_dir.setText(wb.now.wind.dir);
        tv_sc.setText(wb.now.wind.sc.indexOf("风") != -1 ? wb.now.wind.sc : wb.now.wind.sc + "级");
        tv_hum.setText(wb.now.hum + "%");
        tv_vis.setText(wb.now.vis + "km");
        iv_icon.setImageBitmap(wb.now_icon);
        iv_day1Icon.setImageBitmap(wb.day1_icon);
        iv_day2Icon.setImageBitmap(wb.day2_icon);
        iv_day3Icon.setImageBitmap(wb.day3_icon);
        tv_day1Cond.setText(wb.day1.cond.txt_d.equals(wb.day1.cond.txt_n) ? wb.day1.cond.txt_d : wb.day1.cond.txt_d + "转" + wb.day1.cond.txt_n);
        tv_day2Cond.setText(wb.day2.cond.txt_d.equals(wb.day2.cond.txt_n) ? wb.day2.cond.txt_d : wb.day2.cond.txt_d + "转" + wb.day2.cond.txt_n);
        tv_day3Cond.setText(wb.day3.cond.txt_d.equals(wb.day3.cond.txt_n) ? wb.day3.cond.txt_d : wb.day3.cond.txt_d + "转" + wb.day3.cond.txt_n);
        tv_day1Tmp.setText(wb.day1.tmp.min + "°/" + wb.day1.tmp.max + "°");
        tv_day2Tmp.setText(wb.day2.tmp.min + "°/" + wb.day2.tmp.max + "°");
        tv_day3Tmp.setText(wb.day3.tmp.min + "°/" + wb.day3.tmp.max + "°");
        if(wb.aqi==null){
            tv_qlty.setText("无");
            tv_aqi.setText("无");
            tv_pm25.setText("无");
        }
        else{
            tv_qlty.setText(wb.aqi.qlty);
            tv_aqi.setText(wb.aqi.aqi);
            tv_pm25.setText(wb.aqi.pm25);
        }
        tv_dbrf.setText(wb.drsg.brf);
        tv_dtxt.setText(wb.drsg.txt);
        tv_sbrf.setText(wb.sport.brf);
        tv_stxt.setText(wb.sport.txt);
        tv_tbrf.setText(wb.trav.brf);
        tv_ttxt.setText(wb.trav.txt);
        tv_update.setText("最后更新时间：" + wb.time);
    }
}
