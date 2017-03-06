package com.kandon.caramelwaffle.diabetes.Activity;

import android.app.DatePickerDialog;
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
import android.widget.Toast;

import java.util.Calendar;

import com.kandon.caramelwaffle.diabetes.R;

import es.dmoral.toasty.Toasty;

public class Information extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private Context context;
    Toolbar toolbar;
    EditText editTextName, weight, height, UserMedicalCondition, DangerMedical, UserBP1, UserBP2, bloodSugar;
    Button button, btn_dob;
    TextView dob_tv, age, bmi;
    int yearold;
    RadioGroup GenderRg, RGBlood, RGsmoke, drinkRG;
    RadioButton genderRadioButton, bloodRadioButton, smokeRadioButton, drinkRadioButton;
    float heights = 0, weights = 0, bmis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information);
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

        dob_tv = (TextView) findViewById(R.id.dob_tv);
        age = (TextView) findViewById(R.id.age);
        bmi = (TextView) findViewById(R.id.bmi);


        GenderRg = (RadioGroup) findViewById(R.id.rdGender);
        RGBlood = (RadioGroup) findViewById(R.id.RGBlood);
        RGsmoke = (RadioGroup) findViewById(R.id.RGsmoke);
        drinkRG = (RadioGroup) findViewById(R.id.drinkRG);


    }

    private void setInstances() {
        setSupportActionBar(toolbar);
        setTitle("Information");

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences Issave = getSharedPreferences("information_save", MODE_PRIVATE);
        if (Issave.getBoolean("isSave", false)) {
            setUserInformation();
        }


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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!editTextName.getText().toString().equals("") && !dob_tv.getText().toString().equals("")
                        && !weight.getText().toString().equals("") && !height.getText().toString().equals("")
                        ) {
                    saveInformation();

                    Toasty.success(context, "Information saved", Toast.LENGTH_LONG).show();

                    // check isSave
                    SharedPreferences.Editor editor = getSharedPreferences("information_save", MODE_PRIVATE).edit();
                    editor.putBoolean("isSave", true);
                    editor.apply();

                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toasty.warning(context, "Please input all information", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void setUserInformation() {
        SharedPreferences info = getSharedPreferences("information", MODE_PRIVATE);
        editTextName.setText(info.getString("username", "No information"));
        weight.setText(info.getString("weight", "No information"));
        height.setText(info.getString("height", "No information"));
        UserMedicalCondition.setText(info.getString("UserMedicalCondition", "No information"));
        DangerMedical.setText(info.getString("DangerMedical", "No information"));
        UserBP1.setText(info.getString("userBP1", ""));
        UserBP2.setText(info.getString("userBP2", ""));
        bloodSugar.setText(info.getString("bloodSugar", "No information"));


        dob_tv.setText(info.getString("dob", "No information"));
        age.setText(info.getString("age", "No information"));


    }


    public void saveInformation() {

        genderRadioButton = (RadioButton) findViewById(GenderRg.getCheckedRadioButtonId());
        bloodRadioButton = (RadioButton) findViewById(RGBlood.getCheckedRadioButtonId());
        smokeRadioButton = (RadioButton) findViewById(RGsmoke.getCheckedRadioButtonId());
        drinkRadioButton = (RadioButton) findViewById(drinkRG.getCheckedRadioButtonId());


        SharedPreferences.Editor editor = getSharedPreferences("information", MODE_PRIVATE).edit();
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
        editor.apply();

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
