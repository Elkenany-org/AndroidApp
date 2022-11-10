package com.elkenany.api.store

import com.elkenany.api.retrofit_configs.retrofit
import com.elkenany.entities.GenericEntity
import com.elkenany.entities.store.*
import retrofit2.Call
import retrofit2.http.*

interface IStore {
    @GET("store/ads-store")
    fun getAdsStoreData(
        @Query("section_id") type: Long?,
        @Query("search") search: String?,
        @Header("android") device: String?,
    ): Call<GenericEntity<AdsStoreData?>>

    @GET("store/ads-store-detials")
    fun getAdDetailsData(
        @Header("android") device: String?,
        @Query("id") id: Long,
    ): Call<GenericEntity<AdsDetailsData?>>

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
        @Header("android") device: String?,
        @Header("Authorization") apiToken: String?,
        @Field("title") title: String?,
        @Field("desc") description: String?,
        @Field("phone") phone: String?,
        @Field("salary") price: String?,
        @Field("section_id") sectorId: Long?,
        @Field("address") address: String?,
        @Field("con_type") connection: String?,
        @Field("images") imageFile: String?,
    ): Call<GenericEntity<NewAdData?>>

    @FormUrlEncoded
    @POST("store/update-ads-store")
    fun editAd(
        @Header("android") device: String?,
        @Header("Authorization") apiToken: String?,
        @Field("id") adId: Long,
        @Field("title") title: String?,
        @Field("desc") description: String?,
        @Field("phone") phone: String?,
        @Field("salary") price: String?,
        @Field("section_id") sectorId: Long?,
        @Field("address") address: String?,
        @Field("con_type") connection: String?,
        @Field("oldImages") oldImages: String?,
        @Field("NewImages") newImages: String?,
    ): Call<GenericEntity<NewAdData?>>

    @GET("store/my-ads-store")
    fun getAllMyAdsListData(
        @Header("Authorization") apiToken: String?,
        @Query("type") sectorType: String?,
    ): Call<GenericEntity<MyAdsListData?>>

    @GET("store/delete-ads-store")
    fun deleteAdFromDataBase(
        @Header("Authorization") apiToken: String?,
        @Query("id") adId: Long?,
    ): Call<GenericEntity<Any>>

}

object IStoreHandler {
    val singleton: IStore by lazy {
        retrofit.create(IStore::class.java)
    }
}