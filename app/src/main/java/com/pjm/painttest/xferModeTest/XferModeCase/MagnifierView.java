package com.pjm.painttest.xferModeTest.XferModeCase;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.pjm.painttest.R;
import com.pjm.painttest.utils.L;

/**
 *  放大镜效果view
 */

public class MagnifierView extends View {

    private Paint paint;
    private Bitmap bitmap;
    private Bitmap bigBitmap;
    private float scaleFactory = 3.0f;
    private int width ,height;
    private Shader shader;
    private int radius;
    private Matrix matrix;
    private ShapeDrawable shapeDrawable;

    public MagnifierView(Context context) {
        this(context, null);
    }

    public MagnifierView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs , 0);
    }

    public MagnifierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        L.e("init---------------------->");
        radius = 100;
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        matrix = new Matrix();
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.tm);
        bigBitmap = Bitmap.createScaledBitmap(bitmap,(int)(bitmap.getWidth()*scaleFactory), (int)(bitmap.getHeight()*scaleFactory), true);
        shader = new BitmapShader(bigBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        shapeDrawable = new ShapeDrawable(new OvalShape());
        shapeDrawable.getPaint().setShader(shader);
        shapeDrawable.setBounds(0, 0, radius*2, radius*2);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        L.e("onDraw---------------------->");
        super.onDraw(canvas);
        // 1、画原图
        canvas.drawBitmap(bitmap, 0, 0, null);
        // 2、画放大镜的图
        shapeDrawable.draw(canvas);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        L.e("onSizeChanged---------------------->" + w + ", "+ h);
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        // 将放大的图片往相反的方向挪动，开始圆心位置在（radius,radius)
        matrix.setTranslate(radius - x * scaleFactory, radius - y * scaleFactory);
        shapeDrawable.getPaint().getShader().setLocalMatrix(matrix);
        // 切出手势区域点位置的圆
        shapeDrawable.setBounds(x - radius,y - radius, x + radius, y + radius);
        invalidate();
        return true;
    }

}
