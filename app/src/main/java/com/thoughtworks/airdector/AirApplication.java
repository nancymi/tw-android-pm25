package com.thoughtworks.airdector;

import android.app.Application;

/**
 * Created by nancymi on 12/24/15.
 */
public class AirApplication extends Application {
    AppRuntime appRuntime;

    @Override
    public void onCreate() {
        appRuntime = new AppRuntime(getApplicationContext());
        super.onCreate();
    }
}
