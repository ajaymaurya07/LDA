package com.example.lda.utils.phoneEmailValidater

import android.util.Log

object InputValidator {

    // Validate Email
    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // Validate Mobile Number (10 digit for India)
    fun isValidPhone(phone: String): Boolean {
        return phone.matches(Regex("^[6-9]\\d{9}$"))
    }
    fun isValidName(name: String): Boolean {
        return name.matches(Regex("^[A-Za-z ]{3,}$"))
    }
    fun isValidPostalAddress(address: String): Boolean {
        return address.matches(Regex("^[a-zA-Z0-9 ,./-]{10,}$"))
    }

    // Dynamic Method: Verify both
    fun validateInput(phone: String, email: String): Boolean {
        val isPhoneValid = isValidPhone(phone)
        val isEmailValid = isValidEmail(email)

        Log.d("InputValidator", "Phone: $phone -> $isPhoneValid | Email: $email -> $isEmailValid")

        return isPhoneValid && isEmailValid
    }
}
