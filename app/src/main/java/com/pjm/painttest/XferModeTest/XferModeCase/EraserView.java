package com.pjm.painttest.XferModeTest.XferModeCase;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.pjm.painttest.R;

/**
 *  橡皮擦效果
 */

public class EraserView extends View{

    private Paint paint;
    private Bitmap bitmap;
    private Path mPath;
    private float lastX;
    private float lastY;

    public EraserView(Context context) {
        this(context, null);
    }

    public EraserView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EraserView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(80);
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.tm);
        mPath = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(bitmap.getWidth(), bitmap.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //----------------------- 方法二 ----------------------------
        canvas.drawBitmap(bitmap, 0, 0, null);
        paint.setColor(Color.TRANSPARENT);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawPath(mPath, paint);
        paint.setXfermode(null);

        //----------------------- 方法一 ----------------------------
       /* canvas.drawPath(mPath, null);
       // 将paint 设置一个不透明的颜色  paint.setColor(Color.RED);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        canvas.drawBitmap(bitmap, 0, 0, paint);
        paint.setXfermode(null);*/
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
       switch (event.getAction()){

           case MotionEvent.ACTION_DOWN:
               lastX = event.getX();
               lastY =event.getY();
               mPath.moveTo(lastX, lastY);
               break;

           case MotionEvent.ACTION_MOVE:
               float x = event.getX();
               float y = event.getY();
               mPath.quadTo(lastX, lastY, (lastX + x)/2, (lastY + y)/2 );
               lastX = x;
               lastY = y;
               invalidate();
               break;
        }

        return true;
    }
}
