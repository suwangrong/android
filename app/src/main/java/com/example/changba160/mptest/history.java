package com.example.changba160.mptest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;


public class history extends AppCompatActivity {
    private ViewPager vpMain;
    private String mJson = "[{\"date\":\"2018年3月\",\"obj\":[{\"title\":\"接口1\",\"value\":211},{\"title\":\"接口2\",\"value\":303},{\"title\":\"接口3\",\"value\":351},{\"title\":\"接口4\",\"value\":232},{\"title\":\"接口5\",\"value\":438},{\"title\":\"接口6\",\"value\":359},{\"title\":\"接口7\",\"value\":328},{\"title\":\"接口8\",\"value\":304}]},{\"date\":\"2018年3月\",\"obj\":[{\"title\":\"接口1\",\"value\":200},{\"title\":\"接口2\",\"value\":200},{\"title\":\"接口3\",\"value\":472},{\"title\":\"接口4\",\"value\":399},{\"title\":\"接口5\",\"value\":215},{\"title\":\"接口6\",\"value\":439},{\"title\":\"接口7\",\"value\":457},{\"title\":\"接口8\",\"value\":336}]},{\"date\":\"2018年3月\",\"obj\":[{\"title\":\"接口1\",\"value\":230},{\"title\":\"接口2\",\"value\":480},{\"title\":\"接口3\",\"value\":282},{\"title\":\"接口4\",\"value\":367},{\"title\":\"接口5\",\"value\":200},{\"title\":\"接口6\",\"value\":429},{\"title\":\"接口7\",\"value\":451},{\"title\":\"接口8\",\"value\":475}]},{\"date\":\"2018年3月\",\"obj\":[{\"title\":\"接口1\",\"value\":498},{\"title\":\"接口2\",\"value\":336},{\"title\":\"接口3\",\"value\":500},{\"title\":\"接口4\",\"value\":292},{\"title\":\"接口5\",\"value\":489},{\"title\":\"接口6\",\"value\":395},{\"title\":\"接口7\",\"value\":333},{\"title\":\"接口8\",\"value\":307}]},{\"date\":\"2018年3月\",\"obj\":[{\"title\":\"接口1\",\"value\":377},{\"title\":\"接口2\",\"value\":336},{\"title\":\"接口3\",\"value\":229},{\"title\":\"接口4\",\"value\":383},{\"title\":\"接口5\",\"value\":342},{\"title\":\"接口6\",\"value\":266},{\"title\":\"接口7\",\"value\":333},{\"title\":\"接口8\",\"value\":430}]},{\"date\":\"2018年3月\",\"obj\":[{\"title\":\"接口1\",\"value\":398},{\"title\":\"接口2\",\"value\":292},{\"title\":\"接口3\",\"value\":347},{\"title\":\"接口4\",\"value\":247},{\"title\":\"接口5\",\"value\":397},{\"title\":\"接口6\",\"value\":488},{\"title\":\"接口7\",\"value\":403},{\"title\":\"接口8\",\"value\":306}]},{\"date\":\"2018年3月\",\"obj\":[{\"title\":\"接口1\",\"value\":378},{\"title\":\"接口2\",\"value\":374},{\"title\":\"接口3\",\"value\":378},{\"title\":\"接口4\",\"value\":385},{\"title\":\"接口5\",\"value\":279},{\"title\":\"接口6\",\"value\":473},{\"title\":\"接口7\",\"value\":335},{\"title\":\"接口8\",\"value\":376}]}]";    private String s = "[{\"date\":\"2018年3月\",\"obj\":[{\"title\":\"接口1\",\"value\":40},{\"title\":\"接口2\",\"value\":28},{\"title\":\"接口3\",\"value\":29},{\"title\":\"接口4\",\"value\":32},{\"title\":\"接口5\",\"value\":29},{\"title\":\"接口6\",\"value\":28}]},{\"date\":\"2018年4月\",\"obj\":[{\"title\":\"cpu使用率\",\"value\":40},{\"title\":\"磁盘使用率\",\"value\":28},{\"title\":\"带宽使用率\",\"value\":32}]},{\"date\":\"2018年4月\",\"obj\":[{\"title\":\"cpu使用率\",\"value\":40},{\"title\":\"磁盘使用率\",\"value\":28},{\"title\":\"带宽使用率\",\"value\":32}]}]";
    private ArrayList<MonthBean> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);
        vpMain = ((ViewPager) findViewById(R.id.vp_main));
        Button button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(history.this, detail.class);
                intent.putExtra("button","all");
                startActivity(intent);  //跳转页面
            }
        });
        Button button3 = (Button)findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(history.this, detail.class);
                intent.putExtra("button","button3");
                startActivity(intent);  //跳转页面
            }
        });
        Button button4 = (Button)findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(history.this, detail.class);
                intent.putExtra("button","button4");
                startActivity(intent);  //跳转页面
            }
        });
        Button button5 = (Button)findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(history.this, detail.class);
                intent.putExtra("button","button5");
                startActivity(intent);  //跳转页面
            }
        });
        initData();
        initView();
    }

    private void initData() {
        Gson gson = new Gson();
        mData = gson.fromJson(mJson, new TypeToken<ArrayList<MonthBean>>() {
        }.getType());
    }

    private void initView() {
        vpMain.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return PieFragment.newInstance(mData.get(position));
            }

            @Override
            public int getCount() {
                return mData.size();
            }
        });
    }
}


