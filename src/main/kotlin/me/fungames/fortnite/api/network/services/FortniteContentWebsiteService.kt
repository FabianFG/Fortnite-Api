package me.fungames.fortnite.api.network.services

import me.fungames.fortnite.api.model.FortBasicDataResponse
import retrofit2.Call
import retrofit2.http.GET

interface FortniteContentWebsiteService {
    companion object {
        const val BASE_URL = "https://fortnitecontent-website-prod07.ol.epicgames.com"
    }

    @GET("/content/api/pages/fortnite-game")
    fun getBasicData() : Call<FortBasicDataResponse>
}