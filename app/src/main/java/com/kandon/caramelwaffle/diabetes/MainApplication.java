package com.kandon.caramelwaffle.diabetes;

import android.app.Application;

import com.kandon.caramelwaffle.diabetes.Manager.Contextor;

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
