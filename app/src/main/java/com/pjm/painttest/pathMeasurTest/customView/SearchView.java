package com.pjm.painttest.pathMeasurTest.customView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

/**
 *  之前有通过canvas 旋转来实现，这次用PathMeasure来实现
 */

public class SearchView extends View implements View.OnClickListener{

    private Paint mPaint;
    private PathMeasure pathMeasureArc;
    private PathMeasure pathMeasureLine;
    private Path mPathLine;
    private Path mPathArc;
    private float cx,cy;
    // 两边留出的边距
    private float drawPadding;
    // 搜索镜圆的半径
    private float radius;
    // 搜索的手柄长度
    private float handlerLen;
    // 目标路径
    private Path desPath;
    // 线条path的长度
    private float lineLength;
    private float startLen;
    private float endLen;
    private float arcLength;
    private float startArc;
    private float endArc;
    // 圆心x坐标最大移动的距离
    private float cxMaxMove;
    // 圆心cx移动的距离
    private float moveDistance;
    private RectF rectF;
    // 是否反转动画
    private boolean reverse;


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
        // 这个4.4要关闭硬件加速。。。
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        radius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 18, metrics);
        drawPadding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, metrics);
        handlerLen = 1.2f*radius;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, metrics));
        mPaint.setColor(Color.WHITE);

        mPathLine = new Path();
        mPathArc = new Path();
        desPath = new Path();
        setOnClickListener(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        cx = w/2f;
        cy = h/2f;
        //搜索圆的圆心到搜索柄起始距离
        float offsetRadius = (float) (Math.cos(Math.toRadians(45)) * radius );
        //搜索圆的圆心到搜索柄结束距离x，y的偏移量
        float offsetHandler = (float) (Math.cos(Math.toRadians(45)) * (radius + handlerLen));

        cxMaxMove = (float) (w/2f - drawPadding - Math.cos(Math.toRadians(45)) * (radius + handlerLen));
        cx += cxMaxMove;
        rectF = new RectF(cx - radius, cy - radius, cx + radius, cy + radius);
        // 这里采用2条path来实现
        mPathLine.moveTo(cx + offsetRadius, cy + offsetRadius);
        mPathLine.lineTo(w - drawPadding, cy + offsetHandler);
        mPathLine.lineTo(drawPadding, cy + offsetHandler);

        mPathArc.addArc(rectF, 45, 359.99f);

        pathMeasureLine = new PathMeasure(mPathLine, false);
        //这里得到的length是当前mPathMeasure指向线段的长度，并不是path总长度，
        // 如果要得到总长度需要通过nextContour来遍历mPathMeasure，得到每段长度再加起来
        lineLength = pathMeasureLine.getLength();

        pathMeasureArc =  new PathMeasure(mPathArc, false);
        arcLength = pathMeasureArc.getLength();

        startLen = 0 ;
        endLen = handlerLen;
        startArc = 0;
        endArc = arcLength;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.GREEN);
        desPath.reset();
        pathMeasureArc.getSegment(startArc, endArc, desPath, true);
        pathMeasureLine.getSegment(startLen, endLen, desPath, true);
        // 移动path
        desPath.offset(moveDistance - cxMaxMove, 0);
        canvas.drawPath(desPath, mPaint);
    }


    @Override
    public void onClick(View v) {
        if(reverse){
            reverseAnim();
        }else{
            startAnim();
        }
    }

    public void  startAnim(){
        startLen = 0;
        ValueAnimator animator = ValueAnimator.ofFloat(0, arcLength);
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction =  animation.getAnimatedFraction();
                if(fraction < 0.9f) {
                    startArc = (float) animation.getAnimatedValue()/0.9f;
                }else{
                    startArc = endArc;
                    startLen = (fraction-0.9f)/0.1f * handlerLen;
                }
                moveDistance = cxMaxMove * fraction ;
                endLen = fraction * lineLength ;
                endLen = Math.max(endLen,handlerLen);
                invalidate();
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                reverse = true;
            }
        });

        animator.start();
    }

    public void reverseAnim(){
        startLen = 0;
        ValueAnimator animator = ValueAnimator.ofFloat(0, arcLength);
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction =  animation.getAnimatedFraction();
                if(fraction < 0.9f) {
                    startArc = (float) animation.getAnimatedValue()/0.9f;
                }else{
                    //最后0.1f将手柄的动画完成
                    startArc = endArc;
                    startLen = (fraction-0.9f)/0.1f * handlerLen;
                }
                moveDistance = cxMaxMove * fraction ;
                endLen = fraction * lineLength ;
                endLen = Math.max(endLen,handlerLen);
                invalidate();
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                reverse = false;
            }
        });
        animator.reverse();
    }

}
