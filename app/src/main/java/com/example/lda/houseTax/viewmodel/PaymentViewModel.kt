package com.example.lda.houseTax.viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.lda.houseTax.data.InitiateTransactionRequest
import com.example.lda.model.CreateTransactionResponse
import com.example.lda.model.HashResponse
import com.example.lda.model.TransactionsDetailsResponse
import com.example.lda.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentViewModel:ViewModel() {
    fun hashData(
        appVersion: Int,
        hashName: String,
        hashString: String,
        onResult: (String?) -> Unit
    ) {
        val call = RetrofitClient.apiCall.hash(
            appVersion = appVersion,
            hashName = hashName,
            hashString = hashString
        )

        call.enqueue(object : Callback<HashResponse> {
            override fun onResponse(
                call: Call<HashResponse>,
                response: Response<HashResponse>
            ) {
                val hash = response.body()?.data
                onResult(hash)
            }

            override fun onFailure(call: Call<HashResponse>, t: Throwable) {
                onResult(null)
            }
        })
    }


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


    private val _transaction = MutableLiveData<CreateTransactionResponse>()
    val transaction: LiveData<CreateTransactionResponse> = _transaction


    fun initiateTransaction(request: InitiateTransactionRequest) {

        Log.d("TAG", "request: $request")

        incrementLoader()
        val call = RetrofitClient.apiCall.initiateTransaction(
            appVersion = 1,
            request = request
        )
        call.enqueue(object : Callback<CreateTransactionResponse> {
            override fun onResponse(
                call: Call<CreateTransactionResponse>,
                response: Response<CreateTransactionResponse>
            ) {
                decrementLoader()
                _transaction.value= response.body()
            }

            override fun onFailure(call: Call<CreateTransactionResponse>, t: Throwable) {
                decrementLoader()
                _transaction.value= CreateTransactionResponse(
                   data = null,
                    message = "error",
                    status = false
                )
            }

        })
    }




    private val _transactionDetails = MutableLiveData<TransactionsDetailsResponse>()
    val transactionDetails: LiveData<TransactionsDetailsResponse> = _transactionDetails


    fun transactionDetails(transactionId: String) {

        incrementLoader()
        val call = RetrofitClient.apiCall.transactionDetails(
            appVersion = 1,
            mobileTransactionId = transactionId
        )
        call.enqueue(object : Callback<TransactionsDetailsResponse> {
            override fun onResponse(
                call: Call<TransactionsDetailsResponse>,
                response: Response<TransactionsDetailsResponse>
            ) {

                Log.d("TAG", "transactionDetails: ${response.body()}")
                decrementLoader()
                _transactionDetails.value= response.body()
            }

            override fun onFailure(call: Call<TransactionsDetailsResponse>, t: Throwable) {
                decrementLoader()
                _transactionDetails.value= TransactionsDetailsResponse(
                    data = null,
                    message = "error",
                    status = false
                )
            }
        })
    }



}