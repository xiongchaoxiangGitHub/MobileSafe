package com.xiong.mobilesafe.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.xiong.R;

/**
 * Created by Administrator on 2017/7/25.
 */

public class Setup4Activity extends BaseSetupActivity {
    private SharedPreferences sp;
    private CheckBox cb_protecting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup4);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        cb_protecting = findViewById(R.id.cb_protecting);

        boolean protecting = sp.getBoolean("protecting", false);
        if (protecting) {
            cb_protecting.setText("手机防盗已经开启");
            cb_protecting.setChecked(true);
        } else {
            cb_protecting.setText("手机防盗已经开启");
            cb_protecting.setChecked(false);
        }
        cb_protecting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    cb_protecting.setText("手机防盗已经开启");

                } else {
                    cb_protecting.setText("手机防盗已经开启");
                }

                //保存选择状态
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("protecting", b);
                editor.commit();
            }
        });
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
