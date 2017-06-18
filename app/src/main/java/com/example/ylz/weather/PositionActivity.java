package com.example.ylz.weather;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ylz.weather.Utils.PositionUtil;

import java.util.ArrayList;


public class PositionActivity extends AppCompatActivity {
    private Context mContext=this;
    private TextView tv_title;
    private ListView lv_province,lv_city,lv_position;
    private String province,city;
    private String provinceId,cityId;
    private ArrayList<String> list_province,list_city,list_position;
    private ArrayAdapter<String> adapter_province,adapter_city,adapter_position;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.position);
        try {
            ConnectivityManager conn = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo net = conn.getActiveNetworkInfo();
            if(net==null||net.getState()!= NetworkInfo.State.CONNECTED){
                Toast.makeText(mContext,"当前网络连接不可用",Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        tv_title= (TextView) findViewById(R.id.tv_title);
        lv_province= (ListView) findViewById(R.id.lv_province);
        lv_city= (ListView) findViewById(R.id.lv_city);
        lv_position= (ListView) findViewById(R.id.lv_position);

        ImageView iv_btn = (ImageView) findViewById(R.id.iv_btn);
        iv_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                list_province = new ArrayList<>();
                list_province= PositionUtil.getProvince();
                adapter_province=new ArrayAdapter<>(mContext,R.layout.support_simple_spinner_dropdown_item,list_province);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lv_province.setAdapter(adapter_province);
                    }
                });
            }
        }).start();
        lv_province.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lv_position.setAdapter(null) ;
                String str_item = parent.getItemAtPosition(position).toString();
                provinceId=str_item.split("-")[0];
                province=str_item.split("-")[1];
                tv_title.setText(province);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        list_city=new ArrayList<>();
                        list_city=PositionUtil.getCity(provinceId);
                        adapter_city=new ArrayAdapter<>(mContext,R.layout.support_simple_spinner_dropdown_item,list_city);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                lv_city.setAdapter(adapter_city);
                            }
                        });
                    }
                }).start();
            }
        });
        lv_city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String str_item = parent.getItemAtPosition(position).toString();
                cityId=str_item.split("-")[0];
                city=str_item.split("-")[1];
                tv_title.setText(province+"-"+city);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        list_position=new ArrayList<>();
                        list_position=PositionUtil.getLocation(provinceId,cityId);
                        adapter_position=new ArrayAdapter<>(mContext,R.layout.support_simple_spinner_dropdown_item,list_position);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                lv_position.setAdapter(adapter_position);
                            }
                        });
                    }
                }).start();
            }
        });
        lv_position.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String str_item = parent.getItemAtPosition(position).toString();
                MainActivity.position=province+"-"+str_item;
                finish();
            }
        });
    }
}
