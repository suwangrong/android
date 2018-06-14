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


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static java.lang.Character.getType;


public class MainActivity extends AppCompatActivity {

    //拿到okhttpclient对象
    private OkHttpClient okHttpClient = new OkHttpClient();
    private String url = "http://101.132.103.35/bishe/control.php";

    private List<LineData> line_data = new ArrayList<>();

    private List<Integer> colors = new ArrayList<>();

    private List<PieData> pie_data = new ArrayList<>();

    private String LOG_TAG = "true";

    private SwipeRefreshLayout swipeRefreshLayout;

    //健康颜色
    private int green = Color.rgb(59, 212, 136);
    //危险颜色
    private int red = Color.rgb(150, 52, 54);
    //正常颜色
    private int white = Color.rgb(218, 244, 231);

    private int black = Color.rgb(0, 0, 0);

    //健康阀值
    private int health = 20;
    //正常阀值
    private int normal = 60;


    private List<Integer> day1 = Arrays.asList(461, 433, 212, 362, 206, 283, 476, 378, 371);



    private String center_string = "2526";

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

    private Handler handler1 = new Handler() {

        @Override
        public void handleMessage(Message msg) {


            super.handleMessage(msg);
            switch (msg.what) {
                case 0x101:
                    mLineChart.setData(line_data.get(0));
                    mLineChart.setBackgroundColor(colors.get(0));

                    mLineChart2.setData(line_data.get(1));
                    mLineChart2.setBackgroundColor(colors.get(1));

                    mLineChart3.setData(line_data.get(2));
                    mLineChart3.setBackgroundColor(colors.get(2));

                    Description description_linchart = new Description();
                    description_linchart.setText(" ");

                    mLineChart.setDescription(description_linchart);
                    mLineChart2.setDescription(description_linchart);
                    mLineChart3.setDescription(description_linchart);


                    mLineChart.setBorderColor(Color.rgb(0, 0, 0));


                    //设置描述
                    Description description = new Description();
                    description.setText("访问量");

                    Description description1 = new Description();
                    description1.setText("磁盘使用情况");


                    mPieChart.setDescription(description);
                    mPieChart2.setDescription(description1);

                    mPieChart.setData(pie_data.get(0));
                    mPieChart.setCenterText(center_string);
                    mPieChart2.setData(pie_data.get(1));

                    line_data = new ArrayList<>();
                    pie_data = new ArrayList<>();
                    colors = new ArrayList<>();
                    break;
            }
        }
    };

    private List<Integer> get_color(float max_y) {
        List<Integer> list = new ArrayList<>();
        if (max_y > normal) {
            list.add(red);
            list.add(white);
            return list;
        } else if (max_y > health) {
            list.add(white);
            list.add(black);
            return list;
        } else {
            list.add(green);
            list.add(black);
            return list;
        }
    }

    private Runnable runnable = new Runnable() {
        public void run() {
            this.update();
            handler.postDelayed(this, 1000 * 12);// 间隔120秒
        }

        void update() {
            //刷新msg的内容
            setdata();
            mLineChart.invalidate();
            mLineChart2.invalidate();
            mLineChart3.invalidate();
            mPieChart.invalidate();
            mPieChart2.invalidate();
        }
    };

