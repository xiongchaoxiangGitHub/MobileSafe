package com.xiong.mobilesafe.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.xiong.R;

/**
 * Created by Administrator on 2017/7/25.
 */

public class Setup4Activity extends BaseSetupActivity {
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup4);
        sp = getSharedPreferences("config", MODE_PRIVATE);
    }

    @Override
    public void showNext() {
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("configed", true);
        editor.apply();

        Intent intent = new Intent(this, LostFindActivity.class);
        startActivity(intent);
        finish();

        overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
    }

    @Override
    public void showPre() {
        Intent intent = new Intent(this, Setup3Activity.class);
        startActivity(intent);
        finish();

        overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
    }
}
