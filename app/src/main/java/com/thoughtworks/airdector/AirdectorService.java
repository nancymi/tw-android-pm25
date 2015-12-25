package com.thoughtworks.airdector;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.thoughtworks.airdector.data.SharedPrefs;
import com.thoughtworks.airdector.http.RetrofitCallback;
import com.thoughtworks.airdector.http.RetrofitExecutor;
import com.thoughtworks.airdector.model.Air;

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

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        loadData();
    }

    private void loadData() {
        retrofitExecutor = new RetrofitExecutor(AppRuntime.getApplicationContext());
        retrofitExecutor.execute(mArea, new RetrofitCallback() {
            @Override
            public void onCompleted(List<Air> airList) {
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

        switch (actionType) {
            case Airdector.ACTION_AREA_CHANGED: {
                mArea = intent.getStringExtra(Airdector.AREA_CHANGED);
                loadData();
            }
            case Airdector.ACTION_REFRESH:  loadData(); break;
            case Airdector.ACTION_LAST_POSITION: {
                -- curPosition;
                curPosition = curPosition < 0 ? 0 : curPosition;
                writeToSharedPrefs();
            }
            case Airdector.ACTION_NEXT_POSITION: {
                ++ curPosition;
                curPosition = curPosition > maxPosition ? maxPosition : curPosition;
                writeToSharedPrefs();
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
