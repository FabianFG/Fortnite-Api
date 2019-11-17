package me.fungames.fortnite.api.network.services

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LauncherPublicService {
    companion object {
        const val BASE_URL = "https://launcher-public-service-prod06.ol.epicgames.com"
    }

    @GET("/launcher/api/public/assets/{platform}/{assetId}/{assetName}")
    fun getManifest(@Path("platform") platform : String, @Path("assetId") assetId : String, @Path("assetName") assetName : String, @Query("label") label : String)
}