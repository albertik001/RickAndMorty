package com.geekstudio.rickandmorty.core.base

interface BaseDiffModel<T> {
    val id: T?
    override fun equals(other: Any?): Boolean
}