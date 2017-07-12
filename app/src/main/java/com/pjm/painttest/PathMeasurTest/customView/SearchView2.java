package com.pjm.painttest.PathMeasurTest.customView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
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
    private PathMeasure pathMeasure2;

    private PathMeasure dstMeasure;

    private Path mPathLine;
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
    private Path desPath2;
    private float startSearch;
    private float searchLen;

    private float linLen;
    private float startLine;

    private float cxMaxMove;


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
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        radius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 18, metrics);
        drawPadding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, metrics);
        handlerLen = 1.2f*radius;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, metrics));
        mPaint.setColor(Color.WHITE);
        mPathLine = new Path();
        mPathSearch = new Path();
        desPath = new Path();
        desPath2 = new Path();
        setOnClickListener(this);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        cx = w/2f;
        cy = h/2f;
        float offset = (float) (Math.cos(Math.toRadians(45)) * radius);
        //搜索圆的圆心距离右边的距离
        float distance = (float) (Math.cos(Math.toRadians(45)) * (radius + handlerLen));
        float y = (float) (cy + Math.cos(Math.toRadians(45)) * (radius + handlerLen));
        mPathLine.moveTo(width - drawPadding, y);
        mPathLine.lineTo(drawPadding,y);

        cxMaxMove = (float) (w/2f - drawPadding - Math.cos(Math.toRadians(45)) * (radius + handlerLen));



        pathMeasure = new PathMeasure(mPathSearch,true);
        pathMeasure2 = new PathMeasure(mPathLine,true);

        searchLen = pathMeasure.getLength();
        linLen = pathMeasure2.getLength();
        startLine = linLen;
        L.i("searchLen = " + searchLen + ", startLine = " + startLine);

        dstMeasure = new PathMeasure();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.GREEN);
        mPathSearch.reset();
        //mPathLine.reset();

        canvas.save();
        canvas.rotate(45, cx, cy);
        //画布旋转45度之后的路径
        mPathSearch.addCircle(cx, cy, radius, Path.Direction.CW);
        mPathSearch.moveTo(cx + radius, cy);
        mPathSearch.lineTo(cx + radius + handlerLen, cy);

        pathMeasure.getSegment(startSearch, searchLen - startSearch, desPath, false);
        dstMeasure.setPath(desPath,false);
        L.i("startSearch = "+ startSearch + " , dest len = " + dstMeasure.getLength());
        canvas.drawPath(desPath, mPaint);
        canvas.restore();

        canvas.drawPath(mPathLine, mPaint);

//        canvas.save();
//        canvas.rotate(45, cx, cy);
//        canvas.drawPath(mPathSearch, mPaint);
//        canvas.restore();
//        canvas.drawPath(mPathLine, mPaint);



//        desPath.reset();
//        desPath2.reset();
//        pathMeasure2.getSegment(0, linLen, desPath2, false);
//        canvas.drawPath(desPath2, mPaint);

//        boolean flag = pathMeasure.getSegment(startSearch, searchLen - startSearch, desPath, false);
//        L.e("flag = " + flag);
//        canvas.drawPath(desPath, mPaint);
//        canvas.restore();


//        desPath.reset();
//        desPath2.reset();
//        canvas.save();
//        boolean flag = pathMeasure.getSegment(startSearch, searchLen - startSearch, desPath, false);
//        L.e("flag = " + flag);
//        canvas.rotate(45, cx, cy);
//        canvas.drawPath(desPath, mPaint);
//        canvas.restore();
//        pathMeasure2.getSegment(0, linLen - startLine, desPath2, false);
//        canvas.drawPath(desPath2, mPaint);

    }


    @Override
    public void onClick(View v) {
        ValueAnimator animator = ValueAnimator.ofFloat(0, searchLen);
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                L.e("value = " + animation.getAnimatedValue());
                startSearch = (float) animation.getAnimatedValue();
                startLine = linLen * (1-animation.getAnimatedFraction());
                cx = width/2f + cxMaxMove * animation.getAnimatedFraction();
                invalidate();
            }
        });
        animator.start();
    }




}
