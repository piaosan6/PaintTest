package com.pjm.painttest;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.pjm.painttest.baseTest.CustomViewTestActivity;
import com.pjm.painttest.baseTest.DashViewTestActivity;
import com.pjm.painttest.baseTest.MinionTestActivity;
import com.pjm.painttest.baseTest.PaintBaseTestActivity;
import com.pjm.painttest.baseTest.PathDashPathViewActivity;
import com.pjm.painttest.baseTest.SimpleTestActivity;
import com.pjm.painttest.canvasTest.CanvasTestActivity;
import com.pjm.painttest.customProgress.CircleProgressTestActivity;
import com.pjm.painttest.dialogTest.DialogTestActivity;
import com.pjm.painttest.loadingView.SplashViewTestActivity;
import com.pjm.painttest.maskFilterTest.MaskFilterTestActivity;
import com.pjm.painttest.netTest.NetTestActivity;
import com.pjm.painttest.pathMeasurTest.PathMeasureTestActivity;
import com.pjm.painttest.pathTest.PathTestActivity;
import com.pjm.painttest.pingTest.NetWorkPingActivity;
import com.pjm.painttest.recyclerViewTest.RecyclerViewTestActivity;
import com.pjm.painttest.runtimepermissions.PermissionsTestActivity;
import com.pjm.painttest.shaderTest.ShaderViewTestActivity;
import com.pjm.painttest.shadow.ShadowTestActivity;
import com.pjm.painttest.textViewTest.TextSizeColorActivity;
import com.pjm.painttest.timeTest.TimeTestActivity;
import com.pjm.painttest.utils.FileUtils;
import com.pjm.painttest.xferModeTest.XferModeCaseTestActivity;
import com.pjm.painttest.xferModeTest.XferModeTestActivity;
import com.pjm.painttest.coordinatorLayoutTest.CoordinatorLayoutTestActivity;
import com.pjm.painttest.slideDelete.SlideRecyclerViewActivity;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

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
    @BindView(R.id.btnCanvasTest)
    Button btnCanvasTest;
    @BindView(R.id.btnPathTest)
    Button btnPathTest;
    @BindView(R.id.btnPathMeasureTest)
    Button btnPathMeasureTest;
    @BindView(R.id.btnNet)
    Button btnNet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        mContext = this;
        //L.e("density = " + mContext.getResources().getDisplayMetrics().density);
        ObjectAnimator oa = ObjectAnimator.ofFloat(btnNet, "rotation",  0, 180 );
        oa.start();
        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

    }

    @OnClick({R.id.btnPaintBase, R.id.btnDashTest, R.id.btnDashPathTest, R.id.btnCircle, R.id.btnMinion, R.id.btnShader, R.id.btnLoad,
            R.id.btnXferModeUse, R.id.btnXferMode, R.id.btnMaskFilter, R.id.btnCanvasTest, R.id.btnPathTest, R.id.btnPathMeasureTest,
            R.id.btnNet, R.id.btnCustomViewTest, R.id.btnNetTest, R.id.btnSimpleTest, R.id.btnSlideTest, R.id.btnCoordinatorLayout,
            R.id.btnDialog, R.id.btnShadowTest, R.id.btnPermission, R.id.btnTextTest, R.id.btnTime, R.id.btnLoadMore})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.btnNet:
                startActivity(new Intent(mContext, NetWorkPingActivity.class));
                String path = FileUtils.getSDCardPath() + "/test.txt";
                File file = FileUtils.createFile(path);
                Log.i("info", "path = " + path + ", file = " + file.exists());
                break;
            case R.id.btnDialog:
                startActivity(new Intent(mContext, DialogTestActivity.class));
                break;
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

            case R.id.btnCanvasTest:
                startActivity(new Intent(mContext, CanvasTestActivity.class));
                break;

            case R.id.btnPathTest:
                startActivity(new Intent(mContext, PathTestActivity.class));
                break;
            case R.id.btnPathMeasureTest:
                startActivity(new Intent(mContext, PathMeasureTestActivity.class));
                break;

            case R.id.btnCustomViewTest:
                startActivity(new Intent(mContext, CustomViewTestActivity.class));
                break;

            case R.id.btnNetTest:
                startActivity(new Intent(mContext, NetTestActivity.class));
                break;

            case R.id.btnSimpleTest:
                startActivity(new Intent(mContext, SimpleTestActivity.class));
                break;

            case R.id.btnSlideTest:
                startActivity(new Intent(mContext, SlideRecyclerViewActivity.class));
                break;

            case R.id.btnCoordinatorLayout:
                startActivity(new Intent(mContext, CoordinatorLayoutTestActivity.class));
                break;

            case R.id.btnShadowTest:
                startActivity(new Intent(mContext, ShadowTestActivity.class));
                break;

            case R.id.btnTextTest:
                startActivity(new Intent(mContext, TextSizeColorActivity.class));
                break;

            case R.id.btnPermission:
                startActivity(new Intent(mContext, PermissionsTestActivity.class));
                break;

            case R.id.btnTime:
                startActivity(new Intent(mContext, TimeTestActivity.class));
                break;

            case R.id.btnLoadMore:
                startActivity(new Intent(mContext, RecyclerViewTestActivity.class));
                break;

        }
    }
}
