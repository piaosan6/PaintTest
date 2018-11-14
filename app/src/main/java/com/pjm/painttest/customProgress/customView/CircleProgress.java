package com.pjm.painttest.customProgress.customView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import com.pjm.painttest.R;

/**
 * Created by Administrator on 2017/6/14 0014.
 */

public class CircleProgress extends View {

    private static final int DEFAULT_MAX_PROGRESS = 100;

    private Paint paint;
    private int ArcWidth;
    private int progressBackGroundColor;
    private int progressColor;
    private int textColor;
    private int textSize;
    // 半径
    private float radius;
    private int maxProgress ;
    // 百分比
    private int percent;

    private float centerX ,centerY;

    private RectF rectf;

    /**
     * 获取dimension的方法有几种，区别不大
     * 共同点是都会将dp，sp的单位转为px，px单位的保持不变
     *
     * getDimension() 返回float，
     * getDimensionPixelSize 返回int 小数部分四舍五入
     * getDimensionPixelOffset 返回int，但是会抹去小数部分
     */


    public CircleProgress(Context context) {
        this(context, null);
    }

    public CircleProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        paint = new Paint();
        paint.setAntiAlias(true);
        rectf = new RectF();
        DisplayMetrics dm = getResources().getDisplayMetrics();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleProgress);
        ArcWidth = a.getDimensionPixelSize(R.styleable.CircleProgress_ArcWidth, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30 ,dm));
        progressBackGroundColor = a.getColor(R.styleable.CircleProgress_progressBackGroundColor, Color.GRAY);
        progressColor = a.getColor(R.styleable.CircleProgress_progressColor,Color.GREEN);
        textSize = a.getDimensionPixelSize(R.styleable.CircleProgress_textSize, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10 ,dm));
        textColor = a.getColor(R.styleable.CircleProgress_textColor, Color.BLACK);
        maxProgress = a.getInteger(R.styleable.CircleProgress_maxProgress,DEFAULT_MAX_PROGRESS);

        a.recycle();

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = Math.min(w, h) / 2;
        centerY = centerX;
        radius = (centerX - ArcWidth/2)  ;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(progressBackGroundColor);

        paint.setStrokeWidth(1);
        paint.setColor(Color.BLACK);
        // 为了检查文字是否居中的线条
//        canvas.drawLine(0,centerY,centerX *2,centerY,paint);
//        canvas.drawLine(centerY, 0, centerY, centerX *2,paint);

        paint.setStrokeWidth(ArcWidth);
        // 画背景圆
        canvas.drawCircle(centerX,centerY,radius,paint);
        // 画圆弧
        paint.setColor(progressColor);
        rectf.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
        canvas.drawArc(rectf, 0, 360 * percent/maxProgress, false, paint);

        // 画中心的文字
        String text = percent + "%";
        //paint.setTextAlign(Paint.Align.CENTER);
        paint.setStrokeWidth(0);
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        Paint.FontMetrics fm = paint.getFontMetrics();

        // 计算出文字基线的位置
        float textBaseLine = centerY + (fm.bottom - fm.top)/2 - fm.bottom ;
        canvas.drawText(text, centerX - paint.measureText(text) / 2, textBaseLine, paint);

    }

    public void setPercent(int percent){
        this.percent = percent;
        postInvalidate();
    }

    public void setPercentWithAnim(int percent){

        ValueAnimator animator = ValueAnimator.ofInt(0,percent);
        animator.setDuration(2000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                setPercent(value);
            }
        });
        animator.start();

    }

}
