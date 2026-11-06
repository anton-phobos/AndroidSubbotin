package com.example.androidsubbotin

import org.json.JSONArray
import java.io.File


class FileStorage(private val file: File) {

    private val _items = mutableListOf<TodoItem>()
    val items: List<TodoItem> get() = _items.toList() // только чтение


    fun add(item: TodoItem) {
        val index = _items.indexOfFirst { it.uid == item.uid }
        if (index >= 0) _items[index] = item else _items.add(item)
    }


    fun remove(uid: String) {
        _items.removeAll { it.uid == uid }
    }

    fun save() {
        val array = JSONArray()
        _items.forEach { array.put(it.json) }
        file.writeText(array.toString())
    }

    fun load() {
        _items.clear()
        if (!file.exists()) return
        val array = try {
            JSONArray(file.readText())
        } catch (e: Exception) {
            return
        }
        for (i in 0 until array.length()) {
            val item = array.optJSONObject(i)?.let { TodoItem.parse(it) }
            if (item != null) _items.add(item)
        }
    }
}
