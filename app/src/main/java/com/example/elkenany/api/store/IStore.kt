package com.example.elkenany.api.store

import com.example.elkenany.api.retrofit_configs.retrofit
import com.example.elkenany.entities.GenericEntity
import com.example.elkenany.entities.store.*
import retrofit2.Call
import retrofit2.http.*
import java.io.File

interface IStore {
    @GET("store/ads-store")
    fun getAdsStoreData(
        @Query("type") type: String?,
        @Query("search") search: String?,
    ): Call<GenericEntity<AdsStoreData?>>

    @GET("store/ads-store-detials")
    fun getAdDetailsData(@Query("id") id: Long): Call<GenericEntity<AdsDetailsData?>>

    @GET("store/chats")
    fun getAllChatsData(@Header("Authorization") apiToken: String?): Call<GenericEntity<ChatsData?>>

    @GET("store/start-chat")
    fun getAllStartChatData(
        @Header("Authorization") apiToken: String?,
        @Query("id") adsId: Long?,
    ): Call<GenericEntity<StartChatData>>

    @GET("store/chats-massages")
    fun getAllMessagesList(
        @Query("id") id: Long,
        @Header("Authorization") apiToken: String?,
    ): Call<GenericEntity<MessagesData?>>

    @FormUrlEncoded
    @POST("store/add-massages")
    fun sendMessageData(
        @Header("Authorization") apiToken: String?,
        @Field("id") chatId: Long?,
        @Field("massage") messageData: String?,
    ): Call<GenericEntity<MessagesData?>>

    @FormUrlEncoded
    @POST("store/add-ads-store")
    fun createNewAd(
        @Header("Authorization") apiToken: String?,
        @Field("title") title: String?,
        @Field("desc") description: String?,
        @Field("phone") phone: String?,
        @Field("salary") price: String?,
        @Field("section_id") sectorId: Long?,
        @Field("address") address: String?,
        @Field("con_type") connection: String?,
        @Field("images[]") imageFile: File?,
    ): Call<GenericEntity<NewAdData?>>


}

object IStoreHandler {
    val singleton: IStore by lazy {
        retrofit.create(IStore::class.java)
    }
}