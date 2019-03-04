package com.zyd.autotrack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.zyd.test2.TestActivity;

import autotrack.AutoTrack;
import autotrack.annotations.Click;
import autotrack.annotations.ClickAndExposure;
import autotrack.annotations.Exposure;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Click("click")
    public View btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(this);
        AutoTrack.track(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, TestActivity.class);
        startActivity(intent);
    }
}
