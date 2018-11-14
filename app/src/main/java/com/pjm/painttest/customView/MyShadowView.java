package com.pjm.painttest.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.pjm.painttest.R;

public class MyShadowView extends View {

    private float mShadowDx;
    private float mShadowDy;
    private int mShadowColor;
    private float mShadowRadius;
    private String text;
    private Paint mPaint;
    private RectF rectF;
    private float width;
    private float height;
    private int bgStartColor;
    private int bgEndColor;
    private int mAngle; //背景渐变角度


    public MyShadowView(Context context) {
        this(context, null);
    }

    public MyShadowView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyShadowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs){
        // 关闭硬件加速,setShadowLayer 才会有效
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        //setWillNotDraw(false);

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MyShadowView);
        bgStartColor = a.getColor(R.styleable.MyShadowView_bgStartColor,  Color.TRANSPARENT);
        bgEndColor = a.getColor(R.styleable.MyShadowView_bgEndColor,  Color.TRANSPARENT);
        mShadowDx = a.getDimension(R.styleable.MyShadowView_shadowDx, 0);
        mShadowDy = a.getDimension(R.styleable.MyShadowView_shadowDy, 0);
        mShadowRadius = a.getDimension(R.styleable.MyShadowView_shadowRadius, 0);
        mShadowColor = a.getColor(R.styleable.MyShadowView_shadowColor, Color.TRANSPARENT);
        mAngle = a.getInteger(R.styleable.MyShadowView_shadowAngle, 0);

        a.recycle();

//        mShadowDx = 0;
//        mShadowDy = dpToPx(3.3f);
//        mShadowRadius = dpToPx(2f);
//        mShadowColor =0x2321a0ff;//0xff21a0ff;

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        rectF = new RectF(mShadowDx, mShadowDy, w - 2*mShadowDx, h - 2*mShadowDy);
        // 0xff219fff, 0xff66d5ff
        Shader mShader = new LinearGradient(mShadowDx, mShadowDy, w - 2*mShadowDx, mShadowDy,
                new int[] {bgStartColor, bgEndColor}, null, Shader.TileMode.REPEAT);
        mPaint.setShader(mShader);
        mPaint.setShadowLayer(mShadowRadius, mShadowDx, mShadowDy, mShadowColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRoundRect(rectF, dpToPx(30), dpToPx(30), mPaint);
    }

    public float dpToPx(float dp) {
        return dp * getContext().getResources().getDisplayMetrics().density;
    }

}
