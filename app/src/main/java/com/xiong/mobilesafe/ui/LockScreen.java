package com.xiong.mobilesafe.ui;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.xiong.R;

/**
 * Created by 99270 on 2017/8/5.
 */

public class LockScreen extends Activity {
    private DevicePolicyManager dpm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lock_screen);

        dpm = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
    }

    public void lockscreen(View view) {
        dpm.lockNow();
    }
}
