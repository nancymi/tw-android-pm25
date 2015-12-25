package com.thoughtworks.airdector;

import android.content.Context;

import com.thoughtworks.airdector.data.SharedPrefs;

/**
 * Created by nancymi on 12/24/15.
 */
public class AppRuntime {
    private static SharedPrefs mPrefs;
    private static Context mContext;

    public AppRuntime(Context context) {
        mContext = context;
        mPrefs = new SharedPrefs(mContext);
    }

    public static SharedPrefs getSharedPrefs() {
        return mPrefs;
    }

    public static Context getApplicationContext() {
        return mContext;
    }
}
