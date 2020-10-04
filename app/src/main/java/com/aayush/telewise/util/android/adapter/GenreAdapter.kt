package com.aayush.telewise.util.android.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aayush.telewise.databinding.ChipGenreBinding
import com.aayush.telewise.util.android.base.BaseAdapter
import com.aayush.telewise.util.android.base.BaseViewHolder

class GenreAdapter(items: List<String>) : BaseAdapter<GenreAdapter.GenreViewHolder, String>(items, true) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder =
        GenreViewHolder(
            ChipGenreBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemId(position: Int): Long = items[position].hashCode().toLong()

    inner class GenreViewHolder(binding: ChipGenreBinding) : BaseViewHolder<ChipGenreBinding, String>(binding) {
        override fun bindTo(item: String?) {
            binding.chip.text = item
        }
    }
}
