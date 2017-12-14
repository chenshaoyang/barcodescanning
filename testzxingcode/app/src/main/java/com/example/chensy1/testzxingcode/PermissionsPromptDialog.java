package com.example.chensy1.testzxingcode;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by chensy1 on 2017/12/14.
 */

public class PermissionsPromptDialog {
    public PermissionsPromptDialog(Activity activity, String title, String msg, String y, String n) {
        new AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(y, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        operate1();
                    }
                })
                .setNegativeButton(n, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        operate2();
                    }
                })
                .create()
                .show();
    }

    public void operate1() {

    }

    public void operate2() {

    }
}
