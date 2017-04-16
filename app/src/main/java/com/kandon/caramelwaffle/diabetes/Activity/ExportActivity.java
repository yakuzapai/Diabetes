package com.kandon.caramelwaffle.diabetes.Activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.kandon.caramelwaffle.diabetes.Model.Sugar;
import com.kandon.caramelwaffle.diabetes.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import de.cketti.mailto.EmailIntentBuilder;
import es.dmoral.toasty.Toasty;
import io.realm.Realm;
import io.realm.RealmResults;


public class ExportActivity extends AppCompatActivity {

    String userDisplayName;
    String name;
    String dob;
    String age;
    String gender;
    String blood;
    String weight;
    String height;
    String bmi;
    String UserMedicalCondition;
    String DangerMedical;
    String smoke;
    String drink;
    String blood_pressure;
    String bloodSugar;
    String userInfo;
    String dates;


    private Toolbar toolbar;
    private Button btn_send;
    private Context mContext = ExportActivity.this;
    private EditText date;
    private Realm realm;
    private int i = 0;
    private String str ="";
    private EditText email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);
        realm = Realm.getDefaultInstance();
        initInstances();
        setInstances();
    }

    private void initInstances() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        btn_send = (Button) findViewById(R.id.btn_send);
        email = (EditText)findViewById(R.id.email);
        date =(EditText)findViewById(R.id.date);

    }

    private void setInstances() {
        setSupportActionBar(toolbar);
        setTitle("นำออกข้อมูล");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);


        date.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    str ="";
                    DatePickerDialog datePickerDialog = new DatePickerDialog(
                            mContext, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                            RealmResults<Sugar> listSugar = getDateSugar(year, month+1, dayOfMonth);
                            for (Sugar sugar : listSugar) {
                                i++;
                                dates = sugar.getDate();
                                str += "\n ระดับน้ำตาลครั้งที่ " + i + " : " + sugar.getSugarValue() + " mg/dL";
                            }
                            i = 0;

                            if (!str.equals("")) {
                                date.setText("รายการบันทึกวันที่ " + dayOfMonth + "/" + (month+1) + "/" + year);
                            } else {
                                date.setText("ไม่พบรายการบันทึก");
                            }

                        }
                    }, year, month, day);
                    datePickerDialog.show();
                }

                return true;
            }
        });

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidEmail(email.getText().toString())&&!date.getText().equals("ไม่พบรายการบันทึก")){
                    new MaterialDialog.Builder(mContext)
                            .title("ส่งข้อมูล")
                            .content("ต้องการส่งข้อมูลหรือไม่")
                            .positiveText("ตกลง")
                            .negativeText("ยกเลิก")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    sendEmail();
                                }
                            })
                            .show();
                }else {
                    Toasty.warning(mContext,"กรุณาป้อนข้อมูลให้ถูกต้อง",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void sendEmail() {
        ShareCompat.IntentBuilder.from(ExportActivity.this)
                .setType("message/rfc822")
                .addEmailTo(email.getText().toString())
                .setSubject("Diabetes user's report")
                .setText("รายการบันทึกวันที่ "+dates+"\n"+str + getUserInformation())
                //.setHtmlText(body) //If you are using HTML in your body text
                .setChooserTitle("เลือกแอพลิเคชั่นที่ต้องการส่งข้อมูล")
                .startChooser();
    }

    private String getUserInformation() {
        SharedPreferences info = getSharedPreferences("activity_information", MODE_PRIVATE);
        name=info.getString("username","ไม่มีข้อมูล");
        dob=info.getString("dob","ไม่มีข้อมูล");
        age=info.getString("age","ไม่มีข้อมูล")+" ปี";
        gender=info.getString("gender","ไม่มีข้อมูล");
        blood = info.getString("blood","ไม่มีข้อมูล");
        weight=info.getString("weight","ไม่มีข้อมูล");
        height=info.getString("height","ไม่มีข้อมูล");
        bmi=info.getString("bmi","ไม่มีข้อมูล");
        UserMedicalCondition=info.getString("UserMedicalCondition","ไม่มีข้อมูล");
        DangerMedical=info.getString("DangerMedical","ไม่มีข้อมูล");
        smoke=info.getString("smoke","ไม่มีข้อมูล");
        drink=info.getString("drink","ไม่มีข้อมูล");
        blood_pressure=info.getString("blood_pressure","ไม่มีข้อมูล");
        bloodSugar=info.getString("bloodSugar","ไม่มีข้อมูล");

        if (name.equals("")){
            name="ไม่มีข้อมูล";
        }
        if (dob.equals("")){
            dob="ไม่มีข้อมูล";
        }
        if (age.equals("")){
            age="ไม่มีข้อมูล";
        }
        if (gender.equals("")){
            gender="ไม่มีข้อมูล";
        }
        if (blood.equals("")){
            blood="ไม่มีข้อมูล";
        }
        if (weight.equals("")){
            weight="ไม่มีข้อมูล";
        }
        if (height.equals("")){
            height="ไม่มีข้อมูล";
        }
        if (bmi.equals("")){
            bmi="ไม่มีข้อมูล";
        }
        if (UserMedicalCondition.equals("")){
            UserMedicalCondition="ไม่มีข้อมูล";
        }
        if (DangerMedical.equals("")){
            DangerMedical="ไม่มีข้อมูล";
        }
        if (smoke.equals("")){
            smoke="ไม่มีข้อมูล";
        }
        if (drink.equals("")){
            drink="ไม่มีข้อมูล";
        }
        if (blood_pressure.equals("/")){
            blood_pressure="ไม่มีข้อมูล";
        }
        if (bloodSugar.equals("")){
            bloodSugar="ไม่มีข้อมูล";
        }

        userInfo = "\n\n"+
                "ชื่อ : " + name + "\n"+
                "วันเดือนปีเกิด : " + dob + "\n"+
                "อายุ : " + age + "\n"+
                "เพศ : " + gender + "\n" +
                "หมู่โลหิต : " + blood + "\n" +
                "น้ำหนัก : " + weight + " กิโลกรัม"+"\n" +
                "ส่วนสูง : " + height + " เซนติเมตร"+"\n" +
                "ดัชนีมวลกาย : " + bmi  + "\n" +
                "โรคประจำตัว : " + UserMedicalCondition + "\n" +
                "ยาที่แพ้ : " + DangerMedical  + "\n" +
                "สูบบุหรี่ : " + smoke  + "\n" +
                "ดื่มแอลกอฮอร์ : " + drink  + "\n" +
                "ความดันโลหิต : " + blood_pressure ;

        return userInfo;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public RealmResults<Sugar> getDateSugar(int y, int m, int d) {
        return realm.where(Sugar.class).equalTo("date",d + "/" + m + "/" + y).findAll();
    }

    @Override
    protected void onResume() {
        super.onResume();
        realm = Realm.getDefaultInstance();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
}
