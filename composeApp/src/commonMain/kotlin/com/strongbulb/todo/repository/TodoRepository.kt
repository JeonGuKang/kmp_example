package com.strongbulb.todo.repository

import com.russhwolf.settings.Settings
import com.strongbulb.todo.model.TodoItem
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class TodoRepository(private val settings: Settings) {

    private val json = Json {
        prettyPrint = true
        isLenient = true
    }
    private val todoListKey = "todo_list"

    fun saveTodoList(todoList: List<TodoItem>) {
        val jsonString = json.encodeToString(todoList)
        settings.putString(todoListKey, jsonString)
    }

    fun loadTodoList(): List<TodoItem> {
        val jsonString = settings.getString(todoListKey, "[]")
        return try {
            json.decodeFromString<List<TodoItem>>(jsonString)
        } catch (e: Exception) {
            emptyList()
        }
    }
}
