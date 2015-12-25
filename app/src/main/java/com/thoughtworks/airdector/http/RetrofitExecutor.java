package com.thoughtworks.airdector.http;

import android.content.Context;
import android.util.Log;

import com.thoughtworks.airdector.Airdector;
import com.thoughtworks.airdector.AppRuntime;
import com.thoughtworks.airdector.data.SharedPrefs;
import com.thoughtworks.airdector.model.Air;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by nancymi on 12/23/15.
 */
public class RetrofitExecutor {

    private final Retrofit retrofit;
    private final AirdectorService airdectorService;

    private final Context mContext;

    private static final String TAG = "RetrofitExecutor";

    public RetrofitExecutor(Context context) {
        this.mContext = context;

        this.retrofit = new Retrofit.Builder()
                .baseUrl(Airdector.PM_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.airdectorService = retrofit.create(AirdectorService.class);
    }

    public void execute(String area, final RetrofitCallback callback) {

        enqueue(area, callback);
    }

    private void enqueue(String area, final RetrofitCallback callback) {
        Call<List<Air>> all = this.airdectorService.getPM25(area);
        all.enqueue(new Callback<List<Air>>() {
            @Override
            public void onResponse(Response<List<Air>> response, Retrofit retrofit) {
                callback.onCompleted(response.body());
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, "execute error : " + t.toString());
            }
        });
    }
}
