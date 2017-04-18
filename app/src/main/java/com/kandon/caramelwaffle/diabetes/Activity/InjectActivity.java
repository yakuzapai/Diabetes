package com.kandon.caramelwaffle.diabetes.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.kandon.caramelwaffle.diabetes.Model.Medicine;
import com.kandon.caramelwaffle.diabetes.R;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import io.realm.Realm;

public class InjectActivity extends AppCompatActivity {
    Toolbar toolbar;
    MaterialSpinner spinner_name;
    MaterialSpinner spinner_type;
    MaterialSpinner spinner_time;
    EditText med_size;
    ImageView med_img;
    Context mContext = InjectActivity.this;
    List<String> name = new ArrayList<>();
    int count = 0;
    String qty ;
    String medNames;
    String medTypes;
    int positioning;
    String times ="ก่อนอาหารเช้า";
    Button btn_save;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inject);
        realm = Realm.getDefaultInstance();
        InitInstances();
        setInstances();
    }

    private void InitInstances() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        med_img = (ImageView) findViewById(R.id.med_img);
        spinner_name = (MaterialSpinner) findViewById(R.id.spinner_name);
        spinner_type = (MaterialSpinner) findViewById(R.id.spinner_type);
        spinner_time = (MaterialSpinner) findViewById(R.id.spinner_time);
        med_size = (EditText)findViewById(R.id.med_size);
        btn_save = (Button) findViewById(R.id.btn_save);
    }

    private void setInstances() {
        setSupportActionBar(toolbar);
        setTitle("ยาประเภทฉีด");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        addMedName();
        spinner_name.setItems(name);
        spinner_name.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                medNames = item.toString();
                positioning = position;
                if (position != 10) {
                    spinner_type.setEnabled(true);
                }

                if (position == 1) {
                    med_img.setImageResource(R.drawable.byetta);
                    spinner_type.setItems("Byetta (ไบเอตตา)");
                    medTypes="Byetta (ไบเอตตา)";
                } else if (position == 2) {
                    med_img.setImageResource(R.drawable.lira);
                    medTypes = "Victoza (วิกโตซา)";
                    spinner_type.setItems("Victoza (วิคโทซา)");
                }
                count++;
            }
        });
        spinner_type.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                medTypes = item.toString();
            }
        });

        spinner_time.setItems("ก่อนอาหารเช้า","ก่อนอาหารกลางวัน","ก่อนอาหารเย็น","หลังอาหารเช้า","หลังอาหารกลางวัน","หลังอาหารเย็น","ก่อนนอน");
        spinner_time.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                times = item.toString();
            }
        });



        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (positioning!=0&&!times.equals("")&&!med_size.getText().toString().equals("")) {
                    qty = med_size.getText().toString() + " เปอร์เซ็น/มิลลิลิตร";

                    new MaterialDialog.Builder(mContext)
                            .title("บันทึกข้อมูล")
                            .content("ต้องการบันทึกข้อมูลหรือไม่")
                            .positiveText("ตกลง")
                            .negativeText("ยกเลิก")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                    realm.executeTransaction(new Realm.Transaction() {
                                        @Override
                                        public void execute(Realm realm) {

                                            Medicine medicine = realm.createObject(Medicine.class);
                                            medicine.setId(getNextKey());
                                            medicine.setMedName(medNames);
                                            medicine.setMedType(medTypes);
                                            medicine.setMedKind("ยาฉีด");
                                            medicine.setMedQty(qty);
                                            medicine.setMedTime(times);


                                        }
                                    });


                                    SharedPreferences info = getSharedPreferences("medicine", MODE_PRIVATE);
                                    int i = info.getInt("i",0);
                                    i++;
                                    SharedPreferences.Editor editor = getSharedPreferences("medicine", MODE_PRIVATE).edit();
                                    editor.putInt("i",i);
                                    editor.putString("medNames"+i,medNames);
                                    editor.putString("medTypes"+i,medTypes);
                                    editor.putString("medKind"+i,"ยาฉีด");
                                    editor.putString("medQty" + i, qty);

                                    editor.putString("time" + i, times);
                                    editor.apply();

                                    Intent returnIntent = new Intent();
                                    setResult(RESULT_OK, returnIntent);
                                    finish();

                                    Toasty.success(mContext,"บันทึกข้อมูลเรียบร้อย", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .cancelable(true)
                            .show();

                }else {
                    Toasty.warning(mContext,"กรุณาเลือกยาให้ครบถ้วน",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addMedName() {
        name.add("ชื่อยา");
        name.add("อีเซนาไทด์(Exenatide)");
        name.add("ลิรากลูไทด์(Liraglutide)");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public int getNextKey() {
        try {
            return realm.where(Medicine.class).max("id").intValue() + 1;
        } catch (ArrayIndexOutOfBoundsException e) {
            return 0;
        }
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
}
