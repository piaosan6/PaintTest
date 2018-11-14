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

import com.pjm.painttest.utils.L;

/**
 *  loadingView
 */

public class LoadingView extends View  implements View.OnClickListener{

    private Paint mPaint;
    private Path mPath;
    private Path mCirclePath;
    private Path dstPath;
    // 小圆的矩形
    private RectF bigOval;
    // 大圆的矩形
    private RectF smallOval;
    private float cx;
    private float cy;
    private float radius;
    private float handlerLen;

    private PathMeasure pathMeasure;
    private PathMeasure circlePathMeasure;
    private float start;
    private float end;
    // 搜索镜的长度
    private float searchLength;
    private float circleLength;

    private State state;


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

    private void init() {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        radius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 18, metrics);
        handlerLen = 1.2f * radius;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, metrics));
        mPaint.setColor(Color.WHITE);

        mPath = new Path();
        mCirclePath = new Path();
        dstPath = new Path();

        setOnClickListener(this);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.GREEN);
        dstPath.reset();
        dstPath.lineTo(0, 0);
        if(state != null){
            state.draw(canvas);
        }else {
            canvas.drawPath(mPath, mPaint);
        }


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        cx = w/2f;
        cy = h/2f;
        smallOval = new RectF(cx - radius, cy - radius, cx + radius, cy + radius);
        float offset = (float) (Math.cos(Math.toRadians(45)) * (radius + handlerLen));
        mPath.addArc(smallOval, 45, 359.99f);
        mPath.lineTo(cx + offset, cy + offset);
       // searchLength = (float) (Math.PI * 2 * radius + handlerLen);

        pathMeasure = new PathMeasure(mPath, false);
        searchLength = pathMeasure.getLength();
        start = 0;
        end = searchLength;

        float bigRadius = radius + handlerLen;
        bigOval = new RectF(cx - bigRadius, cy - bigRadius, cx + bigRadius, cy + bigRadius);
        mCirclePath.addArc(bigOval, 45, 359.99f);
        circlePathMeasure = new PathMeasure(mCirclePath, false);
        circleLength = circlePathMeasure.getLength();
        L.i("searchLength = " + searchLength + ", circleLength = " + circleLength);

    }


    @Override
    public void onClick(View v) {
        state = new searchState();
        invalidate();
    }

    public class searchState implements State{

        public searchState(){
            ValueAnimator animator = ValueAnimator.ofFloat(0, searchLength);
            animator.setDuration(1000);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    start = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    state = new LoadingState();
                }
            });
            animator.start();
        }

        @Override
        public void draw(Canvas canvas) {
            pathMeasure.getSegment(start, end, dstPath, true);
            canvas.drawPath(dstPath, mPaint);
        }

    }

    public class LoadingState implements State{

        public LoadingState(){
            ValueAnimator anim = ValueAnimator.ofFloat(0, circleLength);
            anim.setDuration(2000);
            anim.setRepeatCount(4);
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    start = (float) animation.getAnimatedValue();

                    end = (float) (start + Math.abs((0.5- animation.getAnimatedFraction()*circleLength/6)));
                    invalidate();
                }
            });
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    state = null;
                    invalidate();
                }
            });
            anim.start();
        }


        @Override
        public void draw(Canvas canvas) {
            circlePathMeasure.getSegment(start, end, dstPath, true);
            canvas.drawPath(dstPath, mPaint);
        }

    }


    public interface State{
        void draw(Canvas canvas);
    }




}
