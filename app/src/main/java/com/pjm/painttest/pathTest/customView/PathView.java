package com.pjm.painttest.pathTest.customView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


public class PathView extends View{

    private Paint mPaint;
    private Path mPath;
    private Path.FillType type;

    public PathView(Context context) {
        this(context, null);
    }

    public PathView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        type = Path.FillType.WINDING;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mPath = new Path();


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.RED);
        mPath.addCircle(200, 200, 150, Path.Direction.CW);
        mPath.addCircle(300, 300, 150, Path.Direction.CW);
        mPath.setFillType(type);
        canvas.drawPath(mPath, mPaint);

    }

    public void updateType(Path.FillType type){
        this.type = type;
        invalidate();
    }


}
