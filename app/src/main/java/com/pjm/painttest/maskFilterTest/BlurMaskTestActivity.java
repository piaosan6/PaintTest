package com.pjm.painttest.maskFilterTest;

import android.graphics.BlurMaskFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.pjm.painttest.BaseActivity;
import com.pjm.painttest.maskFilterTest.customView.BlurView;
import com.pjm.painttest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BlurMaskTestActivity extends BaseActivity {

    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.btn0)
    Button btn0;
    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.blurView)
    BlurView blurView;

    private BlurView b1, b2, b3;
    private int radius = 18;
    BlurMaskFilter blurMaskFilter1;
    BlurMaskFilter blurMaskFilter2;
    BlurMaskFilter blurMaskFilter3;
    BlurMaskFilter blurMaskFilter4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blur_mask_test);
        unbinder =  ButterKnife.bind(this);

        /**
         * radius很容易理解，值越大我们的阴影越扩散
         * SOLID，其效果就是在图像的Alpha边界外产生一层与Paint颜色一致的阴影效果而不影响图像本身
         * NORMAL会将整个图像模糊掉
         * OUTER会在Alpha边界外产生一层阴影且会将原本的图像变透明
         * INNER则会在图像内部产生模糊
         */
        blurMaskFilter1 = new BlurMaskFilter(radius, BlurMaskFilter.Blur.NORMAL);

        blurMaskFilter2 = new BlurMaskFilter(radius, BlurMaskFilter.Blur.INNER);

        blurMaskFilter3 = new BlurMaskFilter(radius, BlurMaskFilter.Blur.OUTER);

        blurMaskFilter4 = new BlurMaskFilter(radius, BlurMaskFilter.Blur.SOLID);

       /* b3 = new BlurView(this);
        BlurMaskFilter blurMaskFilter3 = new BlurMaskFilter(radius, BlurMaskFilter.Blur.OUTER);
        b3.setBlurMaskFilter(blurMaskFilter3);
        ll.addView(b3);*/

    }

    @OnClick({R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.btn0:
                blurView.setBlurMaskFilter(blurMaskFilter1);
                //b1.postInvalidate();
                break;

            case R.id.btn1:
                blurView.setBlurMaskFilter(blurMaskFilter2);
                //b2.postInvalidate();
                break;

            case R.id.btn2:
                blurView.setBlurMaskFilter(blurMaskFilter3);
               // b3.postInvalidate();
                break;
            case R.id.btn3:
                blurView.setBlurMaskFilter(blurMaskFilter4);
               // b3.postInvalidate();
                break;
        }

    }


}
