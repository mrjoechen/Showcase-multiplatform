@file:OptIn(ExperimentalEncodingApi::class, ExperimentalEncodingApi::class, ExperimentalEncodingApi::class, ExperimentalEncodingApi::class)

import java.nio.charset.Charset
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

object AESCrypt {
    private const val ENCRYPTION_METHOD = "AES/CBC/PKCS5PADDING"
    private const val CHARSET_NAME = "UTF-8"
    private const val KEY_ALGORITHM = "AES"

    fun encrypt(password: ByteArray, data: String, iv: ByteArray): String {
        val secretKeySpec = SecretKeySpec(password, KEY_ALGORITHM)
        val cipher = Cipher.getInstance(ENCRYPTION_METHOD)
        val ivParameterSpec = IvParameterSpec(iv)
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec)
        val encrypted = cipher.doFinal(data.toByteArray(Charset.forName(CHARSET_NAME)))
        return Base64.encode(encrypted, 0, encrypted.size)
    }

    fun decrypt(password: ByteArray, encryptedData: String, iv: ByteArray): String {
        val secretKeySpec = SecretKeySpec(
            password,
            KEY_ALGORITHM
        )
        val cipher = Cipher.getInstance(ENCRYPTION_METHOD)
        val ivParameterSpec = IvParameterSpec(iv)
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec)
        val decrypted = cipher.doFinal(Base64.decode(encryptedData, 0, encryptedData.length))
        return String(decrypted, Charset.forName(CHARSET_NAME))
    }

    private fun fixKeyLength(keyBytes: ByteArray): ByteArray {
        val len = keyBytes.size
        return if (len % 16 != 0) {
            val newLen = ((len / 16) + 1) * 16
            val paddedBytes = ByteArray(newLen)
            System.arraycopy(keyBytes, 0, paddedBytes, 0, len)
            paddedBytes
        } else {
            keyBytes
        }
    }

    fun generateAESKey(): ByteArray {
        val keyGenerator = KeyGenerator.getInstance("AES")
        keyGenerator.init(256)
        val secretKey = keyGenerator.generateKey()
        return secretKey.encoded
    }
}

//config name may only contain `0-9`, `A-Z`, `a-z`, `_`, `-`, `.` and space.
actual fun String.encodeName(): String {
    val source = trim().toByteArray()
    val encode = Base64.encode(source, 0, source.size)
    return encode.replace("/", "_").replace("+", "-").replace("=", ".")
}

actual fun String.decodeName(): String{
    val decode = trim().replace("_", "/").replace("-", "+").replace(".", "=")
    val bytes = Base64.decode(decode, 0, length)
    return String(bytes)
}

actual fun String.encodePass(key: String, iv: String): String {
    val source = trim().toByteArray()
    val encode = Base64.encode(source, 0, source.size)
    val replace = encode.replace("/", "_").replace("+", "-").replace("=", ".")
    return AESCrypt.encrypt(key.toByteArray(), replace, iv.toByteArray())
}

actual fun String.decodePass(key: String, iv: String): String{
    val decrypt = AESCrypt.decrypt(key.toByteArray(), this, iv.toByteArray())
    val decode = decrypt.trim().replace("_", "/").replace("-", "+").replace(".", "=")
    return String(Base64.decode(decode, 0, decode.length))
}


//fun main() {
//    val raw = "123"
//    val encrypt = AESCrypt.encrypt(TEST_KEY, raw, TEST_IV)
//    println(encrypt)
//    val decrypt = AESCrypt.decrypt(TEST_KEY, encrypt, TEST_IV)
//    println(decrypt)
//}