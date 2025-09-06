package com.example.tetrisgrid.lib

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class FileStorage(val filesDir: File) {
    inline fun <reified T> save(data: T, fileName: String) {
        File(filesDir, fileName).writeText(Json.Default.encodeToString(data))
    }

    inline fun <reified T> load(fileName: String): T? {
        val file = File(filesDir, fileName)
        return if (file.exists()) {
            Json.Default.decodeFromString(file.readText()) as T
        } else {
            null
        }
    }
}