package com.example.lda.houseTax.viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.lda.constent.Constent
import com.example.lda.houseTax.data.InitiateTransactionRequest
import com.example.lda.houseTax.data.SendOtpRequest
import com.example.lda.houseTax.data.SignInRequest
import com.example.lda.houseTax.data.SignUpRequest
import com.example.lda.houseTax.data.VerifyOtpMailRequest
import com.example.lda.houseTax.data.VerifyOtpRequest
import com.example.lda.model.CreateTransactionResponse
import com.example.lda.model.HashResponse
import com.example.lda.model.OtpVerificationResponse
import com.example.lda.model.SendOtpResponse
import com.example.lda.model.SignInResponse
import com.example.lda.model.SignUpResponse
import com.example.lda.model.TransactionsDetailsResponse
import com.example.lda.model.VerifyOtpMailResponse
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

        incrementLoader()
        val call = RetrofitClient.apiCall.initiateTransaction(
            appVersion = Constent.APP_VERSION,
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
            appVersion = Constent.APP_VERSION,
            mobileTransactionId = transactionId
        )
        call.enqueue(object : Callback<TransactionsDetailsResponse> {
            override fun onResponse(
                call: Call<TransactionsDetailsResponse>,
                response: Response<TransactionsDetailsResponse>
            ) {
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






    private val _sendOtp = MutableLiveData<SendOtpResponse>()
    val sendOtp: LiveData<SendOtpResponse> = _sendOtp

    fun sendOtp(request: SendOtpRequest) {
        incrementLoader()

        val call = RetrofitClient.apiCall.sendOtp(
            appVersion = Constent.APP_VERSION,
            request = request
        )
        call.enqueue(object : Callback<SendOtpResponse> {
            override fun onResponse(
                call: Call<SendOtpResponse>,
                response: Response<SendOtpResponse>
            ) {
                decrementLoader()
                _sendOtp.value= response.body()
            }
            override fun onFailure(
                call: Call<SendOtpResponse>, t: Throwable) {
                decrementLoader()
                _sendOtp.value= SendOtpResponse(
                    data = null,
                    message = "error",
                    success = false
                )
            }
        })
    }





    private val _otpVerification = MutableLiveData<OtpVerificationResponse>()
    val otpVerification: LiveData<OtpVerificationResponse> = _otpVerification

    fun otpVerification(request: VerifyOtpRequest) {
        incrementLoader()


        Log.d("TAG", "request: ${request}")

        val call = RetrofitClient.apiCall.otpVerification(
            appVersion = Constent.APP_VERSION,
            request = request
        )
        call.enqueue(object : Callback<OtpVerificationResponse> {
            override fun onResponse(
                call: Call<OtpVerificationResponse>,
                response: Response<OtpVerificationResponse>
            ) {
                Log.d("TAG", "onResponse: ${response.body()}")
                decrementLoader()
                _otpVerification.value= response.body()

            }
            override fun onFailure(
                call: Call<OtpVerificationResponse>, t: Throwable) {
                decrementLoader()
                Log.d("TAG", "onResponse: ${t}")
                _otpVerification.value= OtpVerificationResponse(
                    data = null,
                    message = "error",
                    success = false
                )
            }
        })
    }





    private val _signUpData = MutableLiveData<SignUpResponse>()
    val signUpData: LiveData<SignUpResponse> = _signUpData

    fun signUp(request: SignUpRequest) {
        incrementLoader()

        val call = RetrofitClient.apiCall.signUp(
            appVersion = Constent.APP_VERSION,
            request = request
        )
        call.enqueue(object : Callback<SignUpResponse> {
            override fun onResponse(
                call: Call<SignUpResponse>,
                response: Response<SignUpResponse>
            ) {
                decrementLoader()
                _signUpData.value= response.body()
            }
            override fun onFailure(
                call: Call<SignUpResponse>, t: Throwable) {
                decrementLoader()
                _signUpData.value = SignUpResponse(
                    data = null,
                    message = "error",
                    status = false,
                    responseCode = 0
                )
            }
        })
    }



    private val _otpVerificationMail = MutableLiveData<VerifyOtpMailResponse>()
    val otpVerificationMail: LiveData<VerifyOtpMailResponse> = _otpVerificationMail

    fun otpVerificationMail(request: VerifyOtpMailRequest) {
        incrementLoader()

        val call = RetrofitClient.apiCall.otpVerificationMail(
            appVersion = Constent.APP_VERSION,
            request = request
        )
        call.enqueue(object : Callback<VerifyOtpMailResponse> {
            override fun onResponse(
                call: Call<VerifyOtpMailResponse>,
                response: Response<VerifyOtpMailResponse>
            ) {
                decrementLoader()
                _otpVerificationMail.value= response.body()
            }
            override fun onFailure(
                call: Call<VerifyOtpMailResponse>, t: Throwable) {
                decrementLoader()
                _otpVerificationMail.value= VerifyOtpMailResponse(
                    data = null,
                    message = "error",
                    status = false,
                    responseCode = 0
                )
            }
        })
    }




    private val _signIn = MutableLiveData<SignInResponse>()
    val signIn: LiveData<SignInResponse> = _signIn

    fun signIn(request: SignInRequest) {
        incrementLoader()

        val call = RetrofitClient.apiCall.signIn(
            appVersion = Constent.APP_VERSION,
            request = request
        )
        call.enqueue(object : Callback<SignInResponse> {
            override fun onResponse(
                call: Call<SignInResponse>,
                response: Response<SignInResponse>
            ) {
                decrementLoader()
                _signIn.value= response.body()
            }
            override fun onFailure(
                call: Call<SignInResponse>, t: Throwable) {
                decrementLoader()
                _signIn.value= SignInResponse(
                    data = null,
                    message = "error",
                    status = false,
                    responseCode = 0
                )
            }
        })
    }



}