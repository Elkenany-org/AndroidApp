package com.example.elkenany.api.search

import com.example.elkenany.api.retrofit_configs.retrofit
import com.example.elkenany.entities.GenericEntity
import com.example.elkenany.entities.search.SearchResult
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