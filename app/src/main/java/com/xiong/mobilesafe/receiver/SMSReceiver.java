package com.xiong.mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.util.Log;

import com.xiong.R;

/**
 * Created by Administrator on 2017/8/1.
 */

public class SMSReceiver extends BroadcastReceiver {
    private static final String TAG = "SMSReceiver";
    private SharedPreferences sp;

    @Override
    public void onReceive(Context context, Intent intent) {
        //接收短信代码
        Object[] objs = (Object[]) intent.getExtras().get("pdus");

        sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        for (Object b : objs) {
            //具体莫一条短信
            SmsMessage sms = SmsMessage.createFromPdu((byte[]) b);
            // 发送者
            String sender = sms.getOriginatingAddress();
            String safenumber = sp.getString("safenumber", "");
            if (sender.contains(safenumber)) {
//                Toast.makeText(context, sender, Toast.LENGTH_SHORT).show();
                Log.i(TAG, "====sneder=====" + sender);
                String body = sms.getMessageBody();
                if ("#*location*#".equals(body)) {
                    Log.i(TAG, "得到手机的GPS");
                    Intent i = new Intent(context, GPSService.class);
                    context.startService(i);
                    SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
                    String lastlocation = sp.getString("lastlocation", null);
                    if (TextUtils.isEmpty(lastlocation)) {
                        SmsManager.getDefault().sendTextMessage(sender, null, "get location", null, null);
                    } else {
                        SmsManager.getDefault().sendTextMessage(sender, null, lastlocation, null, null);
                    }
                    abortBroadcast();
                } else if ("#*alarm*#".equals(body)) {
                    Log.i(TAG, "播放报警音乐");
                    MediaPlayer player = MediaPlayer.create(context, R.raw.ylzs);
                    player.setLooping(false);
                    player.setVolume(1.0f, 1.0f);
                    player.start();
                    abortBroadcast();
                } else if ("#*wipedata*#".equals(body)) {
                    Log.i(TAG, "远程清除数据");
                    abortBroadcast();
                } else if ("#*lockscreen*#".equals(body)) {
                    Log.i(TAG, "远程锁屏");
                    abortBroadcast();
                }
            }

        }
    }
}
