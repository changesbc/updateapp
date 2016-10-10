package com.app.zhaobaocheng.updateapp;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView versionName;  //版本名称
    private String code;
    private String apkurl;
    private String des;
    private ProgressBar progressBar;
    private TextView tv_splash_plan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(1);  //隱藏標題欄
        setContentView(R.layout.activity_main);
        versionName = (TextView) findViewById(R.id.splash_tv_versionname);
        versionName.setText("版本号:"+getVersionName());
//        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        tv_splash_plan = (TextView) findViewById(R.id.tv_splash_plan);
    }

    //獲得版本號
    private String getVersionName() {
        PackageManager pm=getPackageManager();
        try {
            PackageInfo info=pm.getPackageInfo(getPackageName(),0);
            //获得版本号名称
            String versionName=info.versionName;
            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


}
