package com.aayush.telewise.util.android.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import com.aayush.telewise.R
import com.aayush.telewise.databinding.CardCreditsBinding
import com.aayush.telewise.model.UiModel
import com.aayush.telewise.util.android.base.BaseAdapter
import com.aayush.telewise.util.android.base.BaseViewHolder
import com.aayush.telewise.util.android.toast
import com.aayush.telewise.util.common.IMAGE_URL_W500

class CreditsAdapter(
    items: List<UiModel.Person>
) : BaseAdapter<CreditsAdapter.CreditsViewHolder, UiModel.Person>(items, true) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreditsViewHolder =
        CreditsViewHolder(
            CardCreditsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).also { holder ->
            holder.setOnClickListener { view ->
                toast(view.context, items[holder.bindingAdapterPosition].name)
            }
        }

    override fun getItemId(position: Int): Long = items[position].hashCode().toLong()

    inner class CreditsViewHolder(
        binding: CardCreditsBinding
    ) : BaseViewHolder<CardCreditsBinding, UiModel.Person>(binding) {
        override fun bindTo(item: UiModel.Person?) = with(binding) {
            imgPerson.load(IMAGE_URL_W500 + item?.profilePath) {
                placeholder(R.drawable.ic_people_64)
                fallback(R.drawable.ic_people_64)
                error(R.drawable.ic_broken_image_64)
            }
            textName.text = item?.name
            textRole.text = item?.role
        }
    }
}
