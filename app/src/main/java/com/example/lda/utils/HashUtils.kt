package com.example.lda.utils

import java.security.MessageDigest

object HashUtils {
    fun sha512(input: String): String {
        val bytes = input.toByteArray()
        val md = MessageDigest.getInstance("SHA-512")
        val digest = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }
}