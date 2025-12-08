package com.example.lda.eCourtUi.utils


import android.content.Context
import android.content.SharedPreferences

object SharedPrefHelper {

    private const val PREF_NAME = "MyAppPrefs"
    private const val KEY_USER_ID = "USER_ID"
    private const val KEY_PASSWORD = "PASSWORD"
    private const val USER_TYPE = "USER_TYPE"
    private const val DEPARTMENT = "DEPARTMENT"
    private const val USERNAME = "USER_NAME"
    private const val MOBILE_NUMBER = "MOBILE_NUMBER"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }


    fun saveUserCredentials(context: Context, userId: String, password: String,userType:String,department:String,userName:String,mobileNumber:String) {
        getPreferences(context).edit()
            .putString(KEY_USER_ID, userId)
            .putString(KEY_PASSWORD, password)
            .putString(USER_TYPE, userType)
            .putString(DEPARTMENT, department)
            .putString(USERNAME, userName)
            .putString(MOBILE_NUMBER, mobileNumber)
            .apply()
    }

    fun getUserId(context: Context): String? {
        return getPreferences(context).getString(KEY_USER_ID, null)
    }

    fun getUserType(context: Context): String? {
        return getPreferences(context).getString(USER_TYPE, null)
    }
    fun getDepartment(context: Context): String? {
        return getPreferences(context).getString(DEPARTMENT, null)
    }
    fun getUserName(context: Context): String? {
        return getPreferences(context).getString(USERNAME, null)
    }
    fun getMobileNumber(context: Context): String? {
        return getPreferences(context).getString(MOBILE_NUMBER, null)
    }

    fun clearAll(context: Context) {
        getPreferences(context).edit().clear().apply()
    }
}
