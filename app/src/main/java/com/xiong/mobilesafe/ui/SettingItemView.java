package com.xiong.mobilesafe.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiong.R;

/**
 * 我们自定义的组合控件，它里面有两个TextView，还有一个CheckBox,还有一个View
 * Created by Administrator on 2017/7/15.
 */

public class SettingItemView extends RelativeLayout {

    private CheckBox cb_status;
    private TextView tv_desc;
    private TextView tv_title;

    private String desc_on;
    private String desc_off;

    public SettingItemView(Context context) {
        super(context);
        initView(context);
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        String title = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "title");
        desc_on = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "desc_on");
        desc_off = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "desc_off");

        tv_title.setText(title);

    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    /**
     * 初始化布局文件
     *
     * @param context
     */
    private void initView(Context context) {
        //把一个布局文件转成View,并加载在SettingItemView
        View.inflate(context, R.layout.setting_item_view, this);
        cb_status = findViewById(R.id.cb_status);
        tv_desc = findViewById(R.id.tv_desc);
        tv_title = findViewById(R.id.tv_title);
    }

    /**
     * 校验组合控件是否选中
     *
     * @return
     */
    public boolean isChecked() {
        return cb_status.isChecked();
    }

    /**
     * 设置组合控件的状态
     */
    public void setChecked(boolean checked) {
        if (checked) {
            setDesc(desc_on);
        } else {
            setDesc(desc_off);
        }
        cb_status.setChecked(checked);
    }

    /**
     * 组合控件的描述信息
     */
    public void setDesc(String text) {
        tv_desc.setText(text);
    }
}
