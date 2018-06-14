package com.example.changba160.mptest;


import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2018/04/10 .
 */

public class PieFragment extends android.support.v4.app.Fragment {
    private static final String DATA_KEY = "piefragment_data_key";
    private MonthBean mData;
    private PieChart mChart;
    private List<Integer> all = Arrays.asList(3378, 3184, 2826, 3122, 3092, 2761, 3283);
    private int index = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mData = arguments.getParcelable(DATA_KEY);
        }

    }

    public static PieFragment newInstance(MonthBean data) {

        Bundle args = new Bundle();
        args.putParcelable(DATA_KEY, data);
        PieFragment fragment = new PieFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_pie, null);
        mChart = ((PieChart) inflate.findViewById(R.id.pc_chart));

        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.RED);
        mChart.setCenterText(all.get(index).toString());
        index ++;
        mChart.setCenterTextSize(30);
        //饼状账单表添加文字
        initView();
        return inflate;
    }

    private void initView() {
        setData();

    }

    private void setData() {

        List<PieEntry> entrys = new ArrayList<>();
        for (int i = 0; i < mData.obj.size(); i++) {
            MonthBean.PieBean pieBean = mData.obj.get(i);
            PieEntry pieEntry = new PieEntry(pieBean.value,pieBean.title);
            entrys.add(pieEntry);
        }
        PieDataSet dataSet = new PieDataSet(entrys,"");
        List<Integer> mColors = new ArrayList<Integer>();
//        mColors.add(Color.rgb(216,77,71));
//        mColors.add(Color.rgb(183,56,63));
//        mColors.add(Color.rgb(247,85,47));
//        mColors.add(Color.rgb(247,185,47));
//        mColors.add(Color.BLUE);
//        mColors.add(Color.GRAY);

        mColors.add(Color.rgb(255,182,193));
        mColors.add(Color.rgb(	219,112,147));
        mColors.add(Color.rgb(100,149,237));
        mColors.add(Color.rgb(	176,196,222));
        mColors.add(Color.rgb(119,136,153));
        mColors.add(Color.rgb(135,206,250));
        mColors.add(Color.rgb(0,191,255));
        mColors.add(Color.rgb(0,128,128));
        mColors.add(Color.rgb(27, 85, 4));
        mColors.add(Color.rgb(247, 15, 47));
        mColors.add(Color.rgb(47, 15, 47));
        mColors.add(Color.rgb(7, 85, 147));

//        dataSet.setValueTextColors(mColors);
        Description description = new Description();
        description.setText("访问量");

        dataSet.setValueTextSize(20);
        dataSet.setColors(mColors);
        PieData pieData = new PieData(dataSet);
        mChart.setData(pieData);
        mChart.setDescription(description);
    }
}
