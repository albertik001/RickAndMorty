package com.geekstudio.rickandmorty.presentation.ui.fragments.characters

import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.geekstudio.rickandmorty.R
import com.geekstudio.rickandmorty.core.base.BaseFragment
import com.geekstudio.rickandmorty.databinding.FragmentCharactersBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersFragment :
    BaseFragment<FragmentCharactersBinding, CharactersViewModel>(R.layout.fragment_characters) {
    override val binding by viewBinding(FragmentCharactersBinding::bind)
    override val viewModel by viewModels<CharactersViewModel>()

    override fun initialize() {

    }

}