package com.pjm.painttest.LoadingView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

/**
 * Created by Administrator on 2017/6/20 0020.
 */

public class SplashView extends View {

    // 扩展动画最大半径，对角线的一半
    private float maxRadius;
    private int[] colors = {Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN, Color.DKGRAY, Color.MAGENTA};
    // 小球的画笔
    private Paint ballPaint;
    // 扩散动画的画笔
    private Paint spreadPaint;
    // 小球的半径
    private float ballRadius = 18;
    // 大圆的半径
    private float bigRadius = 90;
    //当前大圆的旋转角度
    private float mCurrentRotationAngle;
    // 圆心X
    private float centerY;
    // 圆心Y
    private float centerX;
    // 当前状态 state
    private State state;
    private ValueAnimator rotateAnimator;
    private Handler mHander;


    public SplashView(Context context) {
        this(context, null);
    }

    public SplashView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SplashView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        ballPaint = new Paint();
        ballPaint.setAntiAlias(true);
        ballPaint.setStyle(Paint.Style.FILL);

        spreadPaint = new Paint();
        spreadPaint.setStyle(Paint.Style.STROKE);
        spreadPaint.setColor(Color.WHITE);

        mHander = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == 1){
                    if(rotateAnimator != null){
                        rotateAnimator.cancel();
                        state = new TogetherState();
                        invalidate();
                    }
                }
            }

        };

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w / 2f;
        centerY = h / 2f;
        maxRadius = (float) (Math.sqrt(w*w + h*h)/2f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(state == null){
            state = new RotateState();
        }
        state.draw(canvas);
    }

    public interface State{
        void draw(Canvas canvas);
    }

    /**
     *  旋转动画
     */
    class RotateState implements State{

        public RotateState() {
            rotateAnimator = ValueAnimator.ofFloat(0, (float) Math.PI * 2 );
            rotateAnimator.setDuration(1200);
            rotateAnimator.setInterpolator(new LinearInterpolator());
            rotateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mCurrentRotationAngle = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });
            rotateAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    rotateAnimator.start();
                }
            });

            rotateAnimator.start();
            mHander.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadingOver();
                }
            },2000);

        }

        @Override
        public void draw(Canvas canvas) {
            drawBallCircles(canvas);

        }

    }



    /**
     *  聚合动画
     */
    class TogetherState implements State{

        public TogetherState() {
            //计算某个时刻当前的大圆半径是多少？ r~0中的某个值
            ValueAnimator togetherAnimator = ValueAnimator.ofFloat(0, bigRadius);
            togetherAnimator.setInterpolator(new OvershootInterpolator(10f));
            togetherAnimator.setDuration(1200);
            togetherAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    bigRadius = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });
            togetherAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    state = new SpreadState();
                    invalidate();
                }
            });
           // togetherAnimator.start();
            // 反转动画
            togetherAnimator.reverse();

        }

        @Override
        public void draw(Canvas canvas) {
            drawBallCircles(canvas);
        }
    }

    /**
     *  扩散动画
     */
    class SpreadState implements State{

        float paintWidth;

        public SpreadState(){
            ValueAnimator animator = ValueAnimator.ofFloat(2*maxRadius - ballRadius,0);
            //animator.setInterpolator(new LinearInterpolator());
            animator.setDuration(1200);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    paintWidth = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    SplashView.this.setVisibility(GONE);
                }
            });
            animator.start();
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.drawColor(Color.TRANSPARENT);
            spreadPaint.setStrokeWidth(paintWidth);
            canvas.drawCircle(centerX, centerY, maxRadius, spreadPaint);
        }
    }

    /**
     *  画小球
     * @param canvas
     */
    private void drawBallCircles(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        int count = colors.length;
        for (int i = 0; i < count; i++){
            float x = (float) (centerX + bigRadius * Math.cos(i * Math.PI * 2 / count + mCurrentRotationAngle));
            float y = (float) (centerY + bigRadius * Math.sin(i * Math.PI * 2 / count + mCurrentRotationAngle));
            ballPaint.setColor(colors[i]);
            canvas.drawCircle(x, y, ballRadius, ballPaint);
        }
    }

    public void  loadingOver(){
        mHander.sendEmptyMessage(1);
    }


}
