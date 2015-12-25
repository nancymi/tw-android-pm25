package com.thoughtworks.airdector;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thoughtworks.airdector.data.SharedPrefs;
import com.thoughtworks.airdector.model.Air;
import com.thoughtworks.airdector.utils.Utils;
import com.thoughtworks.airdector.view.AirDialView;

import org.w3c.dom.Text;

public class AirdectorFragment extends Fragment implements
        SharedPreferences.OnSharedPreferenceChangeListener, View.OnClickListener{

    private FrameLayout mFrameLayout;
    private AirDialView mDialView;

    private TextView areaText;
    private TextView positionText;
    private TextView levelText;
    private TextView pm25Text;

    private ImageButton lastPositionButton;
    private ImageButton nextPositionButton;
    private ImageButton changeAreaButton;
    private ImageButton refreshButton;

    private Intent mIntent;

    private Context mContext;

    private Air air;

    public AirdectorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.fragment_airdector, container, false);
        initView(v);

        startService();

        return v;
    }

    private void initView(View v) {
        mFrameLayout = (FrameLayout) v.findViewById(R.id.background);
        mDialView = (AirDialView) v.findViewById(R.id.view_dial);
        areaText = (TextView) v.findViewById(R.id.area_text);
        positionText = (TextView) v.findViewById(R.id.position_text);
        levelText = (TextView) v.findViewById(R.id.level_text);
        pm25Text = (TextView) v.findViewById(R.id.pm25_text);

        lastPositionButton = (ImageButton) v.findViewById(R.id.last_position_button);
        nextPositionButton = (ImageButton) v.findViewById(R.id.next_position_button);
        changeAreaButton = (ImageButton) v.findViewById(R.id.area_button);
        refreshButton = (ImageButton) v.findViewById(R.id.refresh_button);

        mIntent = new Intent();
        mContext = getActivity();
    }

    private void startService() {
        mIntent.setClass(mContext, Airdector.class);
        mContext.startService(mIntent);
    }

    private void updateView() {
        areaText.setText(air.getArea());
        positionText.setText(air.getPositionName());
        levelText.setText(air.getQuality());
        pm25Text.setText(air.getPm25());
    }

    private void updateBackground() {
        //TODO 更新 background
        Utils.changeBgColor(mFrameLayout);
    }

    private void updateDial() {
        //TODO 更新 dial
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
        if (prefs.equals(PreferenceManager.getDefaultSharedPreferences(getActivity()))) {
            air = AppRuntime.getSharedPrefs().getAir();

            switch (key) {
                case SharedPrefs.PREF_AIR: updateView(); break;
                case SharedPrefs.PREF_BG_COLOR: updateBackground(); break;
                case SharedPrefs.PREF_DIAL: updateDial(); break;
                default: break;
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.area_button:  {
                //TODO 选择地区
                String area = "";
                mIntent.setAction(Airdector.ACTION_AREA_CHANGED);
                mIntent.putExtra(Airdector.AREA_CHANGED, area);
                mContext.startService(mIntent);
                break;
            }
            case R.id.refresh_button: {
                mIntent.setAction(Airdector.ACTION_REFRESH);
                mContext.startService(mIntent);
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
}
