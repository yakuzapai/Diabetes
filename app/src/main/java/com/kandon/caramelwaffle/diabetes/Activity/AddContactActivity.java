package com.kandon.caramelwaffle.diabetes.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.kandon.caramelwaffle.diabetes.Model.Contact;
import com.kandon.caramelwaffle.diabetes.Model.Sugar;
import com.kandon.caramelwaffle.diabetes.R;

import es.dmoral.toasty.Toasty;
import io.realm.Realm;

public class AddContactActivity extends AppCompatActivity {
    Toolbar toolbar;
    FloatingActionButton floatingActionButton;
    EditText name;
    EditText number;
    EditText about;
    private Realm realm;
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        realm = Realm.getDefaultInstance();
        innitInstances();
    }

    private void innitInstances() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("เพิ่มรายชื่อผู้ติดต่อ");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = (EditText)findViewById(R.id.name);
        number = (EditText)findViewById(R.id.number);
        about = (EditText)findViewById(R.id.about);

        floatingActionButton = (FloatingActionButton)findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!name.getText().toString().equals("")&&!number.getText().toString().equals("")){


                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {

                            Contact contact = realm.createObject(Contact.class);
                            contact.setId(getNextKey());
                            contact.setName(name.getText().toString());
                            contact.setAbout(about.getText().toString());
                            contact.setNumber(number.getText().toString());

                        }
                    });

                    SharedPreferences info = getSharedPreferences("contact", MODE_PRIVATE);
                    i = info.getInt("i",0);
                    i++;

                    SharedPreferences.Editor editor = getSharedPreferences("contact", MODE_PRIVATE).edit();
                    editor.putInt("i",i);
                    editor.putString("name"+i,name.getText().toString());
                    editor.putString("number"+i,number.getText().toString());
                    if (!about.getText().toString().equals("")){
                        editor.putString("about"+i,about.getText().toString());
                    }
                    editor.apply();
                    Toasty.success(AddContactActivity.this,"บันทึกข้อมูลสำเร็จ",Toast.LENGTH_SHORT).show();
                    Intent returnIntent = new Intent();
                    setResult(RESULT_OK, returnIntent);
                    finish();

//                    Intent intent = new Intent(AddContactActivity.this,MainActivity.class);
//                    startActivity(intent);
//                    finish();
                }else {
                    Toasty.warning(AddContactActivity.this,"กรุณาป้อนข้อมูลให้ครบถ้วน", Toast.LENGTH_SHORT).show();
                }
            }
        });


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
            return realm.where(Contact.class).max("id").intValue() + 1;
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
