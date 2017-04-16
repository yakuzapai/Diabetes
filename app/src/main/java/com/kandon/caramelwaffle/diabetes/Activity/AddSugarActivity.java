package com.kandon.caramelwaffle.diabetes.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.kandon.caramelwaffle.diabetes.Model.Sugar;
import com.kandon.caramelwaffle.diabetes.R;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import io.realm.Realm;
import io.realm.RealmResults;

public class AddSugarActivity extends AppCompatActivity {
    private Realm realm;
    private Toolbar toolbar;
    private EditText sugar_input;
    private Button sugar_save;
    private Boolean isNextDay = false;
    private Context mContext = AddSugarActivity.this;
    private FloatingActionButton fab;
    private TextView dayOfWeek;
    private TextView day;
    private TextView month;
    int loop;
    int i;



    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        realm = Realm.getDefaultInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sugar);
        realm = Realm.getDefaultInstance();
        initInstances();
        setInstances();
    }

    private void initInstances() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        sugar_input = (EditText) findViewById(R.id.sugar_input);
        sugar_save = (Button) findViewById(R.id.sugar_save);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        dayOfWeek = (TextView)findViewById(R.id.dayOfWeek);
        day = (TextView)findViewById(R.id.day);
        month = (TextView)findViewById(R.id.month);
    }

    private void setInstances() {
        setSupportActionBar(toolbar);
        setTitle("บันทึกน้ำตาล");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sd = new SimpleDateFormat("EEEE");
        SimpleDateFormat dd = new SimpleDateFormat("dd");
        DateFormat mm = new SimpleDateFormat("MMMM");



        dayOfWeek.setText(sd.format(calendar.getTime()));
        day.setText(dd.format(calendar.getTime()));
        month.setText(mm.format(calendar.getTime()));





        final int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        final int currentMonth = Calendar.getInstance().get(Calendar.MONTH)+1;
        final int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        SharedPreferences info = getSharedPreferences("sugar_last_time", MODE_PRIVATE);
        final int lastday = info.getInt("date", 0);
//        Toasty.info(mContext, "Last day : " + lastday, Toast.LENGTH_LONG).show();
        SharedPreferences info2 = getSharedPreferences("sugar_value", MODE_PRIVATE);
        loop = info2.getInt("loop", 0);
        if (lastday != currentDay) {
            isNextDay = true;
            SharedPreferences.Editor editor = getSharedPreferences("sugar_last_time", MODE_PRIVATE).edit();
            editor.putInt("i", 0);
            editor.putInt("date", currentDay);
            editor.apply();

        }


        sugar_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!sugar_input.getText().toString().equals("")) {
                    new MaterialDialog.Builder(mContext)
                            .title("บันทึกข้อมูล")
                            .content("ต้องการบันทึกข้อมูลหรือไม่")
                            .positiveText("ตกลง")
                            .negativeText("ยกเลิก")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    // get old i values
                                    SharedPreferences eds = getSharedPreferences("sugar_last_time", MODE_PRIVATE);
                                    i = eds.getInt("i", 0);
                                    i++;
                                    // set new i values
                                    SharedPreferences.Editor editor = getSharedPreferences("sugar_last_time", MODE_PRIVATE).edit();
                                    editor.putInt("i", i);
                                    editor.apply();

                                    Calendar c = Calendar.getInstance();
                                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                                    String formattedDate = df.format(c.getTime());
                                    // get new i values
                                    SharedPreferences ed = getSharedPreferences("sugar_last_time", MODE_PRIVATE);
                                    int newi = ed.getInt("i", 1);
                                    Toasty.success(mContext, "บันทึกข้อมูลประจำวันที่ " + formattedDate + "\n ครั้งที่ " + newi, Toast.LENGTH_LONG).show();

                                    // save sugar
                                    if (isNextDay) {
                                        loop++;
                                        //Toasty.info(mContext, loop + "", Toast.LENGTH_SHORT).show();
                                        SharedPreferences.Editor editor_sugar = getSharedPreferences("sugar_value", MODE_PRIVATE).edit();
                                        editor_sugar.putInt("loop", loop);
                                        editor_sugar.apply();
                                        isNextDay = false;
                                    }


                                    realm.executeTransaction(new Realm.Transaction() {
                                        @Override
                                        public void execute(Realm realm) {

                                            Toasty.info(mContext,currentMonth+"",Toast.LENGTH_LONG).show();
                                            Sugar sugar = realm.createObject(Sugar.class);
                                            sugar.setId(getNextKey());
                                            sugar.setDate(currentDay + "/" + currentMonth + "/" + currentYear);
                                            sugar.setSugarValue(Integer.parseInt(sugar_input.getText().toString()));

                                        }
                                    });

                                    SharedPreferences.Editor editor_sugar = getSharedPreferences("sugar_value", MODE_PRIVATE).edit();
                                    editor_sugar.putString("date" + "time" + loop, newi + "");
                                    editor_sugar.putString("date" + loop, currentDay + "/" + currentMonth);
                                    editor_sugar.putString("sugar" + currentDay + "/" + currentMonth + newi, sugar_input.getText().toString());
                                    editor_sugar.apply();
                                    sugar_input.setText("");
                                }
                            })
                            .cancelable(true)
                            .show();

                } else {
                    Toasty.error(mContext, "กรุณากรอกข้อมูล", Toast.LENGTH_SHORT).show();
                }

            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext,HistoryActivity.class));
            }
        });

    }

    public int getNextKey() {
        try {
            return realm.where(Sugar.class).max("id").intValue() + 1;
        } catch (ArrayIndexOutOfBoundsException e) {
            return 0;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }



}
