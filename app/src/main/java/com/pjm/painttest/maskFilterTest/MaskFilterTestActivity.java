package com.pjm.painttest.maskFilterTest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pjm.painttest.BaseActivity;
import com.pjm.painttest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 滤镜测试
 */
public class MaskFilterTestActivity extends BaseActivity {

    @BindView(R.id.btn0)
    Button btn0;
    @BindView(R.id.btn1)
    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mask_filter_test);
        unbinder = ButterKnife.bind(this);
        mContext = this;
    }

    @OnClick({R.id.btn0, R.id.btn1, R.id.btn2})
    public void OnClick(View v){
        switch (v.getId()) {
            case R.id.btn0:
                startActivity(new Intent(mContext, BlurMaskTestActivity.class));
                break;

            case R.id.btn1:
                startActivity(new Intent(mContext, EmbossMaskTestActivity.class));
                break;

            case R.id.btn2:
                startActivity(new Intent(mContext, ColorMatrixTestActivity.class));
                break;
        }
    }


}
