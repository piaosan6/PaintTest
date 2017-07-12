package com.pjm.painttest.PathTest.customView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import com.pjm.painttest.R;

/**
 * 拖拽气泡view
 */

public class DragBubbleView2 extends View {

    /** 默认状态*/
    private static final int DEFAULT = 0;
    /** 拉伸状态*/
    private static final int TENSION = 1;
    /** 分离状态*/
    private static final int SEPARATE = 2;
    /** 消失状态*/
    private static final int DISMISS = 3;

    private int width;
    private int height;

    private float centerX;
    private float centerY;

    private float moveCenterX;
    private float moveCenterY;

    private int currentState;

    private Paint mPaint;
    private Paint mPaint1;
    private Paint wordPaint;

    private String text;
    private int bgColor;
    private Rect bounds;
    private RectF boundsF;
    // 小球的半径
    private float radius;
    //固定点小球的最小半径
    private float minRadius;
    // 原来位置小球半径变化的差值
    private float scalDx;

    private double degress;
    // 移动的最小距离
    private float mixMoveDistance;
    // 移动的最大距离
    private float maxMoveDistance;

    /**
     *  气泡爆炸的图片id数组
     */
    private int[] mBurstDrawablesArray = {R.mipmap.burst_1, R.mipmap.burst_2 , R.mipmap.burst_3, R.mipmap.burst_4, R.mipmap.burst_5};

    /**
     *  气泡爆炸的bitmap数组
     */
    private Bitmap[] mBurstBitmapsArray;


    private float controlX;
    private float controlY;
    private float stillStarX;
    private float stillStarY;
    private float stillEndX;
    private float stillEndY;
    private float moveStarX;
    private float moveStarY;
    private float moveEndX;
    private float moveEndY;


    private Path mPath;
    private float distance;
    // 当前图片选着的下标
    private int currentIndex;

