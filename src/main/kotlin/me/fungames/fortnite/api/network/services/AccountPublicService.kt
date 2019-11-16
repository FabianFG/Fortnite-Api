package me.fungames.fortnite.api.network.services

import me.fungames.fortnite.api.model.GameProfile
import me.fungames.fortnite.api.model.QueryExternalIdMappingsByIdPayload
import me.fungames.fortnite.api.model.ExternalAuth
import me.fungames.fortnite.api.model.DeviceAuth
import com.google.gson.JsonObject
import me.fungames.fortnite.api.model.XGameProfile
import me.fungames.fortnite.api.model.VerifyResponse
import me.fungames.fortnite.api.model.ExchangeResponse
import me.fungames.fortnite.api.model.LoginResponse
import retrofit2.Call
import retrofit2.http.*


interface AccountPublicService {

    companion object {
        const val BASE_URL = "https://account-public-service-prod.ol.epicgames.com"
    }
    /**
     * grant_type: authorization_code; fields: code
     * grant_type: client_credentials -- used to retrieve client ID without sign in
     * grant_type: device_auth; fields: account_id, device_id, secret
     * grant_type: exchange_code; fields: exchange_code
     * grant_type: external_auth; fields: external_auth_type, external_auth_token
     * grant_type: otp; fields: otp, challenge
     * grant_type: password; fields: username, password //Not working anymore
     * grant_type: refresh_token; fields: refresh_token
     */
    @FormUrlEncoded
    @POST("/account/api/oauth/token")
    fun grantToken(
        @Header("Authorization") auth: String, @Field("grant_type") grantType: String, @FieldMap fields: Map<String, String>, @Field(
            "includePerms"
        ) includePerms: Boolean?
    ): Call<LoginResponse>

    @GET("/account/api/oauth/exchange")
    fun exchangeToken(): Call<ExchangeResponse>

    @GET("/account/api/oauth/verify")
    fun verifyToken(@Query("includePerms") includePerms: Boolean?): Call<VerifyResponse>

    @DELETE("/account/api/oauth/sessions/kill/{accessToken}")
    fun killAuthSession(@Path("accessToken") accessToken: String): Call<Void>

    /**
     * @param killType OTHERS, ALL_ACCOUNT_CLIENT, OTHERS_ACCOUNT_CLIENT, OTHERS_ACCOUNT_CLIENT_SERVICE
     */
    @FormUrlEncoded
    @DELETE("/account/api/oauth/sessions/kill")
    fun killAuthSessions(@Field("killType") killType: String): Call<Void>

    @GET("/account/api/public/account")
    fun queryUserInfo(@Query("accountId") ids: List<String>): Call<Array<GameProfile>>

    @GET("/account/api/public/account/{id}")
    fun queryUserInfo(@Path("id") id: String): Call<XGameProfile>

    @GET("/account/api/accounts/{id}/metadata")
    fun queryUserMetaData(@Path("id") id: String): Call<JsonObject>

    @GET("/account/api/public/account/{accountId}/deviceAuth")
    fun queryDeviceAuths(@Path("accountId") accountId: String): Call<Array<DeviceAuth>>

    @GET("/account/api/public/account/{accountId}/deviceAuth/{deviceId}")
    fun queryDeviceAuths(@Path("accountId") accountId: String, @Path("deviceId") deviceId: String): Call<DeviceAuth>

    @POST("/account/api/public/account/{accountId}/deviceAuth")
    fun createDeviceAuth(@Path("accountId") accountId: String, @Header("X-Epic-Device-Info") deviceInfo: String): Call<DeviceAuth>

    @DELETE("/account/api/public/account/{accountId}/deviceAuth/{deviceId}")
    fun deleteDeviceAuth(@Path("accountId") accountId: String, @Path("deviceId") deviceId: String): Call<Void>

    @GET("/account/api/public/account/{id}/externalAuths")
    fun queryExternalAccounts(@Path("id") id: String): Call<Array<ExternalAuth>>

    @GET("/account/api/public/account/{id}/externalAuths/{type}")
    fun queryExternalAccountsByType(@Path("id") id: String, @Path("type") type: String): Call<ExternalAuth>

    //	TODO @POST("/account/api/public/account/{id}/externalAuths")
    //	JsonObject: authType, externalAuthToken
    //	Call<ExternalAuth[]> addExternalAccount(@Path("id") String id, @Body AddExternalAccountPayload payload);

    @DELETE("/account/api/public/account/{id}/externalAuths/{type}")
    fun removeExternalAccount(@Path("id") id: String, @Path("type") type: String): Call<Void>

    @GET("/account/api/public/account/displayName/{name}")
    fun queryUserIdFromDisplayName(@Path("name") name: String): Call<GameProfile>

    @GET("/account/api/public/account/email/{email}")
    fun queryUserIdFromEmail(@Path("email") email: String): Call<GameProfile>

    @POST("/account/api/public/account/lookup/externalId")
    fun queryExternalIdMappingsById(@Body payload: QueryExternalIdMappingsByIdPayload): Call<Map<String, ExternalAuth>>

    @GET("/account/api/public/account/lookup/externalAuth/{externalAuthType}/displayName/{displayName}")
    fun queryExternalIdMappingsByDisplayName(
        @Path("externalAuthType") externalAuthType: String, @Path("displayName") displayName: String, @Query(
            "caseInsensitive"
        ) caseInsensitive: Boolean?
    ): Call<Array<GameProfile>>

    @GET("/account/api/epicdomains/ssodomains")
    fun querySSODomains(): Call<Array<String>>
}