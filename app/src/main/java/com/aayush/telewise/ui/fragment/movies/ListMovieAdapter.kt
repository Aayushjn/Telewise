package com.aayush.telewise.ui.fragment.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import coil.load
import coil.transform.RoundedCornersTransformation
import com.aayush.telewise.R
import com.aayush.telewise.api.model.ListMovie
import com.aayush.telewise.databinding.CardListItemBinding
import com.aayush.telewise.util.android.base.BasePagingAdapter
import com.aayush.telewise.util.android.base.BaseViewHolder
import com.aayush.telewise.util.android.toast
import com.aayush.telewise.util.common.IMAGE_CORNER_SIZE
import com.aayush.telewise.util.common.IMAGE_URL_ORIGINAL

class ListMovieAdapter : BasePagingAdapter<ListMovie>(ListMovieCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<out ViewBinding, ListMovie> =
        ListMovieViewHolder(
            CardListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).also { holder ->
            holder.setOnClickListener { view ->
                toast(view.context, getItem(holder.bindingAdapterPosition)?.title ?: "Movie")
            }
        }

    class ListMovieViewHolder(
        binding: CardListItemBinding
    ) : BaseViewHolder<CardListItemBinding, ListMovie>(binding) {
        override fun bindTo(item: ListMovie?) = with(binding) {
            if (item != null) {
                imgItem.load(IMAGE_URL_ORIGINAL + (item.posterPath ?: item.backdropPath)) {
                    transformations(RoundedCornersTransformation(IMAGE_CORNER_SIZE))
                    placeholder(R.drawable.ic_movies_64)
                    fallback(R.drawable.ic_movies_64)
                    error(R.drawable.ic_broken_image_64)
                }
                imgItem.contentDescription = root.context.getString(R.string.movie_poster)
                textItemTitle.text = item.title
                textItemOverview.text = item.overview
                textItemRating.text = item.voteAverage.toString()
                imgItemExplicit.isVisible = item.adult == true
            }
        }
    }

    companion object {
        private object ListMovieCallback : DiffUtil.ItemCallback<ListMovie>() {
            override fun areItemsTheSame(oldItem: ListMovie, newItem: ListMovie): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: ListMovie, newItem: ListMovie): Boolean = oldItem == newItem
        }
    }
}
