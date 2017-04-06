package com.kandon.caramelwaffle.diabetes.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.kandon.caramelwaffle.diabetes.Adapter.HospitalAdapter;
import com.kandon.caramelwaffle.diabetes.Model.Hospital;
import com.kandon.caramelwaffle.diabetes.Model.HospitalData;
import com.kandon.caramelwaffle.diabetes.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private HospitalAdapter mAdapter;
    private DatabaseReference mDatabase;
    private DatabaseReference mHospitalRef;
    private ValueEventListener valueEventListener;
    private ChildEventListener childEventListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        initInstances();
    }

    private void initInstances() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("เบอร์ติดต่อโรงพยาบาล");

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mHospitalRef = mDatabase.child("hospital");

        mAdapter = new HospitalAdapter(ContactActivity.this,mHospitalRef);

        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(ContactActivity.this, DividerItemDecoration.HORIZONTAL));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ContactActivity.this));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


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
    protected void onStart() {
        super.onStart();



    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
