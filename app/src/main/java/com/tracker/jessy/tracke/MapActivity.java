package com.tracker.jessy.tracke;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


public class MapActivity extends AppCompatActivity {

    private static final String TAG = "MapActivity";

    @Override
    protected void onCreate(@Nullable Bundle state){
        super.onCreate(state);
        setContentView(R.layout.map_main);
    }
}
