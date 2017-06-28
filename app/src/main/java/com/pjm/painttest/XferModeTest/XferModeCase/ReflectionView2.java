package com.pjm.painttest.XferModeTest.XferModeCase;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.pjm.painttest.R;

/**
 * 只适合1倍的完全倒影
 */

public class ReflectionView2 extends View {

    private Paint mPaint;
    private Bitmap bm;
    private int width;
    private int height;
    private LinearGradient linearGradient;
    private Rect src,dst;

    public ReflectionView2(Context context) {
        this(context, null);
    }

    public ReflectionView2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ReflectionView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);

        bm = BitmapFactory.decodeResource(getResources(), R.mipmap.zm);
        width = bm.getWidth();
        height = bm.getHeight();
        BitmapShader shader = new BitmapShader(bm, Shader.TileMode.MIRROR, Shader.TileMode.MIRROR);
        mPaint.setShader(shader);

       // src = new Rect(0, 0, width, height);
        dst = new Rect(0, 0, width, 2*height);




    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(width, 2*height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(dst, mPaint);
        mPaint.setShader(null);

        linearGradient = new LinearGradient(0, height, 0, 2*height, 0x7fffffff, 0x00ffffff, Shader.TileMode.CLAMP);
        RectF rect = new RectF(0, height, width, 2*height);

        mPaint.setShader(linearGradient);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawRect(rect, mPaint);
    }

}
