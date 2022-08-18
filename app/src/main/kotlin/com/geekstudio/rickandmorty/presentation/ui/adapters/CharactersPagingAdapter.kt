package com.geekstudio.rickandmorty.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.geekstudio.rickandmorty.R
import com.geekstudio.rickandmorty.core.base.BaseDiffUtil
import com.geekstudio.rickandmorty.core.extensions.loadImageWithGlide
import com.geekstudio.rickandmorty.databinding.ItemCharacterBinding
import com.geekstudio.rickandmorty.presentation.models.CharactersUI

class CharactersPagingAdapter :
    PagingDataAdapter<CharactersUI, CharactersPagingAdapter.CharactersViewHolder>(BaseDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CharactersViewHolder(
        ItemCharacterBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        getItem(position)?.let { holder.onBind(it) }
    }

    class CharactersViewHolder(private val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(character: CharactersUI) {
            binding.textItemCharacterFirstSeenInData.isSelected = true
            binding.imageItemCharacter.loadImageWithGlide(character.image)
            binding.textItemCharacterName.text = character.name
            binding.textItemCharacterFirstSeenInData.text = character.created
            binding.textItemCharacterLastKnownLocationData.text = character.location?.name
            binding.textItemCharacterStatusAndSpecies.text =
                binding.imageItemCharacterStatus.context
                    .resources.getString(R.string.hyphen, character.status, character.species)
        }
    }
}