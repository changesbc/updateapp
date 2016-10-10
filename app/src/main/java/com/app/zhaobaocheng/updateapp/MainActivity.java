package com.app.zhaobaocheng.updateapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private TextView versionName;  //版本名称
    private String code;
    private String apkurl;
    private String des;
    private ProgressBar progressBar;
    private TextView tv_splash_plan;
    private final int MSG_UPDATE_DIALOG=1;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    //弹出对话框
                    showdialog();
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(1);  //隱藏標題欄
        setContentView(R.layout.activity_main);
        versionName = (TextView) findViewById(R.id.splash_tv_versionname);
        versionName.setText("版本号:"+getVersionName());
//        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        tv_splash_plan = (TextView) findViewById(R.id.tv_splash_plan);

        update();//更新版本
    }

    //弹出更新对话框
    private void showdialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("新版本:"+code);
        builder.setMessage(des);
        builder.setPositiveButton("下载" +
                "", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //隐藏对话框
                dialog.dismiss();
                //跳转一个空白界面
                enter();
            }
        });

        //显示对话框
        builder.show();
    }

    //點擊取消按鈕
    private void enter() {
        startActivity(new Intent(MainActivity.this,EmptyActivity.class));
        finish();
    }

    //提醒用户更新版本
    private void update() {
        //链接服务器，查看是否有最新版本,耗时操作
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message=Message.obtain();
                try {
                    URL url=new URL("http://192.168.71.1:8080/updateinfo.html");
                    HttpURLConnection conn= (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(8000);
                    conn.setConnectTimeout(8000);
                    conn.setRequestMethod("GET");

                    if (conn.getResponseCode() == 200) {
                        //链接成功
                        Log.d("SplashActivity: ","链接成功");
                        //获取服务返回的流信息
                        InputStream in=conn.getInputStream();
                        //将获取到的流信息转换成字符串
                        String json=StreamUtil.parserStreamUtil(in);
                        //解析Json数据
                        JSONObject jsonObject=new JSONObject(json);
                        code = jsonObject.getString("code");
                        apkurl = jsonObject.getString("apkurl");
                        des = jsonObject.getString("des");

                        Log.d("SplashActivity: ","code: "+code+" apkurl: "+apkurl+" des: "+des);
                        if (code.equals(getVersionName())) {
                            //当前没有新版本

                        } else {
                            //有最新版本
                            //弹出对话框
                            message.what= MSG_UPDATE_DIALOG;

                        }
                    } else {
                        //连接失败
                        Log.d("SplashActivity: ","链接失败");
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    handler.sendMessage(message);
                }
            }
        }).start();

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
