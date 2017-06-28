package com.pjm.painttest.MaskFilterTest.customView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.pjm.painttest.R;

/**
 * Created by Administrator on 2017/6/26 0026.
 */

public class BlurView extends View {

    private Bitmap bm;
    private Paint paint;
    private BlurMaskFilter blurMaskFilter;



    public void setBlurMaskFilter(BlurMaskFilter blurMaskFilter) {
        paint.setMaskFilter(blurMaskFilter);
        invalidate();
    }

    public BlurView(Context context) {
        this(context, null);
    }

    public BlurView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BlurView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLUE);
        bm = BitmapFactory.decodeResource(getResources(), R.mipmap.lxq);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(bm.getWidth() + 100, bm.getHeight() + 100);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bm, 50, 50, paint);
    }


}
