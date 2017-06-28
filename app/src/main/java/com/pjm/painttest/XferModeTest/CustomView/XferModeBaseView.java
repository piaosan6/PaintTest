/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pjm.painttest.XferModeTest.CustomView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.pjm.painttest.R;


public class XferModeBaseView extends View {

    private Paint mPaint;
    private Bitmap bitmap;
    private Bitmap srcBitmap;
    private int bmWidth;
    private int bmHeight;
    private float centerX;
    private float centerY;
    private float radius;
    private PorterDuffXfermode mode;
    private boolean dstIsBackGround;



    public XferModeBaseView(Context context) {
        this(context, null);
    }

    public XferModeBaseView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XferModeBaseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        dstIsBackGround = true;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(3);

        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.zm);
        bmWidth = bitmap.getWidth();
        bmHeight = bitmap.getHeight();
        centerX = bmWidth/2.0f;
        centerY = bmHeight/2.0f;
        radius = Math.min(centerX,centerY);

        srcBitmap = Bitmap.createBitmap(bmWidth + 100, bmHeight +100, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(srcBitmap);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle(centerX, centerY, radius, mPaint);

//        srcBitmap = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(srcBitmap);
////        //canvas.drawColor(Color.GRAY);
//        canvas.drawCircle(200, 200, 150, mPaint);
        //srcBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.tm);

    }

    public void setXferMode(PorterDuff.Mode xferMode){
        mode = new PorterDuffXfermode(xferMode);
        invalidate();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(bmWidth,bmHeight);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(dstIsBackGround){
            canvas.drawBitmap(bitmap, 0, 0, mPaint);
            mPaint.setXfermode(mode);
            canvas.drawBitmap(srcBitmap, 0, 0, mPaint);
            mPaint.setXfermode(null);
        }else{
            canvas.drawBitmap(srcBitmap, 0, 0, mPaint);
            mPaint.setXfermode(mode);
            canvas.drawBitmap(bitmap, 0, 0, mPaint);
            mPaint.setXfermode(null);
        }
    }

    public void setDstAsBackGround(boolean b) {
        dstIsBackGround = b;
    }

//　　CLEAR:清除图像  (关闭硬件加速)
//　　SRC:只显示源图像
//　　SRC_ATOP:在源图像和目标图像相交的地方绘制【源图像】，在不相交的地方绘制【目标图像】，相交处的效果受到源图像和目标图像alpha的影响
//　　SRC_IN:只在源图像和目标图像相交的地方绘制【源图像】
//    SRC_OUT:只在源图像和目标图像不相交的地方绘制【源图像】，相交的地方根据目标图像的对应地方的alpha进行过滤，目标图像完全不透明则完全过滤，完全透明则不过滤
//　　SRC_OVER:将源图像放在目标图像上方
//　　DST:只显示目标图像
//　　DST_ATOP:在源图像和目标图像相交的地方绘制【目标图像】，在不相交的地方绘制【源图像】，相交处的效果受到源图像和目标图像alpha的影响
//　　DST_IN:只在源图像和目标图像相交的地方绘制【目标图像】，绘制效果受到源图像对应地方透明度影响
//　　DST_OUT:只在源图像和目标图像不相交的地方绘制【目标图像】，在相交的地方根据源图像的alpha进行过滤，源图像完全不透明则完全过滤，完全透明则不过滤
//　　DST_OVER:将目标图像放在源图像上方
//　　DARKEN:变暗,较深的颜色覆盖较浅的颜色，若两者深浅程度相同则混合
//　　LIGHTEN:变亮，与DARKEN相反，DARKEN和LIGHTEN生成的图像结果与Android对颜色值深浅的定义有关
//　　MULTIPLY:正片叠底，源图像素颜色值乘以目标图像素颜色值除以255得到混合后图像像素颜色值
//　　OVERLAY:叠加
//　　SCREEN:滤色，色调均和,保留两个图层中较白的部分，较暗的部分被遮盖
//    ADD:饱和相加,对图像饱和度进行相加,不常用
//　　XOR:在源图像和目标图像相交的地方之外绘制它们，在相交的地方受到对应alpha和色值影响，如果完全不透明则相交处完全不绘制


    /*
    二、混合模式分类 1、SRC类 ----优先显示的是源图片 SRC[Sa, Sc]  ---- 处理图片相交区域时，总是显示的是原图片
    SRC_IN [Sa * Da, Sc * Da] ---- 处理图片相交区域时，受到目标图片的Alpha值影响 当我们的目标图片为空白像素的时候，
    目标图片也会变成空白 简单的来说就是用目标图片的透明度来改变源图片的透明度和饱和度，当目标图片的透明度为0时，
    源图片就不会显示 示例：圆角头像 、倒影图片 SRC_OUT [Sa * (1 - Da), Sc * (1 - Da)] --- 同SRC_IN类似  (1 - Da)
    用我们目标图片的透明度的补值来改变源图片的透明度和饱和度,当目标图片的透明度为不透明时，源图片就不会显示
    示例：橡皮擦效果 目标图片 --- 手势的轨迹 源图片 --- 擦除的图片 刮刮卡效果？
    SRC_ATOP [Da, Sc * Da + (1 - Sa) * Dc]----当透明度为100%和0%时，SRC_IN 和 SRC_ATOP是通用的
    当透明度不为上述的两个值时，SRC_ATOP  比 SRC_IN  源图像的饱和度会增加，变得更亮一些
    示例： 用SRC_ATOP来实现  圆角头像 、倒影图片 对比一下SRC_IN 2、DST类 ----优先显示的是目标图片
    DST_IN [Sa * Da, Sa * Dc] ----- 对比一下SRC_IN，正好和我们SRC_IN想法，在相交的时候以源图片的透明度来改变目标图片的透明度和饱和度
    当源图片的透明度为0的时候，目标图片完全不显示

    示例：心电图效果，不规则水波纹效果，当然也可以做SRC_IN 的效果（注意选择谁为源图片，谁为目标图片）

    心电图效果-
    目标图片 ---心电图
    源图片 ---- 不透明的图 就是通过改变透明图片的不透明区域的宽度，来实现心电图的动画效果

    3、其他的叠加效果

    MULTIPLY[Sa * Da, Sc * Dc] ---

    应用：可以把图片的轮廓取出来

    LIGHTEN -- 变亮
    书架 头顶灯光变亮效果
    */

    // Dst 先画在画布上，然后画src  测试api 19 (未关闭硬件加速)
     // PorterDuff.Mode.CLEAR       相交不透明部分显示黑色 ，其他部分仍然显示dest
     // PorterDuff.Mode.SRC         相交部分只显示 src 相交部分，Src只显示相交部分(Src多余部分不显示)，Dst如果大于Src,多余部分会显示出来
     // PorterDuff.Mode.DST         只显示Dst出来
     // PorterDuff.Mode.SRC_OVER    如果Src大小小于Dst将Src图像在目标图像上方,如果Src大于Dst，将Src 调整和Dst一样大小后放在Dst图像上方
     // PorterDuff.Mode.DST_OVER    将Src图像放在Dst图像上方
     // PorterDuff.Mode.SRC_IN      只在源图像和目标图像相交的地方绘制【源图像】, 和PorterDuff.Mode.SRC 效果类似
     // PorterDuff.Mode.DST_IN      只在源图像和目标图像相交的地方绘制【目标图像】，绘制效果受到源图像对应地方透明度影响


}
