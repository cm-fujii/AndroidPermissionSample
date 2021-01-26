package com.example.fujiigenki.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST_LOCATION_PERMISSION = 1000;

    TextView permissionMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        permissionMessage = (TextView)findViewById(R.id.permission_message);
        permissionMessage.setText("None");


        // Android 6 (API 23) 以上で、パーミッションを確認する
        if (Build.VERSION.SDK_INT >= 23) {
            checkPermission();
        }
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // パーミッションが無いのでリクエストする
            requestLocationPermission();
        } else {
            permissionMessage.setText("許可されています");
        }
    }

    // ユーザーに許可を求める（位置情報）
    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(MainActivity.this,
            new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION
            },
            REQUEST_LOCATION_PERMISSION);
    }

    // ユーザが選択した結果を受け取る
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION: {
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // パーミッション許可された
                    permissionMessage.setText("許可されました");
                } else {
                    // パーミッション許可されなかった
                    permissionMessage.setText("許可されませんでした");
                }
                break;
            }
            default:
                break;
        }
    }
}
