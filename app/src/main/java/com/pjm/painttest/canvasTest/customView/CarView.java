package com.pjm.painttest.canvasTest.customView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.pjm.painttest.R;
import com.pjm.painttest.utils.L;

/**
 */

public class CarView extends View {

    private Bitmap bitmap;//水表背景图片
    private float centerX;
    private float centerY;
    //缩放比例
    private float scale;
    // 背景图原始宽度
    private float srcWidth ;
    private Rect srcRect;
    private RectF desRect;
    private float remainTextSize;
    private float eCodeTextSize;


    public CarView(Context context) {
        this(context, null);
    }

    public CarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.car);
        srcRect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        desRect = new RectF();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float width, height;
        float scale = Math.min(w/bitmap.getWidth(), h/bitmap.getHeight()) *0.8f;
        width = bitmap.getWidth()* scale;
        height = bitmap.getHeight()*scale;
        L.i("bm width = " + bitmap.getWidth() + ", bm height = " + bitmap.getHeight());
        L.i(" w = " + w + ", h = " + h  + ",width = " + width + ",height =" + height + ", scale = " + scale);
        desRect.set((w - width)/2f, (h - height)/2f, (w + width)/2f, (h + height)/2f);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        //画背景
        canvas.drawBitmap(bitmap, srcRect, desRect, null);
    }

    public float dpToPx(float dp) {
        return dp * getContext().getResources().getDisplayMetrics().density;
    }

}
