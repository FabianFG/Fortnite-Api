package me.fungames.fortnite.api.network.services

import me.fungames.fortnite.api.model.DSQueryResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DataStoragePublicService {

    companion object {
        const val BASE_URL = "https://datastorage-public-service-live.ol.epicgames.com"
    }

    @GET("/api/v1/access/fnreplaysmetadata/public/{sessionId}.json")
    fun accessMeta(@Path("sessionId") sessionId: String): Call<DSQueryResponse>

    @GET("/api/v1/access/fnreplays/public/{sessionId}/{chunkId}.bin")
    fun accessChunks(@Path("sessionId") sessionId: String,
                     @Path("chunkId") chunkId: String): Call<DSQueryResponse>

}