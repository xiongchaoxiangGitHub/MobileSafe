package com.xiong.mobilesafe.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;
import android.widget.Toast;

import com.xiong.R;
import com.xiong.mobilesafe.utils.StreamTools;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SplashActivity extends Activity {

    private static final int ENTER_HOME = 0;
    private static final int SHOW_UPDATE_DIALOG = 1;
    private static final int URL_ERR = 2;
    private static final int NETWORK_ERR = 3;
    private static final int JSON_ERR = 4;
    private String description;
    private String apkurl;
    private String version;
    protected static final String TAG = "SplashActivity";
    private TextView tv_splash_version;
    private TextView tv_update_info;

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sp = getSharedPreferences("config", MODE_PRIVATE);

        tv_splash_version = findViewById(R.id.tv_splash_version);
        tv_splash_version.setText("版本：" + this.getVersionName());
        tv_update_info = findViewById(R.id.tv_update_info);

        boolean update = sp.getBoolean("update", false);

        if (update) {
            //检查升级
            checkUpdate();
        } else {
            //自动升级已经关闭
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //进入主页面
                    enter_home();
                }
            }, 2000);
        }

        AlphaAnimation aa = new AlphaAnimation(0.2f, 1.0f);
        aa.setDuration(3000);
        findViewById(R.id.rl_root_splash).startAnimation(aa);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SHOW_UPDATE_DIALOG://显示更新对话框
                    Log.i(TAG, "显示更新对话框");
                    showUpdateDialog();
                    break;
                case ENTER_HOME://进入主页面
                    enter_home();
                    break;
                case URL_ERR://URL解析出错
                    Toast.makeText(SplashActivity.this, "URL出错", Toast.LENGTH_SHORT).show();
                    break;
                case NETWORK_ERR://网络连接出错
                    Toast.makeText(SplashActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                    enter_home();
                    break;
                case JSON_ERR://JSON解析出错
                    Toast.makeText(SplashActivity.this, "JSON解析出错", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    /**
     * 弹出升级对话框
     */
    private void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示升级");
        //builder.setCancelable(false);//强制升级
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                //进入主页面
                enter_home();
                dialogInterface.dismiss();
            }
        });
        builder.setMessage(description);
        builder.setPositiveButton("立即升级", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //下载APK，替换安装
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    FinalHttp finalHttp = new FinalHttp();
                    finalHttp.download(apkurl, Environment.getExternalStorageDirectory()
                            .getAbsolutePath() + "/mobilesafe2.0.apk", new AjaxCallBack<File>() {

                        @Override
                        public void onSuccess(File file) {
                            super.onSuccess(file);
                            installAPK(file);
                        }

                        private void installAPK(File file) {
                            Intent intent = new Intent();
                            intent.setAction("android.intent.action.VIEW");
                            intent.addCategory("android.intent.category.DEFAULT");
                            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android" +
                                    ".package-archive");

                            startActivity(intent);
                        }

                        @Override
                        public void onLoading(long count, long current) {
                            super.onLoading(count, current);
                            tv_update_info.setVisibility(View.VISIBLE);
                            int progess = (int) (current * 100 / count);
                            tv_update_info.setText("下载进度：" + progess + "%");
                        }

                        @Override
                        public void onFailure(Throwable t, int errorNo, String strMsg) {
                            t.printStackTrace();
                            Toast.makeText(getApplicationContext(), "下载失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "没有SD卡", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        builder.setNegativeButton("下次再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                //用户不升级，直接进入主页面
                enter_home();
            }
        });

        builder.show();
    }

    private void enter_home() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        //关闭当前页面
        finish();
    }

    /**
     * 检测是否有新版本，有就升级
     */
    private void checkUpdate() {
        new Thread() {
            public void run() {
                Message mes = Message.obtain();
                long startTime = System.currentTimeMillis();
                try {
                    URL url = new URL(getString(R.string.serverurl));
                    //联网
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    con.setConnectTimeout(4000);
                    int code = con.getResponseCode();
                    if (code == 200) {
                        //联网成功
                        InputStream is = con.getInputStream();
                        //把流转换成String
                        String result = StreamTools.readFromStream(is);
                        Log.i(TAG, "联网成功!!!!!!" + result);
                        //json解析
                        JSONObject obj = new JSONObject(result);
                        version = (String) obj.get("version");
                        description = (String) obj.get("description");
                        apkurl = (String) obj.get("apkurl");
                        //校验是否有新版本
                        if (getVersionName().equals(version)) {
                            //版本一致，没有新版本，直接进入主页面
                            mes.what = ENTER_HOME;
                        } else {
                            //有新版本，弹出升级对话框
                            mes.what = SHOW_UPDATE_DIALOG;
                        }
                    }
                } catch (MalformedURLException e) {
                    mes.what = URL_ERR;
                    e.printStackTrace();
                } catch (IOException e) {
                    mes.what = NETWORK_ERR;
                    e.printStackTrace();
                } catch (JSONException e) {
                    mes.what = JSON_ERR;
                    e.printStackTrace();
                } finally {
                    long endTime = System.currentTimeMillis();
                    //用时2s
                    long dTime = endTime - startTime;
                    if (dTime < 4000) {
                        try {
                            Thread.sleep(4000 - dTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    handler.sendMessage(mes);
                }
            }

            ;
        }.start();
    }

    /**
     * 得到应用程序名称
     *
     * @return
     */
    private String getVersionName() {
        PackageManager pm = getPackageManager();

        try {
            PackageInfo info = pm.getPackageInfo(getPackageName(), 0);
            return info.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";

        }
    }
}
