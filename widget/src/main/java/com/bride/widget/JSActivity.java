package com.bride.widget;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bride.baselib.BaseActivity;

import org.jetbrains.annotations.Nullable;

/**
 * <p>Created by shixin on 2018/10/27.
 */
public class JSActivity extends BaseActivity {
    private WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_js);
        initView();
    }

    private void initView() {
        mWebView = findViewById(R.id.web_view);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        // 通过addJavascriptInterface()将Java对象映射到JS对象
        mWebView.addJavascriptInterface(new AndroidToJS(), "test");

        // 载入js代码
        mWebView.loadUrl("file:///android_asset/demo_js.html");

        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                // 拦截警告框
                AlertDialog.Builder builder = new AlertDialog.Builder(JSActivity.this);
                builder.setTitle("标题");
                builder.setMessage(message);
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                });
                builder.setCancelable(false);
                builder.create().show();
                return true;
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
                // 拦截确认框
                AlertDialog.Builder builder = new AlertDialog.Builder(JSActivity.this);
                builder.setTitle("标题");
                builder.setMessage(message);
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.cancel();
                    }
                });
                builder.setCancelable(false);
                builder.create().show();
                return true;
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, final JsPromptResult result) {
                // 拦截输入框
                AlertDialog.Builder builder = new AlertDialog.Builder(JSActivity.this);
                builder.setTitle("标题");
                builder.setMessage(message);
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm("js调用了Android的方法成功啦");
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.cancel();
                    }
                });
                builder.setCancelable(false);
                builder.create().show();
                return true;
            }
        });

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.i("WebViewClient", "onPageStarted "+url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.i("WebViewClient", "onPageFinished "+url);
            }

            @Override
            public void onPageCommitVisible(WebView view, String url) {
                super.onPageCommitVisible(view, url);
                Log.i("WebViewClient", "onPageCommitVisible "+url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 拦截url
                Log.i("WebViewClient", "shouldOverrideUrlLoading "+url);
                Uri uri = Uri.parse(url);
                if(TextUtils.equals(uri.getScheme(), "js")) {
                    if(TextUtils.equals(uri.getAuthority(), "webview")) {
                    }
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_js:
                // addView触发performTraversals，之后执行View#post(Runnable), 确保拿到View宽高
                mWebView.post(new Runnable() {
                    @Override
                    public void run() {
                        // java调用js函数
                        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                            mWebView.loadUrl("javascript:callAlert(\"僵尸来袭\\n准备爆头\")");
                        }else {
                            // 不刷新页面，能获取返回值
                            mWebView.evaluateJavascript("javascript:callAlert(\"僵尸来袭\\n准备爆头\")", new ValueCallback<String>() {
                                @Override
                                public void onReceiveValue(String value) {
                                    Log.i("onReceiveValue", value);
                                }
                            });
                        }
                    }
                });
                break;
            case R.id.tv_confirm:
                mWebView.post(new Runnable() {
                    @Override
                    public void run() {
                        // java调用js函数
                        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                            mWebView.loadUrl("javascript:callConfirm()");
                        }else {
                            // 不刷新页面，能获取返回值
                            mWebView.evaluateJavascript("javascript:callConfirm()", new ValueCallback<String>() {
                                @Override
                                public void onReceiveValue(String value) {
                                    Log.i("onReceiveValue", value);
                                }
                            });
                        }
                    }
                });
                break;
            case R.id.tv_prompt:
                mWebView.post(new Runnable() {
                    @Override
                    public void run() {
                        // java调用js函数
                        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                            mWebView.loadUrl("javascript:callPrompt()");
                        }else {
                            // 不刷新页面，能获取返回值
                            mWebView.evaluateJavascript("javascript:callPrompt()", new ValueCallback<String>() {
                                @Override
                                public void onReceiveValue(String value) {
                                    Log.i("onReceiveValue", value);
                                }
                            });
                        }
                    }
                });
                break;
        }
    }
}
