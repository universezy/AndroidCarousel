package com.zengyu.androidcarousel.carousel

import android.content.Context
import androidx.recyclerview.widget.RecyclerView

/**
 * @author zengyu
 * @date 2020/12/27
 * @desc
 */
abstract class CarouselAdapter<T, H : RecyclerView.ViewHolder>(
    context: Context
) : RecyclerView.Adapter<H>() {

    protected val list = ArrayList<T>()
    protected val TAG = javaClass.simpleName

    open fun updateList(data: List<T>) {
        list.let {
            it.clear()
            it.add(data.last())
            it.addAll(data)
            it.add(data.first())
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun adjust(position: Int): Int {
        return when (position) {
            0 -> list.size - 2
            list.size - 1 -> 1
            else -> position
        }
    }
}