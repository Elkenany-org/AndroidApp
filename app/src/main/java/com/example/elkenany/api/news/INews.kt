package com.example.elkenany.api.news

import com.example.elkenany.api.retrofit_configs.retrofit
import com.example.elkenany.entities.GenericEntity
import com.example.elkenany.entities.news_data.NewsData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface INews {
    @GET("news/news")
    fun getAllNews(
        @Query("type") type: String?,
        @Query("search") search: String?,
    ): Call<GenericEntity<NewsData?>>

}

object INewsHandler {
    val singleton: INews by lazy {
        retrofit.create(INews::class.java)
    }
}