package com.xiong.mobilesafe.service;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by 99270 on 2017/8/4.
 */

public class GPSService extends Service {
    //用到位置服务
    private LocationManager lm;
    private MyLocationListener listener;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        lm = (LocationManager) getSystemService(LOCATION_SERVICE);
//
//        List<String> provider = lm.getAllProviders();
//        for (String l : provider) {
//            System.out.println(l);
//        }

        listener = new MyLocationListener();
        //注册监听位置服务

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Criteria criteria = new Criteria();
        String provide = lm.getBestProvider(criteria, true);
        lm.requestLocationUpdates(provide, 0, 0, listener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //取消监听位置服务
        lm.removeUpdates(listener);
        listener = null;
    }

    class MyLocationListener implements LocationListener {

        //位置改变的时候回掉
        @Override
        public void onLocationChanged(Location location) {
            String longitude = "经度" + location.getLongitude();
            String latitude = "纬度" + location.getLatitude();
            String accuracy = "精确度" + location.getAccuracy();

            //位置变化发短信给安全号码

            //把标准的GPS坐标装还成火星坐标
            InputStream is;

            try {
                is = getAssets().open("axisoffset.dat");
                ModifyOffset offset = ModifyOffset.getInstance(is);
                offset.s2c(new PointDouble(location.getLatitude(),location.getLatitude()));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }


            SharedPreferences sp = getSharedPreferences("config",MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("lastlocation",longitude + latitude + accuracy);
            editor.commit();
        }

        //当前状态发生改变时回掉 开启--关闭 关闭--开启
        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        //位置提供者不可使用了回掉
        @Override
        public void onProviderEnabled(String s) {

        }

        //某一个位置提供者不可使用了调用
        @Override
        public void onProviderDisabled(String s) {

        }
    }
}
