package com.kandon.caramelwaffle.diabetes.Activity;

import android.content.Context;
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
    EditText suger_input;
    Button sugar_save;
    int i = 0 ;
    Context mContext = AddSugarActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sugar);
        initInstances();
        setInstances();
    }

    private void initInstances() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        suger_input = (EditText)findViewById(R.id.suger_input);
        sugar_save = (Button) findViewById(R.id.sugar_save);
    }

    private void setInstances() {
        setSupportActionBar(toolbar);
        setTitle("บันทึกน้ำตาล");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sugar_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                String formattedDate = df.format(c.getTime());
                suger_input.setText("");
                Toasty.success(mContext,"บันทึกข้อมูลประจำวันที่ "+ formattedDate+"\n ครั้งที่ "+i, Toast.LENGTH_LONG).show();
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
