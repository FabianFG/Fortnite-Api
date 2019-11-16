package me.fungames.fortnite.api.network.services

import com.google.gson.JsonElement
import me.fungames.fortnite.api.model.AccountCompetitiveData
import me.fungames.fortnite.api.model.EventDownloadResponse
import me.fungames.fortnite.api.model.LeaderboardsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface EventsPublicService {

    companion object {
        const val BASE_URL = "https://events-public-service-live.ol.epicgames.com"
    }

    /**
     * @param gameId         "Fortnite"
     * @param accountId      "00112233445566778899aabbccddeeff"
     * @param regionId       "ASIA"
     * @param platformName   "Windows"
     * @param teamAccountIds "00112233445566778899aabbccddeeff"
     */
    @GET("/api/v1/events/{GameId}/download/{AccountId}")
    fun download(
        @Path("GameId") gameId: String, @Path("AccountId") accountId: String, @Query("region") regionId: String, @Query(
            "platform"
        ) platformName: String, @Query("teamAccountIds") teamAccountIds: String
    ): Call<EventDownloadResponse>

    /**
     * TODO haven't tried
     *
     * @param gameId          "Fortnite"
     * @param accountId       "00112233445566778899aabbccddeeff"
     * @param regionId        "ASIA"
     * @param bShowPastEvents ???
     */
    @GET("/api/v1/events/{GameId}/data/{AccountId}")
    fun data(
        @Path("GameId") gameId: String, @Path("AccountId") accountId: String, @Query("region") regionId: String, @Query(
            "showPastEvents"
        ) bShowPastEvents: Boolean?
    ): Call<JsonElement>

    /**
     * TODO haven't tried
     *
     * @param gameId        "Fortnite"
     * @param eventId       "epicgames_OnlineOpen_Week2_ASIA"
     * @param eventWindowId "OnlineOpen_Week2_ASIA_Event2"
     */
    @GET("/api/v1/events/{GameId}/{EventId}/{EventWindowId}/history")
    fun history(@Path("GameId") gameId: String, @Path("EventId") eventId: String, @Path("EventWindowId") eventWindowId: String): Call<JsonElement>

    /**
     * @param gameId            "Fortnite"
     * @param eventId           "epicgames_OnlineOpen_Week2_ASIA"
     * @param eventWindowId     "OnlineOpen_Week2_ASIA_Event2"
     * @param accountId         "00112233445566778899aabbccddeeff"
     * @param page              0
     * @param rank              0
     * @param teamAccountIds    ""
     * @param appId             "Fortnite"
     * @param bShowLiveSessions "false"
     */
    @GET("/api/v1/leaderboards/{GameId}/{EventId}/{EventWindowId}/{AccountId}")
    fun leaderboards(
        @Path("GameId") gameId: String, @Path("EventId") eventId: String, @Path("EventWindowId") eventWindowId: String, @Path(
            "AccountId"
        ) accountId: String, @Query("page") page: Int?, @Query("rank") rank: Int?, @Query("teamAccountIds") teamAccountIds: String, @Query(
            "appId"
        ) appId: String, @Query("showLiveSessions") bShowLiveSessions: Boolean?
    ): Call<LeaderboardsResponse>

    /**
     * @param gameId    "Fortnite"
     * @param accountId "00112233445566778899aabbccddeeff"
     */
    @GET("/api/v1/players/{GameId}/{AccountId}")
    fun eventDataForAccount(@Path("GameId") gameId: String, @Path("AccountId") accountId: String): Call<AccountCompetitiveData>

    /**
     * TODO return object
     *
     * @param gameId    "Fortnite"
     * @param eventId   "epicgames_OnlineOpen_Week2_ASIA"
     * @param accountId "00112233445566778899aabbccddeeff"
     */
    @GET("/api/v1/events/{GameId}/{EventId}/history/{AccountId}")
    fun eventHistoryForAccount(@Path("GameId") gameId: String, @Path("EventId") eventId: String, @Path("AccountId") accountId: String): Call<Array<JsonElement>>

    /**
     * TODO haven't tried
     *
     * @param gameId        "Fortnite"
     * @param eventId       "epicgames_OnlineOpen_Week2_ASIA"
     * @param eventWindowId "OnlineOpen_Week2_ASIA_Event2"
     * @param accountId     "00112233445566778899aabbccddeeff"
     */
    @GET("/api/v1/events/{GameId}/{EventId}/{EventWindowId}/history/{AccountId}")
    fun eventWindowHistoryForAccount(
        @Path("GameId") gameId: String, @Path("EventId") eventId: String, @Path("EventWindowId") eventWindowId: String, @Path(
            "AccountId"
        ) accountId: String
    ): Call<Array<JsonElement>>
}