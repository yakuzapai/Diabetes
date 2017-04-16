package com.kandon.caramelwaffle.diabetes.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


import com.afollestad.materialdialogs.MaterialDialog;
import com.kandon.caramelwaffle.diabetes.Model.Sugar;
import com.kandon.caramelwaffle.diabetes.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class HistoryActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Realm realm;
    private Context mContext = HistoryActivity.this;
    private String str = "";
    private int i=0;
    private Button graph;
    private MaterialCalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        realm = Realm.getDefaultInstance();
        initInstances();
        setInstances();
    }

    private void initInstances() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        graph = (Button)findViewById(R.id.graph);
        calendarView = (MaterialCalendarView)findViewById(R.id.calendarView);
    }

    private void setInstances() {
        setSupportActionBar(toolbar);
        setTitle("บันทึกย้อนหลัง");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        calendarView.setSelectedDate(c.getTime());

        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                str = "";
                RealmResults<Sugar> listSugar = getDateSugar(date.getYear(),date.getMonth()+1,date.getDay());
                for (Sugar sugar : listSugar) {
                    i++;
                    str += "\n ระดับน้ำตาลครั้งที่ "+i+" : "+sugar.getSugarValue()+" mg/dL";
                }

                if (str.equals("")){
                    str = "ไม่พบข้อมูลการบันทึก";
                }


                new MaterialDialog.Builder(mContext)
                        .title("ข้อมูลประจำวันที่ " + date.getYear()+"/"+(date.getMonth()+1)+"/"+date.getDay())
                        .content(str)
                        .cancelable(true)
                        .positiveText("ตกลง")
                        .show();

                i = 0;
            }

        });



        graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(mContext,GraphActivity.class));
            }
        });
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public RealmResults<Sugar> getDateSugar(int y, int m, int d) {
        return realm.where(Sugar.class).equalTo("date",d + "/" + m + "/" + y).findAll();
    }

    @Override
    protected void onResume() {
        super.onResume();
        realm = Realm.getDefaultInstance();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
