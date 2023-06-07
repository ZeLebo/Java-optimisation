package chipher

import java.io.File
import java.security.MessageDigest

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


            /*
            //read src/main/kotlin/client/service/ClientService.kt file and hash it
            val md = MessageDigest.getInstance("SHA-256")
            val file = File("src/main/kotlin/client/service/ClientService.kt")
            val bytes = file.readBytes()
            md.update(bytes)
            md.update(salt.toByteArray())
            val digest = md.digest()
            return digest.joinToString(""){ String.format("%02x", it)}
             */
        }
    }

    fun checkHash(hash: String, salt: String): Boolean {
        return hash == getHash(salt)
    }
}

fun main() {
    print(HashGenerator().getHash("jyFXRl"))
}