package com.aayush.telewise.util.android.paging.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.aayush.telewise.databinding.ItemLoadStateBinding
import com.aayush.telewise.util.android.base.BaseViewHolder

class ItemLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<ItemLoadStateAdapter.LoadStateViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder =
        LoadStateViewHolder(
            ItemLoadStateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            retry
        )

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) = holder.bindTo(loadState)

    class LoadStateViewHolder(
        binding: ItemLoadStateBinding,
        retry: () -> Unit
    ) : BaseViewHolder<ItemLoadStateBinding, LoadState>(binding) {
        init {
            binding.retryButton.setOnClickListener { retry() }
        }

        override fun bindTo(item: LoadState?) {
            if (item is LoadState.Error) {
                binding.errorMsg.text = item.error.localizedMessage
            }
            binding.progressBar.isVisible = item is LoadState.Loading
            binding.retryButton.isVisible = item !is LoadState.Loading
            binding.errorMsg.isVisible = item !is LoadState.Loading
        }
    }
}
