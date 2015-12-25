package com.thoughtworks.airdector.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.thoughtworks.airdector.AirdectorActivity;
import com.thoughtworks.airdector.AppRuntime;
import com.thoughtworks.airdector.model.Air;
import com.thoughtworks.airdector.utils.Utils;

/**
 * Created by nancymi on 12/23/15.
 */
public class SharedPrefs {

    public static final String PM_PREF_NAME = "pm_pref";

    public static final String PREF_AIR = "pref_air";

    public static final String PREF_DIAL = "pref_dial";

    public static final String PREF_BG_COLOR = "pref_bg_color";

    private static final int DEFAULT_DIAL = 0;

    private static final long DEFAULT_BG_COLOR = 0x03A9F4;  //Light Blue

    private final Context mContext;

    private final SharedPreferences pmDataPref;
    private final SharedPreferences.Editor editor;

    private static final String TAG = "SharedPrefs";

    private final SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener =
            new SharedPreferences.OnSharedPreferenceChangeListener() {

                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                    Log.d(TAG, "onSharedPreferenceChanged");
                    switch (s) {
                        case SharedPrefs.PREF_AIR: AirdectorActivity.updateView(); break;
                        case SharedPrefs.PREF_BG_COLOR: AirdectorActivity.updateBackground(); break;
                        case SharedPrefs.PREF_DIAL: AirdectorActivity.updateDial(); break;
                        default: break;
                    }
                }
            };

    public SharedPrefs(Context context) {
        pmDataPref = context.getSharedPreferences(PM_PREF_NAME, Context.MODE_PRIVATE);
        editor = pmDataPref.edit();
        pmDataPref.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
        editor.apply();
        mContext = context;
    }

    public void setAir(Air air) {
        editor.putString(PREF_AIR, Utils.objectToString(air));
        editor.apply();
    }

    public Air getAir() {
        String airStr = pmDataPref.getString(PREF_AIR, "");
        Air air = (Air) Utils.stringToObject(airStr);
        return air;
    }

    public void setPrefDial(int dial) {
        editor.putInt(PREF_DIAL, dial);
        editor.apply();
    }

    public int getPrefDial() {
        int dial = pmDataPref.getInt(PREF_DIAL, DEFAULT_DIAL);
        return dial;
    }

    public void setPrefBgColor(long bgColor) {
        editor.putLong(PREF_BG_COLOR, bgColor);
        editor.apply();
    }

    public long getPrefBgColor() {
        long bgColor = pmDataPref.getLong(PREF_BG_COLOR, DEFAULT_BG_COLOR);
        return bgColor;
    }
}
