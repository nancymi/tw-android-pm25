package com.thoughtworks.airdector;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.thoughtworks.airdector.data.SharedPrefs;
import com.thoughtworks.airdector.model.Air;
import com.thoughtworks.airdector.utils.DialogUtils;
import com.thoughtworks.airdector.utils.Utils;
import com.thoughtworks.airdector.view.AirDialView;

import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;

public class AirdectorActivity extends AppCompatActivity implements
        View.OnClickListener {

    private static final String TAG = "AirdectorActivity";

    private static FrameLayout mFrameLayout;
    private static AirDialView mDialView;

    private static TextView areaText;
    private static TextView positionText;
    private static TextView levelText;
    private static TextView pm25Text;

    private ImageButton lastPositionButton;
    private ImageButton nextPositionButton;
    private ImageButton changeAreaButton;
    private ImageButton refreshButton;

    private Intent mIntent;

    private static Context mContext;

    private static Air air;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airdector);

        initView();

        registerListener();
    }

    @Override
    protected void onStart() {
        startService();
        super.onStart();
    }

    private void initView() {
        mFrameLayout = (FrameLayout) findViewById(R.id.background);
        mDialView = (AirDialView) findViewById(R.id.view_dial);
        areaText = (TextView) findViewById(R.id.area_text);
        positionText = (TextView) findViewById(R.id.position_text);
        levelText = (TextView) findViewById(R.id.level_text);
        pm25Text = (TextView) findViewById(R.id.pm25_text);

        lastPositionButton = (ImageButton) findViewById(R.id.last_position_button);
        nextPositionButton = (ImageButton) findViewById(R.id.next_position_button);
        changeAreaButton = (ImageButton) findViewById(R.id.area_button);
        refreshButton = (ImageButton) findViewById(R.id.refresh_button);

        mIntent = new Intent();
        mContext = this;
    }

    private void registerListener() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(
                AppRuntime.getApplicationContext());
        lastPositionButton.setOnClickListener(this);
        nextPositionButton.setOnClickListener(this);
        changeAreaButton.setOnClickListener(this);
        refreshButton.setOnClickListener(this);
    }

    private void startService() {
        mIntent.setClass(mContext, AirdectorService.class);
        mContext.startService(mIntent);
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick");
        switch (view.getId()) {
            case R.id.area_button:  {
                DialogUtils.showInputDialog(this, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        if (!TextUtils.isEmpty((input))) {
                            String area = input.toString();
                            mIntent.setAction(Airdector.ACTION_AREA_CHANGED);
                            mIntent.putExtra(Airdector.AREA_CHANGED, area);
                            mContext.startService(mIntent);
                            DialogUtils.showLoadingDialog(mContext);
                        }
                    }
                });
                break;
            }
            case R.id.refresh_button: {
                mIntent.setAction(Airdector.ACTION_REFRESH);
                mContext.startService(mIntent);
                DialogUtils.showLoadingDialog(mContext);
                break;
            }
            case R.id.last_position_button: {
                mIntent.setAction(Airdector.ACTION_LAST_POSITION);
                mContext.startService(mIntent);
                break;
            }
            case R.id.next_position_button: {
                mIntent.setAction(Airdector.ACTION_NEXT_POSITION);
                mContext.startService(mIntent);
                break;
            }
            default: break;
        }
    }
//
//    @Override
//    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
//        Log.d(TAG, "onSharedPreferenceChanged");
//        if (sharedPreferences.equals(PreferenceManager.getDefaultSharedPreferences(
//                AppRuntime.getApplicationContext()))) {
//            air = AppRuntime.getSharedPrefs().getAir();
//
//            switch (s) {
//                case SharedPrefs.PREF_AIR: updateView(); break;
//                case SharedPrefs.PREF_BG_COLOR: updateBackground(); break;
//                case SharedPrefs.PREF_DIAL: updateDial(); break;
//                default: break;
//            }
//        }
//    }

    public static void updateView() {
        DialogUtils.hideLoadingDialog(mContext);
        Air air = AppRuntime.getSharedPrefs().getAir();

        areaText.setText(air.getArea());
        positionText.setText(air.getPositionName());
        levelText.setText(air.getQuality());
        pm25Text.setText(String.valueOf(air.getPm25()));
    }

    public static void updateBackground() {
        DialogUtils.hideLoadingDialog(mContext);
        Utils.changeBgColor(mFrameLayout);
    }

    public static void updateDial() {
        DialogUtils.hideLoadingDialog(mContext);
        mDialView.invalidate();
    }
}
