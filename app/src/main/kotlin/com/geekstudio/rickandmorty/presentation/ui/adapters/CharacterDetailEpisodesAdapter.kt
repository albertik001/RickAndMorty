package com.geekstudio.rickandmorty.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.geekstudio.rickandmorty.core.base.BaseDiffUtil
import com.geekstudio.rickandmorty.databinding.ItemCharacterDetailEpisodesBinding
import com.geekstudio.rickandmorty.presentation.models.EpisodesUI

class CharacterDetailEpisodesAdapter :
    ListAdapter<EpisodesUI, CharacterDetailEpisodesAdapter.EpisodesViewHolder>(BaseDiffUtil()) {

    inner class EpisodesViewHolder(private val binding: ItemCharacterDetailEpisodesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(episodesUI: EpisodesUI) {
            binding.tvCharacterEpisodes.text = episodesUI.name
            binding.tvDescriptionCharacterEpisodes.text = episodesUI.episode
        }
    }

    override fun onBindViewHolder(holder: EpisodesViewHolder, position: Int) {
        getItem(position)?.let { holder.onBind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = EpisodesViewHolder(
        ItemCharacterDetailEpisodesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )
}