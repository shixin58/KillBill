package com.bride.widget.activity

import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.webkit.WebViewAssetLoader
import androidx.webkit.WebViewAssetLoader.AssetsPathHandler
import androidx.webkit.WebViewClientCompat
import com.bride.ui_lib.BaseActivity
import com.bride.widget.databinding.ActivityMyWebBinding
import timber.log.Timber

class MyWebActivity : BaseActivity() {
    private lateinit var mBinding: ActivityMyWebBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMyWebBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initView()
    }

    private fun initView() {
        // Assets/Resources/InternalStorage三种PathHandler
        val assetLoader = WebViewAssetLoader.Builder()
            .addPathHandler("/assets/", AssetsPathHandler(this))
            .build()
        mBinding.webView.webViewClient = object : WebViewClientCompat() {
            var startMillis: Long = -1L

            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                Timber.i("WebViewClient#shouldOverrideUrlLoading isForMainFrame:%s, url:%s", request.isForMainFrame, request.url)
                return super.shouldOverrideUrlLoading(view, request)
            }

            override fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest?): WebResourceResponse? {
                val url = request?.url
                val response = if (url != null) assetLoader.shouldInterceptRequest(url) else null
                Timber.i("WebViewClient#shouldInterceptRequest local:%s isForMainFrame:%s, url:%s", response!=null, request?.isForMainFrame, request?.url)
                return response
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                startMillis = System.currentTimeMillis()
                Timber.i("onPageStarted")
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                Timber.i("onPageFinished ${System.currentTimeMillis() - startMillis}")
                super.onPageFinished(view, url)
            }
        }

        val webViewSettings: WebSettings = mBinding.webView.settings
        // Setting this off for security. Off by default for SDK versions >= 16.
        webViewSettings.allowFileAccessFromFileURLs = false
        // Off by default, deprecated for SDK versions >= 30.
        webViewSettings.allowUniversalAccessFromFileURLs = false
        // Keeping these off is less critical but still a good idea, especially if your app is not
        // using file:// or content:// URLs.
        webViewSettings.allowFileAccess = false
        webViewSettings.allowContentAccess = false

        // Assets are hosted under http(s)://appassets.androidplatform.net/assets/... .
        // If the application's assets are in the "main/assets" folder this will read the file
        // from "main/assets/www/index.html" and load it as if it were hosted on:
        // https://appassets.androidplatform.net/assets/www/index.html
        mBinding.webView.loadUrl("https://appassets.androidplatform.net/assets/www/index.html")
    }
}