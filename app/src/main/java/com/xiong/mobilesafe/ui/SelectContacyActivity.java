package com.xiong.mobilesafe.ui;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.xiong.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 99270 on 2017/7/30.
 */

public class SelectContacyActivity extends Activity {
    private ListView list_select_contact;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_contact);

        list_select_contact = findViewById(R.id.list_select_contact);
        List<Map<String, String>> data = getContactInfo();
        list_select_contact.setAdapter(new SimpleAdapter(this,data,R.layout.activity_select_contact,new String[]{"name","phone"},new int[]{}));
    }

    /**
     * 读取手机里的联系人
     *
     * @return
     */
    private List<Map<String, String>> getContactInfo() {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        //得到一个内容解析器
        ContentResolver resolver = getContentResolver();
        Uri uri = Uri.parse("content://com.anroid.contacts/raw_contacts");
        Uri uriData = Uri.parse("content://com.android.contacts/data");
        Cursor cursor = resolver.query(uri, new String[]{"contact_id"}, null, null, null);

        while (cursor.moveToNext()) {
            String contact_id = cursor.getString(0);
            if (contact_id != null) {
                Map<String, String> map = new HashMap<String, String>();
                Cursor dataCursor = resolver.query(uriData, new String[]{"data1", "minetype"}, "contact_id=?", new String[]{contact_id}, null);
                while (dataCursor.moveToNext()) {
                    String data1 = dataCursor.getString(0);
                    String minetype = dataCursor.getString(1);
                    if ("vnd.android.cursor.item/name".equals(minetype)) {
                        //联系人姓名
                        map.put("name", data1);

                    } else if ("vnd.android.cursor.item/phone_v2".equals(minetype)) {
                        //联系人的电话号码
                        map.put("phone", data1);
                    }
                }
                list.add(map);
                dataCursor.close();
            }
        }

        cursor.close();
        return list;
    }


}
