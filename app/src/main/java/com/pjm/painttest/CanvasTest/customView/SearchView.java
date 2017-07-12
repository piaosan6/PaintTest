package com.pjm.painttest.CanvasTest.customView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 *  自定义searchView
 */

public class SearchView extends View implements View.OnClickListener{

    private Paint mPaint;
    private float paintWidth;
    // 搜索圆心的x,y
    private float cx;
    private float cy;
    // view 的x的中心点
    private float centerX;
    //半径
    private float radius;
    // 控制弧形的区域
    private RectF oval;
    private float startAngle;
    /** 放大镜手柄的长度 */
    private float handlesLength;

    private State state;
    // 搜索框圆心能够移动的最大距离
    private float maxMoveDistance;
    //底部横线距离 搜索框圆心X,Y的偏移量
    private float offset;
    //左右两边预留的边距
    private float drawPadding;


    public SearchView(Context context) {
        this(context, null);
    }

    public SearchView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        paintWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, metrics);
        radius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 18, metrics);
        drawPadding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, metrics);
        handlesLength = radius * 1.2f;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(paintWidth);
        mPaint.setColor(Color.GREEN);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        startAngle = 0;
        setOnClickListener(this);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(state == null){
            state = new DefaultState();
        }
        state.draw(canvas);

    }

    @Override
    public void onClick(View v) {
        state = new AnimatorState();
    }



    interface State{
        void draw(Canvas canvas);
    }

    class DefaultState implements State{

        @Override
        public void draw(Canvas canvas) {
            canvas.save();
            canvas.rotate(45, cx, cy);
            canvas.drawArc(oval, startAngle, 360 - startAngle, false, mPaint);
            canvas.drawLine(cx + radius, cy, cx + radius + handlesLength, cy, mPaint);
            canvas.restore();
        }



    }

    class AnimatorState implements State{
        // 手柄尾部的x坐标
        private float endX;
        // 手柄尾部的Y坐标
        private float y;
        // 手柄水平移动的距离
        float moveDistance ;
        // 当前手柄的长度
        float currentHandlesLength;
        // 手柄开始变短时候水平方向增加的距离
        float horMoveDistance;


        public AnimatorState(){
            currentHandlesLength = handlesLength;
            // 圆圈动画
            ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
            animator.setInterpolator(new LinearInterpolator());
            animator.setDuration(1200);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float percentage = animation.getAnimatedFraction();
                    if(percentage <= 0.8f){
                        startAngle = 360 * (float) animation.getAnimatedValue()/0.8f;
                        moveDistance = animation.getAnimatedFraction() * maxMoveDistance/0.8f;
                        cx = centerX + moveDistance;
                        oval.set(cx - radius, cy - radius, cx + radius, cy + radius);
                    }else{
                        startAngle = 360;
                        currentHandlesLength = (1- percentage)/0.2f * handlesLength;
                        horMoveDistance = (percentage - 0.8f)/0.2f *(centerX - drawPadding - moveDistance);

                    }
                    invalidate();
                }
            });
            animator.start();
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.save();
            canvas.rotate(45, cx, cy);
            canvas.drawArc(oval, startAngle, 360 - startAngle, false, mPaint);
            canvas.restore();

            endX = cx + offset;
            y = cy + offset;

            if(currentHandlesLength != 0){
                // 画手柄 ，通过旋转画布来画
                canvas.save();
                canvas.rotate(45, endX, y);
                //从尾部画到头部
                canvas.drawLine(endX, y, endX - currentHandlesLength, y, mPaint);
                canvas.restore();
            }
            // 画水平方向直线
            canvas.drawLine(endX, y, centerX - moveDistance - horMoveDistance, y, mPaint);


            /* 通过计算来画
            if(currentHandlesLength != 0){
                float d = (float) (Math.cos(Math.toRadians(45))*currentHandlesLength);
                float startX = (endX - d);
                float startY = (y - d);
                //画手柄 ，从尾部画到头部
                L.e("startX = " + startX + ", startY=" + startY + ", endX=" + endX + ", y=" +y);
                canvas.drawLine(startX, startY, endX, y, mPaint);
            }
            // 画水平方向直线
             canvas.drawLine(endX, y, centerX - moveDistance - horMoveDistance, y, mPaint);*/

        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w/2f;
        cx = centerX;
        cy = h/2f;
        oval = new RectF(cx - radius, cy - radius, cx + radius, cy + radius);
        offset = (float) (Math.sin(Math.toRadians(45))*(handlesLength + radius));
        maxMoveDistance = centerX - drawPadding - offset;
    }

}
