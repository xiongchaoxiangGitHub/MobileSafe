package com.xiong.mobilesafe.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.xiong.R;

/**
 * Created by Administrator on 2017/7/25.
 */

public class Setup1Activity extends Activity {
    //1、定义一个手势识别器
    private GestureDetector detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup1);
        //2、实例化这个识别器
        detector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            /**
             *手指滑动回调
             * @param e1
             * @param e2
             * @param velocityX
             * @param velocityY
             * @return
             */
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (e2.getRawX() - e1.getRawX() > 200) {
                    //显示上一个页面 左到右划
                }

                if (e1.getRawX() - e2.getRawX() > 200) {

                }

                return super.onFling(e1, e2, velocityX, velocityY);
            }
        }
        );
    }


    public void next(View view) {
        Intent intent = new Intent(this, Setup2Activity.class);
        startActivity(intent);
        finish();

        overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
    }
}
