package com.zengyu.androidcarousel.carousel

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.zengyu.androidcarousel.utils.ui
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

/**
 * @author zengyu
 * @date 2020/12/27
 * @desc
 */
class Carousel<T : Terminable>(
    private val vp: ViewPager2
) : CarouselPageChangeCallback(vp), View.OnAttachStateChangeListener, CarouselController {

    companion object {
        private const val TAG = "Carousel"
        private const val DEBUG = false
    }

    private var dataList = ArrayList<T>()
    private var disposable: Disposable? = null
    private var carouselAdapter: CarouselAdapter<T, *>? = null
    private var indicator: View? = null
    private var carouselPageListener: OnCarouselPageListener? = null
    private var pause = false

    init {
        vp.addOnAttachStateChangeListener(this)
        vp.registerOnPageChangeCallback(this)
    }

    override fun onViewAttachedToWindow(v: View?) {
    }

    override fun onViewDetachedFromWindow(v: View?) {
        stop()
        vp.removeOnAttachStateChangeListener(this)
        vp.unregisterOnPageChangeCallback(this)
    }

    override fun onDragging() {
        pause()
    }

    override fun onIdle(position: Int) {
        val adjustedPosition = carouselAdapter?.adjust(position) ?: position
        if (adjustedPosition != position) {
            vp.setCurrentItem(adjustedPosition, false)
        }
        carouselPageListener?.onSelected(adjustedPosition - 1)
        resume()
    }

    override fun onPageSelected(position: Int) {
        super.onPageSelected(position)
        if (!dragging) {
            carouselPageListener?.onSelected(position - 1)
        }
    }

    override fun pause() {
        ui {
            if (pause) return@ui
            if (disposable == null || disposable?.isDisposed == true) {
                pause = true
            } else {
                disposable?.dispose()
            }
        }
    }

    override fun resume() {
        ui {
            if (!pause) return@ui
            pause = false
            schedule(vp.currentItem - 1)
        }
    }

    override fun switch(index: Int) {
        ui {
            if (index < 0 || index >= dataList.size) {
                return@ui
            }
            vp.currentItem = index
        }
    }

    fun setAdapter(carouselAdapter: CarouselAdapter<T, *>) {
        this.carouselAdapter = carouselAdapter
        vp.adapter = carouselAdapter
    }

    fun setIndicator(view: View) {
        indicator = view
    }

    fun setPageListener(listener: OnCarouselPageListener) {
        carouselPageListener = listener
    }

    fun getController(): CarouselController = this

    fun setData(list: List<T>) {
        ui {
            dataList.clear()
            if (list.isEmpty()) {
                stop()
                vp.visibility = View.GONE
                indicator?.visibility = View.GONE
            } else {
                dataList.addAll(list)
                carouselAdapter?.updateList(list)
                play()
            }
        }
    }

    private fun play() {
        stop()
        if (vp.visibility != View.VISIBLE) {
            vp.visibility = View.VISIBLE
        }
        vp.setCurrentItem(1, false)
        if (dataList.size == 1) {
            indicator?.visibility = View.GONE
            vp.isUserInputEnabled = false
        } else {
            indicator?.visibility = View.VISIBLE
            vp.isUserInputEnabled = true
            schedule(0)
        }
    }

    private fun stop() {
        disposable ?: return
        disposable!!.dispose()
        disposable = null
    }

    private fun schedule(dataPosition: Int) {
        if (DEBUG) return
        if (pause) {
            return
        }
        if (dataPosition > dataList.size - 1) {
            return
        }
        val data = dataList[dataPosition]
        val period = data.getDuration()
        if (disposable?.isDisposed == false) {
            disposable?.dispose()
        }
        disposable = Observable.timer(period, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                pause = false
            }
            .doOnDispose {
                pause = true
            }
            .subscribe({
                if (!pause) {
                    val nextData = getNextDataPosition()
                    val nextPage = getNextPagePosition()
                    vp.currentItem = nextPage
                    schedule(nextData)
                }
            }, {
            })
    }

    private fun getNextDataPosition(): Int {
        val currentItem = vp.currentItem
        val size = dataList.size
        val next = currentItem % size
        return next
    }

    private fun getNextPagePosition(): Int {
        val currentItem = vp.currentItem
        val size = vp.adapter?.itemCount ?: 1
        val next = (currentItem + 1) % size
        val adjustedPosition = carouselAdapter?.adjust(next) ?: next
        return adjustedPosition
    }
}