package com.bride.thirdparty.coroutines

import com.bride.baselib.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.selects.select
import kotlinx.coroutines.withContext
import java.io.File
import java.util.concurrent.Executors

val KotlinFileFilter = { file: File -> file.isDirectory || file.name.endsWith(".kt") }

data class FileLines(val file: File, val lines: Int) {
    override fun toString(): String {
        return "${file.name}: $lines"
    }
}

suspend fun main() {
    val result = lineCounter(File("."))
    log(result)
}

suspend fun lineCounter(root: File): HashMap<File, Int> {
    // 线程池用完关闭。不想开线程太多，不用内置调度器。
    return Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1).asCoroutineDispatcher()
        .use {
            withContext(it) {
                val fileChannel = walkFile(root)
                val fileLinesChannels = List(5) {
                    fileLinesCounter(fileChannel)
                }
                resultAggregator(fileLinesChannels)
            }
        }
}

fun CoroutineScope.walkFile(root: File): ReceiveChannel<File> {
    return produce<File>(capacity = Channel.BUFFERED) {
        fileWalker(root)
    }
}

suspend fun SendChannel<File>.fileWalker(file: File) {
    if (file.isDirectory) {
        file.listFiles()?.filter(KotlinFileFilter)?.forEach { fileWalker(it) }
    } else {
        send(file)
    }
}

fun CoroutineScope.fileLinesCounter(input: ReceiveChannel<File>): ReceiveChannel<FileLines> {
    return produce(capacity = Channel.BUFFERED) {
        for (file in input) {
            file.useLines {
                send(FileLines(file, it.count()))
            }
        }
    }
}

suspend fun CoroutineScope.resultAggregator(channels: List<ReceiveChannel<FileLines>>): HashMap<File,Int> {
    val map = HashMap<File,Int>()
    channels.aggregate { filteredChannels ->
        select<FileLines?> {
            filteredChannels.forEach { receiveChannel ->
                receiveChannel.onReceiveCatching {
                    log("Received: ${it.getOrNull()}")
                    it.getOrNull()
                }
            }
        }?.let { map[it.file] = it.lines }
    }
    return map
}

tailrec suspend fun List<ReceiveChannel<FileLines>>.aggregate(block: suspend (List<ReceiveChannel<FileLines>>) -> Unit) {
    // select了一次，有些ReceiveChannel被消费了，需要过滤
    block(this)
    // tailrec把尾递归变成迭代，避免StackOverflow。
    filter { !it.isClosedForReceive }.takeIf { it.isNotEmpty() }?.aggregate(block)
}