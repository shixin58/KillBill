package com.bride.widget;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bride.baselib.BaseActivity;

/**
 * <p>Created by shixin on 2018/10/27.
 */
public class JSActivity extends BaseActivity {
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_js);
        initView();
    }

    private void initView() {
        mWebView = findViewById(R.id.web_view);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//        webSettings.setSavePassword(false);

        // 通过addJavascriptInterface()将Java对象映射到JS对象
        mWebView.addJavascriptInterface(new AndroidToJS(), "test");
//        mWebView.removeJavascriptInterface("test");

        // 载入js代码
        mWebView.loadUrl("file:///android_asset/demo_js.html");

        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                Log.i("WebChromeClient", "onJsAlert "+message);
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
                Log.i("WebChromeClient", "onJsConfirm "+message);
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
                Log.i("WebChromeClient", "onJsPrompt "+message);
                // 拦截输入框
                AlertDialog.Builder builder = new AlertDialog.Builder(JSActivity.this);
                builder.setTitle("标题");
                builder.setMessage(message);
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm("输入框输入的值");
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
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                Log.i("WebChromeClient", "onReceivedTitle "+title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                // 获取网页加载进度更新ProgressBar
                Log.i("WebChromeClient", "onProgressChanged "+newProgress);
            }

            @Override
            public Bitmap getDefaultVideoPoster() {
                Log.i("WebChromeClient", "getDefaultVideoPoster");
                return super.getDefaultVideoPoster();
            }

            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                Log.i("WebChromeClient", "onConsoleMessage");
                return super.onConsoleMessage(consoleMessage);
            }

            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback,
                                             FileChooserParams fileChooserParams) {
                Log.i("WebChromeClient", "onShowFileChooser");
                return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
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
                Uri uri = Uri.parse(url);
                if(TextUtils.equals(uri.getScheme(), "js")) {
                    if(TextUtils.equals(uri.getAuthority(), "webview")) {
                        // 拦截url
                        Log.i("WebViewClient", "shouldOverrideUrlLoading "
                                +uri.getQueryParameter("arg1")+" "+uri.getQueryParameter("arg2"));
                    }
                    return true;
                }
                Log.i("WebViewClient", "shouldOverrideUrlLoading "+url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
                Log.i("WebViewClient", "onLoadResource "+url);
            }

            @Override
            public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
                super.doUpdateVisitedHistory(view, url, isReload);
                Log.i("WebViewClient", "doUpdateVisitedHistory "+url+"-"+isReload);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                Log.i("WebViewClient", "onReceivedSslError");
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
