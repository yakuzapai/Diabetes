package com.kandon.caramelwaffle.diabetes.Activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kandon.caramelwaffle.diabetes.Model.User;
import com.kandon.caramelwaffle.diabetes.R;

import es.dmoral.toasty.Toasty;

public class Information extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {


    DatabaseReference mDatabase;
    DatabaseReference mUserRef;
    FirebaseAuth firebaseAuth;

    public User user;
    private Context context;
    Toolbar toolbar;
    EditText editTextName, weight, height, UserMedicalCondition, DangerMedical, UserBP1, UserBP2, bloodSugar;
    Button button, btn_dob,BtnBreakfast,BtnLanch,BtnDinner;
    TextView dob_tv, age, bmi,tv_break,tv_lanch,tv_dinner;
    int yearold;
    RadioGroup GenderRg, RGBlood, RGsmoke, drinkRG;
    RadioButton genderRadioButton, bloodRadioButton, smokeRadioButton, drinkRadioButton;
    float heights = 0, weights = 0, bmis;

    String h1,h2,h3;
    String m1,m2,m3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        context = Information.this;
        initInstances();
        setInstances();
    }

    private void initInstances() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        editTextName = (EditText) findViewById(R.id.UserName);
        weight = (EditText) findViewById(R.id.weight);
        height = (EditText) findViewById(R.id.height);
        UserMedicalCondition = (EditText) findViewById(R.id.UserMedicalCondition);
        DangerMedical = (EditText) findViewById(R.id.DangerMedical);
        UserBP1 = (EditText) findViewById(R.id.UserBP1);
        UserBP2 = (EditText) findViewById(R.id.UserBP2);
        bloodSugar = (EditText) findViewById(R.id.BloodSugar);

        btn_dob = (Button) findViewById(R.id.btn_dob);
        button = (Button) findViewById(R.id.btn_save);
        BtnBreakfast = (Button) findViewById(R.id.BtnBreakfast);
        BtnLanch = (Button) findViewById(R.id.BtnLanch);
        BtnDinner = (Button) findViewById(R.id.BtnDinner);

        dob_tv = (TextView) findViewById(R.id.dob_tv);
        age = (TextView) findViewById(R.id.age);
        bmi = (TextView) findViewById(R.id.bmi);
        tv_break = (TextView) findViewById(R.id.tv_break);
        tv_lanch = (TextView) findViewById(R.id.tv_lanch);
        tv_dinner = (TextView) findViewById(R.id.tv_dinner);



        GenderRg = (RadioGroup) findViewById(R.id.rdGender);
        RGBlood = (RadioGroup) findViewById(R.id.RGBlood);
        RGsmoke = (RadioGroup) findViewById(R.id.RGsmoke);
        drinkRG = (RadioGroup) findViewById(R.id.drinkRG);


    }

    private void setInstances() {
        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mUserRef = mDatabase.child("user");

        setSupportActionBar(toolbar);
        setTitle("ข้อมูลส่วนตัว");

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences Issave = getSharedPreferences("information_save", MODE_PRIVATE);
        if (Issave.getBoolean("isSave", false)) {
            setUserInformation();
        }


        UserBP1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(UserBP1.getText().toString().length()==3)     //size as per your requirement
                {
                    UserBP2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btn_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //Toast.makeText(context,"Date is : " +dayOfMonth +" / "+ month +" / " +year,Toast.LENGTH_SHORT).show();
                        dob_tv.setText(dayOfMonth + " / " + (month + 1) + " / " + year);
                        Calendar c = Calendar.getInstance();
                        yearold = c.get(Calendar.YEAR) - year;
                        age.setText(yearold + "");
                    }
                }, 2017, 0, 1);
                datePickerDialog.show();
            }
        });

        weight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                bmi();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        height.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                bmi();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        BtnBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        if (selectedHour<10){
                            h1 = "0"+selectedHour;
                        }else {
                            h1 = selectedHour+"";
                        }
                        if (selectedMinute<10){
                            m1 = "0"+selectedMinute;
                        }else {
                            m1 = selectedMinute+"";
                        }
                        tv_break.setText(h1 + ":" + m1);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.show();
            }

        });

        BtnLanch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        if (selectedHour<10){
                            h2 = "0"+selectedHour;
                        }else {
                            h2 = selectedHour+"";
                        }
                        if (selectedMinute<10){
                            m2 = "0"+selectedMinute;
                        }else {
                            m2 = selectedMinute+"";
                        }
                        tv_lanch.setText(h2 + ":" + m2);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.show();
            }
        });


        BtnDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        if (selectedHour<10){
                            h3 = "0"+selectedHour;
                        }else {
                            h3 = selectedHour+"";
                        }
                        if (selectedMinute<10){
                            m3 = "0"+selectedMinute;
                        }else {
                            m3 = selectedMinute+"";
                        }
                        tv_dinner.setText(h3 + ":" + m3);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!editTextName.getText().toString().equals("") && !dob_tv.getText().toString().equals("")
                        && !weight.getText().toString().equals("") && !height.getText().toString().equals("")
                        && !tv_break.getText().toString().equals("")&& !tv_lanch.getText().toString().equals("")
                        && !tv_dinner.getText().toString().equals("")
                        ) {
                    saveInformation();

                    Toasty.success(context, "Information saved", Toast.LENGTH_LONG).show();

                    // check isSave
                    SharedPreferences.Editor editor = getSharedPreferences("information_save", MODE_PRIVATE).edit();
                    editor.putBoolean("isSave", true);
                    editor.apply();

                    // save to firebase
                   // mUserRef.push().setValue(user);
                    mUserRef.child( firebaseAuth.getCurrentUser().getUid()).setValue(user);

                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toasty.error(context, "Please input all information", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void setUserInformation() {
        SharedPreferences info = getSharedPreferences("activity_information", MODE_PRIVATE);
        editTextName.setText(info.getString("username", "ไม่มีข้อมูล"));
        weight.setText(info.getString("weight", "ไม่มีข้อมูล"));
        height.setText(info.getString("height", "ไม่มีข้อมูล"));
        UserMedicalCondition.setText(info.getString("UserMedicalCondition", "ไม่มีข้อมูล"));
        DangerMedical.setText(info.getString("DangerMedical", "ไม่มีข้อมูล"));
        UserBP1.setText(info.getString("userBP1", ""));
        UserBP2.setText(info.getString("userBP2", ""));
        bloodSugar.setText(info.getString("bloodSugar", "ไม่มีข้อมูล"));
        dob_tv.setText(info.getString("dob", "ไม่มีข้อมูล"));
        age.setText(info.getString("age", "ไม่มีข้อมูล"));
        tv_break.setText(info.getString("breakfast", "ไม่มีข้อมูล"));
        tv_lanch.setText(info.getString("lanch","ไม่มีข้อมูล"));
        tv_dinner.setText(info.getString("dinner","ไม่มีข้อมูล"));
    }


    public void saveInformation() {

        genderRadioButton = (RadioButton) findViewById(GenderRg.getCheckedRadioButtonId());
        bloodRadioButton = (RadioButton) findViewById(RGBlood.getCheckedRadioButtonId());
        smokeRadioButton = (RadioButton) findViewById(RGsmoke.getCheckedRadioButtonId());
        drinkRadioButton = (RadioButton) findViewById(drinkRG.getCheckedRadioButtonId());


        SharedPreferences.Editor editor = getSharedPreferences("activity_information", MODE_PRIVATE).edit();
        editor.putString("username", editTextName.getText().toString());
        editor.putString("dob", dob_tv.getText().toString());
        editor.putString("age", age.getText().toString());
        editor.putString("gender", genderRadioButton.getText().toString());
        editor.putString("blood", bloodRadioButton.getText().toString());
        editor.putString("weight", weight.getText().toString());
        editor.putString("height", height.getText().toString());
        editor.putString("bmi", bmi.getText().toString());
        editor.putString("UserMedicalCondition", UserMedicalCondition.getText().toString());
        editor.putString("DangerMedical", DangerMedical.getText().toString());
        editor.putString("smoke", smokeRadioButton.getText().toString());
        editor.putString("drink", drinkRadioButton.getText().toString());
        editor.putString("blood_pressure", UserBP1.getText().toString() + "/" + UserBP2.getText().toString());
        editor.putString("bloodSugar", bloodSugar.getText().toString());
        editor.putString("userBP1", UserBP1.getText().toString());
        editor.putString("userBP2", UserBP2.getText().toString());
        editor.putString("breakfast",tv_break.getText().toString());
        editor.putString("lanch",tv_lanch.getText().toString());
        editor.putString("dinner",tv_dinner.getText().toString());
        editor.apply();

        user = new User(editTextName.getText().toString(),
                dob_tv.getText().toString(),
                age.getText().toString(),
                genderRadioButton.getText().toString(),
                bloodRadioButton.getText().toString(),
                height.getText().toString(),
                weight.getText().toString(),
                bmi.getText().toString(),
                UserMedicalCondition.getText().toString(),
                DangerMedical.getText().toString(),
                smokeRadioButton.getText().toString(),
                drinkRadioButton.getText().toString(),
                UserBP1.getText().toString() + "/" + UserBP2.getText().toString(),
                bloodSugar.getText().toString(),
                UserBP1.getText().toString(),
                UserBP2.getText().toString()
                );

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }

    public void bmi() {
        try {
            weights = Float.parseFloat(weight.getText().toString());
            heights = Float.parseFloat(height.getText().toString());
            heights = heights / 100;

        } catch (NumberFormatException e) {
            weights = 0;
            heights = 0;
        }

        bmis = (weights / (heights * heights));
        bmi.setText(bmis + "");

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
