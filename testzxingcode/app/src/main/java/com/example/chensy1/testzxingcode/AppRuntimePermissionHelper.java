package com.example.chensy1.testzxingcode;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chensy1 on 2017/12/14.
 */

public abstract class AppRuntimePermissionHelper {
    private Activity activity;
    public final String CAMERA = Manifest.permission.CAMERA;//相机权限
    public final String INTERNET = Manifest.permission.INTERNET;//网络权限
    public final String NFC = Manifest.permission.NFC;//NFC权限
    public final String IMEI = Manifest.permission.READ_PHONE_STATE;//获取IMEI
    public final String BLUETOOTH = Manifest.permission.BLUETOOTH;//蓝牙权限
    public final String LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;//位置权限

    public String[] permission;

    private static final int MY_PERMISSIONS = 1213;//code码

    public AppRuntimePermissionHelper(Activity activity, String[] permission) {
        this.activity = activity;
        this.permission = permission;
        setPermissionStatus();
    }

    /**
     * 判断需要授权，需要提醒的权限
     */
    public void setPermissionStatus() {
        if (Build.VERSION.SDK_INT < 23) {
            perTrue();
            return;
        }

        // 这里设置app需要申请授权的权限
//        String[] permission = {CAMERA, LOCATION, INTERNET, NFC, IMEI, BLUETOOTH};


        List<String> listPermission = new ArrayList<String>();//得到用户没有授权的权限
        for (String per : permission) {
            // 检查权限，主要用于检测某个权限是否已经被授予，为PackageManager.PERMISSION_DENIED或者PackageManager.PERMISSION_GRANTED。当返回DENIED就需要进行申请授权了。
            if (ContextCompat.checkSelfPermission(activity, per) != PackageManager.PERMISSION_GRANTED) {
                //得到需要进行授权的
                listPermission.add(per);
            }
        }
        //先提示用户需要授权的权限
        String[] goPermission = new String[listPermission.size()];
        listPermission.toArray(goPermission);
        if (goPermission.length > 0) {
            //展出提示框，授权
            ActivityCompat.requestPermissions(activity, goPermission, MY_PERMISSIONS);
        } else {
            perTrue();
        }
    }

    /**
     * @param requestCode:code
     * @param permissions：权限
     * @param grantResults:权限验证结果(-1,禁止（无论用户是否点击不再提示）)
     */
    public void MyRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS: {
                //如果请求被取消了,结果数组是空的。
                if (grantResults.length > 0 && grantResults.length > 0) {
                    //循环判断，用户禁止了的权限
                    for (int i : grantResults) {
                        if (i == -1) {
                            getNoPermission();
                            return;
                        }
                    }
                    perTrue();
                } else {
                    perTrue();
                }
                return;
            }
        }
    }

    public void getNoPermission() {
//        String[] permission = {CAMERA, LOCATION, INTERNET, NFC, IMEI, BLUETOOTH};
        String strToast = "App need ";
        for (String per : permission) {
            // 检查权限，主要用于检测某个权限是否已经被授予，为PackageManager.PERMISSION_DENIED或者PackageManager.PERMISSION_GRANTED。当返回DENIED就需要进行申请授权了。
            if (ContextCompat.checkSelfPermission(activity, per) != PackageManager.PERMISSION_GRANTED) {
                if (per.equals(CAMERA))
                    strToast += "CAMERA、";
                else if (per.equals(LOCATION))
                    strToast += "LOCATION、";
                else if (per.equals(INTERNET))
                    strToast += "INTERNET、";
                else if (per.equals(NFC))
                    strToast += "NFC、";
                else if (per.equals(IMEI))
                    strToast += "获取IMEI编码、";
                else if (per.equals(BLUETOOTH))
                    strToast += "BLUETOOTH、";
            }
        }
        strToast = strToast.substring(0, strToast.length() - 1) + " Permissions";

        //这里的Dialog，请使用你自己App的dialog；
        PermissionsPromptDialog fbirdPromptDialog = new PermissionsPromptDialog(activity, "", strToast, "Y", "N") {
            @Override
            public void operate1() {
                //跳转App设置权限的界面
                Intent i = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                String pkg = "com.android.settings";
                String cls = "com.android.settings.applications.InstalledAppDetails";
                i.setComponent(new ComponentName(pkg, cls));
                i.setData(Uri.parse("package:" + activity.getPackageName()));
                activity.startActivity(i);
            }

            @Override
            public void operate2() {
                super.operate2();
                //如果用户不授权
            }
        };
    }

    //权限已经授权
    public abstract void perTrue();
}
