package com.kandon.caramelwaffle.diabetes.Activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.AxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.kandon.caramelwaffle.diabetes.Model.Sugar;
import com.kandon.caramelwaffle.diabetes.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import es.dmoral.toasty.Toasty;
import io.realm.Realm;
import io.realm.RealmResults;

public class GraphActivity extends AppCompatActivity {
    private Context mContext = GraphActivity.this;
    private BarChart chart;
    private Realm realm;
    ArrayList<String> sugar_date = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        realm = Realm.getDefaultInstance();
        initInstances();
        setInstances();
    }

    private void initInstances() {
        chart = (BarChart) findViewById(R.id.chart);
    }

    private void setInstances() {



        final ArrayList<BarEntry> entries = new ArrayList<>();
        final RealmResults<Sugar> listSugar = getDateSugar();
        for (Sugar sugar : listSugar) {
            sugar_date.add(sugar.getDate());
            entries.add(new BarEntry(sugar.getId(),sugar.getSugarValue()));
        }

        BarDataSet dataset = new BarDataSet(entries, "#");
        dataset.setValueTextSize(8);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS); // set the color

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(dataset);


        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.getXAxis().setLabelRotationAngle(80);

        final XAxis xAxis = chart.getXAxis();
        xAxis.setTextSize(12);
        xAxis.setCenterAxisLabels(true);
        xAxis.setValueFormatter(new AxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if (value < 0 || value >= listSugar.size()) {
                    return "";
                }
                return listSugar.get((int) value).getDate();
            }

            @Override
            public int getDecimalDigits() {
                return 0;
            }
        });


        YAxis RightAxis = chart.getAxisRight();
        RightAxis.setEnabled(false);

        BarData data = new BarData(dataSets);
        chart.setData(data);
        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Toasty.info(mContext,"วันที่ " +sugar_date.get((int)e.getX()-1) +" ระดับน้ำตาล"+e.getY()+"",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });
        chart.animateXY(1000,1200);
        chart.setDescription("ระดับน้ำตาล mg/dL");

    }


    public RealmResults<Sugar> getDateSugar() {
//
//        Calendar startc = Calendar.getInstance();
//        Calendar endc = Calendar.getInstance();
//        SimpleDateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
//        startc.add(Calendar.DATE, -90);
//
//        Toasty.info(mContext,df.format(startc.getTime())+" - "+df.format(endc.getTime()), Toast.LENGTH_LONG).show();

        return realm.where(Sugar.class).findAll();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    public void onStop() {
        super.onStop();
        realm.close();
    }
}
