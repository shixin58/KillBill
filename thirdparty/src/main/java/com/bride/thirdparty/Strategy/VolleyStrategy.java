package com.bride.thirdparty.Strategy;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

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
import com.bride.thirdparty.BitmapCache;
import com.bride.thirdparty.protocal.IStrategy;
import com.bride.thirdparty.MyApplication;
import com.bride.thirdparty.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.Map;

/**
 * <p>Created by shixin on 2018/9/16.
 */
public class VolleyStrategy implements IStrategy {
    private RequestQueue mQueue;
    public VolleyStrategy() {
        mQueue = MyApplication.getInstance().getRequestQueue();
    }

    public void executeGetString() {
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
        mQueue.add(stringRequest);
        mQueue.start();
    }

    public void executePostString() {
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
        mQueue.add(stringRequest);
        mQueue.start();
    }

    @Override
    public void execute() {
        // post json
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
        mQueue.add(jsonObjectRequest);
        mQueue.start();
    }

    public void executeImage(final ImageView imageView) {
        String url = "http://n.sinaimg.cn/transform/20150811/Vza--fxftkpx3764400.jpg";
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
        mQueue.add(imageRequest);
        mQueue.start();
    }

    public void executeImage2(final ImageView imageView) {
        // 自定义lru缓存
        String url = "http://img.mingxing.com/upload/attach/2016/02-06/301004-FufQKQ.jpg";
        ImageLoader imageLoader = new ImageLoader(mQueue, new BitmapCache());
        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(imageView, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        imageLoader.get(url, imageListener, 0, 0, ImageView.ScaleType.CENTER_CROP);
    }

    public void executeImage3(final NetworkImageView imageView) {
        // NetworkImageView使用
        String url = "http://www.zj.xinhuanet.com/yltd/2016-03/17/1118363732_14582019140151n.jpg";
        ImageLoader imageLoader = new ImageLoader(mQueue, new BitmapCache());
        imageView.setDefaultImageResId(R.mipmap.ic_launcher);
        imageView.setErrorImageResId(R.mipmap.ic_launcher);
        imageView.setImageUrl(url, imageLoader);
    }
}
