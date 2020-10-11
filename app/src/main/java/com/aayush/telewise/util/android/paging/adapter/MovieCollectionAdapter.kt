package com.aayush.telewise.util.android.paging.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import coil.load
import coil.transform.RoundedCornersTransformation
import com.aayush.telewise.MobileNavigationDirections
import com.aayush.telewise.R
import com.aayush.telewise.databinding.CardMovieCollectionBinding
import com.aayush.telewise.util.android.base.BasePagingAdapter
import com.aayush.telewise.util.android.base.BaseViewHolder
import com.aayush.telewise.util.android.toast
import com.aayush.telewise.util.common.IMAGE_CORNER_SIZE
import com.aayush.telewise.util.common.IMAGE_URL_ORIGINAL
import com.aayush.telewise.util.common.IMAGE_URL_W500
import com.aayush.telewise.model.UiModel.MovieCollectionModel as Movie

class MovieCollectionAdapter(private val saveData: Boolean) : BasePagingAdapter<Movie>(ListMovieCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<out ViewBinding, Movie> =
        ListMovieViewHolder(
            CardMovieCollectionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            saveData
        ).also { holder ->
            holder.setOnClickListener { view ->
                val movie = getItem(holder.bindingAdapterPosition)
                if (movie != null) {
                    view.findNavController().navigate(
                        MobileNavigationDirections.navigateToMovieDetails(movie),
                        FragmentNavigatorExtras(
                            holder.binding.imgItem to view.context.getString(R.string.transition_image)
                        )
                    )
                } else {
                    toast(view.context, "Loading...")
                }
            }
            holder.setOnLongClickListener { view ->
                val movie = getItem(holder.bindingAdapterPosition)
                toast(view.context, movie?.title ?: "Loading...")
                true
            }
        }

    private inner class ListMovieViewHolder(
        binding: CardMovieCollectionBinding,
        private val saveData: Boolean
    ) : BaseViewHolder<CardMovieCollectionBinding, Movie>(binding) {
        override fun bindTo(item: Movie?) = with(binding) {
            if (item != null) {
                val prefix = if (saveData) IMAGE_URL_W500 else IMAGE_URL_ORIGINAL
                imgItem.load(prefix + item.posterPath) {
                    transformations(RoundedCornersTransformation(IMAGE_CORNER_SIZE))
                    placeholder(R.drawable.ic_movies_64)
                    fallback(R.drawable.ic_movies_64)
                    error(R.drawable.ic_broken_image_64)
                }
                imgItem.contentDescription = root.context.getString(R.string.movie_poster, item.title)
                textItemTitle.text = item.title
                textItemOverview.text = item.overview
                textItemRating.text = item.rating.toString()
                imgItemExplicit.isVisible = item.adult == true
            }
        }
    }

    companion object {
        private object ListMovieCallback : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem == newItem
        }
    }
}
