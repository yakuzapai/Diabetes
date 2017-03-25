package com.kandon.caramelwaffle.diabetes.Activity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kandon.caramelwaffle.diabetes.R;

public class LoadingActivity extends AppCompatActivity {
    ImageView imageView;
    Context context;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        initInstances();
        setInstances();
        changeScreen();
    }



    private void initInstances() {
        imageView = (ImageView)findViewById(R.id.logo);
    }

    private void setInstances() {
        context = LoadingActivity.this;


    }

    private void changeScreen(){

        new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                SharedPreferences prefs = getSharedPreferences("LOGINTOKEN", MODE_PRIVATE);
                Boolean isLogin = prefs.getBoolean("login", false);

                SharedPreferences prefs2 = getSharedPreferences("information_save", MODE_PRIVATE);
                Boolean isSave = prefs2.getBoolean("isSave", false);
                if (isLogin && isSave) {
                    Intent intent = new Intent(context,MainActivity.class);
                    startActivity(intent);
                    finish();

                } else
                if (isLogin==true && isSave == false){
                    Intent intent = new Intent(context,Information.class);
                    startActivity(intent);
                    finish();
                }else
                    if (!isLogin){
                        Intent intent = new Intent(context,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
            }
        }.start();
    }
}
