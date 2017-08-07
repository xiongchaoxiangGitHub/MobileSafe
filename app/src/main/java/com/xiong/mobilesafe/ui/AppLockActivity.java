package com.xiong.mobilesafe.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.xiong.R;

/**
 * Created by 99270 on 2017/8/6.
 */

public class AppLockActivity extends Activity implements OnClickListener {

    private TextView tv_unlock;
    private TextView tv_locked;
    private LinearLayout ll_unlock;
    private LinearLayout ll_locked;

    private ListView lv_unlock;
    private ListView lv_locked;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_lock);

        tv_locked = findViewById(R.id.tv_locked);
        tv_unlock = findViewById(R.id.tv_unlock);
        tv_locked.setOnClickListener(this);
        tv_unlock.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_locked:
                tv_locked.setBackgroundResource(R.drawable.tab_right_pressed);
                tv_unlock.setBackgroundResource(R.drawable.tab_left_default);
                ll_unlock.setVisibility(View.GONE);
                break;
            case R.id.tv_unlock:
                tv_locked.setBackgroundResource(R.drawable.tab_right_default);
                tv_unlock.setBackgroundResource(R.drawable.tab_left_pressed);
                ll_unlock.setVisibility(View.VISIBLE);
                break;
        }
    }
}
