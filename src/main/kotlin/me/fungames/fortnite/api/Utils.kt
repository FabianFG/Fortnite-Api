package me.fungames.fortnite.api

import java.io.File
import com.google.gson.Gson
import com.google.gson.GsonBuilder


object Utils {
    val DEFAULT_GSON = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create()

    val CLIENT_LAUNCHER_TOKEN =
        "MzQ0NmNkNzI2OTRjNGE0NDg1ZDgxYjc3YWRiYjIxNDE6OTIwOWQ0YTVlMjVhNDU3ZmI5YjA3NDg5ZDMxM2I0MWE"
    val FORTNITE_CLIENT_TOKEN =
        "ZWM2ODRiOGM2ODdmNDc5ZmFkZWEzY2IyYWQ4M2Y1YzY6ZTFmMzFjMjExZjI4NDEzMTg2MjYyZDM3YTEzZmM4NGQ"
    val KAIROS_WEB_TOKEN =
        "NWI2ODU2NTNiOTkwNGMxZDkyNDk1ZWU4ODU5ZGNiMDA6N1EybWNtbmV5dXZQbW9SWWZ3TTdnZkVyQTZpVWpoWHI"

    val cacheDir: String
        get() = cacheDirFile.absolutePath

    val cacheDirFile: File
        get() {
            val tempDir = File(System.getProperty("java.io.tmpdir") + "/Fortnite-Api/Http/Cache")
            tempDir.mkdirs()
            return tempDir
        }
}