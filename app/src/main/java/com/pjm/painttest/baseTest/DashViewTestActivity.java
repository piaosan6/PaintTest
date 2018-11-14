package com.pjm.painttest.baseTest;

import android.os.Bundle;
import android.widget.Button;

import com.pjm.painttest.BaseActivity;
import com.pjm.painttest.baseTest.customView.MyDashView;
import com.pjm.painttest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DashViewTestActivity extends BaseActivity {

    @BindView(R.id.dashView)
    MyDashView dashView;
    @BindView(R.id.btn)
    Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_view_test);
        unbinder = ButterKnife.bind(this);

    }

    @OnClick(R.id.btn)
    public void startAnim() {
        dashView.startAnim();
    }



}
