package com.zengyu.androidcarousel.demo

import com.zengyu.androidcarousel.carousel.Terminable

/**
 * @author zengyu
 * @date 2020/12/27
 * @desc
 */
class DemoBean(val id: Int) : Terminable {
    var title: String = ""
    var color: Int = 0
    var time: Long = 0L
    var event: (() -> Unit)? = null

    override fun getDuration(): Long {
        return time
    }
}