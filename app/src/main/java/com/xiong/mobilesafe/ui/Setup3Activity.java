package com.xiong.mobilesafe.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.xiong.R;

/**
 * Created by Administrator on 2017/7/25.
 */

public class Setup3Activity extends BaseSetupActivity {
    private EditText et_setup3_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);

        et_setup3_phone = findViewById(R.id.et_setup3_phone);

        et_setup3_phone.setText(sp.getString("safenumber", ""));
    }

    @Override
    public void showNext() {
        String phone = et_setup3_phone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "安全号码还没有设置", Toast.LENGTH_SHORT).show();
        }
        //应该保持一些安全号码
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("safenumber", phone);
        editor.commit();

        Intent intent = new Intent(this, Setup4Activity.class);
        startActivity(intent);
        finish();

        overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
    }

    @Override
    public void showPre() {
        Intent intent = new Intent(this, Setup2Activity.class);
        startActivity(intent);
        finish();

        overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
    }

    /**
     * 选择联系人的点击事件
     *
     * @param view
     */
    public void selectContact(View view) {
        Intent intent = new Intent(this, SelectContacyActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null)
            return;

        String phone = data.getStringExtra("phone".replace("-", ""));
        et_setup3_phone.setText(phone);
    }
}


