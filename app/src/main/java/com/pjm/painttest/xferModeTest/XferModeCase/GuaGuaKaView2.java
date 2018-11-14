package com.pjm.painttest.xferModeTest.XferModeCase;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.pjm.painttest.R;

/**
 * 效果不太好的刮刮卡
 */

public class GuaGuaKaView2 extends View{

    private Paint paint;
    private Bitmap bm;
    private Path path;
    private RectF rectf;
    private Shader shader;
    private float x, y;
    private float mLastX, mLastY;


    public GuaGuaKaView2(Context context) {
        this(context, null);
    }

    public GuaGuaKaView2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GuaGuaKaView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        x = -100;
        y = -100;
        rectf = new RectF(0, 0, 0, 0);
        path = new Path();
        bm = BitmapFactory.decodeResource(getResources(), R.mipmap.zm2);
        shader = new BitmapShader(bm, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(bm.getWidth(),bm.getHeight());

    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(0xffcccccc);
        path.addCircle(x, y , 50, Path.Direction.CCW);
        paint.setShader(shader);
        canvas.drawPath(path,paint);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                mLastX = x;
//                mLastY = y;
//
//                break;
//            case MotionEvent.ACTION_MOVE:
//
//                int dx = (int) Math.abs(x - mLastX);
//                int dy = (int) Math.abs(y - mLastY);
//
//                if (dx > 3 || dy > 3){
//                    invalidate();
//                }
//
//        }
//        return true;
//    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE :
                x = event.getX();
                y = event.getY();
                invalidate();
                break;

        }
        return true;
    }

}
