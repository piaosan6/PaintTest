package com.pjm.painttest.PathTest.customView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.pjm.painttest.Utils.L;

/**
 *  充电水波浪
 */

public class WaveView extends View implements View.OnClickListener{

    private Paint mPaint;
    private Path mPath;
    private float width;
    private float height;
    //贝舍尔曲线的y坐标
    float currentY;
    //波浪的控制点高度（并非波浪的实际高度，而是贝舍尔曲线控制点的位置）
    private float waveHeight;
    //半个波浪的宽度
    private float halfWaveWidth;
    // 水平方向的偏移量
    private float offsetX;
//    // 竖直方向的偏移量
//    private float offsetY;
    private float startX;

    private ValueAnimator animator;
    private ValueAnimator animator2;


    public WaveView(Context context) {
        this(context, null);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        waveHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, metrics);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(9);
        mPaint.setColor(Color.GREEN);
        mPath = new Path();
        setOnClickListener(this);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        currentY = 0.75f * height;
        halfWaveWidth = 0.5f*width;
    }




    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 将path清空
        mPath.reset();

        drawRelativePath(canvas);

        //drawAbsolutelyPath(canvas);

    }

    private void drawRelativePath(Canvas canvas) {
        startX = -2f* halfWaveWidth + offsetX;
        mPath.moveTo(startX, currentY);
        for (; startX <= width; startX += 2 * halfWaveWidth){
            // 相对于起始点坐标的X，Y的偏移
            mPath.rQuadTo(halfWaveWidth / 2f, - waveHeight, halfWaveWidth, 0);
            mPath.rQuadTo(halfWaveWidth / 2f,  waveHeight, halfWaveWidth, 0);
        }
        // 将曲线闭合
        mPath.lineTo(width, height);
        mPath.lineTo(0, height);
        mPath.lineTo(0, currentY);
        canvas.drawPath(mPath, mPaint);
    }


    /**
     *  通过绝对值画贝舍尔曲线
     * @param canvas
     */
    private void drawAbsolutelyPath(Canvas canvas) {
        mPath.moveTo(-2f * halfWaveWidth + offsetX, currentY);
        mPath.quadTo(-1.5f * halfWaveWidth + offsetX, currentY - waveHeight, -halfWaveWidth + offsetX, currentY);
        mPath.quadTo(-0.5f * halfWaveWidth + offsetX, currentY + waveHeight, offsetX, currentY);
        mPath.quadTo( 0.5f * halfWaveWidth + offsetX, currentY - waveHeight, halfWaveWidth + offsetX, currentY);
        mPath.quadTo( 1.5f * halfWaveWidth + offsetX, currentY + waveHeight, 2f* halfWaveWidth + offsetX, currentY);
        // 将曲线闭合
        mPath.lineTo(width, height);
        mPath.lineTo(0, height);
        mPath.lineTo(0, currentY);
        canvas.drawPath(mPath, mPaint);
    }


    @Override
    public void onClick(View v) {
        if(animator !=null && !animator.isRunning()){
            startAnimForX();
        }
    }

    public void startAnimForXY(){
        animator = ValueAnimator.ofFloat(0.75f * height, -waveHeight);
        animator.setDuration(6000);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentY = (float) animation.getAnimatedValue();
                offsetX = animation.getAnimatedFraction()*4*2* halfWaveWidth;
                offsetX = offsetX % (2* halfWaveWidth);
                L.e("offsetX = " + offsetX);
                invalidate();
            }
        });

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                offsetX = 0;
                currentY = 0.75f * height;
            }
        });

        animator.start();
    }

    public void startAnimForX(){
        animator2 = ValueAnimator.ofFloat(0, 2* halfWaveWidth);
        animator2.setDuration(1000);
        animator2.setRepeatCount(ValueAnimator.INFINITE);
        animator2.setInterpolator(new LinearInterpolator());
        animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                offsetX = (float) animation.getAnimatedValue();
                L.e("offsetX = " + offsetX);
                invalidate();
            }
        });
        animator2.start();
    }

    public void stopAnim(){

        if(animator !=null && animator.isRunning()){
            animator.cancel();
        }
        if(animator2 != null && animator2.isRunning()){
            animator2.cancel();
        }
    }


}
