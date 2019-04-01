package com.bride.thirdparty.Strategy;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * <p>Created by shixin on 2019/4/1.
 */
public class URLConnectionStrategy {
    public static final String DEMO_URL_GET = "http://apis.juhe.cn/mobile/get?phone=13701116418&key=9a4329bdf84fa69d193ce601c22b949d";
    public static final String DEMO_URL_POST = "https://postman-echo.com/post";

    public static void main(String[] args) {
        get();
        post();
    }

    public static void get() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(DEMO_URL_GET);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.connect();

                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = connection.getInputStream();
                        byte[] bytes = new byte[inputStream.available()];
                        inputStream.read(bytes);
                        System.out.println(new String(bytes));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void post() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(DEMO_URL_POST);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setUseCaches(false);
                    connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                    connection.connect();

                    String body = "{\"phone\": \"13701116418\", \"key\": \"9a4329bdf84fa69d193ce601c22b949d\"}";
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                    writer.write(body);
                    writer.close();

                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = connection.getInputStream();
                        byte[] bytes = new byte[inputStream.available()];
                        inputStream.read(bytes);
                        System.out.println(new String(bytes));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
