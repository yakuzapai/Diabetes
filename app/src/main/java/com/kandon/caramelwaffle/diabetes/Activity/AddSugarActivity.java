package com.kandon.caramelwaffle.diabetes.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kandon.caramelwaffle.diabetes.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import es.dmoral.toasty.Toasty;

public class AddSugarActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText sugar_input;
    Button sugar_save;
    Boolean isNextDay = false;
    int i;
    Context mContext = AddSugarActivity.this;
    int loop;

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sugar);
        initInstances();
        setInstances();
    }

    private void initInstances() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        sugar_input = (EditText)findViewById(R.id.sugar_input);
        sugar_save = (Button) findViewById(R.id.sugar_save);
    }

    private void setInstances() {
        setSupportActionBar(toolbar);
        setTitle("บันทึกน้ำตาล");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        final int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        SharedPreferences info = getSharedPreferences("sugar_last_time", MODE_PRIVATE);
        final int lastday = info.getInt("date",0);
        Toasty.info(mContext,"Last day : "+ lastday,Toast.LENGTH_LONG).show();
        SharedPreferences info2 = getSharedPreferences("sugar_value", MODE_PRIVATE);
        loop = info2.getInt("loop",0);
        if (lastday!=currentDay){
            isNextDay = true;
            SharedPreferences.Editor editor = getSharedPreferences("sugar_last_time", MODE_PRIVATE).edit();
            editor.putInt("i",0);
            editor.putInt("date",currentDay);
            editor.apply();

        }


        sugar_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!sugar_input.getText().toString().equals("")) {
                    // get old i values
                    SharedPreferences eds = getSharedPreferences("sugar_last_time", MODE_PRIVATE);
                    i =  eds.getInt("i", 0);
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
                    if (isNextDay){
                        loop++;
                        Toasty.info(mContext,loop+"",Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor_sugar = getSharedPreferences("sugar_value", MODE_PRIVATE).edit();
                        editor_sugar.putInt("loop",loop);
                        editor_sugar.apply();
                        isNextDay = false;
                    }

                    SharedPreferences.Editor editor_sugar = getSharedPreferences("sugar_value", MODE_PRIVATE).edit();
                    editor_sugar.putString("date"+"time"+loop,newi+"");
                    editor_sugar.putString("date"+loop,currentDay+"/"+currentMonth);
                    editor_sugar.putString("sugar"+currentDay+"/"+currentMonth+newi,sugar_input.getText().toString());
                    editor_sugar.apply();
                    sugar_input.setText("");

                }else {
                    Toasty.error(mContext,"กรุณากรอกข้อมูล",Toast.LENGTH_SHORT).show();
                }

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
}
