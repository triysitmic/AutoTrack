package com.zyd.autotrack;

import android.view.View;

import com.zyd.interceptor.ClickInterceptor;

public class Test {

    private void show(View view){
        if (ClickInterceptor.canClick(view, ClickInterceptor.getDefaultInterceptTime())){
            return;
        }
    }
}
