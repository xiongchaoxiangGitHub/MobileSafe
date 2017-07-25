package com.xiong.mobilesafe.ui;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * md5加密方法
 * Created by Administrator on 2017/7/25.
 */

public class MD5Utils {
    public static String md5Password(String password) {
        //1、得到一个信息摘要器
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(password.getBytes());
            StringBuffer buffer = new StringBuffer();
            //2、把每一个byte做一个与运算0xff
            for (byte b : result) {
                int number = b & 0xff;
                String str = Integer.toHexString(number);
                System.out.println(str);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }

            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }
}