    private void setdata() {

        Request.Builder builder = new Request.Builder();
        Request request = builder.url(url).get().build();

        //将request封装为Call
        Call call = okHttpClient.newCall(request);


        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(LOG_TAG, "onFailure:" + e.getMessage());
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e(LOG_TAG, "success");
                String res = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    JSONArray cpu = jsonObject.getJSONArray("cpu");
                    JSONArray bd = jsonObject.getJSONArray("bd");
                    JSONArray mem = jsonObject.getJSONArray("mem");
                    JSONArray disk = jsonObject.getJSONArray("disk");
                    JSONObject visits = jsonObject.getJSONObject("visits");

                    //设置数据
                    List<Entry> line_entries = new ArrayList<>();
                    float max_y = 0;
                    float y = 0;
                    for (int i = 0; i < 10; i++) {
                        y = Float.parseFloat(cpu.get(i).toString());
                        if (y > max_y) {
                            max_y = y;
                        }
                        line_entries.add(new Entry(i, y));
                    }
                    Log.e(LOG_TAG,cpu.get(0).toString());
                    List<Integer> color = get_color(max_y);
                    //一个LineDataSet就是一条线
                    LineDataSet lineDataSet = new LineDataSet(line_entries, "CPU使用情况");
                    lineDataSet.setColor(color.get(1));
                    lineDataSet.setCircleColor(color.get(1));
                    colors.add(color.get(0));
                    line_data.add(new LineData(lineDataSet));


                    List<Entry> line_entries1 = new ArrayList<>();
                    max_y = 0;
                    y = 0;
                    for (int i = 0; i < 10; i++) {
                        y = Float.parseFloat(bd.get(i).toString());
                        if (y > max_y) {
                            max_y = y;
                        }
                        line_entries1.add(new Entry(i, y));
                    }
                    color = get_color(max_y);
                    //一个LineDataSet就是一条线
                    LineDataSet lineDataSet1 = new LineDataSet(line_entries1, "带宽使用情况");
                    lineDataSet1.setColor(color.get(1));
                    lineDataSet1.setCircleColor(color.get(1));
                    colors.add(color.get(0));
                    line_data.add(new LineData(lineDataSet1));


                    List<Entry> line_entries2 = new ArrayList<>();
                    max_y = 0;
                    y = 0;
                    for (int i = 0; i < 10; i++) {
                        y = Float.parseFloat(mem.get(i).toString());
                        if (y > max_y) {
                            max_y = y;
                        }
                        line_entries2.add(new Entry(i, y));
                    }
                    color = get_color(max_y);
                    //一个LineDataSet就是一条线
                    LineDataSet lineDataSet2 = new LineDataSet(line_entries2, "内存使用情况");
                    lineDataSet2.setColor(color.get(1));
                    lineDataSet2.setCircleColor(color.get(1));
                    colors.add(color.get(0));
                    line_data.add(new LineData(lineDataSet2));


                    //饼状图数据添加
                    ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
                    entries.add(new PieEntry(disk.getInt(0), "已使用磁盘"));
                    entries.add(new PieEntry(100-disk.getInt(0), "剩余磁盘"));

                    //接口访问量数据添加
                    ArrayList<PieEntry> entries1 = new ArrayList<PieEntry>();
                    entries1.add(new PieEntry(day1.get(0), "接口1"));
                    entries1.add(new PieEntry(day1.get(1), "接口2"));
                    entries1.add(new PieEntry(day1.get(2), "接口3"));
                    entries1.add(new PieEntry(day1.get(3) + day1.get(4) + day1.get(5) + day1.get(6) , "其他"));



                    //饼状图颜色添加
                    List<Integer> mColors = new ArrayList<Integer>();
                    mColors.add(Color.rgb(216, 77, 71));
                    mColors.add(Color.rgb(83, 56, 63));
                    mColors.add(Color.rgb(27, 85, 47));
                    mColors.add(Color.rgb(247, 85, 47));
                    mColors.add(Color.rgb(47, 85, 47));
                    mColors.add(Color.rgb(2, 85, 47));
                    mColors.add(Color.rgb(47, 5, 47));
                    mColors.add(Color.rgb(247, 85, 47));
                    mColors.add(Color.rgb(27, 85, 4));
                    mColors.add(Color.rgb(247, 15, 47));
                    mColors.add(Color.rgb(47, 15, 47));
                    mColors.add(Color.rgb(7, 85, 147));


                    //设置饼饼状图数据
                    PieDataSet pieDataSet = new PieDataSet(entries, "饼状图");
                    PieDataSet pieDataSet1 = new PieDataSet(entries1, visits.get("all_visit").toString());
                    pieDataSet.setColors(mColors);
                    pieDataSet1.setColors(mColors);
                    //设置数据字体大小
                    pieDataSet.setValueTextSize(10);
                    pieDataSet1.setValueTextSize(10);
                    //设置比例是否可见
                    pieDataSet.setDrawValues(true);
                    pieDataSet.setHighlightEnabled(true);
                    pie_data.add(new PieData(pieDataSet1));
                    pie_data.add(new PieData(pieDataSet));


                    handler1.sendEmptyMessage(0x101);


                } catch (JSONException e) {
                    Log.e(LOG_TAG, "failed");
                    e.printStackTrace();
                }
            }
        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.main_srl);

        handler.postDelayed(runnable, 1000 * 12);

        //折线图
        mLineChart = (LineChart) findViewById(R.id.lineChart);
        mLineChart2 = (LineChart) findViewById(R.id.lineChart2);
        mLineChart3 = (LineChart) findViewById(R.id.lineChart3);
        //饼状图
        mPieChart = ((PieChart) findViewById(R.id.pc_chart));
        mPieChart2 = ((PieChart) findViewById(R.id.pc_chart2));

        //设置自定义的点击事件
        mPieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.e("true", "dianji");
                Intent intent = new Intent(MainActivity.this, history.class);
                startActivity(intent);  //跳转页面
            }

            @Override
            public void onNothingSelected() {

            }
        });

        mPieChart2.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.e("true", "mpiechart2");
                Intent intent = new Intent(MainActivity.this, detail.class);
                intent.putExtra("button","mpchart2");
                startActivity(intent);  //跳转页面
            }

            @Override
            public void onNothingSelected() {

            }
        });




        //设置中间是否可见
        mPieChart.setDrawHoleEnabled(true);
        //设置中间颜色
        mPieChart.setHoleColor(Color.WHITE);
        //设置中间内容
        mPieChart.setCenterText(center_string);
        //设置中间内容大小
        mPieChart.setCenterTextSize(15);

        mPieChart2.setDrawHoleEnabled(false);


        Legend legend = mPieChart.getLegend();
        legend.setEnabled(false);

        Legend legend2 = mPieChart2.getLegend();
        legend2.setEnabled(false);

        //设置是否显示描述
//        mPieChart.setDrawSliceText(false);


        mPieChart.animateXY(1000, 1000);
        mPieChart2.animateXY(1000, 1000);


        //显示边界
        mLineChart.setDrawBorders(true);
        mLineChart2.setDrawBorders(true);
        mLineChart3.setDrawBorders(true);


        setdata();

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
            setdata();
        }
    }


}
