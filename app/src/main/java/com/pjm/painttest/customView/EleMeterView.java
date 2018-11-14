package com.pjm.painttest.customView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import com.pjm.painttest.R;

/**
 * 自定义电表控件
 */

public class EleMeterView extends View {

    private Paint eCodePaint;//绘制止码数画笔
    private Paint remainPaint;  // 绘制剩余用量量的画笔
    private Bitmap meterBitmap;//表背景图片
    private Bitmap gateOpenBitmap;//开闸背景图片
    private Bitmap gateCloseBitmap;//关闸背景图片
    private Bitmap pulseBitmap;//脉冲背景图片
    private String meterECode ="012345678";//表止码
    // 表剩余用量
    private String remainNum = "13.60" ;
    private float centerX;
    private float centerY;
    //缩放比例
    private float scale;
    private Rect srcRect;
    private RectF desRect;
    private float eCodeTextSize;
    private float remainTextSize;
    private float width;
    private float height;
    private float left;
    private float top;
    private float srcWidth;
    private Bitmap gateBitmap;
    private String gateStr;
    private Paint textPaint;
    private float textSize;
    private boolean isOpen;


    public EleMeterView(Context context) {
        this(context, null);
    }

    public EleMeterView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EleMeterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setMeterECode(String meterECode) {
        this.meterECode = meterECode;//String.format("%8s", meterECode).replaceAll(" ", "0");
        invalidate();
    }

    public void setRemain(String remainNum) {
        this.remainNum = getResources().getString(R.string.remainNum, remainNum);
        invalidate();
    }

    public void setState(boolean isOpen) {
        this.isOpen = isOpen;
        if(isOpen){
            gateStr = "开闸";
        }else{
            gateStr = "关闸";
        }
        invalidate();
    }

    /**实例化相关控件*/
    private void init(Context context){
        srcWidth = dpToPx(296);
        remainNum = getResources().getString(R.string.remainNum, remainNum);
        meterBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.electric_meter);
        gateOpenBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ammeter_brake_nor);
        gateCloseBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ammeter_brake_anomaly);
        pulseBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ammeter_pulse_nor);
        gateStr = "开闸";
        isOpen = true;
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(0xFF333333);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, metrics);

        eCodePaint = new Paint();
        eCodePaint.setAntiAlias(true);
        eCodePaint.setStyle(Paint.Style.FILL);
        eCodePaint.setFakeBoldText(true);
        eCodePaint.setColor(0xFF333333);
        eCodePaint.setTextAlign(Paint.Align.CENTER);
        eCodePaint.setFakeBoldText(true);//粗体
//        if(Build.VERSION.SDK_INT >=21){
//            eCodePaint.setLetterSpacing(0.18f);
//        }
        eCodeTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 30, metrics);

        remainPaint = new Paint();
        remainPaint.setColor(0xFF517efd);
        remainPaint.setStyle(Paint.Style.FILL);
        remainPaint.setAntiAlias(true);
        remainPaint.setTextAlign(Paint.Align.CENTER);
        remainTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 13, metrics);

        srcRect = new Rect(0, 0, meterBitmap.getWidth(), meterBitmap.getHeight());
        desRect = new RectF();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int bmWidth = meterBitmap.getWidth();
        int bmHeight = meterBitmap.getHeight();
        float s = Math.min(1f*w/bmWidth, 1f*h/ bmHeight) *0.9f;
        centerX = w / 2f;
        centerY = h / 2f;
        width = bmWidth * s;
        height = bmHeight * s;
        left = (w - width)/2f;
        top = (h - height)/2f;
        scale =  width /srcWidth;
        desRect.set(centerX - width/2f, centerY - height/2f, centerX + width/2f, centerY + height/2f);
        remainPaint.setTextSize(remainTextSize * scale);
        eCodePaint.setTextSize(eCodeTextSize * scale);
        textPaint.setTextSize(textSize * scale);
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        if(isOpen){
            gateBitmap = Bitmap.createBitmap(gateOpenBitmap, 0, 0, gateOpenBitmap.getWidth(), gateOpenBitmap.getHeight(), matrix, true);
        }else{
            gateBitmap = Bitmap.createBitmap(gateCloseBitmap, 0, 0, gateCloseBitmap.getWidth(), gateCloseBitmap.getHeight(), matrix, true);
        }
        pulseBitmap = Bitmap.createBitmap(pulseBitmap, 0, 0, pulseBitmap.getWidth(), pulseBitmap.getHeight(), matrix, true);

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        //画表背景
        canvas.drawBitmap(meterBitmap, srcRect, desRect, null);
        //画止码
        canvas.drawText(meterECode ,centerX, getBaselineY(height * 333f/770f + top, eCodePaint), eCodePaint);

        //画剩余用量
        canvas.drawText(remainNum, centerX, getBaselineY(552f/770f*height + top, remainPaint), remainPaint);

        float paddingTop = dpToPx(12)*scale;
        //画闸状态
        float gateLeft = left + width * 158f/888f;
        float gateTop = 552f / 770f * height + this.top - gateBitmap.getWidth() / 2;
        canvas.drawBitmap(gateBitmap, gateLeft, gateTop, null);
        // 画开/关闸文字
        canvas.drawText(gateStr, gateLeft + gateBitmap.getWidth()/2f, getBaselineY(gateTop + gateBitmap.getHeight() + paddingTop, textPaint), textPaint);

        //画脉冲
        float pulseLeft = this.left + width * 733f / 888f - pulseBitmap.getWidth();
        float pulseTop = 552f / 770f * height + this.top - pulseBitmap.getWidth() / 2;
        canvas.drawBitmap(pulseBitmap, pulseLeft, pulseTop, null);
        // 画脉冲文字
        canvas.drawText(getContext().getString(R.string.pulse), pulseLeft + pulseBitmap.getWidth()/2f, getBaselineY(gateTop + pulseBitmap.getHeight() + paddingTop, textPaint), textPaint);

    }

    private float getBaselineY(float dy, Paint textPaint) {
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float top = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
        float bottom = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom
        return (dy - top/2 - bottom/2);//基线中间点的y轴计算公式
    }

    public float dpToPx(float dp) {
        return dp * getContext().getResources().getDisplayMetrics().density;
    }

}
