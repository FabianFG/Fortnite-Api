package me.fungames.fortnite.api.network.services

import me.fungames.fortnite.api.model.Affiliate
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface AffiliatePublicService {

    companion object {
        const val BASE_URL = "https://affiliate-public-service-prod.ol.epicgames.com"
    }

    /**
     * Gets information of a Creator Code. Returns 404 if not found. Requires an access token produced by <tt>grant_type=client_credentials</tt>.
     *
     * @param affiliateName the Creator Code to query
     */
    @GET("/affiliate/api/public/affiliates/slug/{affiliatename}")
    fun checkAffiliateName(@Path("affiliatename") affiliateName: String): Call<Affiliate>
}
