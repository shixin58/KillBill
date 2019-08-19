package com.bride.demon.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bride.baselib.PreferenceUtils;
import com.bride.demon.Finals;
import com.bride.demon.R;
import com.bride.ui_lib.BaseActivity;

/**
 * <p>Created by shixin on 2019-08-19.
 */
public class LoginActivity extends BaseActivity {

    private EditText mEditPhone;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEditPhone = findViewById(R.id.edit_phone);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                String phoneNum = mEditPhone.getText().toString().trim();
                PreferenceUtils.putString(Finals.PHONE_NUMBER, phoneNum);
                PreferenceUtils.commit();
                Toast.makeText(this, phoneNum + " 已保存", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
