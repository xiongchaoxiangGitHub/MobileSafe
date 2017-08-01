package com.xiong.mobilesafe.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiong.R;

/**
 * Created by Administrator on 2017/7/25.
 */

public class LostFindActivity extends Activity {

    private SharedPreferences sp;
    private TextView tv_safenumber;
    private ImageView iv_protecting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        //判断是否做过设置向导，如果没有 就跳到设置页面 否者留在当前页面
        boolean configed = sp.getBoolean("configed", false);
        if (configed) {
            //就在手机防盗页面
            setContentView(R.layout.activity_lost_find);
            tv_safenumber = findViewById(R.id.tv_safenumber);
            iv_protecting = findViewById(R.id.iv_protecting);
            String safenumber = sp.getString("safenumber", "");
            tv_safenumber.setText(safenumber);
            //设置防盗保护状态
            boolean protecting = sp.getBoolean("protecting", false);
            if (protecting) {
                //已经开启防盗保护
                iv_protecting.setImageResource(R.drawable.lock);
            } else {
                //没有开启防盗保护
                iv_protecting.setImageResource(R.drawable.unlock);
            }

        } else {
            //还没有做过设置向导
            Intent intent = new Intent(this, Setup1Activity.class);
            startActivity(intent);
            finish();
        }
    }

    public void reEnterSetup(View view) {
        //还没有做过设置向导
        Intent intent = new Intent(this, Setup1Activity.class);
        startActivity(intent);
        finish();
    }
}
