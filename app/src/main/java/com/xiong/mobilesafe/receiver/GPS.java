package com.xiong.mobilesafe.receiver;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.widget.TextView;

/**
 * Created by 99270 on 2017/8/3.
 */

public class GPS extends Activity {
    //用到位置服务
    private LocationManager lm;
    private MyLocationListener listener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
    protected void onDestroy() {
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

            TextView textview = new TextView(GPS.this);
            textview.setText(longitude + "\n" + latitude + "\n" + accuracy);
            setContentView(textview);
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
