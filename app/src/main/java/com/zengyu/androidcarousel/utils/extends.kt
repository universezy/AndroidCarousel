package com.zengyu.androidcarousel.utils

import android.os.Handler
import android.os.Looper

/**
 * @author zengyu
 * @date 2020/12/27
 * @desc
 */
fun ui(block: () -> Unit) {
    if (Looper.myLooper() == Looper.getMainLooper()) {
        block()
    } else {
        Extends.handler.post(block)
    }
}

private object Extends {
    val handler by lazy { Handler(Looper.getMainLooper()) }
}