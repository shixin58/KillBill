package com.bride.demon.demo.imooc

import kotlin.coroutines.CoroutineContext

class CoroutineName(val name: String) : CoroutineContext.Element {
    companion object Key : CoroutineContext.Key<CoroutineName>

    override val key = Key

    override fun toString(): String {
        return name
    }
}