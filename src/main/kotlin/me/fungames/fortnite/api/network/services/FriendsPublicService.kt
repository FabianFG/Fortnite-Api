package me.fungames.fortnite.api.network.services

import me.fungames.fortnite.api.model.BlockedUsers
import me.fungames.fortnite.api.model.Friend
import me.fungames.fortnite.api.model.FriendsSettings
import retrofit2.Call
import retrofit2.http.*


interface FriendsPublicService {
    companion object {
        const val BASE_URL = "https://friends-public-service-prod.ol.epicgames.com"
    }

    @GET("/friends/api/public/friends/{id}")
    fun friends(@Path("id") id: String, @Query("includePending") includePending: Boolean?): Call<Array<Friend>>

    @POST("/friends/api/public/friends/{id}/{friend}")
    fun inviteOrAccept(@Path("id") id: String, @Path("friend") friend: String): Call<Void>

    @DELETE("/friends/api/public/friends/{id}/{friend}")
    fun removeOrReject(@Path("id") id: String, @Path("friend") friend: String): Call<Void>

    @GET("/friends/api/public/blocklist/{id}")
    fun blockList(@Path("id") id: String): Call<BlockedUsers>

    //	@GET("/friends/api/public/blocklist/{id}/{block}")
    //	Call<Friend> blockList(@Path("id") String id, @Path("block") String block);

    @GET("/friends/api/public/list/{namespace}/{id}/recentPlayers")
    fun recentPlayers(@Path("namespace") namespace: String, @Path("id") id: String): Call<Array<Friend>>

    @GET("/friends/api/v1/{id}/settings")
    fun settings(@Path("id") id: String): Call<FriendsSettings>

    @PUT("/friends/api/v1/{id}/settings")
    fun setSettings(@Path("id") id: String, @Body newSettings: FriendsSettings): Call<FriendsSettings>
}