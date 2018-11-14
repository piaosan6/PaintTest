package com.pjm.painttest.maskFilterTest.customView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.pjm.painttest.R;

/**
 * Created by Administrator on 2017/6/26 0026.
 */

public class ColorMatrixView extends View {

    private Paint paint;
    private Bitmap bitmap;
    private int width;
    private int height;
    private boolean embossFilter;
    private Bitmap embossBitmap;



    public ColorMatrixView(Context context) {
        this(context, null);
    }

    public ColorMatrixView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorMatrixView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    public void setColorMatrix(ColorMatrix colorMatrix){
        if(colorMatrix == null){
            paint.setColorFilter(null);
        }else{
            paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        }

        invalidate();
    }



    private void init() {
        // 是一个4行5列的矩阵
        /* {
        *   1 0 0 0 0         { R
        *   0 1 0 0 0           G
        *   0 0 1 0 0    *      B
        *   0 0 0 1 0 }         A
        *                       1 }
        * */
        //ColorMatrix colorMatrix = new ColorMatrix();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.lxq);
        width = bitmap.getWidth();
        height = bitmap.getHeight();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(embossFilter){
            canvas.drawBitmap(embossBitmap, 0, 0, paint);
            embossFilter = false;
        }else{
            canvas.drawBitmap(bitmap, 0, 0, paint);
        }


    }

    /*  浮雕效果
        B.r=C.r-B.r+127
        B.g=C.g-B.g+127
        B.b=C.b-B.b+127
    */
    public void setEmbossFilter() {
        paint.setColorFilter(null);
        embossFilter = true;
        if( embossBitmap == null){
            embossBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            int[] oldPixs = new int[width*height];
            int[] newPixs = new int[width*height];
            bitmap.getPixels(oldPixs, 0, width, 0, 0, width, height);
            int color,oldColor;
            int r,g,b;
            int r0,g0,b0,a0;
            int r1,g1,b1,a;

            for (int i = 1; i < oldPixs.length; i++) {
                oldColor = oldPixs[i-1];
                r0 = Color.red(oldColor);
                g0 = Color.green(oldColor);
                b0 = Color.blue(oldColor);
                a0 = Color.alpha(oldColor);

                color = oldPixs[i];
                r = Color.red(color);
                g = Color.green(color);
                b = Color.blue(color);
                a = Color.alpha(color);

                r1 = r0 - r + 127;
                if (r1<0) {
                    r1 = 0;
                }else if(r1>255){
                    r1 = 255;
                }

                g1 = g0 - g + 127;
                if (g1<0) {
                    g1 = 0;
                }else if(g1>255){
                    g1 = 255;
                }

                b1 = b0 - b + 127;
                if (b1<0) {
                    b1 = 0;
                }else if(b1>255){
                    b1 = 255;
                }
                newPixs[i] = Color.argb(a, r1, g1, b1);
            }
            embossBitmap.setPixels(newPixs, 0, width, 0, 0, width, height);
        }
        invalidate();

    }


}
