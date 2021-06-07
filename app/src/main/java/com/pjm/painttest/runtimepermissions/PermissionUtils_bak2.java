package com.pjm.painttest.runtimepermissions;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.pjm.painttest.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */

public class PermissionUtils_bak2 {

    private static final String TAG = PermissionUtils_bak2.class.getSimpleName();

    public static final int CODE_RECORD_AUDIO = 0;

    public static final int CODE_GET_ACCOUNTS = 1;

    public static final int CODE_READ_PHONE_STATE = 2;

    public static final int CODE_CALL_PHONE = 3;

    public static final int CODE_CAMERA = 4;

    public static final int CODE_ACCESS_FINE_LOCATION = 5;

    public static final int CODE_ACCESS_COARSE_LOCATION = 6;

    public static final int CODE_READ_EXTERNAL_STORAGE = 7;

    public static final int CODE_WRITE_EXTERNAL_STORAGE = 8;

    public static final int CODE_MULTI_PERMISSION = 100;


    public static final String PERMISSION_RECORD_AUDIO = Manifest.permission.RECORD_AUDIO;

    public static final String PERMISSION_GET_ACCOUNTS = Manifest.permission.GET_ACCOUNTS;

    public static final String PERMISSION_READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;

    public static final String PERMISSION_CALL_PHONE = Manifest.permission.CALL_PHONE;

    public static final String PERMISSION_CAMERA = Manifest.permission.CAMERA;

    public static final String PERMISSION_ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;

    public static final String PERMISSION_ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;

    public static final String PERMISSION_READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;

    public static final String PERMISSION_WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;

    private static final String[] requestPermissions = {

            PERMISSION_RECORD_AUDIO,

            PERMISSION_GET_ACCOUNTS,

            PERMISSION_READ_PHONE_STATE,

            PERMISSION_CALL_PHONE,

            PERMISSION_CAMERA,

            PERMISSION_ACCESS_FINE_LOCATION,

            PERMISSION_ACCESS_COARSE_LOCATION,

            PERMISSION_READ_EXTERNAL_STORAGE,

            PERMISSION_WRITE_EXTERNAL_STORAGE

    };

    interface PermissionCallBack {

        void applyPermissionSuccess(int requestCode, List<String> permissionList);

        void applyPermissionFail(int requestCode, List<String> permissionList);

        void neverAskAgain(int requestCode, List<String> permissionList);
    }


