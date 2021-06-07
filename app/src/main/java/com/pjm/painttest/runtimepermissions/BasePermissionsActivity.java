package com.pjm.painttest.runtimepermissions;

import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.util.ArrayMap;

import com.blankj.utilcode.util.ToastUtils;
import com.pjm.painttest.BaseActivity;

public abstract class BasePermissionsActivity extends BaseActivity {

    protected ApplyPermissionCallBack callBack;

    public interface ApplyPermissionCallBack{

        void applySuccess(String mPermissions);

        void applyFail(String mPermissions);

        void showRationale(String mPermissions);
    }

    public void setPermissionCallBack(ApplyPermissionCallBack callBack) {
        this.callBack = callBack;
    }

    protected void requestPermissions(String mPermissions, int requestCode) {
        int checkSelfPermission;
        try {
            checkSelfPermission = ActivityCompat.checkSelfPermission(this, mPermissions);
        } catch (RuntimeException e) {
            if(callBack != null) {
                callBack.showRationale(mPermissions);
            }
            return;
        }
        if (checkSelfPermission == PackageManager.PERMISSION_GRANTED) {
            if(callBack != null){
                callBack.applySuccess(mPermissions);
            }
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, mPermissions)) {
                if(callBack != null) {
                    callBack.showRationale(mPermissions);
                }
            } else {
                ActivityCompat.requestPermissions(this, new String[]{mPermissions}, requestCode);
            }
        }
    }

    protected boolean hasPermission(String permissions) {
        int checkSelfPermission;
        try {
            checkSelfPermission = ActivityCompat.checkSelfPermission(this, permissions);
        } catch (RuntimeException e) {
            checkSelfPermission = PackageManager.PERMISSION_DENIED;
        }
        return checkSelfPermission == PackageManager.PERMISSION_GRANTED;
    }


}
