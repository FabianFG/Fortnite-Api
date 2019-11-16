package me.fungames.fortnite.api.network.services

import com.google.gson.JsonElement
import retrofit2.http.GET
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Path
import retrofit2.http.Query


interface CatalogPublicService {

    companion object {
        const val BASE_URL = "https://catalog-public-service-prod06.ol.epicgames.com"
    }

    // paging, elements ???
    @GET("/catalog/api/shared/categories")
    fun queryCategories(): Call<JsonElement>

    @GET("/catalog/api/shared/currencies")
    fun queryCurrencies(): Call<JsonElement>

    // returnItemDetails, QueryItemsByFilter, QueryOffersById
    @GET("/catalog/api/shared/namespace/{namespace}/offers")
    fun queryOffers(@Path("namespace") namespace: String): Call<JsonElement>

    // items, category, status, sortBy, sortDir
    @GET("/catalog/api/shared/namespace/{namespace}/items")
    fun queryItems(@Path("namespace") namespace: String): Call<JsonElement>

    @GET("/catalog/api/shared/bulk/items")
    fun queryItemsBulk(): Call<JsonElement>

    @GET("/catalog/api/shared/bulk/offers")
    fun queryOffersBulk(@Query("id") ids: List<String>): Call<Map<String, JsonObject>>

    @GET("/catalog/api/shared/namespace/{namespace}/bulk/items")
    fun queryItemsBulkNamespace(@Path("namespace") namespace: String): Call<JsonElement>

    @GET("/catalog/api/shared/namespace/{namespace}/bulk/offers")
    fun queryOffersBulkNamespace(@Path("namespace") namespace: String): Call<JsonElement>
}