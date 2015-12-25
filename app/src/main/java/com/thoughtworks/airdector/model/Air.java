package com.thoughtworks.airdector.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import javax.xml.transform.Source;

/**
 * Created by nancymi on 12/23/15.
 */
public class Air implements Serializable {

    @SerializedName("position_name")
    private String positionName;

    @SerializedName("quality")
    private String quality;

    @SerializedName("area")
    private String area;

    @SerializedName("pm2_5")
    private int pm25;

    public String getPositionName() {
        return positionName;
    }

    public String getQuality() {
        return quality;
    }

    public String getArea() {
        return area;
    }

    public int getPm25() {
        return pm25;
    }

    @Override
    public String toString() {
        return "Air{" +
                "positionName='" + positionName + '\'' +
                ", quality='" + quality + '\'' +
                ", area='" + area + '\'' +
                ", pm25='" + pm25 + '\'' +
                '}';
    }
}
