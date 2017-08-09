package com.xiong.mobilesafe.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xiong.R;
import com.xiong.mobilesafe.db.dao.AddressDao;

import static android.content.ContentValues.TAG;

/**
 * Created by 99270 on 2017/8/6.
 */

public class NumberAddressQueryActivity extends Activity {

    private EditText et_phone;
    private TextView tv_result;
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //得到手机震动器的服务
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        setContentView(R.layout.activity_number_query);
        et_phone = findViewById(R.id.et_phone);
        et_phone.addTextChangedListener(new TextWatcher() {
            //当文本变化之前调用的方法
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            //当文本发生变化的时候调用的方法
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            //文本变化后调用的方法
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        tv_result = findViewById(R.id.tv_result);
    }

    /**
     * 号码归属地查询
     */
    public void query(View view) {
        String phone = et_phone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            et_phone.startAnimation(shake);
            Toast.makeText(this, "号码不能为空", Toast.LENGTH_SHORT).show();
            vibrator.vibrate(new long[]{100, 200, 100, 300, 50, 200}, 1);
            return;
        } else {
            Log.i(TAG, "查询：" + phone + "的归属地");
            String address = AddressDao.getAddress(phone);
            tv_result.setText(address);
        }
    }
}
