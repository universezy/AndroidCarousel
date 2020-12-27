package com.zengyu.androidcarousel.demo

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.zengyu.androidcarousel.R
import com.zengyu.androidcarousel.carousel.Carousel
import com.zengyu.androidcarousel.carousel.OnCarouselPageListener

/**
 * @author zengyu
 * @date 2020/12/27
 * @desc
 */
class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private val demoBeans = arrayListOf(
        DemoBean(1).apply {
            title = "红"
            color = Color.RED
            time = 2L
            event = { toast(title) }
        },
        DemoBean(2).apply {
            title = "绿"
            color = Color.GREEN
            time = 4L
            event = { toast(title) }
        },
        DemoBean(3).apply {
            title = "蓝"
            color = Color.BLUE
            time = 6L
            event = { toast(title) }
        }
    )
    private val vp2Carousel by lazy { findViewById<ViewPager2>(R.id.main_vp2_carousel) }
    private val rvIndicator by lazy { findViewById<RecyclerView>(R.id.main_rv_indicator) }
    private val indicatorAdapter by lazy { DemoIndicatorAdapter(this) }
    private val carouselPageListener = object : OnCarouselPageListener {
        override fun onSelected(position: Int) {
            Log.i(TAG, "[carouselPageListener] onSelected:$position")
            indicatorAdapter.select(position)
        }
    }
    private val carouselAdapter by lazy { DemoCarouselAdapter(this) }
    private var carousel: Carousel<DemoBean>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initData()
    }

    private fun initView() {
        rvIndicator.apply {
            adapter = indicatorAdapter
            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.HORIZONTAL, false)
            itemAnimator?.changeDuration = 500L
        }
        carousel = Carousel<DemoBean>(vp2Carousel).apply {
            setPageListener(carouselPageListener)
            setAdapter(carouselAdapter)
            setIndicator(rvIndicator)
        }
    }

    private fun initData() {
        carousel?.setData(demoBeans)
        val indicators = ArrayList<Boolean>(demoBeans.size)
        repeat(demoBeans.size) {
            indicators.add(false)
        }
        indicators[0] = true
        indicatorAdapter.updateList(indicators)
    }

    private fun toast(title: String) {
        Toast.makeText(this, title, Toast.LENGTH_SHORT).show()
    }
}