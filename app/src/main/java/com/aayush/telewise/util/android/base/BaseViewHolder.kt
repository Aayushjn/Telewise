package com.aayush.telewise.util.android.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<VB : ViewBinding, T>(protected val binding: VB) : RecyclerView.ViewHolder(binding.root) {
    abstract fun bindTo(item: T?)

    fun setOnClickListener(listener: View.OnClickListener?) = binding.root.setOnClickListener(listener)
}
