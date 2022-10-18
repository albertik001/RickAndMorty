package com.geekstudio.rickandmorty.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.geekstudio.rickandmorty.R
import com.geekstudio.rickandmorty.core.base.BaseDiffUtil
import com.geekstudio.rickandmorty.core.extensions.setImage
import com.geekstudio.rickandmorty.core.extensions.setOnSingleClickListener
import com.geekstudio.rickandmorty.databinding.ItemCharacterBinding
import com.geekstudio.rickandmorty.presentation.enums.CharacterStatus
import com.geekstudio.rickandmorty.presentation.models.CharactersUI

class CharactersPagingAdapter(
    private val itemCLick: (id: Int?) -> Unit,
    val fetchFirstSeenIn: (position: Int, episodeUrl: String) -> Unit,
    val onLongClickByImageAvatar: (imageUrl: String, name: String) -> Unit
) : PagingDataAdapter<CharactersUI, CharactersPagingAdapter.CharactersViewHolder>(BaseDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CharactersViewHolder(
        ItemCharacterBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        getItem(position)?.let { holder.onBind(it) }
    }

    override fun onBindViewHolder(
        holder: CharactersViewHolder, position: Int, payloads: MutableList<Any>
    ) {
        if (payloads.isNotEmpty()) {
            if (payloads[0] is String) {
                holder.setupFirstSeenIn(
                    getItem(position)?.firstSeenIn.toString(),
                    getItem(position)?.episode?.first().toString()
                )
            }
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    fun setFirstSeenIn(position: Int, firstSeenIn: String) {
        getItem(position)?.firstSeenIn = firstSeenIn
        notifyItemChanged(position)
    }

    inner class CharactersViewHolder(private val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnSingleClickListener {
                itemCLick(getItem(absoluteAdapterPosition)?.id)
            }

            binding.imageItemCharacter.setOnLongClickListener {
                onLongClickByImageAvatar(
                    getItem(absoluteAdapterPosition)?.image.toString(),
                    getItem(absoluteAdapterPosition)?.name.toString()
                )
                return@setOnLongClickListener false
            }

        }

        fun onBind(character: CharactersUI) = with(binding) {
            textItemCharacterFirstSeenInData.isSelected = true
            imageItemCharacter.setImage(character.image)
            textItemCharacterName.text = character.name
            textItemCharacterLastKnownLocationData.text = character.location.name
            textItemCharacterStatusAndSpecies.text =
                imageItemCharacterStatus.context.resources.getString(
                    R.string.hyphen, character.status, character.species
                )
            setupFirstSeenIn(character.firstSeenIn, character.episode.first())
            setupCharacterStatus(character.status)
        }

        private fun setupCharacterStatus(status: String) = with(binding) {
            when (status) {
                CharacterStatus.ALIVE.status -> {
                    imageItemCharacterStatus.setImageResource(CharacterStatus.ALIVE.image)
                }
                CharacterStatus.DEAD.status -> {
                    imageItemCharacterStatus.setImageResource(CharacterStatus.DEAD.image)
                }
                CharacterStatus.UNKNOWN.status -> {
                    imageItemCharacterStatus.setImageResource(CharacterStatus.UNKNOWN.image)
                }
            }
        }

        fun setupFirstSeenIn(firstSeenIn: String, episode: String) = with(binding) {
            progressBarCharacterFirstSeenIn.isVisible = firstSeenIn.isEmpty()
            textItemCharacterFirstSeenInData.isVisible = firstSeenIn.isNotEmpty()
            if (firstSeenIn.isEmpty()) {
                fetchFirstSeenIn(absoluteAdapterPosition, episode)
            } else {
                textItemCharacterFirstSeenInData.text = firstSeenIn
            }
        }
    }
}