package com.pjm.painttest.PathMeasurTest.customView;

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

import com.pjm.painttest.Utils.L;

/**
 *  之前有通过canvas 旋转来实现，这次用PathMeasure来实现
 */

public class SearchView2 extends View implements View.OnClickListener{

    private Paint mPaint;
    private PathMeasure pathMeasure;
    private Path mPathSearch;
    private float width;
    private float height;
    private float cx,cy;
    // 两边留出的边距
    private float drawPadding;
    // 搜索镜圆的半径
    private float radius;
    // 搜索的手柄长度
    private float handlerLen;

    private Path desPath;
    private float totalLength;
    private float startLength;
    private float endLength;

    private float cxMaxMove;
    private float cxMoveDistance;
    private RectF rectF;
    private float minLen;
    private boolean reverser;


    public SearchView2(Context context) {
        this(context, null);
    }

    public SearchView2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //setLayerType(View.LAYER_TYPE_SOFTWARE, null);
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
        mPathSearch = new Path();
         
        desPath = new Path();
        setOnClickListener(this);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        cx = w/2f;
        cy = h/2f;
        //搜索圆的圆心到搜索柄结束距离x，y的偏移量
        float offsetHandler = (float) (Math.cos(Math.toRadians(45)) * (radius + handlerLen));

        cxMaxMove = (float) (w/2f - drawPadding - Math.cos(Math.toRadians(45)) * (radius + handlerLen));
        cx += cxMaxMove;
        rectF = new RectF(cx - radius, cy - radius, cx + radius, cy + radius);
        // 这里不要弄成360°，360°后系统优化之后起点位置变成了时钟3点位置，这里采用1个path来解决
        mPathSearch.addArc(rectF, 45, 359.99f);
        mPathSearch.lineTo(cx + offsetHandler, cy + offsetHandler);
        mPathSearch.lineTo(drawPadding, cy + offsetHandler);
        pathMeasure = new PathMeasure(mPathSearch, false);

        //这里得到的length是当前mPathMeasure指向线段的长度，并不是path总长度，
        // 如果要得到总长度需要通过nextContour来遍历mPathMeasure，得到每段长度再加起来
//        while (pathMeasure.nextContour()){
//            float len = pathMeasure.getLength();
//            L.i("len = " + len);
//            totalLength += len;
//        }
        totalLength = pathMeasure.getLength();
        L.i("totalLength = " + totalLength);
        //使用nextContour 返回false 后，会影响PathMeasure后面的截取，getSegment返回false

        minLen = (float) (Math.PI * 2 * radius + handlerLen);
        startLength = 0 ;
        endLength = minLen;
        pathMeasure.getLength();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.GREEN);

        desPath.reset();
        // 硬件加速的BUG
        desPath.lineTo(0, 0);

        boolean flag = pathMeasure.getSegment(startLength, endLength, desPath, true);
        L.i("startLength = " + startLength + ", endLength = " + endLength  + ",flag = " +flag);
        desPath.offset(cxMoveDistance- cxMaxMove, 0);
        canvas.drawPath(desPath, mPaint);

    }


    @Override
    public void onClick(View v) {
        if(reverser){
           reverseAnim();
        }else{
            startAnim();
        }
    }

    public void startAnim(){
        ValueAnimator animator = ValueAnimator.ofFloat(0, minLen);
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                startLength = (float) animation.getAnimatedValue();
                float fraction = animation.getAnimatedFraction();
                cxMoveDistance = fraction * cxMaxMove;
                endLength = minLen + fraction  * (totalLength - minLen) ;
                invalidate();
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                reverser = true;
            }
        });

        animator.start();
    }

    public void reverseAnim(){
        ValueAnimator animator = ValueAnimator.ofFloat(0, minLen);
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                startLength = (float) animation.getAnimatedValue();
                float fraction = animation.getAnimatedFraction();
                cxMoveDistance = fraction * cxMaxMove;
                endLength = minLen + fraction  * (totalLength - minLen) ;
                invalidate();
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                reverser = false;
            }
        });
        animator.reverse();
    }

}
