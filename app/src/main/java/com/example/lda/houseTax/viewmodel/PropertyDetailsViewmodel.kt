package com.example.lda.houseTax.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.lda.constent.Constent
import com.example.lda.model.PropertyDetailsResponse
import com.example.lda.network.RetrofitClient
import com.example.lda.utils.dataClass.PropertyDetailsRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PropertyDetailsViewmodel:ViewModel() {

    // Track number of active API calls
    private val _loadingCount = MutableLiveData(0)
    val isLoading: LiveData<Boolean> = _loadingCount.map { it > 0 }

    fun incrementLoader() {
        _loadingCount.value = (_loadingCount.value ?: 0) + 1
    }

    fun decrementLoader() {
        val current = _loadingCount.value ?: 0
        if (current > 0) _loadingCount.value = current - 1
    }


    // for ulb data
    private val _dataList = MutableLiveData<PropertyDetailsResponse>()
    val dataList: LiveData<PropertyDetailsResponse> = _dataList

    val pid= MutableLiveData<String>()

    var propertyDetailsRequest = PropertyDetailsRequest()

    fun propertyDetailsData() {
        incrementLoader()
        val call = RetrofitClient.apiCall.propertyDetails(
            authorization = Constent.APP_VERSION,
            request = propertyDetailsRequest)
        call.enqueue(object : Callback<PropertyDetailsResponse> {
            override fun onResponse(
                call: Call<PropertyDetailsResponse>,
                response: Response<PropertyDetailsResponse>
            ) {
                decrementLoader()
                _dataList.value= response.body()
            }

            override fun onFailure(call: Call<PropertyDetailsResponse>, t: Throwable) {
                decrementLoader()
                _dataList.value=PropertyDetailsResponse(
                    data = null,
                    success = false,
                    message = "network error.",
                    responseCode = 401
                )
            }

        })
    }



}