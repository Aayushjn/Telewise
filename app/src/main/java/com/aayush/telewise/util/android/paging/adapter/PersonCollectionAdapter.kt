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
import com.aayush.telewise.databinding.CardPersonCollectionBinding
import com.aayush.telewise.util.android.base.BasePagingAdapter
import com.aayush.telewise.util.android.base.BaseViewHolder
import com.aayush.telewise.util.android.toast
import com.aayush.telewise.util.common.IMAGE_CORNER_SIZE
import com.aayush.telewise.util.common.IMAGE_URL_ORIGINAL
import com.aayush.telewise.util.common.IMAGE_URL_W500
import com.aayush.telewise.model.UiModel.PersonCollectionModel as Person

class PersonCollectionAdapter(private val saveData: Boolean) : BasePagingAdapter<Person>(ListPersonCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<out ViewBinding, Person> =
        ListPersonViewHolder(
            CardPersonCollectionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            saveData
        ).also { holder ->
            holder.setOnClickListener { view ->
                val person = getItem(holder.bindingAdapterPosition)
                if (person != null) {
                    view.findNavController().navigate(
                        MobileNavigationDirections.navigateToPersonDetails(person),
                        FragmentNavigatorExtras(
                            holder.binding.imgPerson to view.context.getString(R.string.transition_image)
                        )
                    )
                } else {
                    toast(view.context, "Loading...")
                }
            }
            holder.setOnLongClickListener { view ->
                val person = getItem(holder.bindingAdapterPosition)
                toast(view.context, person?.name ?: "Loading...")
                true
            }
        }

    private inner class ListPersonViewHolder(
        binding: CardPersonCollectionBinding,
        private val saveData: Boolean
    ) : BaseViewHolder<CardPersonCollectionBinding, Person>(binding) {
        override fun bindTo(item: Person?) = with(binding) {
            if (item != null) {
                val prefix = if (saveData) IMAGE_URL_W500 else IMAGE_URL_ORIGINAL
                imgPerson.load(prefix + item.profilePath) {
                    transformations(RoundedCornersTransformation(IMAGE_CORNER_SIZE))
                    placeholder(R.drawable.ic_movies_64)
                    fallback(R.drawable.ic_movies_64)
                    error(R.drawable.ic_broken_image_64)
                }
                imgPerson.contentDescription = root.context.getString(R.string.person_profile, item.name)
                textName.text = item.name
                imgExplicit.isVisible = item.adult
            }
        }
    }

    companion object {
        private object ListPersonCallback : DiffUtil.ItemCallback<Person>() {
            override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean = oldItem == newItem
        }
    }
}
