package com.thoughtworks.airdector;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.thoughtworks.airdector.data.SharedPrefs;
import com.thoughtworks.airdector.http.RetrofitCallback;
import com.thoughtworks.airdector.http.RetrofitExecutor;
import com.thoughtworks.airdector.model.Air;
import com.thoughtworks.airdector.utils.DialogUtils;
import com.thoughtworks.airdector.utils.Utils;

import java.util.List;

/**
 * Created by nancymi on 12/23/15.
 */
public class AirdectorService extends Service {

    private RetrofitExecutor retrofitExecutor;
    private static List<Air> airs;

    private static int curPosition = 0;

    private static int maxPosition;

    private static final String DEAFULT_AREA = "xian";

    private static String mArea = DEAFULT_AREA;

    private static final String TAG = "AirdectorService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        loadData();
        AirdectorActivity.updateView();
        AirdectorActivity.updateDial();
        AirdectorActivity.updateBackground();
    }

    private void loadData() {
        retrofitExecutor = new RetrofitExecutor(AppRuntime.getApplicationContext());
        retrofitExecutor.execute(mArea, new RetrofitCallback() {
            @Override
            public void onCompleted(List<Air> airList) {
                curPosition = 0;
                airs = airList;
                maxPosition = airs.size()-1;
                writeToSharedPrefs();
            }
        });
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            return Service.START_NOT_STICKY;
        }

        String actionType = intent.getAction();
        if (actionType == null) {
            return Service.START_NOT_STICKY;
        }

        switch (actionType) {
            case Airdector.ACTION_AREA_CHANGED: {
                mArea = intent.getStringExtra(Airdector.AREA_CHANGED);
                loadData();
                break;
            }
            case Airdector.ACTION_REFRESH: {
                loadData();
                break;
            }
            case Airdector.ACTION_LAST_POSITION: {
                -- curPosition;
                curPosition = curPosition < 0 ? maxPosition : curPosition;
                writeToSharedPrefs();
                break;
            }
            case Airdector.ACTION_NEXT_POSITION: {
                ++ curPosition;
                curPosition = curPosition > maxPosition ? 0 : curPosition;
                writeToSharedPrefs();
                break;
            }
            default:break;
        }

        return START_STICKY;
    }

    private void writeToSharedPrefs() {
        SharedPrefs sharedPrefs = AppRuntime.getSharedPrefs();
        sharedPrefs.setAir(airs.get(curPosition));
        sharedPrefs.setPrefDial(airs.get(curPosition).getPm25());
        sharedPrefs.setPrefBgColor(airs.get(curPosition).getPm25());
    }
}
