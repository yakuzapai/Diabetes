package com.kandon.caramelwaffle.diabetes.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.kandon.caramelwaffle.diabetes.Adapter.MedicineAdapter;
import com.kandon.caramelwaffle.diabetes.R;

import es.dmoral.toasty.Toasty;

public class MedicineActivity extends AppCompatActivity {
    Toolbar toolbar ;
    FloatingActionButton floatingActionButton;
    Context mContext;
    RecyclerView recyclerview_med;
    MedicineAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);
        mContext = MedicineActivity.this;
        initInstances();
        setInstances();
    }

    private void initInstances() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        floatingActionButton = (FloatingActionButton)findViewById(R.id.floatingActionButton);
        recyclerview_med = (RecyclerView)findViewById(R.id.recyclerview_med);
    }

    private void setInstances() {
        setSupportActionBar(toolbar);
        setTitle("รายการยา");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        adapter = new MedicineAdapter(mContext);
        recyclerview_med.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL));
        recyclerview_med.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerview_med.setAdapter(adapter);
        recyclerview_med.setItemAnimator(new DefaultItemAnimator());

        recyclerview_med.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0)
                    floatingActionButton.hide();
                else if (dy < 0)
                    floatingActionButton.show();
            }
        });


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(mContext)
                        .title("เลือกประเภทของยา")
                        .items(R.array.med)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                               if (which==0){
                                   startActivityForResult(new Intent(mContext,SolidMedActivity.class),1100);
                               }else if (which==1){
                                   startActivityForResult(new Intent(mContext,InjectActivity.class),2200);
                               }
                            }
                        })
                        .show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1100) {
            Intent intent = new Intent(mContext,MedicineActivity.class);
            startActivity(intent);
            finish();

        }else if (requestCode == 2200) {
            Intent intent = new Intent(mContext,MedicineActivity.class);
            startActivity(intent);
            finish();

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
}
