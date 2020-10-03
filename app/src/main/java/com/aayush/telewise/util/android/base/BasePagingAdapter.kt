package com.aayush.telewise.util.android.base

import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import com.aayush.telewise.model.UiModel

/**
 * Base class for [PagingDataAdapter] used throughout the app
 * Since the network models are always converted to appropriate UI models, [T] is a [UiModel]
 */
abstract class BasePagingAdapter<T : UiModel>(itemCallback: DiffUtil.ItemCallback<T>) :
    PagingDataAdapter<T, BaseViewHolder<out ViewBinding, T>>(itemCallback) {
    override fun onBindViewHolder(holder: BaseViewHolder<out ViewBinding, T>, position: Int) =
        holder.bindTo(getItem(position))
}
