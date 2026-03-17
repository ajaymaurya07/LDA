package com.example.lda.houseTax.utils



import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(context: Context) {

    companion object {
        private const val PREF_NAME = "property_pref"
        private const val KEY_PROPERTY_ID = "key_property_id"
        private const val KEY_MOBILE_TXN_ID = "key_mobile_transaction_id"
        private const val KEY_ULB_ID = "key_ulb_id"
        private const val ARV_VALUE = "arv_value"
        private const val USER_ID = "user_id"
        private const val IS_LOGIN = "is_login"
        private const val LOGIN_MOBILE_NUMBER = "login_mobile_number"
        private const val KEY_EMAIL = "key_email"
        private const val KEY_USER_TYPE = "key_user_type"
    }

    private val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)


    fun savePropertyId(propertyId: String) {
        prefs.edit()
            .putString(KEY_PROPERTY_ID, propertyId)
            .apply()
    }


    fun getPropertyId(): String? {
        return prefs.getString(KEY_PROPERTY_ID, null)
    }


    fun clearPropertyId() {
        prefs.edit()
            .remove(KEY_PROPERTY_ID)
            .apply()
    }



    fun saveMobileTransactionId(transactionId: String) {
        prefs.edit()
            .putString(KEY_MOBILE_TXN_ID, transactionId)
            .apply()
    }
    fun getMobileTransactionId(): String? {
        return prefs.getString(KEY_MOBILE_TXN_ID, null)
    }
    fun clearMobileTransactionId() {
        prefs.edit()
            .remove(KEY_MOBILE_TXN_ID)
            .apply()
    }


    fun saveUlbId(ulbId: String) {
        prefs.edit()
            .putString(KEY_ULB_ID, ulbId)
            .apply()
    }

    fun getUlbId(): String? {
        return prefs.getString(KEY_ULB_ID, null)
    }


    fun saveArvValue(arvValue: String) {
        prefs.edit()
            .putString(ARV_VALUE, arvValue)
            .apply()
    }

    fun getArvValue(): String? {
        return prefs.getString(ARV_VALUE, null)
    }


    fun saveUserId(userId: String) {
        prefs.edit()
            .putString(USER_ID, userId)
            .apply()
    }

    fun getUserId(): String? {
        return prefs.getString(USER_ID, null)
    }
    fun clearUserId() {
        prefs.edit().remove(USER_ID).apply()
    }


    fun login(loginFlag: Boolean) {
        prefs.edit()
            .putBoolean(IS_LOGIN, loginFlag)
            .apply()
    }

    fun isLogin(): Boolean {
        return prefs.getBoolean(IS_LOGIN, false)
    }


    fun saveLoginMobileNumber(loginMobileNumber: String) {
        prefs.edit()
            .putString(LOGIN_MOBILE_NUMBER, loginMobileNumber)
            .apply()
    }

    fun getLoginMobileNumber(): String? {
        return prefs.getString(LOGIN_MOBILE_NUMBER, null)
    }

    fun saveEmail(email: String) {
        prefs.edit().putString(KEY_EMAIL, email).apply()
    }

    fun getEmail(): String? {
        return prefs.getString(KEY_EMAIL, null)
    }

    fun saveUserType(userType: String) {
        prefs.edit().putString(KEY_USER_TYPE, userType).apply()
    }

    fun getUserType(): String? {
        return prefs.getString(KEY_USER_TYPE, null)
    }

}
