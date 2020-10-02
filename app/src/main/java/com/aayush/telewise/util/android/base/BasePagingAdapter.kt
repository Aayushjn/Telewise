package com.aayush.telewise.util.android.base

import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding

abstract class BasePagingAdapter<T : Any>(itemCallback: DiffUtil.ItemCallback<T>) :
    PagingDataAdapter<T, BaseViewHolder<out ViewBinding, T>>(itemCallback) {
    override fun onBindViewHolder(holder: BaseViewHolder<out ViewBinding, T>, position: Int) =
        holder.bindTo(getItem(position))
}
