package client.service

import  chipher.HashGenerator
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import top.jfunc.json.impl.JSONObject

class ClientService {
    private var salt = ""
    private val client = OkHttpClient()

    fun close() {
        client.dispatcher.executorService.shutdown()
        client.connectionPool.evictAll()
        client.cache?.close()
        client.dispatcher.cancelAll()
        client.dispatcher.executorService.shutdownNow()
    }

    fun makeRequest(path: String): Response {
        val body = FormBody.Builder().build()
        val request = Request.Builder()
            .url(path)
            .post(body)
            .build()

        return client.newCall(request).execute()
    }

    fun login(login: String, password: String) {
        val response = makeRequest("http://localhost:8080/login?login=${login}&password=${password}")
        if (response.code != 200) {
            throw RuntimeException(response.body.toString())
        }
    }

    fun sendMoney(login: String, to: String, amount: Long) {
        val response = makeRequest("http://localhost:8080/sendMoney?login=${login}&to=${to}&amount=${amount}")
        if (response.code != 200) {
            throw RuntimeException(response.message.toString())
        }
        try {
            // extract field salt from response
            val jsonString = response.body?.string().orEmpty()
            val jsonObject = JSONObject(jsonString)
            this.salt = jsonObject["salt"].toString()
            if (this.salt.isEmpty()) {
                throw RuntimeException("Cannot extract salt")
            }
        } catch (e: Exception) {
            throw RuntimeException(e.message.toString())
        }
    }

    fun verify(login: String, code: Int) {
        val hash = HashGenerator().getHash(salt)
        val response = makeRequest("http://localhost:8080/verify?login=${login}&hash=${hash}&code=${code}")
        if (response.code != 200) {
            throw RuntimeException(response.body.toString())
        }
    }
}
