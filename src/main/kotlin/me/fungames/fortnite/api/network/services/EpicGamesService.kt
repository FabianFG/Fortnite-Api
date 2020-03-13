package me.fungames.fortnite.api.network.services

import com.google.gson.JsonObject
import me.fungames.fortnite.api.model.EpicExchangeCode
import retrofit2.Call
import retrofit2.http.*

interface EpicGamesService {
    companion object {
        const val BASE_URL = "https://www.epicgames.com"
    }

    @GET("/id/api/reputation")
    fun captcha(): Call<JsonObject>

    @GET("/id/api/csrf")
    fun csrfToken(): Call<Void>

    @FormUrlEncoded
    @POST("/id/api/login")
    fun login(@FieldMap fields: Map<String, String>, @Header("X-XSRF-TOKEN") xsfrToken : String) : Call<Void>

    @GET("/id/api/exchange")
    fun exchange() : Call<EpicExchangeCode>
}