package com.bride.thirdparty.Strategy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bride.baselib.UrlParams;
import com.bride.baselib.Urls;
import com.bride.thirdparty.ThirdPartyApplication;
import com.facebook.stetho.urlconnection.ByteArrayRequestEntity;
import com.facebook.stetho.urlconnection.SimpleRequestEntity;
import com.facebook.stetho.urlconnection.StethoURLConnectionManager;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HttpsURLConnection;

/**
 * <p>Created by shixin on 2019/4/1.
 */
public class URLConnectionStrategy {
    private static final String TAG = URLConnectionStrategy.class.getSimpleName();

    public enum Platform {
        Java, Android
    }

    public static void main(String[] args) {
        getJava();
        postJava();
    }

    public static void getJava() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(new UrlParams(Urls.JUHE_MOBILE).put("phone", "13701116418").put("key", Urls.JUHE_KEY).toString());
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.connect();

                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = connection.getInputStream();
                        String result = inputStreamToString(inputStream);
                        show(result, Platform.Java);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void get() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(new UrlParams(Urls.JUHE_MOBILE).put("phone", "13701116418").put("key", Urls.JUHE_KEY).toString());
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.connect();

                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = connection.getInputStream();
                        String result = inputStreamToString2(inputStream);
                        show(result, Platform.Android);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void getStecho() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    StethoURLConnectionManager stethoURLConnectionManager = new StethoURLConnectionManager("APOD RSS");

                    URL url = new URL(new UrlParams(Urls.JUHE_MOBILE).put("phone", "13701116418").put("key", Urls.JUHE_KEY).toString());
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Accept-Encoding", "gzip");
                    stethoURLConnectionManager.preConnect(connection, null);
                    connection.connect();
                    stethoURLConnectionManager.postConnect();

                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = connection.getInputStream();
                        inputStream = stethoURLConnectionManager.interpretResponseStream(inputStream);
                        if (inputStream != null && "gzip".equals(connection.getContentEncoding())) {
                            inputStream = new GZIPInputStream(inputStream);
                        }
                        String result = inputStreamToString2(inputStream);
                        show(result, Platform.Android);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void getImage(ImageView imageView) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(Urls.Images.LOGO_REDIRECT);
                    HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();

                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = connection.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        ThirdPartyApplication.getInstance().getHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                imageView.setImageBitmap(bitmap);
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void getImageStecho(ImageView imageView) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    StethoURLConnectionManager stethoURLConnectionManager = new StethoURLConnectionManager("APOD RSS");

                    URL url = new URL(Urls.Images.LOGO_REDIRECT);
                    HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.setRequestProperty("Accept-Encoding", "gzip");
                    stethoURLConnectionManager.preConnect(connection, null);
                    connection.connect();
                    stethoURLConnectionManager.postConnect();

                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = connection.getInputStream();
                        inputStream = stethoURLConnectionManager.interpretResponseStream(inputStream);
                        if (inputStream != null && "gzip".equals(connection.getContentEncoding())) {
                            inputStream = new GZIPInputStream(inputStream);
                        }
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        ThirdPartyApplication.getInstance().getHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                imageView.setImageBitmap(bitmap);
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void postJava() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(Urls.POSTMAN_POST);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setUseCaches(false);
                    connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                    connection.connect();

                    String body = "{\"phone\": \"13701116418\", \"key\": \""+Urls.JUHE_KEY+"\"}";
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                    writer.write(body);
                    writer.close();

                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = connection.getInputStream();
                        String result = inputStreamToString(inputStream);
                        show(result, Platform.Java);
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
                    URL url = new URL(Urls.POSTMAN_POST);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setUseCaches(false);
                    connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                    connection.connect();

                    String body = "{\"phone\": \"13701116418\", \"key\": \""+Urls.JUHE_KEY+"\"}";
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                    writer.write(body);
                    writer.close();

                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = connection.getInputStream();
                        String result = inputStreamToString2(inputStream);
                        show(result, Platform.Android);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void postStecho() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    StethoURLConnectionManager stethoURLConnectionManager = new StethoURLConnectionManager("APOD RSS");
                    URL url = new URL(Urls.POSTMAN_POST);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setUseCaches(false);
                    connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                    connection.setRequestProperty("Accept-Encoding", "gzip");
                    String body = "{\"phone\": \"13701116418\", \"key\": \""+Urls.JUHE_KEY+"\"}";
                    SimpleRequestEntity requestEntity = new ByteArrayRequestEntity(body.getBytes());
                    stethoURLConnectionManager.preConnect(connection, requestEntity);
                    requestEntity.writeTo(connection.getOutputStream());
                    connection.connect();
                    stethoURLConnectionManager.postConnect();

                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = connection.getInputStream();
                        inputStream = stethoURLConnectionManager.interpretResponseStream(inputStream);
                        if (inputStream != null && "gzip".equals(connection.getContentEncoding())) {
                            inputStream = new GZIPInputStream(inputStream);
                        }
                        String result = inputStreamToString2(inputStream);
                        show(result, Platform.Android);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static String inputStreamToString(InputStream inputStream) {
        try {
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            return new String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String inputStreamToString2(InputStream inputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        try {
            while ((length = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, length);
            }
            return byteArrayOutputStream.toString(StandardCharsets.UTF_8.name());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void show(String value, Platform platform) {
        switch (platform) {
            case Android:
                Log.i(TAG, "show - "+value);
                ThirdPartyApplication.getInstance().getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ThirdPartyApplication.getInstance(), value, Toast.LENGTH_LONG).show();
                    }
                });
                break;
            case Java:
            default:
                System.out.println(value);
        }
    }
}
