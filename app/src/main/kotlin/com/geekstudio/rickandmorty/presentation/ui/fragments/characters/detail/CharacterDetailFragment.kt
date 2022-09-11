package com.geekstudio.rickandmorty.presentation.ui.fragments.characters.detail

import android.net.Uri
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.geekstudio.rickandmorty.R
import com.geekstudio.rickandmorty.core.base.BaseFragment
import com.geekstudio.rickandmorty.core.extensions.loadImageWithGlide
import com.geekstudio.rickandmorty.core.utils.ConnectivityStatus
import com.geekstudio.rickandmorty.databinding.FragmentCharacterDetailBinding
import com.geekstudio.rickandmorty.presentation.models.CharactersUI
import com.geekstudio.rickandmorty.presentation.models.EpisodesUI
import com.geekstudio.rickandmorty.presentation.models.toUI
import com.geekstudio.rickandmorty.presentation.ui.adapters.CharacterDetailEpisodesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterDetailFragment :
    BaseFragment<FragmentCharacterDetailBinding, CharacterDetailViewModel>(R.layout.fragment_character_detail) {

    override val binding by viewBinding(FragmentCharacterDetailBinding::bind)
    override val viewModel by viewModels<CharacterDetailViewModel>()
    private val episodesAdapter = CharacterDetailEpisodesAdapter()
    private val args by navArgs<CharacterDetailFragmentArgs>()
    private val isConnected: ConnectivityStatus by lazy {
        ConnectivityStatus(requireActivity())
    }
    private val episodeDetailList = arrayListOf<EpisodesUI>()

    override fun initialize() {
        binding.rvEpisodes.adapter = episodesAdapter
    }

    override fun establishRequest() {
        isConnected.observe(viewLifecycleOwner) {
            if (it) {
                viewModel.fetchSingleCharacter(args.id)
            }
        }
    }

    override fun launchObservers() {
        isConnected.observe(viewLifecycleOwner) { connect ->
            if (connect) {
                viewModel.characterState.spectateUiState(success = {
                    setupCharacter(it)
                    it.episode.forEach { url ->
                        getIdFromUrl(url)?.let { it -> fetchFirstSeenIn(it, true) }
                    }
                })
            } else {
                viewModel.viewModelScope.launch {
                    viewModel.fetchLocalSingleCharacter(args.id).collectLatest {
                        setupCharacter(it.toUI())
                        it.toUI().episode.forEach { url ->
                            fetchFirstSeenIn(check = false, url = url)
                        }
                    }
                }
            }
        }
    }

    private fun setupCharacter(model: CharactersUI) = with(binding) {
        imLogoCharacter.loadImageWithGlide(model.image)
        nameTxt.text = model.name
        genderTxt.text = model.gender
    }

    private fun fetchFirstSeenIn(episodeUrl: Int? = null, check: Boolean, url: String? = null) {
        if (check) {
            episodeUrl?.let {
                viewModel.fetchSingleEpisode(episodeUrl)
                viewModel.episodesState.spectateUiState(success = {
                    episodeDetailList.add(it)
                    episodesAdapter.submitList(episodeDetailList)
                })
            }
        } else {
            viewModel.viewModelScope.launch {
                try {
                    url?.let {
                        viewModel.fetchLocalSingleEpisode(it).collectLatest { episodeModel ->
                            episodeDetailList.add(episodeModel.toUI())
                            Log.e("fuck", "fetchFirstSeenIn: $episodeDetailList")
                            episodesAdapter.submitList(episodeDetailList)
                        }
                    }
                } catch (e: NullPointerException) {
                }
            }
        }
    }

    private fun getIdFromUrl(url: String) = Uri.parse(url).lastPathSegment?.toInt()
}