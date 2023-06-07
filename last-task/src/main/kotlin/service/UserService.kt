package service

import chipher.HashGenerator
import entity.Transaction
import entity.User

class UserService(
    // keeps the info login -> transaction
    private var transactions: MutableMap<String, Transaction> = HashMap(),
    // keeps the info about users
    private var users: HashSet<User> = HashSet(setOf(User("login", "a"), User("login1", "a"))),
    // keeps the logins
    private var logins: MutableSet<String> = HashSet(),
    // stores salt string for specific user
    private var salts: HashMap<String, String> = HashMap(),
    private val generator: HashGenerator = HashGenerator(),
) {
    fun login(login: String, password: String) {
        val user = User(login, password)
        if (users.contains(user)) {
            throw RuntimeException("Such user already exists")
        }
        users.add(user)
        logins.add(login)
    }

    private fun generateSalt(): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..6).map {allowedChars.random()}
            .joinToString("")
    }

    fun getSalt(login: String): String {
        return salts[login] ?: ""
    }

    // todo maybe multiple transactions for one user?
    private fun addTransaction(key: String, transaction: Transaction) {
        transactions[key] = transaction
    }

    fun sendMoney(login: String, to: String, amount: Long): String {
        if (users.none { it.login == login }) {
            throw RuntimeException("User with such login does not exists")
        }
        if (users.none {it.login == to}) {
            throw RuntimeException("Cannot send money to unknown user")
        }

        val code = (0..1000).random()
        val transaction = Transaction(login=login, to=to, amount=amount, code=code)

        val salt = generateSalt()
        salts[login] = salt
        addTransaction(login, transaction)
        println("Code for ${login}'s transaction is $code")
        return salt
    }

    fun verify(login: String, hash: String, code: Int) {
        // check that login exists
        if (users.none {it.login == login}) {
            println("no such user")
            throw RuntimeException("No such login in the system")
        }

        // verify the transaction existence
        if (!transactions.contains(login)) {
            println("no transactions")
            throw RuntimeException("Nothing to verify, user has no transactions")
        }

        // check the hash
        if (!generator.checkHash(hash, salts[login]!!)) {
            println("hash is different")
            throw RuntimeException("The hash of the user is not the same as on server, YOU ARE LIAR")
        }

        val transaction: Transaction = transactions[login]!!
        println("""
            Transaction ${transaction.login} --> ${transaction.to} is done
            Sent ${transaction.amount} babok from ${transaction.login} to ${transaction.to}
        """.trimIndent())
        transactions.remove(login)
    }
}
