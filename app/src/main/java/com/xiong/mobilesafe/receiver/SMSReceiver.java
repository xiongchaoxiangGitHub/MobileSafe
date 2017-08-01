package com.xiong.mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * Created by Administrator on 2017/8/1.
 */

public class SMSReceiver extends BroadcastReceiver {
    private static final String TAG = "SMSReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        //接收短信代码
        Object[] objs = (Object[]) intent.getExtras().get("pdus");

        for (Object b : objs) {
            //具体莫一条短信
            SmsMessage sms = SmsMessage.createFromPdu((byte[]) b);
            // 发送者
            String sender = sms.getOriginatingAddress();
            String body = sms.getMessageBody();
            if("#*location*#".equals(body)) {
                //得到GPS
                Log.i(TAG,"收到防盗短信");
            }
        }
    }
}
