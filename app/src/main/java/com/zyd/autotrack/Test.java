package com.zyd.autotrack;

import android.view.View;

import com.zyd.interceptor.ClickInterceptor;

public class Test {

    public void show(View view, long time){
        if (ClickInterceptor.canClick(view, 1000)){

        }
    }
}
