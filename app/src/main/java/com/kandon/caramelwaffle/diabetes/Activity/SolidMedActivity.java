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
import android.widget.Spinner;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.kandon.caramelwaffle.diabetes.Model.Contact;
import com.kandon.caramelwaffle.diabetes.Model.Medicine;
import com.kandon.caramelwaffle.diabetes.R;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import io.realm.Realm;

public class SolidMedActivity extends AppCompatActivity {
    Toolbar toolbar;
    private Realm realm;
    MaterialSpinner spinner_name;
    MaterialSpinner spinner_type;
    MaterialSpinner spinner_time;
    EditText med_size;
    ImageView med_img;
    Context mContext = SolidMedActivity.this;
    List<String> name = new ArrayList<>();
    int count = 0;
    String medNames;
    String medTypes;
    String times ="ก่อนอาหารเช้า";
    String qty;
    int positioning;
    Button btn_save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solid_med);
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
        setTitle("ยาประเภทเม็ด");
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
                if (position == 0) {
                    med_img.setImageResource(R.drawable.med_mock);
                }
                if (position == 1) {
                    med_img.setImageResource(R.drawable.metformin);
                    spinner_type.setItems("กลูกอน(Glugon)", "กลูโคฟาก์(Glucophage )");
                    medTypes = "กลูกอน(Glugon)";
                } else if (position == 2) {
                    med_img.setImageResource(R.drawable.glibenclamide);
                    medTypes = "ดาโอนิล(Daonil)";
                    spinner_type.setItems("ดาโอนิล(Daonil)");
                } else if (position == 3) {
                    med_img.setImageResource(R.drawable.glimepiride);
                    medTypes = "อะแมริล(Amaryl)";
                    spinner_type.setText("อะแมริล(Amaryl)");
                } else if (position == 4) {
                    med_img.setImageResource(R.drawable.repaglinide);
                    medTypes = "แพรนดิน(Prandin)";
                    spinner_type.setItems("แพรนดิน(Prandin)");
                } else if (position == 5) {
                    med_img.setImageResource(R.drawable.pioglitazones);
                    medTypes = "แอคโตส(Actos)";
                    spinner_type.setItems("แอคโตส(Actos)");
                } else if (position == 6) {
                    med_img.setImageResource(R.drawable.acarbose);
                    medTypes = "กลูโคเบย์(Glucobay)";
                    spinner_type.setItems("กลูโคเบย์(Glucobay)");
                } else if (position == 7) {
                    med_img.setImageResource(R.drawable.voglibose);
                    medTypes = "เบเซน(Basen)";
                    spinner_type.setItems("เบเซน(Basen)");
                } else if (position == 8) {
                    med_img.setImageResource(R.drawable.saxagliptin);
                    medTypes = "อองไกลซา(Onglyza)";
                    spinner_type.setItems("อองไกลซา(Onglyza)");
                } else if (position == 9) {
                    med_img.setImageResource(R.drawable.saxa);
                    medTypes = "แจนูเวีย(Januvia)";
                    spinner_type.setItems("แจนูเวีย(Januvia)");
                } else if (position == 10) {
                    med_img.setImageResource(R.drawable.gliflozin);
                    spinner_type.setEnabled(false);
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
                    qty = med_size.getText().toString() + " เม็ด";
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
                                            medicine.setMedKind("ยาเม็ด");
                                            medicine.setMedQty(qty);
                                            medicine.setMedTime(times);


                                        }
                                    });


                                    SharedPreferences info = getSharedPreferences("medicine", MODE_PRIVATE);
                                    int i = info.getInt("i", 0);
                                    i++;
                                    SharedPreferences.Editor editor = getSharedPreferences("medicine", MODE_PRIVATE).edit();
                                    editor.putInt("i", i);
                                    editor.putString("medNames" + i, medNames);
                                    editor.putString("medTypes" + i, medTypes);
                                    editor.putString("medKind" + i, "ยาเม็ด");
                                    editor.putString("medQty" + i, qty);
                                    editor.putString("time" + i, times);
                                    editor.apply();

                                    Intent returnIntent = new Intent();
                                    setResult(RESULT_OK, returnIntent);
                                    finish();

                                    Toasty.success(mContext, "บันทึกข้อมูลเรียบร้อย", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .cancelable(true)
                            .show();
                } else {
                    Toasty.warning(mContext, "กรุณาเลือกยาให้ครบถ้วน", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addMedName() {
        name.add("ชื่อยา");
        name.add("เมทฟอร์มิน(Metformin)");
        name.add("ไกลเบนคลาไมด์(Glibenclamide)");
        name.add("ไกลเมพิไรด์(Glimepiride)");
        name.add("รีพาไกลไนด์(Repaglinide)");
        name.add("ไพโอกลิตาโซน(Repaglinide)");
        name.add("อะคาร์โบส(Acarbose)");
        name.add("โวกลิโบส(Voglibose)");
        name.add("ซาซ่ากลิปติน(Saxagliptin)");
        name.add("ซิตากลิปติน(Sitagliptin)");
        name.add("ดาพากลิโฟลซิน(Dapagliflozin)");
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
