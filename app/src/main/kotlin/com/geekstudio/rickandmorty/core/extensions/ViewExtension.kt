package com.geekstudio.rickandmorty.core.extensions

import android.util.Log
import android.view.View
import com.geekstudio.rickandmorty.core.utils.OnSingleClickListener

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.setOnSingleClickListener(l: (View) -> Unit) {
    setOnClickListener(OnSingleClickListener(l))
}

fun loge(msg: String, value: Any? = null) = Log.e("fuck", msg + value)