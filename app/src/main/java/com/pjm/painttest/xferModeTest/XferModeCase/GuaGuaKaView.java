package com.pjm.painttest.xferModeTest.XferModeCase;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.pjm.painttest.R;

/**
 * 效果不太好的刮刮卡
 */

public class GuaGuaKaView extends View{

    private Paint paint;
    private Bitmap bm;
    private Path path;
    private Shader shader;
    private float mLastX, mLastY;


    public GuaGuaKaView(Context context) {
        this(context, null);
    }

    public GuaGuaKaView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GuaGuaKaView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND); // 圆角
        paint.setStrokeCap(Paint.Cap.ROUND); // 圆角
        // 设置画笔宽度
        paint.setStrokeWidth(100);

        path = new Path();

        bm = BitmapFactory.decodeResource(getResources(), R.mipmap.zm2);
        shader = new BitmapShader(bm, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(0xffcccccc);
        paint.setShader(shader);
        canvas.drawPath(path,paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
         setMeasuredDimension(bm.getWidth(),bm.getHeight());

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                path.moveTo(mLastX, mLastY);
                break;
            case MotionEvent.ACTION_MOVE:

                int dx = (int) Math.abs(x - mLastX);
                int dy = (int) Math.abs(y - mLastY);

                if (dx > 3 || dy > 3){
                    path.lineTo(x, y);
                }

                mLastX = x;
                mLastY = y;
                invalidate();
                break;
        }


        return true;
    }

}
