package me.fungames.fortnite.api.network.services

import me.fungames.fortnite.api.model.GameProfile
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface PersonaPublicService {
    companion object {
        const val BASE_URL = "https://persona-public-service-prod06.ol.epicgames.com"
    }

    @GET("/persona/api/public/account/lookup")
    fun getAccountIdByDisplayName(@Query("q") displayName: String): Call<GameProfile>
}