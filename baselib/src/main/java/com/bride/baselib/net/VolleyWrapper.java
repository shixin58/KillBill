package com.bride.baselib.net;

import android.app.Application;
import android.content.Context;
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
import com.android.volley.toolbox.Volley;
import com.bride.baselib.BitmapCache;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Volley可设置app全局单例
 * 网络请求入队出队使用 PriorityBlockingQueue<Request>#add/take。
 * 网络请求默认UrlConnection, 可自定义。
 * 磁盘缓存目录为Context#getCacheDir()(/data/data/package/cache/volley)，默认5MB。
 * <p>Created by shixin on 2018/9/16.
 */
public class VolleyWrapper {
    private static final String TAG = VolleyWrapper.class.getSimpleName();

    private static Context context;
    private RequestQueue mQueue;

    private static class InstanceWrapper {
        static VolleyWrapper INSTANCE = new VolleyWrapper();
    }

    private VolleyWrapper() {
        mQueue = Volley.newRequestQueue(context);
    }

    public static void init(Application application) {
        context = application;
    }

    public static VolleyWrapper getInstance() {
        return InstanceWrapper.INSTANCE;
    }

    public void cancel(Object tag) {
        mQueue.cancelAll(tag);
    }

    public void getString(UrlParams urlParams, Callback callback, Object tag) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlParams.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error);
            }
        });
        stringRequest.setTag(tag);
        mQueue.add(stringRequest);
        mQueue.start();
    }

    public void postString(String url, Map<String, String> params, Callback callback) {
        postString(url, params, callback, null, null);
    }

    public void postString(String url, Map<String, String> params, Callback callback, Object tag) {
        postString(url, params, callback, tag, null);
    }

    public void postString(String url, Map<String, String> params,
                           Callback callback, Object tag, Map<String, String> headers) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (params==null || params.isEmpty()) {
                    return super.getParams();
                }
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (headers==null || headers.isEmpty()) {
                    return super.getHeaders();
                }
                return headers;
            }
        };
        if (tag != null)
            stringRequest.setTag(tag);
        mQueue.add(stringRequest);
        mQueue.start();
    }

    public void executePostJson(String url, Map<String, String> params, Callback callback, Object tag) {
        // target Pie 启用TLS，isCleartextTrafficPermitted返回false
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for(Map.Entry<String, String> entry : params.entrySet()) {
            sb.append("\"").append(entry.getKey()).append("\": \"").append(entry.getValue()).append("\"");
            sb.append(", ");
        }
        sb.append("}");
        JsonRequest<JSONObject> jsonObjectRequest = new JsonRequest<JSONObject>(Request.Method.POST, Urls.POSTMAN_POST,
                sb.toString(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                callback.onSuccess(jsonObject.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callback.onError(volleyError);
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
        jsonObjectRequest.setTag(tag);
        mQueue.add(jsonObjectRequest);
        mQueue.start();
    }

    public void executeImage(final ImageView imageView, String url) {
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
    public void executeImageCache(final ImageView imageView, String url, int defaultImageResId, int errorImageResId) {
        ImageLoader imageLoader = new ImageLoader(mQueue, BitmapCache.getInstance());
        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(imageView, defaultImageResId, errorImageResId);
        imageLoader.get(url, imageListener, 0, 0, ImageView.ScaleType.CENTER_CROP);
    }

    // NetworkImageView使用
    public void executeNetworkImageView(final NetworkImageView imageView, String url, int defaultImageResId, int errorImageResId) {
        ImageLoader imageLoader = new ImageLoader(mQueue, BitmapCache.getInstance());
        imageView.setDefaultImageResId(defaultImageResId);
        imageView.setErrorImageResId(errorImageResId);
        imageView.setImageUrl(url, imageLoader);
    }

    public interface Callback {
        void onSuccess(String response);
        void onError(Exception e);
    }
}
