package com.xiong.mobilesafe.ui;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.xiong.R;

/**http://blog.csdn.net/qq_34625397/article/details/56830877
 * Created by Administrator on 2017/7/14.
 */

public class SettingActivity extends Activity {
    private SettingItemView siv_update;
    private SharedPreferences sp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        siv_update = findViewById(R.id.siv_update);

        boolean update = sp.getBoolean("update", false);

        if (update) {
            siv_update.setChecked(false);
        } else {
            siv_update.setChecked(true);
        }

        siv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sp.edit();
                //判断是否选中
                // 已经打开自动升级了
                if (siv_update.isChecked()) {
                    siv_update.setChecked(false);
                    editor.putBoolean("update", false);
                } else {
                    //没有打开自动升级
                    siv_update.setChecked(true);
                    editor.putBoolean("update", true);
                }
                editor.commit();
            }
        });
    }
}
