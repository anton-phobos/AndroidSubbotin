package com.example.androidsubbotin

import android.graphics.Color
import org.json.JSONObject
import java.util.Date
import java.util.UUID

val TodoItem.json: JSONObject
    get() = JSONObject().apply {
        put("uid", uid)
        put("text", text)
        if (importance != Importance.NORMAL) put("importance", importance.name)
        if (color != Color.WHITE) put("color", color)
        deadline?.let { put("deadline", it.time) } // миллисекунды
        put("isDone", isDone)
    }

fun TodoItem.Companion.parse(json: JSONObject): TodoItem? {
    return try {
        if (!json.has("text")) return null
        val importance = Importance.values()
            .firstOrNull { it.name == json.optString("importance") }
            ?: Importance.NORMAL
        TodoItem(
            text = json.getString("text"),
            importance = importance,
            uid = json.optString("uid").ifEmpty { UUID.randomUUID().toString() },
            color = json.optInt("color", Color.WHITE),
            deadline = if (json.has("deadline")) Date(json.getLong("deadline")) else null,
            isDone = json.optBoolean("isDone", false)
        )
    } catch (e: Exception) {
        null
    }
}
