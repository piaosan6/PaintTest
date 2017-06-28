package com.pjm.painttest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.pjm.painttest.BaseTest.DashViewTestActivity;
import com.pjm.painttest.BaseTest.MinionTestActivity;
import com.pjm.painttest.BaseTest.PaintBaseTestActivity;
import com.pjm.painttest.BaseTest.PathDashPathViewActivity;
import com.pjm.painttest.CustomProgress.CircleProgressTestActivity;
import com.pjm.painttest.LoadingView.SplashViewTestActivity;
import com.pjm.painttest.MaskFilterTest.MaskFilterTestActivity;
import com.pjm.painttest.ShaderTest.ShaderViewTestActivity;
import com.pjm.painttest.XferModeTest.XferModeCaseTestActivity;
import com.pjm.painttest.XferModeTest.XferModeTestActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btnPaintBase)
    Button btnPaintBase;
    @BindView(R.id.btnDashTest)
    Button btnDashTest;
    @BindView(R.id.btnDashPathTest)
    Button btnDashPathTest;
    @BindView(R.id.btnCircle)
    Button btnCircle;
    @BindView(R.id.btnMinion)
    Button btnMinion;
    @BindView(R.id.btnShader)
    Button btnShader;
    @BindView(R.id.btnLoad)
    Button btnLoad;
    @BindView(R.id.btnXferModeUse)
    Button btnbtnXferModeUse;
    @BindView(R.id.btnXferMode)
    Button btnXferMode;
    @BindView(R.id.btnMaskFilter)
    Button btnMaskFilter;
    private Unbinder unbinder;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        mContext = this;
        //L.e("density = " + mContext.getResources().getDisplayMetrics().density);

    }

    @OnClick({R.id.btnPaintBase, R.id.btnDashTest, R.id.btnDashPathTest, R.id.btnCircle, R.id.btnMinion, R.id.btnShader, R.id.btnLoad,
            R.id.btnXferModeUse, R.id.btnXferMode, R.id.btnMaskFilter})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.btnPaintBase:
                startActivity(new Intent(mContext, PaintBaseTestActivity.class));
                break;
            case R.id.btnDashTest:
                startActivity(new Intent(mContext, DashViewTestActivity.class));
                break;
            case R.id.btnDashPathTest:
                startActivity(new Intent(mContext, PathDashPathViewActivity.class));
                break;
            case R.id.btnCircle:
                startActivity(new Intent(mContext, CircleProgressTestActivity.class));
                break;
            case R.id.btnMinion:
                startActivity(new Intent(mContext, MinionTestActivity.class));
                break;
            case R.id.btnShader:
                startActivity(new Intent(mContext, ShaderViewTestActivity.class));
                break;
            case R.id.btnLoad:
                startActivity(new Intent(mContext, SplashViewTestActivity.class));
                break;

            case R.id.btnXferMode:
                startActivity(new Intent(mContext, XferModeTestActivity.class));
                break;

            case R.id.btnXferModeUse:
                startActivity(new Intent(mContext, XferModeCaseTestActivity.class));
                break;

            case R.id.btnMaskFilter:
                startActivity(new Intent(mContext, MaskFilterTestActivity.class));
                break;

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


}
