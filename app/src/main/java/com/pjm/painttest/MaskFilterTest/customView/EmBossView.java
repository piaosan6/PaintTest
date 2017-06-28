package com.pjm.painttest.MaskFilterTest.customView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 *
 */

public class EmBossView extends View {

   // private EmbossMaskFilter filter;
    private Paint paint;
    private Bitmap bm;
    private RectF rect;
    private RectF rect1;

    public EmBossView(Context context) {
        this(context, null);
    }

    public EmBossView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmBossView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setFilter(EmbossMaskFilter filter) {
        paint.setMaskFilter(filter);
        invalidate();
    }

    private void init() {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        /**
         * direction表示x,y,z的三个方向，
         * ambient表示的是光的强度，范围为[0-1]，
         * specular是反射亮度的系数，
         * blurRadius是指模糊的半径，
         */

        //filter = new EmbossMaskFilter(new float[]{1f, 1f, 1f}, 1f, 0.5f, 10f);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.GREEN);
        rect = new RectF(50f, 50f, 350f, 350f);
        rect1 = new RectF(20,220,420, 620);
       // bm = BitmapFactory.decodeResource(getResources(), R.mipmap.lxq);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(900, 700);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //canvas.drawBitmap(bm, 50, 50, paint);
        canvas.drawRect(rect, paint);
        canvas.drawCircle(600, 200, 150, paint);
        canvas.drawArc(rect1,0, 145, true, paint);
    }


}
