package com.aayush.telewise.util.android.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import coil.load
import com.aayush.telewise.MobileNavigationDirections
import com.aayush.telewise.R
import com.aayush.telewise.databinding.CardPersonCreditsBinding
import com.aayush.telewise.model.UiModel
import com.aayush.telewise.util.android.base.BaseAdapter
import com.aayush.telewise.util.android.base.BaseViewHolder
import com.aayush.telewise.util.android.toast
import com.aayush.telewise.util.common.IMAGE_URL_ORIGINAL
import com.aayush.telewise.util.common.IMAGE_URL_W500

class PersonCreditsAdapter(
    items: List<UiModel.PersonCreditsModel>,
    private val saveData: Boolean
) : BaseAdapter<PersonCreditsAdapter.CreditsViewHolder, UiModel.PersonCreditsModel>(items, true) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreditsViewHolder =
        CreditsViewHolder(
            CardPersonCreditsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            saveData
        ).also { holder ->
            holder.setOnClickListener { view ->
                val credit = items[holder.bindingAdapterPosition]
                when (credit.mediaType) {
                    "movie" -> {
                        view.findNavController().navigate(
                            MobileNavigationDirections.navigateToMovieDetails(
                                // Providing default values is fine since the details page will fetch data again
                                UiModel.MovieCollectionModel(
                                    false,
                                    credit.profilePath,
                                    credit.id,
                                    "",
                                    0.0,
                                    credit.title
                                )
                            ),
                            FragmentNavigatorExtras(
                                holder.binding.imgItem to view.context.getString(R.string.transition_image)
                            )
                        )
                    }
                    "tv" -> toast(view.context, "Support for TV will be added soon!")
                    else -> toast(view.context, "Unknown media type (${credit.mediaType}) found")
                }
            }
            holder.setOnLongClickListener { view ->
                toast(view.context, items[holder.bindingAdapterPosition].title)
                true
            }
        }

    override fun getItemId(position: Int): Long = items[position].hashCode().toLong()

    inner class CreditsViewHolder(
        binding: CardPersonCreditsBinding,
        private val saveData: Boolean
    ) : BaseViewHolder<CardPersonCreditsBinding, UiModel.PersonCreditsModel>(binding) {
        override fun bindTo(item: UiModel.PersonCreditsModel?) = with(binding) {
            val prefix = if (saveData) IMAGE_URL_W500 else IMAGE_URL_ORIGINAL
            val typeDrawable = when (item?.mediaType) {
                "movie" -> R.drawable.ic_movies_24
                "tv" -> R.drawable.ic_tv_24
                else -> R.drawable.ic_broken_image_64
            }
            imgItem.load(prefix + item?.profilePath) {
                placeholder(typeDrawable)
                fallback(typeDrawable)
                error(R.drawable.ic_broken_image_64)
            }
            imgItem.contentDescription = root.context.getString(R.string.movie_poster, item?.title)
            textTitle.text = item?.title
            textRole.text = item?.role
            textRole.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                typeDrawable,
                0
            )
        }
    }
}
