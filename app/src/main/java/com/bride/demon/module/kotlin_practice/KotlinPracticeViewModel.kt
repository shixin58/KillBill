package com.bride.demon.module.kotlin_practice

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bride.demon.module.kotlin_practice.DownloadManager.DownloadStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn

class KotlinPracticeViewModel : ViewModel() {
    val downloadStatusLive = MutableLiveData<DownloadStatus>(DownloadStatus.None)

    suspend fun download(url: String, fileName: String) {
        DownloadManager.download(url, fileName)
            .flowOn(Dispatchers.IO)
            .collect {
                downloadStatusLive.value = it
            }
    }
}