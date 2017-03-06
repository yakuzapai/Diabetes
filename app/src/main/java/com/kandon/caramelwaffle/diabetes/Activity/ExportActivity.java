package com.kandon.caramelwaffle.diabetes.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.kandon.caramelwaffle.diabetes.R;

public class ExportActivity extends AppCompatActivity {

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);
        initInstances();
        setInstances();
    }

    private void initInstances() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);

    }

    private void setInstances() {
        setSupportActionBar(toolbar);
        setTitle("Export");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
