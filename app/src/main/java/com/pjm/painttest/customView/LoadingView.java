package com.pjm.painttest.customView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.pjm.painttest.utils.L;

public class LoadingView extends View {

    private int cirCleColor = 0xFF53BBF7;
    private Paint mPaint;
    private float radius;
    /**
     * 最大半径
     */
    private float minCircleRadius;
    private float centX;
    private float centY;
    private ValueAnimator mAnimator;
    private int position;
    private Paint mTextPaint;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(cirCleColor);
        radius = dpToPx(21);
        minCircleRadius = dpToPx(1.5f);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setColor(cirCleColor);
        mTextPaint.setTextSize(sp2px(6));
        L.i("sp2 = " + sp2px(8) + ", spTo =" + spToPx(8));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centX = w/2f;
        centY = h/2f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCircle(canvas);
        drawText(canvas);
    }

    private void drawText(Canvas canvas) {
        canvas.drawText("LOADING", centX, getBaselineY(centY, mTextPaint), mTextPaint);
    }

    private void drawCircle(Canvas canvas) {
        float cx, cy, circleRadius;
        for(int i = 0; i< 8;i++){
            cx = (float) (centX + radius * Math.cos(Math.PI * i * 45/180));
            cy = (float) (centY + radius * Math.sin(Math.PI * i * 45/180));
            circleRadius = minCircleRadius + Math.abs(i + position)%8 ;
            canvas.drawCircle(cx, cy, circleRadius, mPaint);
        }
    }

    public void showLoading(){
        mAnimator = ValueAnimator.ofInt(8,0);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.setDuration(800);
        mAnimator.setRepeatMode(ValueAnimator.RESTART);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.addUpdateListener(animation -> {
            position = (Integer) animation.getAnimatedValue();
            invalidate();
        });
        mAnimator.start();
    }

    public void hideLoading(){
        if(mAnimator != null && mAnimator.isRunning()){
            mAnimator.cancel();
        }
    }

    private float getBaselineY(float dy, Paint textPaint) {
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float top = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
        float bottom = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom
        return (dy - top/2 - bottom/2);//基线中间点的y轴计算公式
    }

    public float dpToPx(float dp) {
        return dp * getContext().getResources().getDisplayMetrics().density;
    }

    public float spToPx(float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getContext().getResources().getDisplayMetrics());
    }
    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    public float sp2px(float spValue) {
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return spValue * fontScale;
    }


}
