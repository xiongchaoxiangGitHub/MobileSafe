package com.xiong.mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

/**
 * Created by 99270 on 2017/7/30.
 */

public class BootCompleteReceiver extends BroadcastReceiver {
    private SharedPreferences sp;
    private TelephonyManager tm;

    @Override
    public void onReceive(Context context, Intent intent) {

        sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        boolean protecting = sp.getBoolean("protecting", false);

        if (protecting) {
            tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            //读取之前保存的sim卡信息
            String saveSim = sp.getString("sim", "" + "afu");

            //读取当前的sim卡信息
            String realSim = tm.getSimSerialNumber();

            //比较是否一样
            if (saveSim.equals(realSim)) {
                //sim卡没有变更
            } else {
                //sim卡已经变更 发短信给安全号码
                System.out.println("sim卡已经变更");
                Toast.makeText(context, "sim卡已经变更", Toast.LENGTH_SHORT).show();
                SmsManager.getDefault().sendTextMessage(sp.getString("safenumber", ""), null,
                        "sim changed....", null, null);
            }
        }

    }
}
