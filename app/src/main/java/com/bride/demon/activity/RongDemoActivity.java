package com.bride.demon.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.bride.demon.R;
import com.bride.demon.model.User;
import com.bride.demon.repository.RongConnect;
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
        toEditText = findViewById(R.id.edit_to_userId);
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
                User toUser = map.get(toEditText.getText().toString());
                RongIM.getInstance().startPrivateChat(this, toUser.userId, toUser.name);
                break;
            case R.id.tv_disconnect:
                // 断开连接，能收到推送消息
                RongIM.getInstance().disconnect();
                break;
            case R.id.tv_logout:
                // 断开连接，收不到推送消息
                RongIM.getInstance().logout();
                break;
        }
    }
}
