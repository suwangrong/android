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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class detail extends AppCompatActivity {
    private List<Integer> all = Arrays.asList(2526, 2718, 2914, 3150, 2696, 2878, 2978);
    private List<Integer> day1 = Arrays.asList(211, 303, 351, 232, 438, 359, 328, 304);
    private List<Integer> day2 = Arrays.asList(200, 200, 472, 399, 215, 439, 457, 336);
    private List<Integer> day3 = Arrays.asList(230, 480, 282, 367, 200, 429, 451, 475);
    private List<Integer> day4 = Arrays.asList(498, 336, 500, 292, 489, 395, 333, 307);
    private List<Integer> day5 = Arrays.asList(377, 336, 229, 383, 342, 266, 333, 430);
    private List<Integer> day6 = Arrays.asList(398, 292, 347, 247, 397, 488, 403, 306);
    private List<Integer> day7 = Arrays.asList(378, 374, 378, 385, 279, 473, 335, 376);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String button = intent.getStringExtra("button");

        LineChart lineChart = findViewById(R.id.detail_lineChart);
        //显示边界
        lineChart.setDrawBorders(true);
        //设置数据
        List<Entry> entries = new ArrayList<>();
        List<Entry> entries1 = new ArrayList<>();
        List<Entry> entries2 = new ArrayList<>();
        List<Entry> entries3 = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            entries.add(new Entry(i, (float) all.get(i)));
            entries1.add(new Entry(i, (float) day1.get(i)));
            entries2.add(new Entry(i, (float) day2.get(i)));
            entries3.add(new Entry(i, (float) day3.get(i)));
        }
        //一个LineDataSet就是一条线
        LineDataSet lineDataSet = new LineDataSet(entries, "总访问量");
        LineDataSet lineDataSet1 = new LineDataSet(entries1, "主要接口1");
        LineDataSet lineDataSet2 = new LineDataSet(entries2, "主要接口2");
        LineDataSet lineDataSet3 = new LineDataSet(entries3, "主要接口3");
        LineData data = new LineData();

        lineDataSet1.setColors(Color.rgb(50, 52, 54));
        lineDataSet2.setColors(Color.rgb(150, 52, 54));
        lineDataSet3.setColors(Color.rgb(150, 152, 54));


        switch (button) {
            case "all":
                data.addDataSet(lineDataSet);
                data.addDataSet(lineDataSet1);
                data.addDataSet(lineDataSet2);
                data.addDataSet(lineDataSet3);
                lineChart.setData(data);
                break;
            case "button3":
                data.addDataSet(lineDataSet1);
                lineChart.setData(data);
                break;
            case "button4":
                data.addDataSet(lineDataSet2);
                lineChart.setData(data);
                break;
            case "button5":
                data.addDataSet(lineDataSet3);
                lineChart.setData(data);
                break;
            case "mpchart2":
                data.addDataSet(lineDataSet3);
                lineChart.setData(data);
                break;
        }

        Description description = new Description();
        description.setText("");

        lineChart.setDescription(description);

    }
}
