package me.fungames.fortnite.api.network

import me.fungames.fortnite.api.FortniteApi
import me.fungames.fortnite.api.events.LoggedOutEvent
import okhttp3.Interceptor
import okhttp3.Response
import java.net.HttpURLConnection


class DefaultInterceptor(private val api: FortniteApi) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        if (api.isLoggedIn)
            builder.addHeader("Authorization", api.accountTokenType + " " + api.accountToken)
        builder.addHeader("Accept-Language", api.language)

        val response = chain.proceed(builder.build())

        if (response.code == HttpURLConnection.HTTP_UNAUTHORIZED)
            api.fireEvent(LoggedOutEvent())
        return response
    }

}