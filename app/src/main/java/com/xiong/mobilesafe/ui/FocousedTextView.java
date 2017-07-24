package com.xiong.mobilesafe.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/7/14.
 * 自定义TextView
 * Error:(12, 39) This custom view should extend `android.support.v7.widget.AppCompatTextView` instead
 */

public class FocousedTextView extends android.support.v7.widget.AppCompatTextView {

    public FocousedTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public FocousedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FocousedTextView(Context context) {
        super(context);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}
