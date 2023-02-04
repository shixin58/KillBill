package com.bride.demon.demo.imooc.core

sealed class CoroutineState {
    private var disposableList: DisposableList = DisposableList.Nil

    // 保持线程安全：不共享可变变量。
    // 创建新的CoroutineState，直接引用disposableList。
    fun from(state: CoroutineState): CoroutineState {
        this.disposableList = state.disposableList
        return this
    }

    fun with(disposable: Disposable): CoroutineState {
        this.disposableList = DisposableList.Cons(disposable, this.disposableList)
        return this
    }

    fun without(disposable: Disposable): CoroutineState {
        this.disposableList = this.disposableList.remove(disposable)
        return this
    }

    fun <T> notifyCompletion(result: Result<T>) {
        this.disposableList.loopOn<CompletionHandlerDisposable<T>> {
            it.onComplete(result)
        }
    }

    fun clear() {
        this.disposableList = DisposableList.Nil
    }

    class InComplete : CoroutineState()
    class Complete<T>(val value: T? = null, val exception: Throwable? = null) : CoroutineState()
}