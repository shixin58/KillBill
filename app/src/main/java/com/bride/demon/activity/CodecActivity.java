package com.bride.demon.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.bride.demon.EncryptDecryptUtils;
import com.bride.demon.R;
import com.bride.ui_lib.BaseActivity;

/**
 * <li>集成Stetho，运行app</li>
 * <li>Chrome浏览器输入chrome://inspect, 点击对应process</li>
 * <li>copy数据至Mac版微信，发送至手机微信</li>
 * <li>copy数据至app编辑框，执行编解码</li>
 * <li>copy数据至手机微信，发送至Mac微信</li>
 * <li>copy至Mac文件，Chrome浏览器打开</li>
 * <p>Created by shixin on 2019-08-30.
 */
public class CodecActivity extends BaseActivity {

    private EditText editContent;

    public static void openActivity(Context ctx) {
        Intent intent = new Intent(ctx, CodecActivity.class);
        ctx.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codec);

        editContent = findViewById(R.id.edit_result);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_encode:
                editContent.setText(EncryptDecryptUtils.doEncryptEncode(editContent.getText().toString()));
                break;
            case R.id.btn_decode:
                try {
                    editContent.setText(EncryptDecryptUtils.doDecodeDecrypt(editContent.getText().toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
