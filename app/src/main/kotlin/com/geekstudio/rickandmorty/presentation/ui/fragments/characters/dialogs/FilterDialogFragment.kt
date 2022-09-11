package com.geekstudio.rickandmorty.presentation.ui.fragments.characters.dialogs

import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.geekstudio.rickandmorty.R
import com.geekstudio.rickandmorty.core.base.BaseDialogFragmentWithoutViewModel
import com.geekstudio.rickandmorty.core.extensions.directionsSafeNavigation
import com.geekstudio.rickandmorty.core.extensions.setOnCheckedChangeListenerAndRetrieveItsText
import com.geekstudio.rickandmorty.databinding.FragmentFilterDialogBinding
import com.geekstudio.rickandmorty.presentation.models.CharacterSelectedFilters


class FilterDialogFragment :
    BaseDialogFragmentWithoutViewModel<FragmentFilterDialogBinding>(R.layout.fragment_filter_dialog) {

    override val binding by viewBinding(FragmentFilterDialogBinding::bind)
    private val filterSettings = CharacterSelectedFilters()

    override fun initialize() {
        dialog?.setCancelable(true)
        dialog?.setCanceledOnTouchOutside(true)
    }

    override fun setupListeners() = with(binding) {
        binding.radioButtonAlive.setOnCheckedChangeListenerAndRetrieveItsText {
            filterSettings.status = it
        }
        binding.radioButtonDead.setOnCheckedChangeListenerAndRetrieveItsText {
            filterSettings.status = it
        }
        binding.radioButtonMale.setOnCheckedChangeListenerAndRetrieveItsText {
            filterSettings.gender = it
        }
        binding.radioButtonFemale.setOnCheckedChangeListenerAndRetrieveItsText {
            filterSettings.gender = it
        }
        binding.radioButtonHuman.setOnCheckedChangeListenerAndRetrieveItsText {
            filterSettings.species = it
        }
        binding.radioButtonAlien.setOnCheckedChangeListenerAndRetrieveItsText {
            filterSettings.species = it
        }

        buttonEnter.setOnClickListener {
            findNavController().directionsSafeNavigation(
                FilterDialogFragmentDirections.actionFilterDialogFragmentToCharactersFragment(
                    filterSettings
                )
            )
        }
    }
}
