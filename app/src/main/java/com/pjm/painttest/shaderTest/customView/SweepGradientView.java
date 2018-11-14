package com.pjm.painttest.shaderTest.customView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.pjm.painttest.utils.L;

/**
 * 扫描渲染 view
 */

public class SweepGradientView extends View{

    private Paint sweepPaint;
    private Paint circlePaint;
    private RectF rectf;
    private int[] colors = {Color.RED, 0xffff6600 ,Color.YELLOW, Color.GREEN, Color.BLUE, 0xffff00b2,Color.TRANSPARENT};
    private SweepGradient sweepGradient;
    private float startAngle;
    private float sweepAngle = 300;
    private ValueAnimator animator;
    private Matrix mMatrix;

    public SweepGradientView(Context context) {
        this(context, null);
    }

    public SweepGradientView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SweepGradientView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        rectf = new RectF();
        rectf.set(200, 200, 600, 600);

        sweepPaint = new Paint();
        sweepPaint.setAntiAlias(true);
        sweepPaint.setColor(0x9D00ff00);

        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(0x99000000);

        mMatrix = new Matrix();

        sweepGradient = new SweepGradient(400, 400, Color.TRANSPARENT, Color.GREEN  );
        sweepPaint.setShader(sweepGradient);
        startAnim();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(400, 400, 200, circlePaint);

        sweepPaint.setShader(sweepGradient);
        canvas.drawCircle(400, 400, 200, sweepPaint);

//        canvas.drawCircle(400, 400, 200, circlePaint);
//        sweepPaint.setShader(sweepGradient);
//        canvas.drawArc(rectf, startAngle, sweepAngle, true, sweepPaint);

    }

    public void startAnim(){
        animator = ValueAnimator.ofFloat(0, 360);
        animator.setDuration(2000);
        animator.setInterpolator(new LinearInterpolator(getContext(),null));
        animator.setRepeatCount(2);
//        animator.setRepeatMode(ValueAnimator.RESTART);
//        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                startAngle = (float) animation.getAnimatedValue();
                L.i("--------------> startAngle = " + startAngle);
                mMatrix.setRotate(startAngle, 400, 400);
                sweepPaint.getShader().setLocalMatrix(mMatrix);
                invalidate();
            }
        });
        animator.start();
    }

    public void stopAnim(){
        L.e("--------------> stopAnim");
        if(animator != null && animator.isRunning()){
            animator.cancel();
        }
    }


}
