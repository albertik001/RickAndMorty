package com.geekstudio.rickandmorty.data.repository.base

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import com.geekstudio.rickandmorty.domain.common.Either
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal fun <T> doRequest(
    gatherIfSucceed: ((T) -> Unit)? = null,
    request: suspend () -> T,
) = flow<Either<String, T>> {
    request().also { data ->
        gatherIfSucceed?.invoke(data)
        emit(Either.Right(value = data))
    }
}.flowOn(Dispatchers.IO).catch { exception ->
    emit(Either.Left(exception.localizedMessage ?: "An error occurred"))
}

internal fun <Key : Any, Model : Any> doPagingRequest(
    pagingSource: PagingSource<Key, Model>,
) = Pager(
    PagingConfig(
        pageSize = 10,
        prefetchDistance = 10,
        enablePlaceholders = true,
        initialLoadSize = 10 * 3,
        maxSize = Int.MAX_VALUE,
        jumpThreshold = Int.MIN_VALUE

    ),
    pagingSourceFactory =
    {
        pagingSource
    }
).flow