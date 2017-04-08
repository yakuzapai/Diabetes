package com.kandon.caramelwaffle.diabetes;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.kandon.caramelwaffle.diabetes.Manager.Contextor;

import java.util.Calendar;

import es.dmoral.toasty.Toasty;

/**
 * Created by CaramelWaffle on 14/2/2560.
 */

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Contextor.getInstance().init(getApplicationContext());

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
