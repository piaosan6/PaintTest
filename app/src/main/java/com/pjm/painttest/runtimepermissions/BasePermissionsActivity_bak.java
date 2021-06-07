package com.pjm.painttest.runtimepermissions;

import com.pjm.painttest.BaseActivity;

public abstract class BasePermissionsActivity_bak extends BaseActivity {

    protected ApplyPermissionCallBack callBack;

    public interface ApplyPermissionCallBack{

        void applySuccess(String mPermissions);

        void applyFail(String mPermissions);
    }

    public void setPermissionCallBack(ApplyPermissionCallBack callBack) {
        this.callBack = callBack;
    }

    protected void requestPermissions(String mPermissions) {
        if(hasPermission(mPermissions)){
            if(callBack != null){
                callBack.applySuccess(mPermissions);
            }
        }else {
            PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(this, new String[]{mPermissions},
                    new PermissionsResultAction() {
                        @Override
                        public void onGranted() {
                            if(callBack != null){
                                callBack.applySuccess(mPermissions);
                            }
                        }

                        @Override
                        public void onDenied(String permission) {
                            if(callBack != null){
                                callBack.applyFail(mPermissions);
                            }
                        }


                    });
        }
    }

    protected boolean hasPermission(String permissions) {
        return PermissionsManager.getInstance().hasPermission(this, permissions);
    }


}
