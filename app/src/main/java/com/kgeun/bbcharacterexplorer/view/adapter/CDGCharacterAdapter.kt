package com.kgeun.bbcharacterexplorer.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.kgeun.bbcharacterexplorer.R
import com.kgeun.bbcharacterexplorer.analytics.CDGAnalytics
import com.kgeun.bbcharacterexplorer.data.model.ui.BBCharacterListItem
import com.kgeun.bbcharacterexplorer.databinding.ListitemBreedsBinding
import com.kgeun.bbcharacterexplorer.view.fragment.CDGBreedsFragmentDirections

class CDGCharacterAdapter(val parentView: ViewGroup, val breedsList: List<BBCharacterListItem>?) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CharacterHolder(
                ListitemBreedsBinding.inflate(
                    LayoutInflater.from(parentView.context), parentView, false
                )
            )
    }

    override fun getItemCount(): Int = breedsList?.size ?: 0

    inner class CharacterHolder(
        private val binding: ListitemBreedsBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: BBCharacterListItem) {
            binding.apply {
                breedItem = item
                cardView.isClickable = true
                cardView.isFocusable = true

                cardView.setOnClickListener {
                    val navBuilder = NavOptions.Builder()
                        .setEnterAnim(R.anim.slide_from_right)
                        .setExitAnim(R.anim.slide_to_left)
                        .setPopEnterAnim(R.anim.slide_from_left)
                        .setPopExitAnim(R.anim.slide_to_right)

                    findNavController(root)
                        .navigate(
                            CDGBreedsFragmentDirections.breedsToGallery(item.name!!), navBuilder.build()
                        )
                    CDGAnalytics.sendClick("BreedsItem_${item.name}_${javaClass.simpleName}")
                }
                executePendingBindings()
            }
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position > 0) {
            breedsList?.let {
                (holder as CharacterHolder).bind(breedsList[position])
            }
        }
    }
}