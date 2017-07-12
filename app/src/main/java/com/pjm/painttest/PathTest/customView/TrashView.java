package com.pjm.painttest.PathTest.customView;

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
 *  垃圾桶View
 */

public class TrashView extends View implements View.OnClickListener{

    private Paint mPaint;
    private float strokedWidth;
    private Path mPath;
    // 上边可以移动的路径
    private Path mPathTop;
    private float centerX;
    private float centerY;
    // 垃圾桶右边旋转点的X,Y
    private float rotateX;
    private float rotateY;
    private float degrees;
    // 上盖的长度
    private float topLen;
    // 中间线条高度的一半
    private float halfMiddleHeight;
    // 总高度的一半
    private float halfTotalHeight;
    //水平间距
    private float horDistance;
    // 矩形的高度
    private float rectHeight;
    private float radius;
    // 上边盖高度多出的一些空隙
    private float drawPadding;



    public TrashView(Context context) {
        this(context, null);
    }

    public TrashView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TrashView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        halfMiddleHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, metrics);
        halfTotalHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, metrics);
        horDistance = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, metrics);
        radius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, metrics);
        rectHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, metrics);
        drawPadding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, metrics);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.GRAY);
        mPaint.setStyle(Paint.Style.STROKE);
        strokedWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, metrics);
        mPaint.setStrokeWidth(strokedWidth);

        mPath = new Path();
        mPathTop = new Path();

        setOnClickListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 添加上面的盖子和圆
        mPathTop.reset();
        canvas.save();
        canvas.rotate(degrees, rotateX, rotateY);
        mPathTop.moveTo(rotateX + drawPadding*3 , rotateY - drawPadding);
        mPathTop.lineTo(rotateX - drawPadding*3 - topLen, centerY - halfTotalHeight - drawPadding);
        mPathTop.moveTo(rotateX - topLen/2f, centerY - halfTotalHeight - radius - drawPadding);
        mPathTop.addRect(centerX - 2*rectHeight, centerY - halfTotalHeight - 2*rectHeight - drawPadding, centerX + 2*rectHeight,
                centerY - halfTotalHeight - drawPadding, Path.Direction.CW);
        //mPathTop.addCircle(rotateX - topLen/2f, centerY - halfTotalHeight - radius - drawPadding, radius, Path.Direction.CW);
        canvas.drawPath(mPathTop, mPaint);
        canvas.restore();

        canvas.drawPath(mPath, mPaint);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        centerX = w/2f;
        centerY = h/2f;
        L.e("centerX = " + centerX + " , centerY = " + centerY);

        // 画中间3条线
        mPath.moveTo(centerX , centerY - halfMiddleHeight);
        mPath.lineTo(centerX , centerY + halfMiddleHeight);

        mPath.moveTo(centerX - horDistance, centerY - halfMiddleHeight);
        mPath.lineTo(centerX - horDistance, centerY + halfMiddleHeight);

        mPath.moveTo(centerX + horDistance, centerY - halfMiddleHeight);
        mPath.lineTo(centerX + horDistance, centerY + halfMiddleHeight);

        float offset = (float) Math.tan(Math.toRadians(30) * halfTotalHeight *2);
        rotateX = centerX + horDistance * 3 + offset;
        rotateY = centerY - halfTotalHeight;

        mPath.moveTo(rotateX, rotateY);
        // 画右边斜边
        mPath.lineTo(centerX + horDistance * 2, centerY + halfTotalHeight);
        // 画底部的连线
        mPath.lineTo(centerX - horDistance * 2, centerY + halfTotalHeight);
        // 画左边斜边
        mPath.lineTo(centerX - horDistance * 3 - offset, centerY - halfTotalHeight);
        topLen = 2 * (horDistance * 3 +  offset);
    }

    @Override
    public void onClick(View v) {
        ValueAnimator animator = ValueAnimator.ofFloat(0,30,45,45,30,0);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(2000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                degrees = (float) animation.getAnimatedValue();
                L.i("degrees = " + degrees);
                invalidate();
            }
        });
        animator.start();
    }
}
