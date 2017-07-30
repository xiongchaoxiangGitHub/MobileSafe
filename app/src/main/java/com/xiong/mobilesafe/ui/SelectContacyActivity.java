package com.xiong.mobilesafe.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.xiong.R;

/**
 * Created by 99270 on 2017/7/30.
 */

public class SelectContacyActivity extends Activity {
    private ListView list_select_contact;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_contact);

        list_select_contact = findViewById(R.id.list_select_contact);

    }
}
