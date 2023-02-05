package com.bride.demon.demo.imooc

interface Deferred<T> : Job {
    suspend fun await(): T
}