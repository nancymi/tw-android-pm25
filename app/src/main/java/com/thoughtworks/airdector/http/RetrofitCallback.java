package com.thoughtworks.airdector.http;

import com.thoughtworks.airdector.model.Air;

import java.util.List;

/**
 * Created by nancymi on 12/23/15.
 */
public interface RetrofitCallback {
    void onCompleted(List<Air> airList);
}
