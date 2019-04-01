package com.bride.thirdparty.Strategy;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.bride.thirdparty.R;
import com.bride.thirdparty.ThirdPartyApplication;
import com.bride.thirdparty.util.BitmapCache;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.Map;

/**
 * <p>Created by shixin on 2018/9/16.
 */
public class VolleyStrategy {
    private static final String TAG = VolleyStrategy.class.getSimpleName();

    private RequestQueue mQueue;
    public VolleyStrategy() {
        mQueue = ThirdPartyApplication.getInstance().getRequestQueue();
    }

    public void executeGetString() {
        String url = "http://apis.juhe.cn/mobile/get?phone=13701116418&key=9a4329bdf84fa69d193ce601c22b949d";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.i(TAG, "executeGetString - onResponse - "+s);
                Toast.makeText(ThirdPartyApplication.getInstance(), s, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.i(TAG, "executeGetString - onErrorResponse - "+volleyError.getMessage());
                Toast.makeText(ThirdPartyApplication.getInstance(), volleyError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        stringRequest.setTag("xyz");
        mQueue.add(stringRequest);
        mQueue.start();
    }

    public void executePostString() {
        String url = "http://apis.juhe.cn/mobile/get";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.i(TAG, "executePostString - onResponse - "+s);
                Toast.makeText(ThirdPartyApplication.getInstance(), s, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.i(TAG, "executePostString - onErrorResponse - "+volleyError.getMessage());
                Toast.makeText(ThirdPartyApplication.getInstance(), volleyError.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new Hashtable<>();
                map.put("phone", "13701116418");
                map.put("key", "9a4329bdf84fa69d193ce601c22b949d");
                return map;
            }
        };
        stringRequest.setTag("xyz");
        mQueue.add(stringRequest);
        mQueue.start();
    }

    public void executePostJson() {
        // target Pie 启用TLS，isCleartextTrafficPermitted返回false
        String url = "https://postman-echo.com/post";
        String params = "{\"phone\": \"13701116418\", \"key\": \"9a4329bdf84fa69d193ce601c22b949d\"}";
        JsonRequest<JSONObject> jsonObjectRequest = new JsonRequest<JSONObject>(Request.Method.POST, url,
                params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.i(TAG, "executePostJson - onResponse - "+jsonObject.toString());
                Toast.makeText(ThirdPartyApplication.getInstance(), jsonObject.toString(), Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.i(TAG, "executePostJson - onErrorResponse - "+volleyError.getMessage());
                Toast.makeText(ThirdPartyApplication.getInstance(), volleyError.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {
                    String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                    return Response.success(new JSONObject(jsonString), HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException var3) {
                    return Response.error(new ParseError(var3));
                } catch (JSONException var4) {
                    return Response.error(new ParseError(var4));
                }
            }
        };
        jsonObjectRequest.setTag("xyz");
        mQueue.add(jsonObjectRequest);
        mQueue.start();
    }

    public void executeImage(final ImageView imageView) {
        String url = "http://img1.3lian.com/2015/a2/204/d/15.jpg";
        ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
            }
        }, 180, 240, ImageView.ScaleType.CENTER_CROP, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG,"executeImage - onErrorResponse - "+volleyError.getMessage());
            }
        });
        mQueue.add(imageRequest);
        mQueue.start();
    }

    // 自定义lru缓存
    public void executeImageCache(final ImageView imageView) {
        String url = "http://img1.3lian.com/2015/a2/204/d/15.jpg";
        ImageLoader imageLoader = new ImageLoader(mQueue, new BitmapCache());
        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(imageView, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        imageLoader.get(url, imageListener, 0, 0, ImageView.ScaleType.CENTER_CROP);
    }

    // NetworkImageView使用
    public void executeNetworkImageView(final NetworkImageView imageView) {
        String url = "https://www.nanrenwo.net/uploads/171009/8478-1G009160016215.jpg";
        ImageLoader imageLoader = new ImageLoader(mQueue, new BitmapCache());
        imageView.setDefaultImageResId(R.mipmap.ic_launcher);
        imageView.setErrorImageResId(R.mipmap.ic_launcher);
        imageView.setImageUrl(url, imageLoader);
    }
}
