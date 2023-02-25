package com.bride.demon.module.kotlin_practice

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import com.bride.baselib.alert
import com.bride.baselib.toast
import com.bride.demon.R.string
import com.bride.demon.databinding.ActivityKotlinPracticeBinding
import com.bride.demon.module.kotlin_practice.DownloadManager.DownloadStatus.*
import com.bride.ui_lib.BaseActivity
import kotlinx.coroutines.launch

class KotlinPracticeActivity : BaseActivity() {
    companion object {
        fun openActivity(ctx: Context) {
            val intent = Intent(ctx, KotlinPracticeActivity::class.java)
            ctx.startActivity(intent)
        }
    }

    private lateinit var mBinding: ActivityKotlinPracticeBinding
    private val mViewModel: KotlinPracticeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityKotlinPracticeBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.btnShowDialog.setOnClickListener {
            lifecycleScope.launch {
                val myChoice = alert("Warning!", "Do you want this?")
                toast("My choice is $myChoice")
            }
        }

        mViewModel.downloadStatusLive.observe(this) { downloadStatus ->
            when(downloadStatus) {
                null, None -> {
                    mBinding.btnDownload.isEnabled = true
                    mBinding.btnDownload.text = getString(string.download)
                    mBinding.btnDownload.setOnClickListener {
                        lifecycleScope.launch {
                            mViewModel.download("https://kotlinlang.org/docs/kotlin-docs.pdf", "Kotlin-Docs.pdf")
                        }
                    }
                }
                is Done -> {
                    toast(downloadStatus.file)
                    mBinding.btnDownload.isEnabled = true
                    mBinding.btnDownload.text = "Open File"
                    mBinding.btnDownload.setOnClickListener {
                        Intent(Intent.ACTION_VIEW).also {
                            it.setDataAndType(FileProvider.getUriForFile(this, "${packageName}.provider", downloadStatus.file), "application/pdf")
                            it.flags = Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_GRANT_READ_URI_PERMISSION
                        }.also(::startActivity)
                    }
                }
                is Error -> {
                    toast(downloadStatus.throwable)
                    mBinding.btnDownload.isEnabled = true
                    mBinding.btnDownload.text = "Download Error"
                    mBinding.btnDownload.setOnClickListener {
                        lifecycleScope.launch {
                            mViewModel.download("https://kotlinlang.org/docs/kotlin-docs.pdf", "Kotlin-Docs.pdf")
                        }
                    }
                }
                is Progress -> {
                    mBinding.btnDownload.isEnabled = false
                    mBinding.btnDownload.text = "Downloading(${downloadStatus.value})"
                }
            }
        }
    }
}