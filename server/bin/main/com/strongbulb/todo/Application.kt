package com.strongbulb.todo

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.WebSockets
import io.ktor.server.websocket.webSocket
import io.ktor.websocket.DefaultWebSocketSession
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.receiveAsFlow

fun main() {
    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(WebSockets)

    routing {
        get("/") {
            call.respondText("Ktor: ${Greeting().greet()}")
        }
        get("/hello") {
            call.respondText("Hello, World!")
        }

        val clients = mutableListOf<DefaultWebSocketSession>()

        webSocket("/chat") {
            clients.add(this)
            incoming.receiveAsFlow().onCompletion {
                clients.remove(this@webSocket)
            }.collect {received ->
                when(received) {
                    is Frame.Text -> {
                        clients.forEach { client ->
                            val receivedText = received.readText()
                            val sendFrame = Frame.Text("You said: $receivedText")

                            client.send(sendFrame)
                        }
                    }
                    else -> {
                        println("Unsupported frame type: ${received.frameType}")
                    }
                }
            }
        }
    }
}