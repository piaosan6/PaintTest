package com.pjm.painttest.baseTest;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.pjm.painttest.customView.LoadingView;
import com.pjm.painttest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SimpleTestActivity extends AppCompatActivity {

    @BindView(R.id.loadingView)
    LoadingView loadingView;
    private boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_test);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.loadingView})
    public void onClick(View view){
        if(flag){
            loadingView.hideLoading();
            backgroundAlpha(this, 1f);
            flag = false;
        }else{
            loadingView.showLoading();
            backgroundAlpha(this, 0.5f);
            flag = true;
        }
    }

    public static void backgroundAlpha(Activity mActivity, float bgAlpha) {
        Window window = mActivity.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setAttributes(lp);
    }
}
