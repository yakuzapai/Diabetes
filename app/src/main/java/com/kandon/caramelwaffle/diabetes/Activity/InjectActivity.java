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
import android.widget.ImageView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.kandon.caramelwaffle.diabetes.R;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class InjectActivity extends AppCompatActivity {
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
    Context mContext = InjectActivity.this;
    List<String> name = new ArrayList<>();
    int count = 0;
    String medNames;
    String medTypes;
    String medQty = "500 ml";
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
        setContentView(R.layout.activity_inject);
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
                    medTypes = "Victoza ()";
                    spinner_type.setItems("Victoza ()");
                }
                spinner_size.setItems("500 ml","1000 ml");
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
                    editor.putString("medKind"+i,"ยาฉีด");
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

                    Toasty.success(mContext,"บันทึกข้อมูลเรียบร้อย", Toast.LENGTH_SHORT).show();
                }else {
                    Toasty.warning(mContext,"กรุณาเลือกยาให้ครบถ้วน",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addMedName() {
        name.add("ชื่อยา");
        name.add("Exenatide");
        name.add("Liraglutide");
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
