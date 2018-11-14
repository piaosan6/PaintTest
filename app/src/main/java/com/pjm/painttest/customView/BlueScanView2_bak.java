package com.pjm.painttest.customView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.pjm.painttest.R;
import com.pjm.painttest.utils.L;

public class BlueScanView2_bak extends View {

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
    private Paint pointPaint;
    private float ry;
    private float circleRadius;
    private ValueAnimator mAnimator;
    private float position;
    private String connectStr;
    /**
     * 文字Y轴的位置
     */
    private float textY;
    private Paint textPaint;


    public BlueScanView2_bak(Context context) {
        this(context,null);
    }

    public BlueScanView2_bak(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BlueScanView2_bak(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        phoneBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.phone_icon);
        blueBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bluetooth_icon);
        meterBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.biao_icon);
        circleRadius = dpToPx(1.5f);

        phoneSrcRect = new Rect(0, 0, phoneBitmap.getWidth(), phoneBitmap.getHeight());
        blueSrcRect = new Rect(0, 0, blueBitmap.getWidth(), blueBitmap.getHeight());
        meterSrcRect = new Rect(0, 0, meterBitmap.getWidth(), meterBitmap.getHeight());

        bgPaint = new Paint();
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setAntiAlias(true);
//        bgPaint.setColor(0xFF2DA1EC);
        bgPaint.setColor(0xDE003C66);

        pointPaint = new Paint();
        pointPaint.setStyle(Paint.Style.FILL);
        pointPaint.setAntiAlias(true);
        pointPaint.setColor(0xff01f1fe);

        textPaint = new Paint();
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.CENTER);
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

        textY = centerY + height/4f + dpToPx(10) * scale;
        textPaint.setTextSize(dpToPx(17)*scale);
        phoneDstRect = new RectF(centerX - radius + scale*dpToPx(47), centerY - scale*dpToPx(33f),
                centerX - scale*dpToPx(56) , centerY + scale*dpToPx(33f));

        meterDstRect = new RectF(centerX + scale*dpToPx(41.5f), centerY - scale*dpToPx(33f),
                centerX + scale*dpToPx(107.5f), centerY + scale*dpToPx(33f));

        pointRadius = scale * dpToPx(74.5f);
        ry = centerY - scale * dpToPx(34) - circleRadius*2;

        blueDstRect = new RectF(centerX - scale * dpToPx(8), ry - pointRadius- scale*dpToPx(10.5f),
                centerX + scale * dpToPx(8), ry - pointRadius + scale*dpToPx(10.5f));

//        phoneDstRect = new RectF(centerX - radius + scale*dpToPx(37), centerY - scale*dpToPx(33f),
//                centerX - scale*dpToPx(66) , centerY + scale*dpToPx(33f));
//        L.i("phoneDstRect  left = " + phoneDstRect.left + ", top = " + phoneDstRect.top
//                + ", right = " + phoneDstRect.right + ",  bottom = " + phoneDstRect.bottom);
//
//        meterDstRect = new RectF(centerX + scale*dpToPx(51.5f), centerY - scale*dpToPx(33f),
//                centerX + scale*dpToPx(117.5f), centerY + scale*dpToPx(33f));
//
//        pointRadius = scale * dpToPx(84.5f);
//        ry = centerY - scale * dpToPx(34) - circleRadius*2;
//
//        blueDstRect = new RectF(centerX - scale * dpToPx(8), ry - pointRadius- scale*dpToPx(10.5f),
//                centerX + scale * dpToPx(8), ry - pointRadius + scale*dpToPx(10.5f));



    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBg(canvas);
        drawPic(canvas);
        drawConnectLine(canvas);
        drawConnectText(canvas);
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
        for(int i = 0; i < 12; i++){
            int pos ;
            if(i >= 6){
                pos = i + 1;
            }else{
                pos = i;
            }
            float a = position - 0.16f*i;
            if(a >0.8f){
                a = 0;
            }
            int alpha = Math.round(256*a);
            L.e("a = " + a + ", alpha = " + alpha );
            pointPaint.setARGB(alpha, 1, 241, 254);
            cx = (float) (centerX + Math.cos(Math.PI*(180-pos*15)/180) * pointRadius);
            cy = (float) (ry - Math.sin(Math.PI*(180 -pos*15)/180) * pointRadius);
            canvas.drawCircle(cx, cy, circleRadius, pointPaint);
        }
    }

//    private void drawConnectLine(Canvas canvas) {
//        float cx;
//        float cy;
//        for(int i = 0; i <= position; i++){
//            int pos ;
//            if(i >= 6){
//                pos = i + 1;
//            }else{
//                pos = i;
//            }
//            cx = (float) (centerX + Math.cos(Math.PI*(180-pos*15)/180) * pointRadius);
//            cy = (float) (ry - Math.sin(Math.PI*(180 -pos*15)/180) * pointRadius);
//            canvas.drawCircle(cx, cy, circleRadius, pointPaint);
//        }
//    }

    private void drawConnectText(Canvas canvas) {
        if(!TextUtils.isEmpty(connectStr)){
            canvas.drawText(connectStr, centerX, textY, textPaint);
        }
    }

    public void startScanAnim(){
        this.startScanAnim("");
    }

    public void startScanAnim(String connectStr){
        this.connectStr = connectStr;
        mAnimator = ValueAnimator.ofFloat(0,0.8f);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.setDuration(1500);
        mAnimator.setRepeatMode(ValueAnimator.RESTART);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.addUpdateListener(animation -> {
            float value = (Float) animation.getAnimatedValue();
            position = value;
            invalidate();
        });
        mAnimator.start();
    }

    public void stoScanAnim(String  connectStr){
        if (mAnimator != null){
            mAnimator.cancel();
            mAnimator = null;
        }
        this.connectStr = connectStr;
        textPaint.setColor(0xFFFFDA00);
        position = 11;
        pointPaint.setColor(Color.GRAY);
        invalidate();
    }



    public float dpToPx(float dp) {
        return dp * getContext().getResources().getDisplayMetrics().density;
    }

    public void setState(String connectStr) {
        this.connectStr = connectStr;
        if(mAnimator == null || !mAnimator.isRunning()){
            invalidate();
        }
    }
}
