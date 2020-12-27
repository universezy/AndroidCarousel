package com.zengyu.androidcarousel.demo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.zengyu.androidcarousel.R
import com.zengyu.androidcarousel.carousel.CarouselAdapter

/**
 * @author zengyu
 * @date 2020/12/27
 * @desc
 */
class DemoCarouselAdapter(
    context: Context
) : CarouselAdapter<DemoBean, DemoCarouselAdapter.DemoHolder>(context) {
    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DemoHolder {
        val view = inflater.inflate(R.layout.layout_demo_bean, parent, false)
        return DemoHolder(view)
    }

    override fun onBindViewHolder(holder: DemoHolder, position: Int) {
        val bean = list[position]
        holder.clRoot.setBackgroundColor(bean.color)
        holder.tvTitle.text = bean.title
        holder.itemView.setOnClickListener { bean.event?.invoke() }
    }

    class DemoHolder(view: View) : RecyclerView.ViewHolder(view) {
        val clRoot: ConstraintLayout = view.findViewById(R.id.demo_bean_cl_root)
        val tvTitle: TextView = view.findViewById(R.id.demo_bean_tv_title)
    }
}