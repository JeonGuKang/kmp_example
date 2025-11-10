package com.strongbulb.todo

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform