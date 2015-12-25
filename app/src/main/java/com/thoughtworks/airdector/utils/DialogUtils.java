package com.thoughtworks.airdector.utils;

import android.content.Context;
import android.text.TextUtils;

import com.afollestad.materialdialogs.MaterialDialog;
import com.thoughtworks.airdector.R;

/**
 * Created by nancymi on 12/25/15.
 */
public class DialogUtils {

    private static MaterialDialog materialDialog;

    public static void showInputDialog(Context context, MaterialDialog.InputCallback callback) {
        new MaterialDialog.Builder(context)
                .title(R.string.search)
                .input(R.string.input_hint, R.string.input_prefill, callback)
                .show();
    }

    public static void showLoadingDialog(Context context) {
        if (materialDialog == null) {
            materialDialog = new MaterialDialog.Builder(context)
                    .title(R.string.wait)
                    .content(R.string.downloading)
                    .progress(true, 0)
                    .build();
        }

        materialDialog.show();

    }

    public static void hideLoadingDialog(Context context) {
        if (materialDialog != null && materialDialog.isShowing() == true) {
            materialDialog.dismiss();
        }
    }
}
