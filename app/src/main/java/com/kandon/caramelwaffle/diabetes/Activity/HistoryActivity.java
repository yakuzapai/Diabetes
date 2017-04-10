package com.kandon.caramelwaffle.diabetes.Activity;

import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.kandon.caramelwaffle.diabetes.Model.Sugar;
import com.kandon.caramelwaffle.diabetes.R;

import es.dmoral.toasty.Toasty;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class HistoryActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private CalendarView calendarView;
    private Realm realm;
    private Context mContext = HistoryActivity.this;
    private String str = "";
    private int i=0;
    private Button graph;

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
        calendarView = (CalendarView)findViewById(R.id.calendarView);
        graph = (Button)findViewById(R.id.graph);
    }

    private void setInstances() {
        setSupportActionBar(toolbar);
        setTitle("บันทึกย้อนหลัง");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                str = "";
                RealmResults<Sugar> listSugar = getDateSugar(year,month,dayOfMonth);
                for (Sugar sugar : listSugar) {
                    i++;
                    str += "\n ระดับน้ำตาลครั้งที่ "+i+" : "+sugar.getSugarValue()+" mg/dl";
                }

                if (str.equals("")){
                    str = "ไม่พบข้อมูลการบันทึก";
                }


                new MaterialDialog.Builder(mContext)
                        .title("ข้อมูลประจำวันที่ " + dayOfMonth+"/"+month+"/"+year)
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
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
