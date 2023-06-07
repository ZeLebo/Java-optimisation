package zhora.plugins

import dto.SendMoneyDto
import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import service.UserService

fun Application.configureRouting() {
    val userService = UserService()
    routing {
        get("/test") {
            call.respondText("Hello World!")
        }
        post("/login") {
            val login = call.parameters["login"]
            val password = call.parameters["password"]

            if (login.isNullOrEmpty()) {
                call.respond(HttpStatusCode.BadRequest, "No login provided")
                return@post
            }
            if (password.isNullOrEmpty()) {
                call.respond(HttpStatusCode.BadRequest, "No password provided")
                return@post
            }

            try {
                userService.login(login, password)
                call.respond(HttpStatusCode.OK, "User has been logged")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, e.message.toString())
            }
        }
        post("/sendMoney") {
            val login = call.parameters["login"]
            val to = call.parameters["to"]
            val amount = call.parameters["amount"]

            if (login.isNullOrEmpty()) {
                call.respond(HttpStatusCode.BadRequest, "No login provided")
                return@post
            }

            if (to.isNullOrEmpty()) {
                call.respond(HttpStatusCode.BadRequest, "No nickname to whom send the money provided")
                return@post
            }

            if (amount.isNullOrEmpty()) {
                call.respond(HttpStatusCode.BadRequest, "No amount of money provided")
                return@post
            }

            var quantity: Long = 0

            try {
                quantity = amount.toLong()
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Need to provide value in number format")
                return@post
            }

            try {
                val salt = userService.sendMoney(login, to, quantity)
                call.respond(HttpStatusCode.OK, Json.encodeToString(SendMoneyDto(salt)))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, e.message.toString())
            }

        }
        post("/verify") {
            val login = call.parameters["login"]
            val userHash = call.parameters["hash"]
            val code = call.parameters["code"]

            if (login.isNullOrEmpty()) {
                call.respond(HttpStatusCode.BadRequest, "No login provided")
                return@post
            }

            if (userHash.isNullOrEmpty()) {
                call.respond(HttpStatusCode.BadRequest, "No hash provided")
                return@post
            }

            if (code.isNullOrEmpty()) {
                call.respond(HttpStatusCode.BadRequest, "No code provided")
                return@post
            }

            try {
                code.toInt()
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "code is numeric")
                return@post
            }

            try {
                userService.verify(login, userHash, code.toInt())
                call.respond(HttpStatusCode.OK, "The money has been sent")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, e.message.toString())
            }
        }
    }
}
