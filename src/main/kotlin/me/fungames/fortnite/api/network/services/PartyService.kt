package me.fungames.fortnite.api.network.services

import com.google.gson.JsonElement
import me.fungames.fortnite.api.model.PartyInvite
import retrofit2.Call
import retrofit2.http.*


interface PartyService {
    companion object {
        const val BASE_URL = "https://party-service-prod.ol.epicgames.com"
    }

    @POST("/party/api/v1/{namespace}/parties")
    fun createParty(@Path("namespace") namespace: String, @Body payload: JsonElement): Call<JsonElement>

    @GET("/party/api/v1/{namespace}/parties/{partyId}")
    fun getParty(@Path("namespace") namespace: String, @Path("partyId") partyId: String): Call<JsonElement>

    @PATCH("/party/api/v1/{namespace}/parties/{partyId}")
    fun updateParty(@Path("namespace") namespace: String, @Path("partyId") partyId: String, @Body payload: JsonElement): Call<JsonElement>

    @PATCH("/party/api/v1/{namespace}/parties/{partyId}")
    fun deleteParty(@Path("namespace") namespace: String, @Path("partyId") partyId: String): Call<Void>

    @POST("/party/api/v1/{namespace}/parties/{partyId}/invites/{accountId}")
    fun sendInvite(@Path("namespace") namespace: String, @Path("partyId") partyId: String, @Path("accountId") accountId: String): Call<JsonElement>

    @DELETE("/party/api/v1/{namespace}/parties/{partyId}/invites/{accountId}")
    fun cancelInvite(@Path("namespace") namespace: String, @Path("partyId") partyId: String, @Path("accountId") accountId: String): Call<Void>

    //{"party_id":"b939c70996ee467a9676b5fd1ff5935a","sent_by":"bd7021824e39499d8be7936cf1e1189d","meta":{"urn:epic:conn:type_s":"game","urn:epic:member:dn_s":"amrsatrio","urn:epic:conn:platform_s":"IOS","urn:epic:cfg:build-id_s":"1:1:6663274","urn:epic:invite:platformdata_s":""},"sent_to":"17e251bfdd6d439882f1948efe609c2f","sent_at":"2019-07-02T09:51:22.085Z","updated_at":"2019-07-02T09:52:17.872Z","expires_at":"2019-07-02T13:52:17.872Z","status":"DECLINED"}
    @POST("/party/api/v1/{namespace}/parties/{partyId}/invites/{accountId}/decline")
    fun declineInvite(@Path("namespace") namespace: String, @Path("partyId") partyId: String, @Path("accountId") accountId: String): Call<PartyInvite>

    @DELETE("/party/api/v1/{namespace}/parties/{partyId}/members/{accountId}")
    fun deleteMember(@Path("namespace") namespace: String, @Path("partyId") partyId: String, @Path("accountId") accountId: String): Call<Void>

    @POST("/party/api/v1/{namespace}/parties/{partyId}/members/{accountId}/conferences/connection")
    fun generateConferenceConnection(@Path("namespace") namespace: String, @Path("partyId") partyId: String, @Path("accountId") accountId: String): Call<JsonElement>

    @POST("/party/api/v1/{namespace}/parties/{partyId}/members/{accountId}/confirm")
    fun confirmMembership(@Path("namespace") namespace: String, @Path("partyId") partyId: String, @Path("accountId") accountId: String): Call<JsonElement>

    @POST("/party/api/v1/{namespace}/parties/{partyId}/members/{accountId}/join")
    fun joinParty(@Path("namespace") namespace: String, @Path("partyId") partyId: String, @Path("accountId") accountId: String): Call<JsonElement>

    @PATCH("/party/api/v1/{namespace}/parties/{partyId}/members/{accountId}/meta")
    fun updateMemberState(@Path("namespace") namespace: String, @Path("partyId") partyId: String, @Path("accountId") accountId: String, @Body payload: JsonElement): Call<JsonElement>

    @POST("/party/api/v1/{namespace}/parties/{partyId}/members/{accountId}/promote")
    fun transferLeadershipToMember(@Path("namespace") namespace: String, @Path("partyId") partyId: String, @Path("accountId") accountId: String): Call<JsonElement>

    @POST("/party/api/v1/{namespace}/parties/{partyId}/members/{accountId}/reject")
    fun rejectApplicant(@Path("namespace") namespace: String, @Path("partyId") partyId: String, @Path("accountId") accountId: String): Call<JsonElement>

    @GET("/party/api/v1/{namespace}/user/{accountId}")
    fun getUserSummary(@Path("namespace") namespace: String, @Path("accountId") accountId: String): Call<JsonElement>

    @POST("/party/api/v1/{namespace}/user/{accountId}/pings/{fromAccountId}")
    fun createPing(@Path("namespace") namespace: String, @Path("accountId") accountId: String, @Path("fromAccountId") fromAccountId: String): Call<JsonElement>

    @DELETE("/party/api/v1/{namespace}/user/{accountId}/pings/{fromAccountId}")
    fun deletePing(@Path("namespace") namespace: String, @Path("accountId") accountId: String, @Path("fromAccountId") fromAccountId: String): Call<JsonElement>

    @POST("/party/api/v1/{namespace}/user/{accountId}/pings/{fromAccountId}/join")
    fun joinPartyFromPing(@Path("namespace") namespace: String, @Path("accountId") accountId: String, @Path("fromAccountId") fromAccountId: String): Call<JsonElement>
}