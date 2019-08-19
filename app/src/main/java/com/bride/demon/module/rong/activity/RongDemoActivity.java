package com.bride.demon.module.rong.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bride.baselib.PreferenceUtils;
import com.bride.demon.Finals;
import com.bride.demon.R;
import com.bride.demon.model.User;
import com.bride.demon.module.rong.RongConnect;
import com.bride.ui_lib.BaseActivity;

import java.util.HashMap;
import java.util.Map;

import io.rong.imkit.RongIM;

/**
 * <p>Created by shixin on 2019-08-01.
 */
public class RongDemoActivity extends BaseActivity {

    private EditText editText, toEditText;

    private Map<String, User> map = new HashMap<>();

    public static void openActivity(Context c) {
        Intent intent = new Intent(c, RongDemoActivity.class);
        c.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rong_demo);

        map.put("18600166830", new User("18600166830", "Jacob",
                "http://47.91.249.201:8080/images/jacob.jpg"));
        map.put("13701116418", new User("13701116418", "Max",
                "http://47.91.249.201:8080/images/max.jpg"));

        editText = findViewById(R.id.edit_from_userId);
        editText.setText(PreferenceUtils.getString(Finals.PHONE_NUMBER));
        toEditText = findViewById(R.id.edit_to_userId);
        toEditText.setText(PreferenceUtils.getString(Finals.TO_USER_PHONE));
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_rong_connect:
                User user = map.get(editText.getText().toString());
                RongConnect.getToken(user.userId, user.name, user.portraitUri);
                break;
            case R.id.tv_open_conversation_list:
                RongIM.getInstance().startConversationList(this , null);
                break;
            case R.id.tv_open_conversation:
                String toPhone = toEditText.getText().toString().trim();
                if (TextUtils.isEmpty(toPhone)) {
                    Toast.makeText(this, "toUserId不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                PreferenceUtils.putString(Finals.TO_USER_PHONE, toPhone);
                PreferenceUtils.commit();
                User toUser = map.get(toPhone);
                RongIM.getInstance().startPrivateChat(this, toUser.userId, toUser.name);
                break;
            case R.id.tv_disconnect:
                // 断开连接，能收到推送消息
                RongIM.getInstance().disconnect();
                Toast.makeText(this, "disconnect", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_logout:
                // 断开连接，收不到推送消息
                RongIM.getInstance().logout();
                Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