    public void setText(String text) {
        this.text = text;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    public DragBubbleView2(Context context) {
        this(context, null);
    }

    public DragBubbleView2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragBubbleView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        mixMoveDistance = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        minRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 3, metrics);
        maxMoveDistance = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 100, metrics);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.RED);

        mPaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint1.setStyle(Paint.Style.STROKE);
        mPaint1.setStrokeWidth(1);
        mPaint1.setColor(Color.BLUE);

        mPath = new Path();

        wordPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        wordPaint.setStyle(Paint.Style.FILL);
        wordPaint.setColor(Color.WHITE);
        wordPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, metrics));

        bounds = new Rect();
        boundsF = new RectF();
        text = "99";
        mBurstBitmapsArray = new Bitmap[mBurstDrawablesArray.length];
        for (int i = 0; i < mBurstDrawablesArray.length; i++) {
            //将气泡爆炸的drawable转为bitmap
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), mBurstDrawablesArray[i]);
            mBurstBitmapsArray[i] = bitmap;
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        switch (currentState){
            case DEFAULT:
                // 1、画气泡静止状态
                 drawDefault(canvas, centerX, centerY);
                break;

                //拉伸状态
            case TENSION:

//                mPath.moveTo(movePointA.x, movePointA.y);
//                mPath.lineTo(stillPointA.x, stillPointA.y);
//                mPath.lineTo(stillPointB.x, stillPointB.y);
//                mPath.lineTo( movePointB.x, movePointB.y);
//                mPath.close();
//                canvas.drawPath(mPath, mPaint1);

                // 1、画原点变小的小球
                //if(distance > radius)
                    canvas.drawCircle(centerX, centerY, radius - scalDx, mPaint);
                // 2、画之间连接的地方

                controlX =(moveCenterX + centerX) / 2f;
                controlY =(moveCenterY + centerY) / 2f;

                scalDx = (distance/maxMoveDistance) * (radius - minRadius) ;

                float sin = (moveCenterY - centerY)/distance;
                float cos = (moveCenterX - centerX)/distance;

                // 计算点
                stillStarX = centerX - sin*(radius - scalDx);
                stillStarY = centerY + cos*(radius - scalDx);
                stillEndX  = centerX + sin*(radius - scalDx);
                stillEndY  = centerY - cos*(radius - scalDx);

                moveStarX = moveCenterX - sin*radius;
                moveStarY = moveCenterY + cos*radius;
                moveEndX  = moveCenterX + sin*radius;
                moveEndY  = moveCenterY - cos*radius;


                mPath.moveTo(stillStarX, stillStarY);
                mPath.quadTo(controlX, controlY , moveStarX, moveStarY);
                mPath.lineTo(moveEndX, moveEndY);
                mPath.quadTo(controlX, controlY , stillEndX, stillEndY);
                mPath.close();
                canvas.drawPath(mPath, mPaint);
                // 3、画移动点的小球
                drawDefault(canvas, moveCenterX, moveCenterY);

                break;

            //分离状态
            case SEPARATE:
                drawDefault(canvas, moveCenterX, moveCenterY);
                break;

            case DISMISS:
                //执行爆炸动画
                Bitmap bm = mBurstBitmapsArray[currentIndex];
                canvas.drawBitmap(bm, moveCenterX - bm.getWidth()/2f, moveCenterY - bm.getHeight()/ 2f, null);
                break;

        }


    }

    // 画圆圈背景和文字
    private void drawDefault(Canvas canvas, float cx, float cy) {
        wordPaint.getTextBounds(text,0, text.length(), bounds);
        int bw = bounds.width();
        int bh = bounds.height();
        float max = Math.max(bw, bh);
        radius = max;
        canvas.drawCircle(cx, cy, radius, mPaint);
        canvas.drawText(text, cx - bw/2f , cy + bh/2f, wordPaint);

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h ;
        centerX = w/2f;
        centerY = h/2f;
        radius = Math.min(w,h)/2f;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        moveCenterX = event.getX();
        moveCenterY = event.getY();
//        float dx = moveCenterX - centerX;
//        float dy = moveCenterY - centerY;
        distance = (float) Math.hypot(moveCenterX - centerX, moveCenterY - centerY);
        switch (action){
            case MotionEvent.ACTION_DOWN:
                if(currentState != DISMISS){
                    if(distance > radius + mixMoveDistance){
                        currentState = DEFAULT;
                    }else{
                        currentState = TENSION;
                    }

                }

                break;

            case MotionEvent.ACTION_MOVE:
                if(currentState == TENSION){
                    if( distance > mixMoveDistance && distance < maxMoveDistance){
                        currentState = TENSION;
                    }else if ( distance > mixMoveDistance){
                        currentState = SEPARATE;
                    }

                }
                invalidate();
                break;

            case MotionEvent.ACTION_UP:
                switch (currentState){
                    case TENSION:
                        // 执行回弹动画
                        currentState = SEPARATE;
                        startSpringBackAnim();
                        break;

                    case SEPARATE:
                        if(distance > maxMoveDistance){
                            currentState = DISMISS;
                            // 执行爆炸动画
                            startDismissAnim();

                        }else{
                           //执行回弹动画
                            currentState = SEPARATE;
                            startSpringBackAnim();
                        }

                        break;
                    default:

                        break;
                }

        }
        return true;
    }

    private void startDismissAnim() {
        ValueAnimator animator = ValueAnimator.ofInt(0, mBurstBitmapsArray.length);
        animator.setDuration(500);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentIndex = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                currentState = DEFAULT;
            }
        });
        animator.start();
    }

    // 回弹动画
    private void startSpringBackAnim() {
        ValueAnimator animator = ValueAnimator.ofObject(new MyPointFEvaluator(),
                new PointF(moveCenterX, moveCenterY), new PointF(centerX, centerY));
        animator.setDuration(100);
        animator.setInterpolator(new OvershootInterpolator(5f));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                moveCenterX = pointF.x;
                moveCenterY = pointF.y;
                distance = (float) Math.hypot(moveCenterX - centerX, moveCenterY - centerY);
                invalidate();
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                currentState = DEFAULT;
            }
        });
        animator.start();

    }


    private class MyPointFEvaluator implements TypeEvaluator<PointF> {

        private PointF mPoint;

        @Override
        public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
            float x = startValue.x + (fraction * (endValue.x - startValue.x));
            float y = startValue.y + (fraction * (endValue.y - startValue.y));
            if (mPoint != null) {
                mPoint.set(x, y);
                return mPoint;
            } else {
                return new PointF(x, y);
            }
        }

    }


}
