package com.xiong.mobilesafe.ui;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xiong.R;

import static android.content.ContentValues.TAG;

/**
 * Created by 99270 on 2017/8/6.
 */

public class NumberAddressQueryActivity extends Activity {

    private EditText et_phone;
    private TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_query);


        et_phone = findViewById(R.id.et_phone);
        tv_result = findViewById(R.id.tv_result);
    }

    /**
     * 号码归属地查询
     */
    public void query(View view) {
        String phone = et_phone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
//            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
//            et_phone.startAnimation(shake);
            Toast.makeText(this, "号码不能为空", Toast.LENGTH_SHORT).show();
//            vibrator.vibrate(new long[]{100, 200, 100, 300, 50, 200}, 1);
            return;
        } else {
            Log.i(TAG, "查询：" + phone + "的归属地");
//            String address = AddressDao.getAddress(phone);
//            tv_result.setText(address);
        }
    }
}
