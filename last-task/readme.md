# Server specification
Endpoint for adding new user:

POST `localhost:8080/login?login=<login-of-new-user>&password=<password-for-new-user>`

Endpoint to make transaction for sending money

POST `http://localhost:8080/sendMoney?login=<login-of-user-from-we-send-money>&to=<login-to-whom-we-send-money>&amount=<amount-of-money-to-send>`

Endpoint to verify the transaction

POST `http://localhost:8080/verify?hash=<hash-of-class>&code=<code-for-transaction>`

[Routings на сервере](src/main/kotlin/zhora/plugins/Routing.kt)

# Как работает защита

- На каждом запросе проверяется наличие пользователя от невалидный параметров

  - На запрос по добавлению транзакции пользователю присылается `salt`, который используется при хешировании - [hashGenerator](src/main/kotlin/chipher/HashGenerator.kt)

    - При `/verify` пользователь скидывает `hash` и `code`, дальше сервер проверяет код и хеш
    [server-service](src/main/kotlin/service/UserService.kt)
      - Сам хеш достается classLoader'ом (в hashGenerator), поэтому если классы будут отличаться на сервере и на клиенте, то hash (в теории) будет разным, из-за этого транзакция не прошла.
          ```kotlin
        class HashGenerator {
          private val className = "client.service.ClientService"
          fun getHash(salt: String): String {
          synchronized(this) {
          val classLoader = ClassLoader.getSystemClassLoader()
          val clazz = classLoader.loadClass(className)
          val bytecode = clazz.classLoader.getResourceAsStream(className.replace('.', '/') + ".class")?.readBytes()

                 val md = MessageDigest.getInstance("SHA-256")
                 md.update(bytecode)
                 md.update(salt.toByteArray())
                 val digest = md.digest()


                 return digest.joinToString(""){ String.format("%02x", it)}
            }
        }

            fun checkHash(hash: String, salt: String): Boolean {
                return hash == getHash(salt)
            }
        }
        ```

# Запуск
[Код сервера](src/main/kotlin/zhora/Application.kt)

[Код клиента](src/main/kotlin/client/Client.kt)