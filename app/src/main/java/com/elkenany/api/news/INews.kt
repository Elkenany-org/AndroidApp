package com.elkenany.api.news

import com.elkenany.api.retrofit_configs.retrofit
import com.elkenany.entities.GenericEntity
import com.elkenany.entities.news_data.NewsData
import com.elkenany.entities.news_data.NewsDetailsData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface INews {
    @GET("news/news")
    fun getAllNews(
        @Query("type") type: String?,
        @Query("search") search: String?,
        @Query("sort") sort: String?,
    ): Call<GenericEntity<NewsData?>>

    @GET("news/news-detials")
    fun getNewsDetailsByID(@Query("id") id: Int): Call<GenericEntity<NewsDetailsData?>>
}

object INewsHandler {
    val singleton: INews by lazy {
        retrofit.create(INews::class.java)
    }
}