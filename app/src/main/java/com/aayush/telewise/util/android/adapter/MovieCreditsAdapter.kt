package com.aayush.telewise.util.android.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import coil.load
import com.aayush.telewise.MobileNavigationDirections
import com.aayush.telewise.R
import com.aayush.telewise.databinding.CardMovieCreditsBinding
import com.aayush.telewise.model.UiModel
import com.aayush.telewise.util.android.base.BaseAdapter
import com.aayush.telewise.util.android.base.BaseViewHolder
import com.aayush.telewise.util.android.toast
import com.aayush.telewise.util.common.IMAGE_URL_ORIGINAL
import com.aayush.telewise.util.common.IMAGE_URL_W500

class MovieCreditsAdapter(
    items: List<UiModel.PersonCollectionModel>,
    private val saveData: Boolean
) : BaseAdapter<MovieCreditsAdapter.CreditsViewHolder, UiModel.PersonCollectionModel>(items, true) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreditsViewHolder =
        CreditsViewHolder(
            CardMovieCreditsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            saveData
        ).also { holder ->
            holder.setOnClickListener { view ->
                val person = items[holder.bindingAdapterPosition]
                view.findNavController().navigate(
                    MobileNavigationDirections.navigateToPersonDetails(person),
                    FragmentNavigatorExtras(
                        holder.binding.imgPerson to view.context.getString(R.string.transition_image)
                    )
                )
            }
            holder.setOnLongClickListener { view ->
                toast(view.context, items[holder.bindingAdapterPosition].name)
                true
            }
        }

    override fun getItemId(position: Int): Long = items[position].hashCode().toLong()

    inner class CreditsViewHolder(
        binding: CardMovieCreditsBinding,
        private val saveData: Boolean
    ) : BaseViewHolder<CardMovieCreditsBinding, UiModel.PersonCollectionModel>(binding) {
        override fun bindTo(item: UiModel.PersonCollectionModel?) = with(binding) {
            val prefix = if (saveData) IMAGE_URL_W500 else IMAGE_URL_ORIGINAL
            imgPerson.load(prefix + item?.profilePath) {
                placeholder(R.drawable.ic_people_64)
                fallback(R.drawable.ic_people_64)
                error(R.drawable.ic_broken_image_64)
            }
            imgPerson.contentDescription = root.context.getString(R.string.person_profile, item?.name)
            textName.text = item?.name
            textRole.text = item?.role
        }
    }
}
