package com.thoughtworks.airdector.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.View;

import com.thoughtworks.airdector.AppRuntime;
import com.thoughtworks.airdector.R;
import com.thoughtworks.airdector.data.SharedPrefs;
import com.thoughtworks.airdector.utils.Utils;

/**
 * Created by nancymi on 12/24/15.
 */
public class AirDialView extends View {

    private int mAccentColor;
    private int mWhiteColor;

    private static float mDotRadius = 6;
    private static float mMarkerStrokeSize = 2;
    private static float mStrokeSize = 4;

    private final Paint mPaint = new Paint();
    private final Paint mFill = new Paint();
    private final RectF mArcRect = new RectF();

    private float mRadiusOffset;

    private int mDialVal;

    private static int DIAL_MAX = 800;

    @SuppressWarnings("unused")
    public AirDialView(Context context) {
        super(context);
    }

    public AirDialView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawDial(canvas);
    }

    public void drawDial(Canvas canvas) {
        readFromSharedPrefs();

        int xCenter = getWidth() / 2;
        int yCenter = getHeight() / 2;

        float radius = Math.min(xCenter, yCenter) - mRadiusOffset;

        mPaint.setColor(mWhiteColor);

        mArcRect.top = yCenter - radius;
        mArcRect.bottom = yCenter + radius;
        mArcRect.left = xCenter - radius;
        mArcRect.right = xCenter + radius;

        float blackPercent = (float) mDialVal / DIAL_MAX;
        float whitePercent = 1 - blackPercent;

        mPaint.setColor(mAccentColor);

        canvas.drawArc(mArcRect, 270, blackPercent * 360, false, mPaint);

        mPaint.setStrokeWidth(mStrokeSize);
        mPaint.setColor(mWhiteColor);

        canvas.drawArc(mArcRect,270 + (1 - whitePercent) * 360,
                whitePercent * 360, false, mPaint);

        drawDot(canvas, blackPercent, xCenter, yCenter, radius);
    }

    private void init(Context context) {
        Resources resources = context.getResources();

        mStrokeSize = resources.getDimension(R.dimen.dial_circle_size);
        float dotDiameter = resources.getDimension(R.dimen.dial_dot_size);
        mMarkerStrokeSize = resources.getDimension(R.dimen.dial_mark_size);
        mRadiusOffset = Utils.calculateRadiusOffset(mStrokeSize, dotDiameter, mMarkerStrokeSize);

        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mWhiteColor = resources.getColor(R.color.colorWhite);
        mAccentColor = resources.getColor(R.color.colorBlack);
        mFill.setStyle(Paint.Style.FILL);
        mFill.setAntiAlias(true);
        mFill.setColor(mAccentColor);
        mDotRadius = dotDiameter / 2f;
    }

    protected void drawDot(
            Canvas canvas, float degrees, int xCenter, int yCenter, float radius) {
        mPaint.setColor(mAccentColor);
        float dotPercent;
        dotPercent = 270 + degrees * 360;

        final double dotRadians = Math.toRadians(dotPercent);
        canvas.drawCircle(xCenter + (float) (radius * Math.cos(dotRadians)),
                yCenter + (float) (radius * Math.sin(dotRadians)), mDotRadius, mFill);
    }

    public void readFromSharedPrefs() {
        SharedPrefs prefs = AppRuntime.getSharedPrefs();

        mDialVal = prefs.getPrefDial();
    }
}
