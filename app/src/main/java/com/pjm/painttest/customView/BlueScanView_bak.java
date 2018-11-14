package com.pjm.painttest.customView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.pjm.painttest.R;
import com.pjm.painttest.utils.L;

public class BlueScanView_bak extends View {

    private Bitmap phoneBitmap;
    private Bitmap blueBitmap;
    private Bitmap meterBitmap;
    private Paint bgPaint;
    private int width;
    private int height;
    private float centerX;
    private float centerY;
    private float radius;
    private float pointRadius;
    private Rect phoneSrcRect;
    private Rect blueSrcRect;
    private Rect meterSrcRect;
    private RectF phoneDstRect;
    private RectF blueDstRect;
    private RectF meterDstRect;
    private Paint pontPaint;
    private float ry;


    public BlueScanView_bak(Context context) {
        this(context,null);
    }

    public BlueScanView_bak(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BlueScanView_bak(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        phoneBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.phone_icon);
        blueBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bluetooth_icon);
        meterBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.biao_icon);

        phoneSrcRect = new Rect(0, 0, phoneBitmap.getWidth(), phoneBitmap.getHeight());
        blueSrcRect = new Rect(0, 0, blueBitmap.getWidth(), blueBitmap.getHeight());
        meterSrcRect = new Rect(0, 0, meterBitmap.getWidth(), meterBitmap.getHeight());

        bgPaint = new Paint();
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setAntiAlias(true);
        bgPaint.setColor(0xFF2CA0EC);

        pontPaint = new Paint();
        pontPaint.setStyle(Paint.Style.FILL);
        pontPaint.setAntiAlias(true);
        pontPaint.setColor(0xff01f1fe);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w ;
        height = h;
        centerX = w/2f;
        centerY = h/2f;
        radius = Math.min(w, h) * 0.5f;
        float scale = radius / dpToPx(140) ;
        L.i("scale = " + scale);

        // left = 0.16785714, top = 419.88394, right = 420.33215,  bottom = 420.11606
        phoneDstRect = new RectF(centerX - radius + scale*dpToPx(47), centerY - scale*dpToPx(32.5f),
                centerX - scale*dpToPx(66) , centerY + scale*dpToPx(32.5f));
        L.i("phoneDstRect  left = " + phoneDstRect.left + ", top = " + phoneDstRect.top
                + ", right = " + phoneDstRect.right + ",  bottom = " + phoneDstRect.bottom);

        blueDstRect = new RectF(centerX - scale * dpToPx(8), centerY - radius + scale * dpToPx(36),
                centerX + scale * dpToPx(8), centerY - scale *dpToPx(83f));

        meterDstRect = new RectF(centerX + scale*dpToPx(38), centerY - scale*dpToPx(33f),
                centerX + scale*dpToPx(104), centerY + scale*dpToPx(33f));

//        meterDstRect = new RectF(centerX + scale*dpToPx(20), centerY - scale*dpToPx(41.5f),
//                centerX + radius - scale*dpToPx(37), centerY + scale*dpToPx(41.5f));

        pointRadius = radius - scale * dpToPx(37*3+33)/2;
        ry = centerY - dpToPx(33);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBg(canvas);
        drawPic(canvas);
        drawConnectLine(canvas);
    }

    private void drawBg(Canvas canvas) {
        canvas.drawCircle(centerX, centerY, radius, bgPaint);
    }

    private void drawPic(Canvas canvas) {
        canvas.drawBitmap(phoneBitmap, phoneSrcRect, phoneDstRect, null);
        canvas.drawBitmap(blueBitmap, blueSrcRect, blueDstRect, null);
        canvas.drawBitmap(meterBitmap, meterSrcRect, meterDstRect, null);

    }

    private void drawConnectLine(Canvas canvas) {
        float cx;
        float cy;
        for(int i = 0; i < 13; i++){
            if(i == 6){
                continue;
            }
            cx = (float) (centerX + Math.cos(Math.PI*(i)*15/180) * pointRadius);
            cy = (float) (ry - Math.sin(Math.PI*(i)*15/180) * pointRadius);
            canvas.drawCircle(cx, cy, dpToPx(1f), pontPaint);
        }
    }
    public float dpToPx(float dp) {
        return dp * getContext().getResources().getDisplayMetrics().density;
    }
}
