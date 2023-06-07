package client

import client.service.ClientService

class Client {
    private val clientService = ClientService()
    private var login = ""
    private var variant = 0

    fun start() {
        while (true) {
            println("""
                select the variant:
                1. Login
                2. Send money
                3. Verify sending money
                4. Exit
            """.trimIndent())

            variant= readln().toInt()

            when (variant) {
                1 -> readLogin()
                2 -> sendMoney()
                3 -> verify()
                4 -> {
                    clientService.close()
                    Thread.sleep(100)
                    return
                }
            }
        }
    }

    private fun readLogin() {
        println("Give me the login:")
        var input = readln()
        if (input.isNotEmpty()) {
            login = input
        }
        println("Give me the password:")
        input = readln()
        while(input.isEmpty()) {
            println("The password cannot be empty\nPassword: ")
            input = readln()
        }
        val password = input

        try {
            clientService.login(login, password)
        } catch (e: Exception) {
            println("Cannot proceed the request due to ${e.message}")
        }
        println("new user with login:$login has been created")
    }

    private fun sendMoney() {
        if (login.isEmpty()) {
            println("Need to specify the user first")
            return
        }

        println("To whom you wanna send the money?")
        val to = readln()

        println("How much do you wanna send?")
        val amount = readln().toLong()

        try {
            clientService.sendMoney(login, to, amount)
        } catch (e: Exception) {
            println("Cannot proceed the operation due to ${e.message}")
            return
        }
        println("In order to finish sending money verify the transaction")
    }

    private fun verify() {
        if (login.isEmpty()) {
            println("Need to specify the user first")
            return
        }
        println("Give me the verification code:")
        val code = readln().toInt()
        try {
            clientService.verify(login, code)
        } catch (e: Exception) {
            println("Cannot proceed the operation due to $e")
            return
        }
        println("Money has been sent")
    }
}

fun main() {
    Client().start()
    println("Thanks for using me")
}