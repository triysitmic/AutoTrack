package com.zyd.autotrack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.zyd.test.TestActivity;

import autotrack.AutoTrack;
import autotrack.annotations.ClickAndExposure;
import autotrack.annotations.Page;

@Page("main activity")
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @ClickAndExposure("btn15")
    public View btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btn15);
        btn.setOnClickListener(this);
        AutoTrack.track(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, TestActivity.class);
        startActivity(intent);
    }
}
