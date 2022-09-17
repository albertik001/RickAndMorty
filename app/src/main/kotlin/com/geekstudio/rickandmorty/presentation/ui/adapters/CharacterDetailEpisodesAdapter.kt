package com.geekstudio.rickandmorty.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geekstudio.rickandmorty.databinding.ItemCharacterDetailEpisodesBinding
import com.geekstudio.rickandmorty.presentation.models.EpisodesUI

class CharacterDetailEpisodesAdapter :
    RecyclerView.Adapter<CharacterDetailEpisodesAdapter.EpisodesViewHolder>() {

    private var list = ArrayList<EpisodesUI>()

    override fun onBindViewHolder(holder: EpisodesViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = EpisodesViewHolder(
        ItemCharacterDetailEpisodesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun getItemCount() = list.size

    fun setList(list: ArrayList<EpisodesUI>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class EpisodesViewHolder(private val binding: ItemCharacterDetailEpisodesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(episodesUI: EpisodesUI) {
            binding.tvCharacterEpisodes.text = episodesUI.name
            binding.tvDescriptionCharacterEpisodes.text = episodesUI.episode
        }
    }

}