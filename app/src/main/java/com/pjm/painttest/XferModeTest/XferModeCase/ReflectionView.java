package com.pjm.painttest.XferModeTest.XferModeCase;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.pjm.painttest.R;

/**
 * Created by Administrator on 2017/6/26 0026.
 */

public class ReflectionView extends View {

    private Paint mPaint;
    private Bitmap bm;
    private int width;
    private int height;
    private Bitmap resultBm;
    private Bitmap rflectBm;
    private Matrix matrix;
    private LinearGradient linearGradient;

    public ReflectionView(Context context) {
        this(context, null);
    }

    public ReflectionView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ReflectionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);



        bm = BitmapFactory.decodeResource(getResources(), R.mipmap.zm);
        width = bm.getWidth();
        height = bm.getHeight();

        matrix = new Matrix();
        matrix.preScale(1, -0.75f);

        /*resultBm =  Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        rflectBm = Bitmap.createBitmap(bm.getWidth(), height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(rflectBm);

        linearGradient = new LinearGradient(0, 0, 0, height, 0x8fffffff, 0x00ffffff, Shader.TileMode.CLAMP);
        RectF rect = new RectF(0, 0, width, height);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setShader(linearGradient);
        canvas.drawRect(rect, mPaint);

        mPaint.setShader(null);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(resultBm, 0, 0 ,mPaint);*/

        rflectBm = Bitmap.createBitmap(bm, 0, 0, width, (int) (0.75f*height), matrix, false);
        Canvas canvas = new Canvas(rflectBm);

        linearGradient = new LinearGradient(0, 0, 0, (int) (0.75f*height), 0x7fffffff, 0x00ffffff, Shader.TileMode.CLAMP);
        RectF rect = new RectF(0, 0, width, (int) (0.75f*height));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setShader(linearGradient);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawRect(rect, mPaint);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(width, (int) (1.75f*height));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bm, 0, 0, null);
        canvas.drawBitmap(rflectBm, 0, height, null);
    }

}
