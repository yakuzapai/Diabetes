package com.kandon.caramelwaffle.diabetes.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.kandon.caramelwaffle.diabetes.Model.Hospital;
import com.kandon.caramelwaffle.diabetes.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private DatabaseReference mHospitalRef;
    private ValueEventListener valueEventListener;
    private ChildEventListener childEventListener;
    private List<String> mHospitalKey = new ArrayList<>();
    private List<String> mHospitalName = new ArrayList<>();
    private List<String> mHospitalTel = new ArrayList<>();
    private Button button;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        initInstances();
    }

    private void initInstances() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mHospitalRef = mDatabase.child("hospital");


        button = (Button)findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            childEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    GenericTypeIndicator<Map<String, Object>> m = new GenericTypeIndicator<Map<String, Object>>(){};
                    Map<String, Object> map = dataSnapshot.getValue(m);

                    mHospitalKey.add(dataSnapshot.getKey());
                    mHospitalName.add(map.get("name").toString());
                    mHospitalTel.add(map.get("tel").toString());
                    Log.i("chart", "Key: " + mHospitalKey);
                    Log.i("chart", "hospitalname: " + mHospitalName);
                    Log.i("chart", "User Name Value is: " + mHospitalTel);

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    String HospitalKey = dataSnapshot.getKey();
                    int hospitalKeyIndex = mHospitalKey.indexOf(HospitalKey);
                    Log.i("chart",hospitalKeyIndex+"");
                    if (hospitalKeyIndex>-1){
                        GenericTypeIndicator<Map<String, Object>> m = new GenericTypeIndicator<Map<String, Object>>(){};
                        Map<String, Object> map = dataSnapshot.getValue(m);
                        mHospitalName.set(hospitalKeyIndex,map.get("name").toString());
                        mHospitalTel.set(hospitalKeyIndex,map.get("tel").toString());
                        Log.i("chart", "Key: " + mHospitalKey);
                        Log.i("chart", "hospitalname: " + mHospitalName);
                        Log.i("chart", "User Name Value is: " + mHospitalTel);
                    }


                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    String HospitalKey = dataSnapshot.getKey();
                    int hospitalKeyIndex = mHospitalKey.indexOf(HospitalKey);
                    Log.i("chart",hospitalKeyIndex+"");
                    if (hospitalKeyIndex>-1){
                        mHospitalName.remove(hospitalKeyIndex);
                        mHospitalTel.remove(hospitalKeyIndex);
                        Log.i("chart", "Key: " + mHospitalKey);
                        Log.i("chart", "hospitalname: " + mHospitalName);
                        Log.i("chart", "User Name Value is: " + mHospitalTel);
                    }


                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
                mHospitalRef.addChildEventListener(childEventListener);


            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();



    }

    @Override
    protected void onStop() {
        super.onStop();
        if (valueEventListener != null) {
            mHospitalRef.removeEventListener(valueEventListener);
        }
    }
}
