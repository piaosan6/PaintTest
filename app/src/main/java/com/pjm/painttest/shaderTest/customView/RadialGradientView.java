package com.pjm.painttest.shaderTest.customView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 *  环形渐变view 测试
 */

public class RadialGradientView extends View {

    private Paint paint;
    private RectF rectf;
    private int[] colors = {Color.RED, 0xffff6600 ,Color.YELLOW, Color.GREEN, Color.BLUE, 0xffff00b2};

    public RadialGradientView(Context context) {
        this(context, null);
    }

    public RadialGradientView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadialGradientView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        rectf = new RectF();
        paint = new Paint();
        paint.setAntiAlias(true);
        RadialGradient radialGradient = new RadialGradient(150, 150, 100, colors, null, Shader.TileMode.REPEAT);
        paint.setShader(radialGradient);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        rectf.set(0, 0, 300, 300);
        canvas.drawRect(rectf,paint);

        canvas.drawCircle(700, 300, 300, paint);
    }
}
