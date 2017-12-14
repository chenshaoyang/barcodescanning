package com.example.chensy1.testzxingcode;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chensy1.testzxingcode.corecode.CaptureActivity;

public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_SCANNING_RESPONSE_CODE = 100;
    private AppRuntimePermissionHelper arp;
    public final String CAMERA = Manifest.permission.CAMERA;//位置权限

    Button click;
    TextView numb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        click = (Button) findViewById(R.id.click);
        numb = (TextView) findViewById(R.id.numb);

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String[] permission = {CAMERA};
                //权限检查
                arp = new AppRuntimePermissionHelper(MainActivity.this, permission) {
                    //权限检查完毕
                    @Override
                    public void perTrue() {
                        //权限授权通过后，所做的操作
                        Intent intent = new Intent(getApplicationContext(), CaptureActivity.class);
                        startActivityForResult(intent, CAMERA_SCANNING_RESPONSE_CODE);
                    }
                };

            }
        });
    }

    //必须要把result返回给AppRuntimePermission【权限类】用来判断用户是否授权
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        arp.MyRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 100 && data != null) {
            String result = data.getStringExtra("result");
            numb.setText(result);
        }
    }
}
