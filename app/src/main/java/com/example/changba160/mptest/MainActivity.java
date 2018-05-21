package com.example.changba160.mptest;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayout;

    //健康颜色
    private int green = Color.rgb(59, 212, 136);
    //危险颜色
    private int red = Color.rgb(150, 52, 54);
    //正常颜色
    private int white = Color.rgb(218, 244, 231);

    //健康阀值
    private int health = 20;
    //正常阀值
    private int normal = 60;


    private String center_string = "1223";

    private LineChart mLineChart = null;
    private LineChart mLineChart2 = null;
    private LineChart mLineChart3 = null;

    private PieChart mPieChart;
    private PieChart mPieChart2;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            mPieChart.animateXY(1000, 1000);
            mPieChart2.animateXY(1000, 1000);
            mLineChart.animateXY(1000, 1000);
            mLineChart2.animateXY(1000, 1000);
            mLineChart3.animateXY(1000, 1000);


            super.handleMessage(msg);
            switch (msg.what) {
                case 0x101:
                    if (swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(false);//设置不刷新
                    }
                    break;
            }
        }
    };

    private int get_color(float max_y) {
        if (max_y > normal) {
            return red;
        } else if (max_y > health) {
            return white;
        } else {
            return green;
        }
    }

    private Handler handler_l = new Handler();
    private Runnable runnable = new Runnable() {
        public void run() {
            this.update();
            handler.postDelayed(this, 1000 * 12);// 间隔120秒
        }

        void update() {
            //刷新msg的内容
            List<Entry> entries = new ArrayList<>();
            for (int i = 10; i < 20; i++) {
                entries.add(new Entry(i, (float) (Math.random()) * 80));
            }
            //一个LineDataSet就是一条线
            LineDataSet lineDataSet = new LineDataSet(entries, "内存");
            LineData datas = new LineData(lineDataSet);
            mLineChart.setData(datas);
            mLineChart3.setData(datas);

            mLineChart.invalidate();
            mLineChart2.invalidate();
            mLineChart3.invalidate();
            mPieChart.invalidate();
            mPieChart2.invalidate();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.main_srl);

        handler.postDelayed(runnable, 1000 * 5);

        mLineChart = (LineChart) findViewById(R.id.lineChart);
        mLineChart2 = (LineChart) findViewById(R.id.lineChart2);
        mLineChart3 = (LineChart) findViewById(R.id.lineChart3);

        //设置自定义的点击事件
        mLineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.e("true", "dianji");
            }

            @Override
            public void onNothingSelected() {

            }
        });


        mPieChart = ((PieChart) findViewById(R.id.pc_chart));
        mPieChart2 = ((PieChart) findViewById(R.id.pc_chart2));

        //设置中间是否可见
        mPieChart.setDrawHoleEnabled(true);
        //设置中间颜色
        mPieChart.setHoleColor(Color.WHITE);
        //设置中间内容
        mPieChart.setCenterText(center_string);
        //设置中间内容大小
        mPieChart.setCenterTextSize(15);

        mPieChart2.setDrawHoleEnabled(false);


        //饼状图数据添加
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        entries.add(new PieEntry(40, "优秀"));
        entries.add(new PieEntry(20, "满分"));
        entries.add(new PieEntry(40, "及格"));


        //饼状图颜色添加
        List<Integer> mColors = new ArrayList<Integer>();
        mColors.add(Color.rgb(216, 77, 71));
        mColors.add(Color.rgb(183, 56, 63));
        mColors.add(Color.rgb(247, 85, 47));


        //设置饼饼状图数据
        PieDataSet pieDataSet = new PieDataSet(entries, "饼状图");
        pieDataSet.setColors(mColors);
        //设置数据字体大小
        pieDataSet.setValueTextSize(10);
        //设置比例是否可见
        pieDataSet.setDrawValues(true);
        pieDataSet.setHighlightEnabled(true);
        PieData pieData = new PieData(pieDataSet);


        Legend legend = mPieChart.getLegend();
        legend.setEnabled(false);

        Legend legend2 = mPieChart2.getLegend();
        legend2.setEnabled(false);

        //设置是否显示描述
        mPieChart.setDrawSliceText(false);

        mPieChart.setData(pieData);
        //设置描述
        Description description = new Description();
        description.setText("ssss");
        mPieChart.setDescription(description);
        mPieChart2.setDescription(description);
        mPieChart.animateXY(1000, 1000);
        mPieChart2.setData(pieData);
        mPieChart2.animateXY(1000, 1000);


        //显示边界
        mLineChart.setDrawBorders(true);
        mLineChart2.setDrawBorders(true);
        mLineChart3.setDrawBorders(true);

        mLineChart.setBackgroundColor(green);
        mLineChart2.setBackgroundColor(white);
        mLineChart3.setBackgroundColor(red);
        //设置数据
        List<Entry> line_entries = new ArrayList<>();
        float max_y = 0;
        float y = 0;
        for (int i = 0; i < 288; i++) {
            y = (float) (Math.random()) * 80;
            if (y > max_y) {
                max_y = y;
            }
            line_entries.add(new Entry(i, y));
        }
        int color = get_color(max_y);
        //一个LineDataSet就是一条线
        LineDataSet lineDataSet = new LineDataSet(line_entries, "温度");
        LineData data = new LineData(lineDataSet);
        mLineChart.setData(data);
        mLineChart2.setData(data);
        mLineChart3.setData(data);

        mLineChart.setBackgroundColor(color);
        mLineChart2.setBackgroundColor(color);
        mLineChart3.setBackgroundColor(color);

        mLineChart.animateXY(1000, 1000);
        mLineChart2.animateXY(1000, 1000);
        mLineChart3.animateXY(1000, 1000);


        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new LoadDataThread().start();
            }
        });

    }


    /**
     * 模拟加载数据的线程
     */
    class LoadDataThread extends Thread {
        @Override
        public void run() {
            initData();

            handler.sendEmptyMessage(0x101);//通过handler发送一个更新数据的标记
        }

        private void initData() {
            List<Entry> entries = new ArrayList<>();
            for (int i = 10; i < 20; i++) {
                entries.add(new Entry(i, (float) (Math.random()) * 80));
            }
            //一个LineDataSet就是一条线
            LineDataSet lineDataSet = new LineDataSet(entries, "内存");
            LineData datas = new LineData(lineDataSet);
            mLineChart.setData(datas);
            mLineChart3.setData(datas);
            mLineChart.invalidate();

        }
    }


}
