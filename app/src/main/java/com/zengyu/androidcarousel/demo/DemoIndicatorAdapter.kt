package com.zengyu.androidcarousel.demo

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zengyu.androidcarousel.R

/**
 * @author zengyu
 * @date 2020/12/27
 * @desc
 */
class DemoIndicatorAdapter(private val context: Context) :
    RecyclerView.Adapter<DemoIndicatorAdapter.IndicatorHolder>() {

    private val inflater = LayoutInflater.from(context)
    private val colorNormal = Color.parseColor("#999999")
    private val colorSelect = Color.parseColor("#000000")
    private val indicators = ArrayList<Boolean>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndicatorHolder {
        val view = inflater.inflate(R.layout.layout_demo_indicator, parent, false)
        return IndicatorHolder(view)
    }

    override fun onBindViewHolder(holder: IndicatorHolder, position: Int) {
        val indicator = indicators[position]
        val color = if (indicator) colorSelect else colorNormal
        holder.vIndicator.background.setTint(color)
    }

    override fun getItemCount(): Int {
        return indicators.size
    }

    fun updateList(list: List<Boolean>) {
        indicators.clear()
        indicators.addAll(list)
        notifyDataSetChanged()
    }

    fun select(index: Int) {
        if (indicators.size - 1 < index) return
        indicators.fill(false)
        indicators[index] = true
        notifyDataSetChanged()
    }

    class IndicatorHolder(view: View) : RecyclerView.ViewHolder(view) {
        val vIndicator: View = view.findViewById(R.id.demo_indicator_v_item)
    }
}