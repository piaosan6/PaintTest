package com.pjm.painttest.customView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;


import com.pjm.painttest.R;

import java.text.DecimalFormat;

/**
 * 自定义水表控件
 */

public class WaterMeterView extends View {

    private Paint eCodePaint;//绘制水表度数画笔
    private Paint remainPaint;  // 绘制剩余量的画笔
    private Bitmap meterBitmap;//水表背景图片
    private String meterECode ="01234";//水表止码
    // 水表剩余用量
    private String remainNum = "0" ;
    //剩余用量文字X轴的位置
    private float remainX;
    //剩余用量文字Y轴的位置
    private float remainY;
    // 剩余用量文字
    private String remainNumStr;
    private static final String STR_FORMAT = "0000";
    private DecimalFormat df = new DecimalFormat(STR_FORMAT);
    private float centerX;
    private float centerY;
    //缩放比例
    private float scale;
    // 背景图原始宽度
    private float srcWidth ;
    private Rect srcRect;
    private RectF desRect;
    private float remainTextSize;
    private float eCodeTextSize;


    public WaterMeterView(Context context) {
        this(context, null);
    }

    public WaterMeterView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaterMeterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setMeterECode(String meterECode) {
        this.meterECode = meterECode;
        invalidate();
    }

    public void setRemain(String remainNum) {
        this.remainNum = remainNum;
        remainNumStr = getResources().getString(R.string.remainNum, remainNum);
        invalidate();
    }

    /**实例化相关控件*/
    private void init(Context context){
        meterBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.water_meter);
        srcWidth = dpToPx(265);
        remainPaint = new Paint();
        remainPaint.setColor(0xFF53BBF7);
        remainPaint.setStyle(Paint.Style.FILL);
        remainPaint.setAntiAlias(true);
        remainPaint.setTextAlign(Paint.Align.CENTER);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        // 1：1字体大小16sp
        remainTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 15, metrics);

        eCodePaint = new Paint();
        eCodePaint.setAntiAlias(true);
        eCodePaint.setStyle(Paint.Style.FILL);
        eCodePaint.setFakeBoldText(true);
        eCodePaint.setColor(0xFF333333);
        eCodePaint.setTextAlign(Paint.Align.CENTER);
        eCodePaint.setFakeBoldText(true);//粗体
        eCodeTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 30, metrics);

        remainNumStr = getResources().getString(R.string.remainNum, remainNum);
        srcRect = new Rect(0, 0, meterBitmap.getWidth(), meterBitmap.getHeight());
        desRect = new RectF();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float width, height, maxW;
        maxW = w - dpToPx(40); //最有20dp间距，宽高1：1
        if(maxW > h){
            width = height = h;
        }else{
            width = height = maxW;
        }
        centerX = w / 2f;
        centerY = h / 2f;
        desRect.set(centerX - width/2f, centerY - height/2f, centerX + width/2f, centerY + height/2f);
        scale =  width /srcWidth;
        remainX = centerX;
        remainY = centerY + height/4f -dpToPx(12) * scale;
        remainPaint.setTextSize(remainTextSize * scale);
        eCodePaint.setTextSize(eCodeTextSize * scale);
    }

//    @Override
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(w, h, oldw, oldh);
//        int min = Math.min(w, h);
//        float width, height;
//        width = height = min*0.9f;
//        // 设计控件最小宽高是260
//        float minWidth = dpToPx(260);
//        float minPadding = dpToPx(10);
//        if(min > minWidth){
//            if(width < minWidth){
//                width = height = min - 2*minPadding;
//            }
//        }else{ //如果本身不满足260dp,则最小留10dp空隙
//            width = height = min - 2*minPadding;
//        }
//        centerX = w / 2f;
//        centerY = h / 2f;
//        desRect.set(centerX - width/2f, centerY - height/2f, centerX + width/2f, centerY + height/2f);
//        scale =  width /srcWidth;
//        remainX = centerX;
//        remainY = centerY + height/4f -dpToPx(12) * scale;
//        remainPaint.setTextSize(remainTextSize * scale);
//        eCodePaint.setTextSize(eCodeTextSize * scale);
//    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        //画水表背景
        canvas.drawBitmap(meterBitmap, srcRect, desRect, null);
        //画止码
        for (int i = 0; i< 5 ;i++){
            canvas.drawText(String.valueOf(meterECode.charAt(i)) , centerX - (2 - i) * dpToPx(30)*scale, centerY + dpToPx(8)*scale, eCodePaint);
        }
        // 画剩余用量的文字
        canvas.drawText(remainNumStr, remainX, remainY, remainPaint);

    }

    public float dpToPx(float dp) {
        return dp * getContext().getResources().getDisplayMetrics().density;
    }

}
