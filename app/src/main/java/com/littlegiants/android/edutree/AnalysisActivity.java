package com.littlegiants.android.edutree;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.net.CookieHandler;
import java.util.ArrayList;

import me.anwarshahriar.calligrapher.Calligrapher;

public class AnalysisActivity extends AppCompatActivity {

    BarChart mbarChart;
    PieChart mpieChart;
    String[] mSubjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "truenolt.otf", true);

        Toolbar toolbar = findViewById(R.id.ana_tool);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        calligrapher.setFont(toolbar, "truenolt.otf");

        mbarChart = findViewById(R.id.ana_barChart);
        setBarData();

        mpieChart = findViewById(R.id.ana_pieChart);
        calligrapher.setFont(mpieChart, "truenolt.otf");
        mSubjects = new String[]{"Biology", "Mathematics", "Physics", "Chemistry"};

        setData(4, 100);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mpieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
                mbarChart.animateXY(1400, 1400, Easing.EasingOption.EaseInOutSine, Easing.EasingOption.EaseInOutBack);
            }
        }, 20);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.done_avatar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.done_pro){

        }
        return super.onOptionsItemSelected(item);
    }

    private void setBarData(){

        ArrayList<BarEntry> BarEntry = new ArrayList<>();

        BarEntry.add(new BarEntry(2f, 0));
        BarEntry.add(new BarEntry(4f, 1));
        BarEntry.add(new BarEntry(6f, 2));
        BarEntry.add(new BarEntry(8f, 3));
        BarEntry.add(new BarEntry(7f, 4));
        BarEntry.add(new BarEntry(3f, 5));

        BarDataSet dataSet = new BarDataSet(BarEntry, "Subjects");

        ArrayList<String> labels = new ArrayList<>();

        labels.add("Mathematics");
        labels.add("Physics");
        labels.add("Chemistry");
        labels.add("Biology");
        labels.add("English");
        labels.add("Computer Science/Physical Education");

        BarData data = new BarData(dataSet);
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        mbarChart.setData(data);

    }

    private void setData(int count, int range) {

        ArrayList<PieEntry> values = new ArrayList<>();

        for (int i = 0; i < count; i++) {

            float val = (float) ((Math.random() * range) + range / 5);
            values.add(new PieEntry(val, mSubjects[i]));

        }

        PieDataSet dataSet = new PieDataSet(values, "Subjects");
        dataSet.setSelectionShift(4f);
        dataSet.setSliceSpace(2f);
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(15f);
        data.setValueTextColor(Color.WHITE);

        mpieChart.setUsePercentValues(true);
        mpieChart.setHoleColor(Color.parseColor("#FFFAFAFA"));

        mpieChart.setData(data);
        mpieChart.invalidate();

    }
}

