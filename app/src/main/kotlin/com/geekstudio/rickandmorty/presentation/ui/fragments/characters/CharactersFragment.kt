package com.geekstudio.rickandmorty.presentation.ui.fragments.characters

import android.graphics.Color
import android.graphics.PorterDuff
import android.net.Uri
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.geekstudio.rickandmorty.R
import com.geekstudio.rickandmorty.core.base.BaseFragment
import com.geekstudio.rickandmorty.core.extensions.*
import com.geekstudio.rickandmorty.core.utils.ConnectivityStatus
import com.geekstudio.rickandmorty.data.db.preferences.NotificationsPreferencesManager
import com.geekstudio.rickandmorty.databinding.FragmentCharactersBinding
import com.geekstudio.rickandmorty.domain.common.Either
import com.geekstudio.rickandmorty.presentation.models.CharacterSelectedFilters
import com.geekstudio.rickandmorty.presentation.models.toUI
import com.geekstudio.rickandmorty.presentation.ui.adapters.CharactersPagingAdapter
import com.geekstudio.rickandmorty.presentation.ui.adapters.paging.LoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CharactersFragment :
    BaseFragment<FragmentCharactersBinding, CharactersViewModel>(R.layout.fragment_characters) {

    override val binding by viewBinding(FragmentCharactersBinding::bind)
    override val viewModel by viewModels<CharactersViewModel>()
    private val args by navArgs<CharactersFragmentArgs>()
    private val charactersAdapter = CharactersPagingAdapter(
        this::itemClick,
        this::fetchFirstSeenIn,
    )
    private val isConnected: ConnectivityStatus by lazy {
        ConnectivityStatus(requireActivity())
    }

    @Inject
    lateinit var notificationsPreferencesManager: NotificationsPreferencesManager

    override fun initialize() {
        setupAdapterLoader()
    }

    private fun setupAdapterLoader() = with(binding) {
        recyclerCharacters.apply {
            adapter = charactersAdapter.withLoadStateFooter(
                footer = LoadStateAdapter { charactersAdapter.retry() }
            )
            layoutManager = LinearLayoutManager(context)
        }
        charactersAdapter.bindUIToLoadState(
            recyclerCharacters,
            progressCharactersLoader,
            tvNoCharacterFound
        )
    }

    override fun constructViews() {
        customizeSearchView()
    }

    private fun customizeSearchView() {
        val searchView: EditText =
            binding.searchView.findViewById(androidx.appcompat.R.id.search_src_text)
        searchView.setHintTextColor(Color.WHITE)
        val searchClearButton: ImageView =
            binding.searchView.findViewById(androidx.appcompat.R.id.search_close_btn)
        searchClearButton.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
    }

    override fun performListeners() {
        setupSearch()
        binding.btnFilter.setOnClickListener {
            findNavController().navigateSafely(R.id.action_charactersFragment_to_filterDialogFragment)
        }
    }

    private fun setupSearch() {
        binding.searchView.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let {
                        if (newText.isNotEmpty()) {
                            fetchCharacterSearchName(newText)
                        }
                    }
                    return false
                }
            })
            this.findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn)
                .setOnClickListener {
                    setQuery("", false)
                    binding.recyclerCharacters.scrollToPosition(0)
                    args.filter?.status = null
                    args.filter?.species = null
                    args.filter?.gender = null
                    fetchCharacterSearchName("")
                    onActionViewCollapsed()
                }
        }
    }

    private fun fetchCharacterSearchName(name: String) {
        isConnected.observe(viewLifecycleOwner) { connect ->
            if (connect) {
                viewModel.fetchPagedCharacters(name).spectatePaging(success = {
                    charactersAdapter.submitData(it)
                })
            } else {
                viewModel.viewModelScope.launch {
                    viewModel.fetchLocalPagedCharacters(name).collectLatest {
                        charactersAdapter.submitData(PagingData.from(it.map { charactersModel ->
                            charactersModel.toUI()
                        }))
                        binding.tvNoCharacterFound.isVisible = charactersAdapter.itemCount == 0
                    }
                }
            }
        }
    }

    override fun launchObservers() {
        subscribeToCharacters()
    }

    private fun subscribeToCharacters() = with(binding) {
        val filter = args.filter
        isConnected.observe(viewLifecycleOwner) { connect ->
            if (connect) {
                if (includedNoInternet.root.isVisible) {
                    includedNoInternet.root.gone()
                }
                notificationsPreferencesManager.isShowCheckInternet = false
                viewModel.fetchPagedCharacters(
                    null,
                    filter?.status,
                    filter?.species,
                    filter?.gender
                ).spectatePaging {
                    charactersAdapter.submitData(it)
                }
            } else {
                if (!notificationsPreferencesManager.isShowCheckInternet) {
                    binding.includedNoInternet.root.visible()
                    includedNoInternet.btnDoNotShowMore.setOnClickListener {
                        notificationsPreferencesManager.isShowCheckInternet = true
                        extractDataRoom(filter)
                    }
                }
                if (notificationsPreferencesManager.isShowCheckInternet) {
                    extractDataRoom(filter)
                } else {
                    includedNoInternet.btnExtractDataRoom.setOnClickListener {
                        extractDataRoom(filter)
                    }
                }
            }
        }
    }

    private fun extractDataRoom(filter: CharacterSelectedFilters?) {
        viewModel.viewModelScope.launch {
            viewModel.fetchLocalPagedCharacters(
                null,
                filter?.status,
                filter?.species,
                filter?.gender
            ).collectLatest {
                charactersAdapter.submitData(PagingData.from(it.map { charactersModel ->
                    charactersModel.toUI()
                }))
            }
        }
        binding.includedNoInternet.root.gone()
    }

    private fun fetchFirstSeenIn(position: Int, episodeUrl: String) {
        isConnected.observe(viewLifecycleOwner) { connect ->
            if (connect) {
                viewModel.viewModelScope.launch {
                    try {
                        getIdFromUrl(episodeUrl)?.let { url ->
                            viewModel.fetchSingleEpisode(url).collect {
                                when (it) {
                                    is Either.Right -> {
                                        it.value.toUI().name.let { name ->
                                            charactersAdapter.setFirstSeenIn(position, name)
                                        }
                                    }
                                    is Either.Left -> {}
                                }
                            }
                        }
                    } catch (e: IndexOutOfBoundsException) {
                    }
                }
            } else {
                viewModel.viewModelScope.launch {
                    try {
                        viewModel.fetchLocalSingleEpisode(episodeUrl).collectLatest {
                            it.toUI().name.let { name ->
                                charactersAdapter.setFirstSeenIn(position, name)
                            }
                        }
                    } catch (e: IllegalStateException) {
                    }
                }
            }
        }
    }

    private fun itemClick(id: Int?) {
        id?.let {
            findNavController().directionsSafeNavigation(
                CharactersFragmentDirections.actionCharactersFragmentToCharacterDetailFragment(
                    it
                )
            )
        }
    }

    override fun onDestroy() {
        notificationsPreferencesManager.isShowCheckInternet = false
        super.onDestroy()
    }

    private fun getIdFromUrl(url: String) = Uri.parse(url).lastPathSegment?.toInt()
}