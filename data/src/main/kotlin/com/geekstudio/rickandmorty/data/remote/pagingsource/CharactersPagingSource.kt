package com.geekstudio.rickandmorty.data.remote.pagingsource

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.geekstudio.rickandmorty.data.remote.apiservices.CharactersApi
import com.geekstudio.rickandmorty.data.remote.dtos.CharactersDto
import okio.IOException
import retrofit2.HttpException

class CharactersPagingSource(
    private val charactersApi: CharactersApi,
    private val name: String?,
    private val status: String?,
    private val species: String?,
    private val gender: String?
) : PagingSource<Int, CharactersDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharactersDto> {
        val page = params.key ?: 1
        return try {
            val response = charactersApi.fetchCharacters(page, name, status, species, gender)

            val nextPageNumber = if (response.info?.next == null) {
                null
            } else
                Uri.parse(response.info.next).getQueryParameter("page")?.toInt()
            LoadResult.Page(data = response.results, prevKey = null, nextKey = nextPageNumber)

        } catch (ioException: IOException) {
            LoadResult.Error(ioException)
        } catch (httpException: HttpException) {
            LoadResult.Error(httpException)
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CharactersDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)

        }
    }
}