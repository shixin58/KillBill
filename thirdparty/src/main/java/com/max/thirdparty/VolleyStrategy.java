package com.max.thirdparty;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

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
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * <p>Created by shixin on 2018/9/16.
 */
public class VolleyStrategy implements IContextStrategy {
    @Override
    public void execute(Context context) {
        /*RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        String url = "http://apis.juhe.cn/mobile/get?phone=13701116418&key=9a4329bdf84fa69d193ce601c22b949d";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.i("onResponse", s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.i("onErrorResponse", volleyError.getMessage());
            }
        });
        stringRequest.setTag("xyz");
        requestQueue.add(stringRequest);
        requestQueue.start();*/
        /*RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        String url = "http://apis.juhe.cn/mobile/get";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.i("onResponse", s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.i("onErrorResponse", volleyError.getMessage());
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
        requestQueue.add(stringRequest);
        requestQueue.start();*/
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = "http://apis.juhe.cn/mobile/get";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("phone").append("=").append("13701116418");
        stringBuilder.append("&").append("key").append("=").append("9a4329bdf84fa69d193ce601c22b949d");
        JsonRequest<JSONObject> jsonObjectRequest = new JsonRequest<JSONObject>(Request.Method.POST, url, stringBuilder.toString(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.i("onResponse", jsonObject.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.i("onErrorResponse", volleyError.getMessage());
            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=" + this.getParamsEncoding();
            }

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
        requestQueue.add(jsonObjectRequest);
        requestQueue.start();
    }

    public void execute(Context context, final ImageView imageView) {
        /*RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = "http://img.my.csdn.net/uploads/201507/21/1437459520_6685.jpg";
        ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
            }
        }, 0, 0, ImageView.ScaleType.CENTER_CROP, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        requestQueue.add(imageRequest);
        requestQueue.start();*/
        // 自定义lru缓存
        String url = "http://img.my.csdn.net/uploads/201507/21/1437459520_6685.jpg";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        ImageLoader imageLoader = new ImageLoader(requestQueue, new BitmapCache());
        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(imageView, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        imageLoader.get(url, imageListener, 0, 0, ImageView.ScaleType.CENTER_CROP);
    }

    public void execute(Context context, final NetworkImageView imageView) {
        // NetworkImageView使用
        String url = "http://img.my.csdn.net/uploads/201507/21/1437459520_6685.jpg";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        ImageLoader imageLoader = new ImageLoader(requestQueue, new BitmapCache());
        imageView.setDefaultImageResId(R.mipmap.ic_launcher);
        imageView.setErrorImageResId(R.mipmap.ic_launcher);
        imageView.setImageUrl(url, imageLoader);
    }
}
