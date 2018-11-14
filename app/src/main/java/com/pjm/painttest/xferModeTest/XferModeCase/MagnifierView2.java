package com.pjm.painttest.xferModeTest.XferModeCase;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.pjm.painttest.R;

/**
 *  放大镜效果view
 */

public class MagnifierView2 extends View {

    private Paint paint;
    private Bitmap bitmap;
    private Bitmap bigBitmap;
    private float scaleFactory = 2.0f;
    private int width ,height;
    private Shader shader;
    private float radius;
    private Path path;
    private Matrix matrix;
    private float currentX;
    private float currentY;

    public MagnifierView2(Context context) {
        this(context, null);
    }

    public MagnifierView2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs , 0);
    }

    public MagnifierView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        radius = 100;
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        matrix = new Matrix();
        path = new Path();
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.tm);
        bigBitmap = Bitmap.createScaledBitmap(bitmap,(int)(bitmap.getWidth()*scaleFactory), (int)(bitmap.getHeight()*scaleFactory), true);
        shader = new BitmapShader(bigBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, 0, 0, null);
        paint.setShader(shader);
        path.reset();
        // 这个圆需要画在点击处的位置
        path.addCircle(currentX, currentY, radius, Path.Direction.CCW);
        canvas.drawPath(path, paint);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        currentX = (int) event.getX();
        currentY = (int) event.getY();
        //将画布往反方向移动
        matrix.setTranslate( - currentX * (scaleFactory-1),  - currentY * (scaleFactory-1));
        paint.getShader().setLocalMatrix(matrix);
        invalidate();
        return true;
    }

}
