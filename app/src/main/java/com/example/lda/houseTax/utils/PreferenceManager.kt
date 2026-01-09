package com.example.lda.houseTax.utils



import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(context: Context) {

    companion object {
        private const val PREF_NAME = "property_pref"
        private const val KEY_PROPERTY_ID = "key_property_id"
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
}
