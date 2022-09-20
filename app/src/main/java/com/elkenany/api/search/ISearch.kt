package com.elkenany.api.search

import com.elkenany.api.retrofit_configs.retrofit
import com.elkenany.entities.GenericEntity
import com.elkenany.entities.search.SearchResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ISearch {
    @GET("search-all")
    fun getAllSearchData(@Query("search") search: String?): Call<GenericEntity<SearchResult?>>
}

object ISearchHandler {
    val singleton: ISearch by lazy {
        retrofit.create(ISearch::class.java)
    }
}