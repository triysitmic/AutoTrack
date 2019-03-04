package com.zyd.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.zyd.test2.R;

import autotrack.AutoTrack;
import autotrack.annotations.ClickAndExposure;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    @ClickAndExposure("test")
    public Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_main);
        button = findViewById(R.id.btn1);
        button.setOnClickListener(this);

        AutoTrack.track(this);
    }

    @Override
    public void onClick(View view) {

    }
}
