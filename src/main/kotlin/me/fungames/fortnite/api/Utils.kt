package me.fungames.fortnite.api

import java.io.File
import com.google.gson.Gson


object Utils {
    val DEFAULT_GSON = Gson()

    val CLIENT_LAUNCHER_TOKEN =
        "MzQ0NmNkNzI2OTRjNGE0NDg1ZDgxYjc3YWRiYjIxNDE6OTIwOWQ0YTVlMjVhNDU3ZmI5YjA3NDg5ZDMxM2I0MWE"
    val FORTNITE_CLIENT_TOKEN =
        "ZWM2ODRiOGM2ODdmNDc5ZmFkZWEzY2IyYWQ4M2Y1YzY6ZTFmMzFjMjExZjI4NDEzMTg2MjYyZDM3YTEzZmM4NGQ"

    val cacheDir: String
        get() = cacheDirFile.absolutePath

    val cacheDirFile: File
        get() {
            val tempDir = File(System.getProperty("java.io.tmpdir") + "/Fortnite-Api/Http/Cache")
            tempDir.mkdirs()
            return tempDir
        }
}