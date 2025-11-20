package com.strongbulb.todo.model

import kotlinx.serialization.Serializable

@Serializable
data class TodoItem(
    val id: Int,
    val text: String,
    val isDone: Boolean = false
)
