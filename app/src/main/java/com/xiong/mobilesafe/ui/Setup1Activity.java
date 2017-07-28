package com.xiong.mobilesafe.ui;

import android.content.Intent;
import android.os.Bundle;

import com.xiong.R;

/**
 * Created by Administrator on 2017/7/25.
 */

public class Setup1Activity extends BaseSetupActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup1);
    }

    public void showNext() {
        Intent intent = new Intent(this, Setup2Activity.class);
        startActivity(intent);
        finish();

        overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
    }

    public void showPre() {

    }
}
