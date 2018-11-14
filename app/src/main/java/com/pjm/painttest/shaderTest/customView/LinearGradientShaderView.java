package com.pjm.painttest.shaderTest.customView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 *  线性渐变view测试
 */

public class LinearGradientShaderView extends View {

    private Paint paint;
    private RectF rectf1;
    private RectF rectf2;
    private RectF rectf3;
    private LinearGradient linearGradient1,linearGradient2,linearGradient3;
    private int[] colors = {Color.RED, 0xffff6600 ,Color.YELLOW, Color.GREEN, Color.BLUE, 0xffff00b2};

    public LinearGradientShaderView(Context context) {
        this(context, null);
    }

    public LinearGradientShaderView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinearGradientShaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        //设置线性渐变的梯度区域
        rectf1  = new RectF(0, 0, 300, 300);
        // 0, 0 ,300,300  --> 表示 从（0,0） 到（300,0） 水平方向的梯度变化
        linearGradient1 = new LinearGradient(0, 0, 300, 0, colors, null,Shader.TileMode.CLAMP);


        rectf2  = new RectF(400, 0, 700, 300);
        //400, 0, 400, 300  --> 表示 从（400,0） 到（400,300） 竖直方向的梯度变化
        linearGradient2 = new LinearGradient(400, 0, 400, 300, colors, null,Shader.TileMode.CLAMP);

        rectf3  = new RectF(0, 400, 300, 700);
        // 0, 900 ,300,1200  --> 表示 从（0,900） 到（300,1200） 对角线方向的梯度变化
        linearGradient3 = new LinearGradient(0, 400 ,300,700, colors, null,Shader.TileMode.CLAMP);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setShader(linearGradient1);
        paint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,12,getContext().getResources().getDisplayMetrics()));
        canvas.drawRect(rectf1,paint);
        canvas.drawText("水平方向渐变", 0, 350, paint);

        paint.setShader(linearGradient2);
        canvas.drawRect(rectf2,paint);
        canvas.drawText("竖直方向渐变", 450, 350, paint);

        paint.setShader(linearGradient3);
        canvas.drawRect(rectf3,paint);
        canvas.drawText("对角线方向渐变", 0, 750, paint);
    }
}
