package zhora

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import zhora.plugins.*

fun main() {
    try {
        embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
            .start(wait = true)
    } catch (e: Throwable) {
        return
    }
}

fun Application.module() {
    configureRouting()
}
