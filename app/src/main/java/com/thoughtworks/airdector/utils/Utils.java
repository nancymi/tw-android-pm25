package com.thoughtworks.airdector.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.thoughtworks.airdector.AppRuntime;
import com.thoughtworks.airdector.R;
import com.thoughtworks.airdector.model.Air;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;

/**
 * Created by nancymi on 12/24/15.
 */
public class Utils {

    private static final int[] BACKGROUND_SPECTRUM = {
            0xFF81D4FA,
            0xFF03A9F4,
            0xFF0288D1,
            0xFF90A4AE,
            0xFF607D8B,
            0xFF546E7A
    };

    private static int getBgColorByPM(long pm_25) {
        int bgColor;
        switch ((int)pm_25 / 50) {
            case 0: bgColor = BACKGROUND_SPECTRUM[0]; break;
            case 1: bgColor = BACKGROUND_SPECTRUM[1]; break;
            case 2: bgColor = BACKGROUND_SPECTRUM[2]; break;
            case 3: bgColor = BACKGROUND_SPECTRUM[3]; break;
            case 4: bgColor = BACKGROUND_SPECTRUM[4]; break;
            case 5: bgColor = BACKGROUND_SPECTRUM[5]; break;
            default: bgColor = BACKGROUND_SPECTRUM[5]; break;
        }
        return bgColor;
    }

    public static void changeBgColor(View view) {
        FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.background);
        Drawable drawable = frameLayout.getBackground();
        drawable.getColorFilter();

        Air air = AppRuntime.getSharedPrefs().getAir();
        int bgColor = getBgColorByPM(air.getPm25());
        frameLayout.setBackgroundColor(bgColor);
    }

    public static float calculateRadiusOffset(
            float strokeSize, float dotStrokeSize, float markerStrokeSize) {
        return Math.max(strokeSize, Math.max(dotStrokeSize, markerStrokeSize));
    }

    public static String objectToString(Object obj) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bos);
            os.writeObject(obj);
            String bytesToHexString = bytesToHexString(bos.toByteArray());
            return bytesToHexString;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Object stringToObject(String s) {
        try {
            byte[] stringToBytes = StringToBytes(s);
            ByteArrayInputStream bis=new ByteArrayInputStream(stringToBytes);
            ObjectInputStream is = new ObjectInputStream(bis);
            //返回反序列化得到的对象
            Object readObject = is.readObject();
            return readObject;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (OptionalDataException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String bytesToHexString(byte[] bArray) {
        if(bArray == null){
            return null;
        }
        if(bArray.length == 0){
            return "";
        }
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    private static byte[] StringToBytes(String data) {
        String hexString = data.toUpperCase().trim();
        if (hexString.length() % 2 != 0) {
            return null;
        }
        byte[] retData = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length(); i++) {
            int int_ch;  // 两位16进制数转化后的10进制数
            char hex_char1 = hexString.charAt(i); ////两位16进制数中的第一位(高位*16)
            int int_ch1;
            if (hex_char1 >= '0' && hex_char1 <= '9')
                int_ch1 = (hex_char1 - 48) * 16;   //// 0 的Ascll - 48
            else if (hex_char1 >= 'A' && hex_char1 <= 'F')
                int_ch1 = (hex_char1 - 55) * 16; //// A 的Ascll - 65
            else
                return null;
            i++;
            char hex_char2 = hexString.charAt(i); ///两位16进制数中的第二位(低位)
            int int_ch2;
            if (hex_char2 >= '0' && hex_char2 <= '9')
                int_ch2 = (hex_char2 - 48); //// 0 的Ascll - 48
            else if (hex_char2 >= 'A' && hex_char2 <= 'F')
                int_ch2 = hex_char2 - 55; //// A 的Ascll - 65
            else
                return null;
            int_ch = int_ch1 + int_ch2;
            retData[i / 2] = (byte) int_ch;//将转化后的数放入Byte里
        }
        return retData;
    }
}
