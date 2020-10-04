package com.aayush.telewise.util.android.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter<VH : BaseViewHolder<out ViewBinding, T>, T>(
    protected val items: List<T>,
    hasStableIds: Boolean
) : RecyclerView.Adapter<VH>() {
    init {
        super.setHasStableIds(hasStableIds)
    }

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bindTo(items[position])

    override fun getItemCount(): Int = items.size

    abstract override fun getItemId(position: Int): Long
}
