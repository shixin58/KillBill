package com.bride.demon.demo.imooc.scope

import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

object GlobalScope : CoroutineScope {
    override val scopeContext: CoroutineContext
        get() = EmptyCoroutineContext
}