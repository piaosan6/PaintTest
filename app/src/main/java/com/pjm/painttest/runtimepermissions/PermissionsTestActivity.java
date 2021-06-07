package com.pjm.painttest.runtimepermissions;

import android.Manifest;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.pjm.painttest.R;
import com.pjm.painttest.utils.L;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PermissionsTestActivity extends BasePermissionsActivity implements
        BasePermissionsActivity.ApplyPermissionCallBack, PermissionUtils.PermissionCallBack {

    @BindView(R.id.tvTime)
    TextView tvTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions_test);
        unbinder = ButterKnife.bind(this);
        setPermissionCallBack(this);
    }

    /**
     * 单纯的使用shouldShowRequestPermissionRationale去做什么判断，是没用的，只能在请求权限回调后再使用。
     * Google的原意是：
     * 1，没有申请过权限，申请就是了，所以返回false；
     * 2，申请了用户拒绝了，那你就要提示用户了，所以返回true；
     * 3，用户选择了拒绝并且不再提示，那你也不要申请了，也不要提示用户了，所以返回false；
     * 4，已经允许了，不需要申请也不需要提示，所以返回false
     */
    @OnClick({R.id.btnRead, R.id.btnWrite, R.id.btnAudio, R.id.btnWifi, R.id.btnBlue, R.id.btnCamera})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.btnRead:
                //PermissionUtils.requestPermission(this, PermissionUtils.CODE_READ_EXTERNAL_STORAGE, this);
                PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(this,
                        new String[]{Manifest.permission.RECORD_AUDIO}, new PermissionsResultAction() {
                            @Override
                            public void onGranted() {
                                L.e("--->permission onGranted ");
                            }

                            @Override
                            public void onDenied(String permission) {
                                if(!ActivityCompat.shouldShowRequestPermissionRationale(PermissionsTestActivity.this,
                                        Manifest.permission.RECORD_AUDIO)){
                                     ToastUtils.showShort("被禁止了授权，不再弹框");
                                    L.e("--> permission = " + permission);
                                }else {
                                    ToastUtils.showShort("用户拒绝了授权");
                                }
                            }
                        });
                break;

            case R.id.btnWrite:
                PermissionUtils.requestPermission(this, PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE, this);

                break;

            case R.id.btnAudio:
                PermissionUtils.requestPermission(this, PermissionUtils.CODE_RECORD_AUDIO, this);
                PermissionUtils.requestPermission(this, PermissionUtils.CODE_CAMERA, this);
                break;

            case R.id.btnBlue:
                PermissionUtils.requestPermission(this, PermissionUtils.CODE_ACCESS_FINE_LOCATION, this);
                break;

            case R.id.btnWifi:
                PermissionUtils.requestPermission(this, PermissionUtils.CODE_ACCESS_COARSE_LOCATION, this);
                break;

            case R.id.btnCamera:
                PermissionUtils.requestPermission(this, PermissionUtils.CODE_CAMERA, this);
                break;

        }
    }

    @Override
    public void showRationale(String mPermissions) {
        Toast.makeText(PermissionsTestActivity.this, "showRationale " + mPermissions, Toast.LENGTH_SHORT).show();
    }


    public void applyPermissionSuccess(int requestCode, List<String> permissionList) {
        switch (requestCode) {
            case PermissionUtils.CODE_RECORD_AUDIO:
                Toast.makeText(PermissionsTestActivity.this, "录音授权成功", Toast.LENGTH_SHORT).show();
                break;

            case PermissionUtils.CODE_GET_ACCOUNTS:
                Toast.makeText(PermissionsTestActivity.this, "联系人授权成功", Toast.LENGTH_SHORT).show();
                break;

            case PermissionUtils.CODE_READ_PHONE_STATE:
                Toast.makeText(PermissionsTestActivity.this, "手机状态权成功", Toast.LENGTH_SHORT).show();
                break;

            case PermissionUtils.CODE_CALL_PHONE:
                Toast.makeText(PermissionsTestActivity.this, "呼叫授权成功", Toast.LENGTH_SHORT).show();
                break;

            case PermissionUtils.CODE_CAMERA:
                Toast.makeText(PermissionsTestActivity.this, "摄像头授权成功", Toast.LENGTH_SHORT).show();
                break;

            case PermissionUtils.CODE_ACCESS_FINE_LOCATION:
                Toast.makeText(PermissionsTestActivity.this, "定位1授权成功", Toast.LENGTH_SHORT).show();
                break;

            case PermissionUtils.CODE_ACCESS_COARSE_LOCATION:
                Toast.makeText(PermissionsTestActivity.this, "定位2授权成功", Toast.LENGTH_SHORT).show();
                break;

            case PermissionUtils.CODE_READ_EXTERNAL_STORAGE:
                Toast.makeText(PermissionsTestActivity.this, "读sd卡授权成功", Toast.LENGTH_SHORT).show();
                break;

            case PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE:
                Toast.makeText(PermissionsTestActivity.this, "写sd卡授权成功", Toast.LENGTH_SHORT).show();
                break;

            default:

                break;

        }
    }

    @Override
    public void applyPermissionFail(int requestCode, List<String> permissionList) {
        Toast.makeText(PermissionsTestActivity.this, "授权失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void neverAskAgain(int requestCode, List<String> permissionList) {
        Toast.makeText(PermissionsTestActivity.this, "需要用户去设置", Toast.LENGTH_SHORT).show();
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //PermissionUtils.requestPermissionsResult(this, requestCode, permissions, grantResults, this);
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }




/*
    @OnClick({R.id.btnRead, R.id.btnWrite, R.id.btnAudio, R.id.btnWifi, R.id.btnBlue, R.id.btnCamera})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.btnRead:
                requestPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, 1);
                break;

            case R.id.btnWrite:
                requestPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, 2);
                break;

            case R.id.btnAudio:
                requestPermissions(Manifest.permission.RECORD_AUDIO, 3);
                break;

            case R.id.btnBlue:
                requestPermissions(Manifest.permission.BLUETOOTH,4 );
                break;

            case R.id.btnWifi:
                requestPermissions(Manifest.permission.ACCESS_COARSE_LOCATION, 5);
                break;

            case R.id.btnCamera:
                requestPermissions(Manifest.permission.CAMERA, 6);
                break;

        }
    }
*/


    @Override
    public void applySuccess(String mPermissions) {
        switch (mPermissions) {
            case Manifest.permission.READ_EXTERNAL_STORAGE:
                ToastUtils.showShort("打开度权限成功");
                break;

            case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                ToastUtils.showShort("打开写权限成功");
                break;

            case Manifest.permission.ACCESS_COARSE_LOCATION:
                ToastUtils.showShort("打开定位权限成功");
                break;

            case Manifest.permission.CAMERA:
                ToastUtils.showShort("打开相机权限成功");
                break;

            case Manifest.permission.BLUETOOTH:
                ToastUtils.showShort("打开蓝牙权限成功");
                break;
        }
    }

    @Override
    public void applyFail(String mPermissions) {
        switch (mPermissions) {
            case Manifest.permission.READ_EXTERNAL_STORAGE:
                ToastUtils.showShort("打开度权限失败");
                break;

            case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                ToastUtils.showShort("打开写权限失败");
                break;

            case Manifest.permission.ACCESS_COARSE_LOCATION:
                ToastUtils.showShort("打开定位权限失败");
                break;

            case Manifest.permission.CAMERA:
                ToastUtils.showShort("打开相机权限失败");
                break;

            case Manifest.permission.BLUETOOTH:
                ToastUtils.showShort("打开蓝牙权限失败");
                break;
        }
    }



}