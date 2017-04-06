package com.kandon.caramelwaffle.diabetes.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.kandon.caramelwaffle.diabetes.R;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class SolidMedActivity extends AppCompatActivity {
    Toolbar toolbar;
    MaterialSpinner spinner_name;
    MaterialSpinner spinner_type;
    MaterialSpinner spinner_size;
    ImageView med_img;
    CheckBox checkbox_bb;
    CheckBox checkbox_bl;
    CheckBox checkbox_bd;
    CheckBox checkbox_ab;
    CheckBox checkbox_al;
    CheckBox checkbox_ad;
    Context mContext = SolidMedActivity.this;
    List<String> name = new ArrayList<>();
    int count = 0;
    String medNames;
    String medTypes;
    String medQty = "1 เม็ด";

    Boolean bb = false;
    Boolean bl = false;
    Boolean bd = false;
    Boolean ab = false;
    Boolean al = false;
    Boolean ad = false;
    int positioning;
    Button btn_save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solid_med);
        InitInstances();
        setInstances();
    }

    private void InitInstances() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        med_img = (ImageView) findViewById(R.id.med_img);
        spinner_name = (MaterialSpinner) findViewById(R.id.spinner_name);
        spinner_type = (MaterialSpinner) findViewById(R.id.spinner_type);
        spinner_size = (MaterialSpinner) findViewById(R.id.spinner_size);
        checkbox_bb = (CheckBox) findViewById(R.id.checkbox_bb);
        checkbox_bl = (CheckBox) findViewById(R.id.checkbox_bl);
        checkbox_bd = (CheckBox) findViewById(R.id.checkbox_bd);
        checkbox_ab = (CheckBox) findViewById(R.id.checkbox_ab);
        checkbox_al = (CheckBox) findViewById(R.id.checkbox_al);
        checkbox_ad = (CheckBox) findViewById(R.id.checkbox_ad);
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
                    spinner_type.setItems("Glugon (กลูกอน)", "Glucophage  (กลูโคฟาก์)");
                    medTypes="Glugon (กลูกอน)";
                } else if (position == 2) {
                    med_img.setImageResource(R.drawable.glibenclamide);
                    medTypes = "Daonil";
                    spinner_type.setItems("Daonil");
                } else if (position == 3) {
                    med_img.setImageResource(R.drawable.glimepiride);
                    medTypes = "Amaryl (อะแมริล)";
                    spinner_type.setText("Amaryl (อะแมริล)");
                } else if (position == 4) {
                    med_img.setImageResource(R.drawable.repaglinide);
                    medTypes = "Prandin (แพรนดิน)";
                    spinner_type.setItems("Prandin (แพรนดิน)");
                } else if (position == 5) {
                    med_img.setImageResource(R.drawable.pioglitazones);
                    medTypes = "Actos";
                    spinner_type.setItems("Actos");
                } else if (position == 6) {
                    med_img.setImageResource(R.drawable.acarbose);
                    medTypes = "Glucobay (กลูโคเบย์)";
                    spinner_type.setItems("Glucobay (กลูโคเบย์)");
                } else if (position == 7) {
                    med_img.setImageResource(R.drawable.voglibose);
                    medTypes = "Basen (เบเซน)";
                    spinner_type.setItems("Basen (เบเซน)");
                } else if (position == 8) {
                    med_img.setImageResource(R.drawable.saxagliptin);
                    medTypes = "Onglyza (อองไกลซา)";
                    spinner_type.setItems("Onglyza (อองไกลซา)");
                } else if (position == 9) {
                    med_img.setImageResource(R.drawable.med_mock);
                    medTypes = "Januvia (แจนูเวีย)";
                    spinner_type.setItems("Januvia (แจนูเวีย)");
                } else if (position == 10) {
                    med_img.setImageResource(R.drawable.gliflozin);
                    spinner_type.setEnabled(false);
                }

                spinner_size.setItems("1 เม็ด","2 เม็ด","3 เม็ด");
                count++;
            }
        });
        spinner_type.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                medTypes = item.toString();
            }
        });

        spinner_size.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                medQty = item.toString();
            }
        });

        checkbox_bb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                   bb = true;
                }else {
                    bb = false;
                }
            }
        });

        checkbox_bl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    bl = true;
                }else {
                    bl = false;
                }
            }
        });

        checkbox_bd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    bd = true;
                }else {
                    bd = false;
                }
            }
        });

        checkbox_ab.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    ab = true;
                }else {
                    ab = false;
                }
            }
        });

        checkbox_al.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    al = true;
                }else {
                    al = false;
                }
            }
        });

        checkbox_ad.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    ad = true;
                }else {
                    ad = false;
                }
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if (positioning!=0){
                  SharedPreferences info = getSharedPreferences("medicine", MODE_PRIVATE);
                  int i = info.getInt("i",0);
                  i++;
                  SharedPreferences.Editor editor = getSharedPreferences("medicine", MODE_PRIVATE).edit();
                  editor.putInt("i",i);
                  editor.putString("medNames"+i,medNames);
                  editor.putString("medTypes"+i,medTypes);
                  editor.putString("medKind"+i,"ยาเม็ด");
                  editor.putString("medQty"+i,medQty);
                  editor.putBoolean("bb"+i,bb);
                  editor.putBoolean("bl"+i,bl);
                  editor.putBoolean("bd"+i,bd);
                  editor.putBoolean("ab"+i,ab);
                  editor.putBoolean("al"+i,al);
                  editor.putBoolean("ad"+i,ad);
                  editor.apply();

                  Intent returnIntent = new Intent();
                  setResult(RESULT_OK, returnIntent);
                  finish();

                  Toasty.success(mContext,"บันทึกข้อมูลเรียบร้อย",Toast.LENGTH_SHORT).show();
              }else {
                  Toasty.warning(mContext,"กรุณาเลือกยาให้ครบถ้วน",Toast.LENGTH_SHORT).show();
              }
            }
        });
    }

    private void addMedName() {
        name.add("ชื่อยา");
        name.add("Metformin (Glucophage)");
        name.add("Glibenclamide");
        name.add("Glimepiride");
        name.add("Repaglinide");
        name.add("Pioglitazone");
        name.add("Acarbose");
        name.add("Voglibose");
        name.add("Saxagliptin");
        name.add("Sitagliptin");
        name.add("Gliflozin");
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
