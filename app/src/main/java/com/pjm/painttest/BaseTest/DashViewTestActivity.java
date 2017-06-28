package com.pjm.painttest.BaseTest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.pjm.painttest.R;
import com.pjm.painttest.BaseTest.customView.MyDashView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DashViewTestActivity extends AppCompatActivity {

    @BindView(R.id.dashView)
    MyDashView dashView;
    @BindView(R.id.btn)
    Button btn;
    private Unbinder unbinder;


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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

}
