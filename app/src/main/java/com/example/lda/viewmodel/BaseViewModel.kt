package com.example.lda.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.lda.constent.Constent
import com.example.lda.houseTax.data.database.AppDatabase
import com.example.lda.houseTax.utils.PreferenceManager
import com.example.lda.model.LogoutResponse
import com.example.lda.model.RefreshTokenResponse
import com.example.lda.model.TransactionsByEmailResponse
import com.example.lda.network.RetrofitClient
import com.example.lda.utils.DeviceUtils
import com.example.lda.utils.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class BaseViewModel : ViewModel() {

    // Track number of active API calls
    protected val _loadingCount = MutableLiveData(0)
    val isLoading: LiveData<Boolean> = _loadingCount.map { it > 0 }

    fun incrementLoader() {
        _loadingCount.value = (_loadingCount.value ?: 0) + 1
    }

    fun decrementLoader() {
        val current = _loadingCount.value ?: 0
        if (current > 0) _loadingCount.value = current - 1
    }

    // Common error and session state
    protected val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage


    fun handleTokenRefresh(
        preferenceManager: PreferenceManager,
        onSuccess: () -> Unit
    ) {
        val refreshToken = preferenceManager.getRefreshToken() ?: ""
        val body = mapOf("refresh_token" to refreshToken)

        incrementLoader()

        RetrofitClient.apiCall
            .refreshToken(Constent.APP_VERSION, body)
            .enqueue(object : Callback<RefreshTokenResponse> {

                override fun onResponse(
                    call: Call<RefreshTokenResponse>,
                    response: Response<RefreshTokenResponse>
                ) {
                    decrementLoader()

                    if (response.code() == 401 || response.code() == 403) {
                        sessionExpired()
                        return
                    }

                    val resBody = response.body()

                    if (response.isSuccessful && resBody?.status == true) {
                        val newToken = resBody.data?.accessToken

                        if (!newToken.isNullOrEmpty()) {
                            preferenceManager.saveAccessToken(newToken)
                            onSuccess()
                        } else {
                            sessionExpired()
                        }
                    } else {
                        sessionExpired()
                    }
                }

                override fun onFailure(call: Call<RefreshTokenResponse>, t: Throwable) {
                    decrementLoader()
                    _errorMessage.value = "Network error: ${t.localizedMessage}"
                }
            })
    }

    private val _logoutData = MutableLiveData<LogoutResponse>()
    val logoutData: LiveData<LogoutResponse> = _logoutData

    fun logout(context: Context, preferenceManager: PreferenceManager) {
        val token = "Bearer ${preferenceManager.getAccessToken() ?: ""}"
        val deviceId = DeviceUtils.getDeviceId(context)

        incrementLoader()
        RetrofitClient.apiCall.logout(Constent.APP_VERSION, deviceId, token).enqueue(object : Callback<LogoutResponse> {
            override fun onResponse(call: Call<LogoutResponse>, response: Response<LogoutResponse>) {

                if (response.code() == 403) {
                    decrementLoader()
                    handleTokenRefresh(preferenceManager){
                        logout(context, preferenceManager)
                    }
                }

                decrementLoader()
                if (response.isSuccessful && response.body() != null) {
                    _logoutData.value = response.body()
                    sessionExpired()
                }
                else{
                    _logoutData.value = LogoutResponse(
                        message = "No Response Found",
                        status = false,
                        responseCode = 0
                    )
                }

            }

            override fun onFailure(call: Call<LogoutResponse>, t: Throwable) {
                decrementLoader()
                _logoutData.value = LogoutResponse(
                    message = "No Response Found",
                    status = false,
                    responseCode = 0
                )
            }
        })
    }



    private fun sessionExpired(
    ) {
        SessionManager.logoutLiveData.value = true
    }
}
