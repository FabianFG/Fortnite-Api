package me.fungames.fortnite.api.model

import me.fungames.fortnite.api.Utils
import retrofit2.Response
import java.io.IOException


open class EpicError(
    var errorCode: String, var errorMessage: String, var messageVars: Array<String>, var numericErrorCode: Int?,
    var originatingService: String, var intent: String
) {

    val displayText: String
        get() = if (errorMessage.isEmpty()) errorCode else errorMessage

    companion object {

        fun parse(response: Response<*>): EpicError {
            return parse(response, EpicError::class.java)
        }

        fun <T : EpicError> parse(response: Response<*>, toErrorClass: Class<T>): T {
            try {
                return parse(response.errorBody()!!.string(), toErrorClass)
            } catch (e: IOException) {
                throw RuntimeException("Unexpected error whilst parsing error data", e)
            }

        }

        fun <T : EpicError> parse(s: String, toErrorClass: Class<T>): T {
            return Utils.DEFAULT_GSON.fromJson(s, toErrorClass)
        }
    }
}




