package me.fungames.fortnite.api.network.services

import com.google.gson.JsonObject
import me.fungames.fortnite.api.model.BuildResponse
import me.fungames.fortnite.api.model.ClientDetails
import me.fungames.fortnite.api.model.ManifestInfoResponse
import me.fungames.fortnite.api.model.PackageInstallerDetails
import retrofit2.Call
import retrofit2.http.*

interface LauncherAdminService {
    companion object {
        const val BASE_URL = "https://launcher-admin-service-prod.ol.epicgames.net"
    }

    @GET("/launcher/api/public/assets/{platform}/{assetId}/{assetName}")
    fun getManifest(@Path("platform") platform : String, @Path("assetId") assetId : String, @Path("assetName") assetName : String, @Query("label") label : String) : Call<ManifestInfoResponse>

    @POST("launcher/api/admin/assets/v2/platform/{platform}/catalogItem/{catalogItemId}/app/{appName}/label/{label}")
    fun getItemBuild(@Path("platform") platform : String, @Path("catalogItemId") catalogItemId : String, @Path("appName") appName : String, @Path("label") label : String, @Body clientDetails : ClientDetails) : Call<BuildResponse>

    @POST("launcher/api/admin/assets/v2/platform/{platform}/catalogItem/{catalogItemId}/label/{label}")
    fun getItemBuilds(@Path("platform") platform: String, @Path("catalogItemId") catalogItemId: String, @Path("label") label: String, @Body clientDetails: ClientDetails): Call<JsonObject>

    @POST("launcher/api/admin/assets/v2/platform/{platform}/launcher/label/{label}")
    fun getLauncherBuild(@Path("platform") platform: String, @Path("label") label : String, @Body body : ClientDetails) : Call<BuildResponse>

    @POST("launcher/api/admin/assets/v2/platform/{platform}/catalogItem/{catalogItemId}/app/{appName}/label/{label}/compatibility")
    fun isAppDeviceCompatible(@Path("platform") platform: String, @Path("catalogItemId") catalogItemId: String, @Path("appName") appName: String, @Path("label") label: String, @Body clientDetails: ClientDetails) : Call<Void>
}