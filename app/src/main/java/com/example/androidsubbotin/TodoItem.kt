package com.example.androidsubbotin

import android.graphics.Color
import java.util.Date
import java.util.UUID

enum class Importance { LOW, NORMAL, HIGH }

data class TodoItem(
    val text: String,
    val importance: Importance,
    val uid: String = UUID.randomUUID().toString(),
    val color: Int = Color.WHITE,
    val deadline: Date? = null,
    val isDone: Boolean = false
) {
    companion object
}
