package com.kandon.caramelwaffle.diabetes.Activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.kandon.caramelwaffle.diabetes.R;




public class ExportActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button btn_send;
    private Context mContext = ExportActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);
        initInstances();
        setInstances();
    }

    private void initInstances() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        btn_send = (Button) findViewById(R.id.btn_send);

    }

    private void setInstances() {
        setSupportActionBar(toolbar);
        setTitle("Export");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(mContext,MailActivity.class));
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
