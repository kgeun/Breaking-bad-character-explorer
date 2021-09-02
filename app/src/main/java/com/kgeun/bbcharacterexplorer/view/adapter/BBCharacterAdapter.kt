package com.kgeun.bbcharacterexplorer.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.kgeun.bbcharacterexplorer.R
import com.kgeun.bbcharacterexplorer.data.model.network.BBCharacter
import com.kgeun.bbcharacterexplorer.databinding.ListitemCharacterBinding
import com.kgeun.bbcharacterexplorer.view.fragment.BBCharacterListFragmentDirections

class BBCharacterAdapter(val parentView: ViewGroup, val characterList: List<BBCharacter>?) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CharacterHolder(
                ListitemCharacterBinding.inflate(
                    LayoutInflater.from(parentView.context), parentView, false
                )
            )
    }

    override fun getItemCount(): Int = characterList?.size ?: 0

    inner class CharacterHolder(
        private val binding: ListitemCharacterBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: BBCharacter) {
            binding.apply {
                character = item
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
                            BBCharacterListFragmentDirections.listToDetail(item.char_id), navBuilder.build()
                        )
                }
                executePendingBindings()
            }
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        characterList?.let {
            (holder as CharacterHolder).bind(characterList[position])
        }
    }
}