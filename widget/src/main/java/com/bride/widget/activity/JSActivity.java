package com.bride.widget.activity;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;

import com.bride.ui_lib.BaseActivity;
import com.bride.widget.AndroidToJS;
import com.bride.widget.databinding.ActivityJsBinding;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import timber.log.Timber;

/**
 * 混合开发WebView总结：
 * <p>1）设置回调：WebView.setWebViewClient()/setWebChromeClient()；
 * <p>2）拦截JS3种对话框：onJsAlert(), onJsConfirm(), onJsPrompt();
 * <p>3）标记网页加载进度的3个方法：onPageStarted(), onPageFinished(), onProgressChanged();
 * <p>4）Java调用JS方法：WebView.evaluateJavascript(KitKat开始支持), WebView.loadUrl();
 * <p>5）JSBridge将Java对象映射到JS、供JS调用：WebView.add/removeJavaScriptInterface(), @JavascriptInterface；
 * <p>6）native给H5提供数据：覆写shouldInterceptRequest()，OkHttp发起网络请求并返回数据；
 * <p>Created by shixin on 2018/10/27.
 */
public class JSActivity extends BaseActivity {
    private ActivityJsBinding mBinding;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityJsBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        initView();
    }

    private void initView() {
        mWebView = mBinding.webView;

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
                Timber.i("WebChromeClient onJsAlert %s", message);
                // 拦截alert警告框
                new AlertDialog.Builder(JSActivity.this)
                        .setTitle("标题")
                        .setMessage(message)
                        .setPositiveButton("确认", (dialog, which) -> result.confirm())
                        .setCancelable(false)
                        .create()
                        .show();
                return true;
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
                Timber.i("WebChromeClient onJsConfirm %s", message);
                // 拦截confirm确认框
                new AlertDialog.Builder(JSActivity.this)
                        .setTitle("标题")
                        .setMessage(message)
                        .setPositiveButton("确认", (dialog, which) -> result.confirm())
                        .setNegativeButton("取消", (dialog, which) -> result.cancel())
                        .setCancelable(false)
                        .create()
                        .show();
                return true;
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, final JsPromptResult result) {
                Timber.i("WebChromeClient onJsPrompt %s", message);
                // 拦截prompt输入框
                new AlertDialog.Builder(JSActivity.this)
                        .setTitle("标题")
                        .setMessage(message)
                        .setPositiveButton("确认", (dialog, which) -> result.confirm("输入框输入的值"))
                        .setNegativeButton("取消", (dialog, which) -> result.cancel())
                        .setCancelable(false)
                        .create()
                        .show();
                return true;
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                Timber.i("WebChromeClient onReceivedTitle %s", title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                // 获取网页加载进度更新ProgressBar[0, 100]
                Timber.i("WebChromeClient onProgressChanged %s", newProgress);
            }

            @Override
            public Bitmap getDefaultVideoPoster() {
                Timber.i("WebChromeClient getDefaultVideoPoster");
                return super.getDefaultVideoPoster();
            }

            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                Timber.i("WebChromeClient onConsoleMessage " + consoleMessage.message()
                        + ", messageLevel=" + consoleMessage.messageLevel() + ", sourceId=" + consoleMessage.sourceId() + ", lineNumber=" + consoleMessage.lineNumber());
                return super.onConsoleMessage(consoleMessage);
            }

            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback,
                                             FileChooserParams fileChooserParams) {
                Timber.i("WebChromeClient onShowFileChooser");
                return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
            }
        });

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Timber.i("WebViewClient onPageStarted %s", url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Timber.i("WebViewClient onPageFinished %s", url);
            }

            @Override
            public void onPageCommitVisible(WebView view, String url) {
                super.onPageCommitVisible(view, url);
                Timber.i("WebViewClient onPageCommitVisible %s", url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Uri uri = Uri.parse(url);
                if(TextUtils.equals(uri.getScheme(), "js")) {
                    if(TextUtils.equals(uri.getAuthority(), "webview")) {
                        // 拦截url
                        Timber.i("WebViewClient shouldOverrideUrlLoading "
                                + uri.getQueryParameter("arg1") + " " + uri.getQueryParameter("arg2"));
                    }
                    return true;
                }
                Timber.i("WebViewClient shouldOverrideUrlLoading %s", url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
                Timber.i("WebViewClient onLoadResource %s", url);
            }

            @Override
            public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
                super.doUpdateVisitedHistory(view, url, isReload);
                Timber.i("WebViewClient doUpdateVisitedHistory " + url + "-" + isReload);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                Timber.i("WebViewClient onReceivedSslError");
            }

            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                Timber.i("WebViewClient. isForMainFrame:%s, url:%s", request.isForMainFrame(), request.getUrl());
                return handleIntercept(request);
            }
        });
    }

    private WebResourceResponse handleIntercept(WebResourceRequest request) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();
        Request okHttpRequest = new Request.Builder()
                .url(request.getUrl().toString())
                .method(request.getMethod(), null)
                .headers(Headers.of(request.getRequestHeaders()))
                .build();
        Call okhttpCall = okHttpClient.newCall(okHttpRequest);
        try {
            // 'Response' used without 'try'-with-resources statement
            // The close() method of an AutoCloseable object is called automatically when exiting a try-with-resources block.
            try (Response okhttpResponse = okhttpCall.execute()) {
                ResponseBody body = okhttpResponse.body();
                if (body != null) {
                    return new WebResourceResponse(
                            okhttpResponse.header("content-type", "text/plain"),
                            okhttpResponse.header("content-encoding", "utf-8"),
                            body.byteStream());
                }
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void onClick(View view) {
        if (view==mBinding.tvJs) {
            // java调用js函数。alert弹窗仅有确认按钮，点空白区域不消失。点确认alert()返回null。
            // 不刷新页面，能获取返回值
            mWebView.evaluateJavascript("javascript:callAlert(\"僵尸来袭\\n准备爆头\")", value -> Timber.i("onReceiveValue alert: %s", value));
        } else if (view==mBinding.tvConfirm) {
            // java调用js函数。confirm弹窗有确认、取消俩按钮，点空白区域不消失。点确认confirm()返回true，点取消返回false。
            // 不刷新页面，能获取返回值
            mWebView.evaluateJavascript("javascript:callConfirm()", value -> Timber.i("onReceiveValue confirm %s", value));
        } else if (view==mBinding.tvPrompt) {
            // java调用js函数。prompt弹窗有确认、取消俩按钮，点空白区域不消失。点确认prompt()返回"输入框输入的值"，点取消返回null。
            // 不刷新页面，能获取返回值
            mWebView.evaluateJavascript("javascript:callPrompt()", value -> Timber.i("onReceiveValue prompt %s", value));
        } else if (view==mBinding.tvLog) {
            mWebView.loadUrl("javascript:console.log('test console message')");
        }
    }
}
