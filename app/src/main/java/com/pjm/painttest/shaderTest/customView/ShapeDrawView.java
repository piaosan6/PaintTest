package com.pjm.painttest.shaderTest.customView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.pjm.painttest.R;

/**
 * Created by Administrator on 2017/6/21 0021.
 */

public class ShapeDrawView extends View {

    private Paint paint;
    private Bitmap bm;
    private Shader shader;
    private ShapeDrawable shapeDrawable;
    private int left, top, right, bootom;

    public ShapeDrawView(Context context) {
        this(context, null);
    }

    public ShapeDrawView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShapeDrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setBackgroundColor(Color.TRANSPARENT);
        paint = new Paint();
        paint.setAntiAlias(true);
        bm = BitmapFactory.decodeResource(getResources(), R.mipmap.zm2);
        shader = new BitmapShader(bm, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        shapeDrawable = new ShapeDrawable(new OvalShape());
        shapeDrawable.getPaint().setShader(shader);
        int bmWidth = bm.getWidth();
        int bmHeight = bm.getHeight();
        if(bmWidth > bmHeight){
            left = (int) ((bmWidth - bmHeight)/2.0f);
            right = left + bmHeight;
            top = 0;
            bootom = bmHeight;
        }else{
            left = 0;
            right = bmWidth;
            top = (int) ((bmHeight - bmWidth)/2.0f);
            bootom = top + bmWidth;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        shapeDrawable.setBounds(left, top, right, bootom);
        shapeDrawable.draw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }
}
