package com.zengyu.androidcarousel.carousel

import androidx.viewpager2.widget.ViewPager2

/**
 * @author zengyu
 * @date 2020/12/27
 * @desc 选中到静止期间，禁用滑动
 */
open class CarouselPageChangeCallback(private val vp2: ViewPager2) :
    ViewPager2.OnPageChangeCallback() {

    protected var dragging = false
    private var position = 0

    override fun onPageScrollStateChanged(state: Int) {
        when (state) {
            ViewPager2.SCROLL_STATE_DRAGGING -> {
                dragging = true
                onDragging()
            }
            ViewPager2.SCROLL_STATE_SETTLING -> {

            }
            ViewPager2.SCROLL_STATE_IDLE -> {
                if (dragging) {
                    dragging = false
                    vp2.isUserInputEnabled = true
                    onIdle(position)
                }
            }
        }
    }

    override fun onPageSelected(position: Int) {
        this.position = position
        if (dragging) {
            vp2.isUserInputEnabled = false
        }
    }

    open fun onDragging() {

    }

    open fun onIdle(position: Int) {

    }
}