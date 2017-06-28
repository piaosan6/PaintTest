package com.pjm.painttest.ShaderTest.customView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.pjm.painttest.R;

/**
 *  shaderView
 */

public class BitmapShaderView extends View{

    private Bitmap srcBitmap;
    private Paint paint;
    private RectF rectf;

    public BitmapShaderView(Context context) {
        this(context, null);
    }

    public BitmapShaderView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BitmapShaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        srcBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.zm2);
        paint = new Paint();
        paint.setAntiAlias(true);
        rectf = new RectF();


    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawArc(rectf, 0, 360, true, paint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int bmWidth = srcBitmap.getWidth();
        int bmHeight = srcBitmap.getHeight();

        float scale;
        if(bmWidth > bmHeight){
            scale = 1.0f * bmWidth / bmHeight;
            rectf.set(0, 0,h, h);
            w = (int) (h *scale);
        }else{
            scale = 1.0f * bmHeight / bmWidth ;
            rectf.set(0, 0,w, w);
            h  = (int) (w *scale);
        }

        Bitmap mBitmap ;
        if (bmWidth == w &&  bmHeight== h) {
            mBitmap = srcBitmap;
        } else {
            mBitmap = Bitmap.createScaledBitmap(srcBitmap, w, h, true);
            srcBitmap.recycle();
        }
        BitmapShader shader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(shader);
    }
}
