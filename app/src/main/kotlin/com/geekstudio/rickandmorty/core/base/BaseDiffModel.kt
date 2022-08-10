package com.geekstudio.rickandmorty.core.base

interface BaseDiffModel<T> {
    val nameService: T?
    override fun equals(other: Any?): Boolean
}