    /**
     * 单个授权请求
     */
    public static void requestPermission(final Activity activity, final int requestCode, PermissionCallBack callBack) {
        if (activity == null) {
            return;
        }
        if (requestCode < 0 || requestCode >= requestPermissions.length) {
            Log.d(TAG, "requestPermission illegal requestCode:" + requestCode);
            return;
        }
        ArrayList<String> list = new ArrayList<>();
        String requestPermission = requestPermissions[requestCode];
        int checkSelfPermission;
        try {
            checkSelfPermission = ActivityCompat.checkSelfPermission(activity, requestPermission);
        } catch (RuntimeException e) {
            if(callBack != null){
                list.add(requestPermission);
                callBack.neverAskAgain(requestCode, list);
            }
            return;
        }
        if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{requestPermission},  requestCode);
        } else {
            if(callBack != null){
                list.add(requestPermission);
                callBack.applyPermissionSuccess(requestCode, list);
            }
        }
    }

    private static void requestMultiResult(Activity activity, String[] permissions, int[] grantResults, PermissionCallBack callBack) {
        if (activity == null) {
            return;
        }
        Log.d(TAG, "onRequestPermissionsResult permissions length:" + permissions.length);
        //Map<String, Integer> perms = new HashMap<>();
        ArrayList<String> notGrantedList = new ArrayList<>();
        ArrayList<String> grantedList = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            Log.d(TAG, "permissions: [i]:" + i + ", permissions[i]" + permissions[i] + ",grantResults[i]:" + grantResults[i]);
            //perms.put(permissions[i], grantResults[i]);
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                notGrantedList.add(permissions[i]);
            }else {
                grantedList.add(permissions[i]);
            }
        }

        if (notGrantedList.size() == 0) {
            callBack.applyPermissionSuccess(CODE_MULTI_PERMISSION, grantedList);
        } else {
            callBack.applyPermissionFail(CODE_MULTI_PERMISSION, notGrantedList);
            openSettingActivity(activity, "those permission need granted!");
        }
    }

    /**
     * 一次申请多个权限
     */
    public static void requestMultiPermissions(final Activity activity, int requestCode, String[] requestPermissions, PermissionCallBack callBack) {

        final List<String> permissionsList = getNoGrantedPermission(activity, requestPermissions,false);

        final List<String> shouldRationalePermissionsList = getNoGrantedPermission(activity, requestPermissions,true);
        //TODO checkSelfPermission
        if (permissionsList == null || shouldRationalePermissionsList == null) {
            return;
        }
        Log.d(TAG, "requestMultiPermissions permissionsList:" + permissionsList.size() + ",shouldRationalePermissionsList:" + shouldRationalePermissionsList.size());
        if (permissionsList.size() > 0) {
            ActivityCompat.requestPermissions(activity, permissionsList.toArray(new String[permissionsList.size()]), CODE_MULTI_PERMISSION);
        } else if (shouldRationalePermissionsList.size() > 0) {
            callBack.neverAskAgain(requestCode, shouldRationalePermissionsList);
        } else {
            if(callBack != null){
                callBack.applyPermissionSuccess(CODE_MULTI_PERMISSION,  Arrays.asList(requestPermissions));
            }
        }
    }

    private static void shouldShowRationale(final Activity activity, final int requestCode, final String requestPermission) {
        String[] permissionsHint = activity.getResources().getStringArray(R.array.permissions);
        showMessageOKCancel(activity, "Rationale: " + permissionsHint[requestCode], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCompat.requestPermissions(activity,
                        new String[]{requestPermission},  requestCode);
                Log.d(TAG, "showMessageOKCancel requestPermissions:" + requestPermission);
            }
        });
    }

    private static void showMessageOKCancel(final Activity context, String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    /**
     * @param activity
     * @param requestCode Need consistent with requestPermission
     * @param permissions
     * @param grantResults
     */
    public static void requestPermissionsResult(final Activity activity, final int requestCode,
                                                @NonNull String[] permissions,  @NonNull int[] grantResults,
                                                PermissionCallBack permissionCallBack) {
        if (activity == null) {
            return;
        }
        Log.d(TAG, "requestPermissionsResult requestCode:" + requestCode);
        if (requestCode == CODE_MULTI_PERMISSION) {
            requestMultiResult(activity, permissions, grantResults, permissionCallBack);
            return;
        }

        if (requestCode < 0 || requestCode >= requestPermissions.length) {
            Log.w(TAG, "requestPermissionsResult illegal requestCode:" + requestCode);
            Toast.makeText(activity, "illegal requestCode:" + requestCode, Toast.LENGTH_SHORT).show();
            return;
        }
        Log.i(TAG, "onRequestPermissionsResult requestCode:" + requestCode + ",permissions:" + permissions.toString()
                + ",grantResults:" + grantResults.toString() + ",length:" + grantResults.length);
        ArrayList<String> list = new ArrayList<>();
        if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            list.add(permissions[0]);
            //授权成功
            permissionCallBack.applyPermissionSuccess(requestCode, list);
        } else {
            //授权失败
            permissionCallBack.applyPermissionFail(requestCode, list);
        }
    }

    private static void openSettingActivity(final Activity activity, String message) {
        showMessageOKCancel(activity, message, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Log.d(TAG, "getPackageName(): " + activity.getPackageName());
                Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                intent.setData(uri);
                activity.startActivity(intent);
            }
        });
    }

    /**
     * @param activity
     * @param isShouldRationale true: return no granted and shouldShowRequestPermissionRationale permissions,
     *                          false:return no granted and !shouldShowRequestPermissionRationale
     * @return
     */
    public static ArrayList<String> getNoGrantedPermission(Activity activity, String[] requestPermissions, boolean isShouldRationale) {
        ArrayList<String> permissions = new ArrayList<>();
        for (int i = 0; i < requestPermissions.length; i++) {
            String requestPermission = requestPermissions[i];
            int checkSelfPermission = PackageManager.PERMISSION_DENIED;
            try {
                checkSelfPermission = ActivityCompat.checkSelfPermission(activity, requestPermission);
            } catch (RuntimeException e) {
                if(isShouldRationale){
                    permissions.add(requestPermission);
                }
                Toast.makeText(activity, "please open those permission", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "RuntimeException:" + e.getMessage());
                return null;
            }

            if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "getNoGrantedPermission ActivityCompat.checkSelfPermission != PackageManager.PERMISSION_GRANTED:" + requestPermission);
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, requestPermission)) {
                    Log.d(TAG, "shouldShowRequestPermissionRationale if");
                    if (isShouldRationale) {
                        permissions.add(requestPermission);
                    }
                } else {
                    if (!isShouldRationale) {
                        permissions.add(requestPermission);
                    }
                    Log.d(TAG, "shouldShowRequestPermissionRationale else");
                }
            }
        }
        return permissions;
    }

}
