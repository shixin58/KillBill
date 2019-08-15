package com.bride.demon.module.rong;

import android.util.Log;
import android.widget.Toast;

import com.bride.baselib.codec.MD5Utils;
import com.bride.baselib.net.Urls;
import com.bride.demon.DemonApplication;
import com.bride.baselib.net.VolleyWrapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

/**
 * <p>Created by shixin on 2019-08-01.
 */
public class RongConnect {
    public static final String TAG = RongConnect.class.getSimpleName();
    public static String token;

    /**
     * 例：18600166830 - Jacob - http://47.91.249.201:8080/images/jacob.jpg,
     * 13701116418 - Max - http://47.91.249.201:8080/images/max.jpg
     * @param userId 手机号
     * @param name 昵称
     * @param portraitUri 头像url
     */
    public static void getToken(String userId, String name, String portraitUri) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("name", name);
        params.put("portraitUri", portraitUri);
        Map<String, String> headers = new HashMap<>();
        headers.put("RC-App-Key", Urls.RONG_APP_KEY);
        headers.put("RC-Nonce", String.valueOf(new Random(System.nanoTime()).nextInt()));
        headers.put("RC-Timestamp", String.valueOf(System.currentTimeMillis()));
        headers.put("RC-Signature", MD5Utils.sha1(Urls.RONG_APP_SECRET+headers.get("RC-Nonce")
                +headers.get("RC-Timestamp")));
        VolleyWrapper.getInstance().postString(Urls.RONG_GET_TOKEN, params, new VolleyWrapper.Callback() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    int code = object.getInt("code");
                    if (code != 200)
                        return;
                    token = object.getString("token");
                    connect();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(DemonApplication.Companion.getInstance(), response, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(DemonApplication.Companion.getInstance(),
                        e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }, null, headers);
    }

    public static void connect() {
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                Log.i(TAG, "onTokenIncorrect");
            }

            @Override
            public void onSuccess(String s) {
                Log.i(TAG, "onSuccess "+s);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.i(TAG, "onError "+errorCode.toString());
            }
        });
    }
}
