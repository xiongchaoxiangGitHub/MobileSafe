package com.xiong.mobilesafe.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiong.R;
import com.xiong.mobilesafe.utils.MD5Utils;

import static android.view.View.inflate;

/**
 * Created by Administrator on 2017/7/10.
 */

public class HomeActivity extends Activity {

    protected static final String TAG = "HomeActivity";
    private SharedPreferences sp;
    private GridView list_home;
    private MyAdapter adapter;
    private static String[] names = {"手机防盗", "通讯卫士", "软件管家",
            "进程管理", "流量统计", "手机杀毒",
            "缓存清理", "高级工具", "设置中心"};

    private static int[] ids = {R.drawable.safe, R.drawable.callmsgsafe, R.drawable.app,
            R.drawable.taskmanager, R.drawable.netmanager, R.drawable.trojan,
            R.drawable.sysoptimize, R.drawable.atools, R.drawable.settings
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        list_home = findViewById(R.id.list_home);
        adapter = new MyAdapter();
        list_home.setAdapter(adapter);
        list_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 8://设置中心
                        Intent intent = new Intent(HomeActivity.this, SettingActivity.class);
                        startActivity(intent);
                        break;
                    case 0://进入手机防盗页面
                        showLostFindDialog();
                        break;
                    case 7:
                        Intent intents = new Intent(HomeActivity.this, AtoolsActivity.class);
                        startActivity(intents);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void showLostFindDialog() {
        //判断是否设置过密码
        if (isSetupPwd()) {
            //已经设置密码，让该用户填写密码
            showEnterDiaolog();
        } else {
            //没有设置密码，进入设置密码对话框
            showSetupPwdDialog();
        }
    }

    private EditText et_setup_pwd;
    private EditText et_setup_confirm;
    private Button ok;
    private Button cancel;
    private AlertDialog dialog;

    /**
     * 设置密码对话框
     */
    private void showSetupPwdDialog() {
        AlertDialog.Builder build = new AlertDialog.Builder(HomeActivity.this);
        //自定义一个布局View
        View view = View.inflate(HomeActivity.this, R.layout.dialog_setup_password, null);

        et_setup_pwd = view.findViewById(R.id.et_setup_pwd);
        et_setup_confirm = view.findViewById(R.id.et_setup_confirm);
        ok = view.findViewById(R.id.setup_ok);
        cancel = view.findViewById(R.id.setup_cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //取出密码
                String password = et_setup_pwd.getText().toString().trim();
                String password_confirm = et_setup_confirm.getText().toString().trim();
                if (TextUtils.isEmpty(password) || TextUtils.isEmpty(password_confirm)) {
                    Toast.makeText(HomeActivity.this, "密码为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                //判断密码是不是一样
                if (password.equals(password_confirm)) {
                    //一致 保存密码 对话框取消 进入手机防盗页面
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("password", MD5Utils.md5Password(password));//保存md5加密
                    editor.apply();
                    dialog.dismiss();
                    Log.i(TAG, "一致 保存密码 对话框取消 进入手机防盗页面");
                    Intent intent = new Intent(HomeActivity.this, LostFindActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(HomeActivity.this, "密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
        dialog = build.create();
        dialog.setView(view, 0, 0, 0, 0);
        dialog.show();
    }

    /**
     * 输入密码对话框
     */
    private void showEnterDiaolog() {
        AlertDialog.Builder build = new AlertDialog.Builder(HomeActivity.this);
        //自定义一个布局View
        View view = View.inflate(HomeActivity.this, R.layout.dialog_enter_password, null);

        et_setup_pwd = view.findViewById(R.id.et_setup_pwd);
        ok = view.findViewById(R.id.enter_ok);
        cancel = view.findViewById(R.id.enter_cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = et_setup_pwd.getText().toString().trim();
                String savePassword = sp.getString("password", "");//取出md5加密后的
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(HomeActivity.this, "密码为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (MD5Utils.md5Password(password).equals(savePassword)) {
                    //输入密码正确
                    dialog.dismiss();
                    Log.i(TAG, "把对话框取消掉，进入主页面");
                    Intent intent = new Intent(HomeActivity.this, LostFindActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(HomeActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                    et_setup_pwd.setText("");
                    return;
                }
            }
        });

        dialog = build.create();
        dialog.setView(view, 0, 0, 0, 0);
        dialog.show();
    }

    /**
     * 判断是否设置过密码
     *
     * @return
     */
    private boolean isSetupPwd() {
        String password = sp.getString("password", null);

        //        if (TextUtils.isEmpty(password)) {
        //            return false;
        //        } else {
        //            return true;
        //        }

        return !TextUtils.isEmpty(password);
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return names.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = inflate(HomeActivity.this, R.layout.list_item_home, null);
            ImageView iv_item = view1.findViewById(R.id.iv_item);
            TextView tv_item = view1.findViewById(R.id.tv_item);
            tv_item.setText(names[i]);
            iv_item.setImageResource(ids[i]);
            return view1;
        }
    }
}
