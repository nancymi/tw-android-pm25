package com.thoughtworks.airdector.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import javax.xml.transform.Source;

/**
 * Created by nancymi on 12/23/15.
 */
public class Air implements Parcelable {

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

    private Air(Parcel parcel) {
        this.area = parcel.readString();
        this.positionName = parcel.readString();
        this.quality = parcel.readString();
        this.pm25 = parcel.readInt();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(area);
        parcel.writeString(positionName);
        parcel.writeString(quality);
        parcel.writeInt(pm25);
    }

    public static final Parcelable.Creator<Air> CREATOR = new Parcelable.Creator<Air>() {

        @Override
        public Air createFromParcel(Parcel parcel) {
            return new Air(parcel);
        }

        @Override
        public Air[] newArray(int i) {
            return new Air[i];
        }
    };
}
