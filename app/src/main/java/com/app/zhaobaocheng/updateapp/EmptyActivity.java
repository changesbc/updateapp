package com.app.zhaobaocheng.updateapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

/**
 * Created by ZhaoBaocheng on 2016/10/10.
 */
public class EmptyActivity extends AppCompatActivity{

    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);


    }
}
