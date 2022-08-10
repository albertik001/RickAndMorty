package com.geekstudio.rickandmorty.core.extensions

import java.text.SimpleDateFormat
import java.util.*

fun formatCurrentUserTime(dateFormat: String): String =
    SimpleDateFormat(dateFormat, Locale.getDefault()).format(Date())

