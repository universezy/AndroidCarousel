package com.zengyu.androidcarousel.carousel

/**
 * @author zengyu
 * @date 2020/12/27
 * @desc
 */
interface CarouselController {
    fun pause()

    fun resume()

    fun switch(index: Int)
}