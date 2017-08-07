package com.xiong.mobilesafe.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.xiong.R;
import com.xiong.mobilesafe.utils.SmsTools;

import java.io.File;

/**
 * Created by 99270 on 2017/8/6.
 */

public class AtoolsActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atools);
    }

    /**
     * 号码归属地查询
     */
    public void numberAddressQuery(View view) {
        Intent intent = new Intent(this, NumberAddressQueryActivity.class);
        startActivity(intent);
    }

    /**
     * 常用号码查询
     */
    public void commonNumberQuery(View view) {
        Intent intent = new Intent(this, CommonNumberQueryActivity.class);
        startActivity(intent);
    }

    /**
     * 短信备份
     */
    public void smsBackup(View view) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            final File file = new File(Environment.getExternalStorageDirectory(), "smsbackup.xml");
            final ProgressDialog pd = new ProgressDialog(this);
            pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pd.setMessage("正在备份中...");
            pd.show();
            new Thread() {
                @Override
                public void run() {
                    try {
                        SmsTools.backup(getApplicationContext(), file.getAbsolutePath(), new SmsTools.BackUpCallBack() {
                            @Override
                            public void onSmsBackup(int progress) {
                                pd.setProgress(progress);
                            }

                            @Override
                            public void beforeSmsBackup(int total) {
                                pd.setMax(total);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    pd.dismiss();
                }
            }.start();
        } else {
            Toast.makeText(this, "SD卡不可用", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 进入程序锁界面
     */
    public void enterApplock(View view) {
        Intent intent = new Intent(this, AppLockActivity.class);
        startActivity(intent);
    }
}
