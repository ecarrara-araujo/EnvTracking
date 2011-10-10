package com.uint.envtracking.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.uint.envtracking.R;

public class EnvTrackingActivity extends FragmentActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collector);
    }
}