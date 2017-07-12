package com.pjm.painttest.CanvasTest.customView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;

/**
 * 使用drawable
 */

public class MyDrawable2 extends Drawable {

    private Drawable drawable;
    private Drawable grayDrawable;
    private Rect container;
    private Rect resultRect;

    public MyDrawable2(Context mContext, Drawable drawable){
        setLevel(5000);
        this.drawable = drawable;
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        resultRect = new Rect();
        if(drawable.getIntrinsicWidth() == -1){
            grayDrawable = drawable;
           // L.i("-->width = " + grayDrawable.getBounds().width() + "height = " + grayDrawable.getBounds().height());
        }else{
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(((BitmapDrawable) drawable).getBitmap(), 0, 0, paint);
            grayDrawable = new BitmapDrawable(mContext.getResources(), bitmap);

        }
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        int level = getLevel();
       // L.e("on draw , --------> level = " + level);
        if(level == 0 || level == 10000){
            //灰色图片
            drawGray(canvas);
        }else if(level == 5000){
            // 彩色图片
            drawColour(canvas);
        }else{
            container = getBounds();
            float ratio = level/5000f - 1;
            int gravity = ratio < 0 ? Gravity.LEFT : Gravity.RIGHT;
            int totalWidth  = container.width();
            int totalHeight  = container.height();
            // 灰色部分
            int w = (int) (totalWidth * Math.abs(ratio));
           // L.i("resultRect = " + resultRect);
            Gravity.apply(gravity, w, totalHeight,container, resultRect);
           // L.i("left = " + resultRect.left + ", right = " + resultRect.right);
            canvas.save();
            canvas.clipRect(resultRect, Region.Op.INTERSECT);
            drawGray(canvas);
            canvas.restore();
            // 彩色部分
            canvas.save();
            canvas.clipRect(resultRect,Region.Op.DIFFERENCE);
            drawColour(canvas);
            canvas.restore();
        }

    }

    /** 画彩色图片 */
    private void drawColour(Canvas canvas) {
        drawable.draw(canvas);
    }

    /** 画灰色图片 */
    private void drawGray(Canvas canvas) {
        grayDrawable.draw(canvas);
    }

    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    protected boolean onLevelChange(int level) {
        //通过修改level来重新绘制
        invalidateSelf();
        return super.onLevelChange(level);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        drawable.setBounds(bounds);
        grayDrawable.setBounds(bounds);

    }

    @Override
    public int getIntrinsicHeight() {
        return drawable.getIntrinsicHeight();
    }

    @Override
    public int getIntrinsicWidth() {
        return drawable.getIntrinsicWidth();
    }


}
