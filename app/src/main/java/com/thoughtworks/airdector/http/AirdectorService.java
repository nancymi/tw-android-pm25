package com.thoughtworks.airdector.http;

import com.thoughtworks.airdector.model.Air;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by nancymi on 12/23/15.
 */
public interface AirdectorService {

    @GET("/api/querys/aqi_details.json?token=4esfG6UEhGzNkbszfjAp")
    Call<List<Air>> getPM25(@Query("city") String city);

}
