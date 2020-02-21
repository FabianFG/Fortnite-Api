package me.fungames.fortnite.api.network.services

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import me.fungames.fortnite.api.model.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface FortnitePublicService {

    @POST("/fortnite/api/game/v2/profile/{id}/client/{command}")
    fun clientCommand(
        @Path("command") command: String, @Path("id") accountId: String, @Query("profileId") profileId: String, @Query(
            "rvn"
        ) currentProfileRevision: Int?, @Header("X-EpicGames-ProfileRevisions") profileRevisionsMeta: String?, @Body payload: Any
    ): Call<FortMcpResponse>

    @GET("/fortnite/api/game/v2/world/info")
    fun pveWorldInfo(): Call<WorldInfoResponse>

    @GET("/fortnite/api/game/v2/privacy/account/{id}")
    fun getAccountPrivacy(@Path("id") id: String): Call<AccountPrivacyResponse>

    @POST("/fortnite/api/game/v2/privacy/account/{id}")
    fun setAccountPrivacy(@Path("id") id: String, @Body payload: AccountPrivacyResponse): Call<AccountPrivacyResponse>

    @GET("/fortnite/api/storefront/v2/catalog")
    fun storefrontCatalog(): Call<FortCatalogResponse>

    @GET("/fortnite/api/calendar/v1/timeline")
    fun calendarTimeline(): Call<CalendarTimelineResponse>

    @GET("/fortnite/api/statsv2/account/{id}")
    fun querySingleUserStats(@Path("id") id: String, @Query("startTime") startTime: Long?, @Query("endTime") endTime: Long?): Call<StatsV2Response>

    @POST("/fortnite/api/statsv2/query")
    fun queryMultipleUserStats(@Body payload: QueryMultipleUserStats): Call<Array<StatsV2Response>>

    @GET("/fortnite/api/cloudstorage/user/{id}")
    fun enumerateUserFiles(@Path("id") id: String): Call<Array<JsonObject>>

    @POST("/fortnite/api/game/v2/events/v2/processPendingRewards/{id}")
    fun eventsProcessPendingRewards(@Path("id") id: String): Call<Array<JsonElement>>

    @GET("/fortnite/api/game/v2/tryPlayOnPlatform/account/{id}")
    fun tryPlayOnPlatform(@Path("id") id: String, @Query("platform") platform: String): Call<Boolean>

    @GET("/fortnite/api/game/v2/enabled_features")
    fun enabledFeatures(): Call<Array<JsonElement>>

    @POST("/fortnite/api/game/v2/grant_access/{id}")
    fun grantAccess(@Path("id") id: String): Call<Void>

    @GET("/fortnite/api/storefront/v2/keychain")
    fun storefrontKeychain(@Query("numKeysDownloaded") numKeysDownloaded: Int?): Call<Array<String>>

    @GET("/fortnite/api/receipts/v1/account/{id}/receipts")
    fun receipts(@Path("id") id: String): Call<Array<Receipt>>

    /**
     * @param olderThan in ISO 8601 date format
     */
    @GET("/fortnite/api/game/v2/creative/favorites/{accountId}")
    fun queryCreativeFavorites(@Path("accountId") accountId: String, @Query("limit") limit: Int?, @Query("olderThan") olderThan: String): Call<LinksQueryResponse>

    @PUT("/fortnite/api/game/v2/creative/favorites/{accountId}/{mnemonic}")
    fun addCodeToCreativeFavorites(@Path("accountId") accountId: String, @Path("mnemonic") mnemonic: String): Call<LinkEntry>

    /**
     * Requires permission fortnite:profile:MissingParameterValue:commands DELETE
     */
    @DELETE("/fortnite/api/game/v2/creative/favorites/{accountId}/{mnemonic}")
    fun removeCodeFromCreativeFavorites(@Path("accountId") accountId: String, @Path("mnemonic") mnemonic: String): Call<Void>

    /**
     * @param olderThan in ISO 8601 date format
     */
    @GET("/fortnite/api/game/v2/creative/history/{accountId}")
    fun queryCreativeHistory(@Path("accountId") accountId: String, @Query("limit") limit: Int?, @Query("olderThan") olderThan: String): Call<LinksQueryResponse>

    /**
     * Requires permission fortnite:fortnite_role:dedicated_server ALL
     */
    @PUT("/fortnite/api/game/v2/creative/history/{accountId}/{mnemonic}")
    fun addCodeToCreativeHistory(@Path("accountId") accountId: String, @Path("mnemonic") mnemonic: String): Call<LinkEntry>

    @DELETE("/fortnite/api/game/v2/creative/history/{accountId}/{mnemonic}")
    fun removeCodeFromCreativeHistory(@Path("accountId") accountId: String, @Path("mnemonic") mnemonic: String): Call<Void>

    @GET("/fortnite/api/storefront/v2/gift/check_eligibility/recipient/{recipientAccountId}/offer/{offerId}")
    fun checkGiftEligibility(@Path("recipientAccountId") recipientAccountId: String, @Path("offerId") offerId: String): Call<Void>

    @GET("/fortnite/api/cloudstorage/system")
    fun getCloudstorageList() : Call<List<CloudStorageResponse>>

    @GET("/fortnite/api/cloudstorage/system/{uniqueFilename}")
    fun downloadCloudstorageFile(@Path("uniqueFilename") uniqueFilename: String) : Call<ResponseBody>

    companion object {
        val BASE_URL = "https://fortnite-public-service-prod11.ol.epicgames.com"
    }
}