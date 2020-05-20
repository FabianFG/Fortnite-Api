package me.fungames.fortnite.api

import com.google.gson.GsonBuilder
import java.io.File


object Utils {
    val DEFAULT_GSON = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create()

    val cacheDir: String
        get() = cacheDirFile.absolutePath

    val cacheDirFile: File
        get() {
            val tempDir = File(System.getProperty("java.io.tmpdir") + "/Fortnite-Api/Http/Cache")
            tempDir.mkdirs()
            return tempDir
        }
}