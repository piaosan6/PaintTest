package com.pjm.painttest.customView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
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
 * 自定义气表控件
 */

public class GasMeterView extends View {

    private Paint titlePaint;//绘制标题画笔
    private Paint eCodePaint;//绘制表度数画笔
    private Paint remainPaint;  // 绘制剩余量的画笔
    private Bitmap meterBitmap;//表背景图片
    private String meterECode ="012345678";//表止码
    // 水表剩余用量
    private String remainNum = "13.60" ;
    //剩余用量文字X轴的位置
    private float remainX;
    //剩余用量文字Y轴的位置
    private float remainY;
    // 剩余用量文字
    private String remainNumStr;
    private float centerX;
    private float centerY;
    //缩放比例
    private float scale;
    private Rect srcRect;
    private RectF desRect;
    private float titleTextSize;
    private float remainTextSize;
    private float eCodeTextSize;
    private float width;
    private float height;
    private float left;
    private float top;
    private float srcWidth;


    public GasMeterView(Context context) {
        this(context, null);
    }

    public GasMeterView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GasMeterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setMeterECode(String meterECode) {
        this.meterECode = String.format("%8s", meterECode).replaceAll(" ", "0");
        invalidate();
    }

    public void setRemain(String remainNum) {
        this.remainNum = remainNum;
        invalidate();
    }

    /**实例化相关控件*/
    private void init(Context context){
        srcWidth = dpToPx(290);
        meterBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.gas_meter);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        titlePaint = new Paint();
        titlePaint.setStyle(Paint.Style.FILL);
        titlePaint.setAntiAlias(true);
        titlePaint.setTextAlign(Paint.Align.CENTER);
        titleTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, metrics);

        eCodePaint = new Paint();
        eCodePaint.setAntiAlias(true);
        eCodePaint.setStyle(Paint.Style.FILL);
        eCodePaint.setFakeBoldText(true);
        eCodePaint.setColor(Color.WHITE);
        eCodePaint.setTextAlign(Paint.Align.CENTER);
        eCodePaint.setFakeBoldText(true);//粗体
        eCodeTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 18, metrics);

        remainPaint = new Paint();
        remainPaint.setColor(0xFF333333);
        remainPaint.setStyle(Paint.Style.FILL);
        remainPaint.setAntiAlias(true);
        remainPaint.setTextAlign(Paint.Align.CENTER);
        remainTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 30, metrics);

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
        if(left < dpToPx(20)){

        }
        top = (h - height)/2f;
        scale =  width /srcWidth;
        desRect.set(centerX - width/2f, centerY - height/2f, centerX + width/2f, centerY + height/2f);
        titlePaint.setTextSize(titleTextSize * scale);
        remainPaint.setTextSize(remainTextSize * scale);
        eCodePaint.setTextSize(eCodeTextSize * scale);

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        //画表背景
        canvas.drawBitmap(meterBitmap, srcRect, desRect, null);
        //画标题
        titlePaint.setColor(0xFF333333);
        canvas.drawText(getContext().getString(R.string.gasTitle), centerX, getBaselineY(0.19f * height + top, titlePaint),  titlePaint);
        //画止码
        for (int i = 0; i< 8 ;i++){
//            canvas.drawText(String.valueOf(meterECode.charAt(i)) ,
//                    (0.229885f + i * 0.0689655f)*width + left, getBaselineY(0.36f*height + top, eCodePaint), eCodePaint);
            canvas.drawText(String.valueOf(meterECode.charAt(i)) ,
                    ((198f + i * 60f)/870f)*width + left, getBaselineY(0.36f*height + top, eCodePaint), eCodePaint);
        }
        titlePaint.setColor(0xFF517efd);
        // 画剩余用量文字
        canvas.drawText("剩余用量", centerX, getBaselineY(0.536f*height + top, titlePaint), titlePaint);
        //  画剩余用量
        canvas.drawText(remainNum, centerX, getBaselineY(0.725f*height + top, titlePaint), remainPaint);

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
