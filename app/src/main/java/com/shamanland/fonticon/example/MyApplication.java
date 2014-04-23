package com.shamanland.fonticon.example;

import android.app.Application;

import com.shamanland.fonticon.FontIconTypefaceHolder;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        FontIconTypefaceHolder.init(getAssets(), "icons.ttf");
    }
}
