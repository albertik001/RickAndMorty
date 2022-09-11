package com.geekstudio.rickandmorty.core.extensions

import android.widget.RadioButton

fun RadioButton.setOnCheckedChangeListenerAndRetrieveItsText(
    actionWhenChecked: (radioButtonText: String) -> Unit
) {
    setOnCheckedChangeListener { _, checked ->
        if (checked) {
            actionWhenChecked(text.toString())
        }
    }
}