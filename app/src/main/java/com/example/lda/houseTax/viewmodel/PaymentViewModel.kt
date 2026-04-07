package com.example.lda.houseTax.viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lda.constent.Constent
import com.example.lda.houseTax.data.ChallengeRequest
import com.example.lda.houseTax.data.ChallengeResponse
import com.example.lda.houseTax.data.ForgotPasswordRequest
import com.example.lda.houseTax.data.ForgotPasswordResponse
import com.example.lda.houseTax.data.InitiateTransactionRequest
import com.example.lda.houseTax.data.SendOtpRequest
import com.example.lda.houseTax.data.SignInRequest
import com.example.lda.houseTax.data.SignUpRequest
import com.example.lda.houseTax.data.VerifyForgotPasswordOtpRequest
import com.example.lda.houseTax.data.VerifyForgotPasswordOtpResponse
import com.example.lda.houseTax.data.VerifyOtpMailRequest
import com.example.lda.houseTax.data.VerifyOtpRequest
import com.example.lda.model.CreateTransactionResponse
import com.example.lda.model.FetchGrievanceResponse
import com.example.lda.model.HashResponse
import com.example.lda.model.OtpVerificationResponse
import com.example.lda.model.SaveGrievanceResponse
import com.example.lda.model.SendOtpResponse
import com.example.lda.model.SignIn
import com.example.lda.model.SignInResponse
import com.example.lda.model.SignUpResponse
import com.example.lda.model.TransactionsByEmailResponse
import com.example.lda.model.TransactionsDetailsResponse
import com.example.lda.model.VerifyOtpMailResponse
import com.example.lda.model.GrievanceStatusResponse
import com.example.lda.model.GrievanceDetailsResponse
import com.example.lda.network.RetrofitClient
import com.example.lda.viewmodel.BaseViewModel
import com.example.lda.houseTax.utils.PreferenceManager
import com.example.lda.utils.DeviceUtils
import android.content.Context
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class PaymentViewModel: BaseViewModel() {

    fun hashData(
        appVersion: Int,
        hashName: String,
        hashString: String,
        context: Context,
        preferenceManager: PreferenceManager,
        onResult: (String?) -> Unit,

    ) {

        val token = "Bearer ${preferenceManager.getAccessToken() ?: ""}"

        val call = RetrofitClient.apiCall.hash(
            appVersion = appVersion,
            hashName = hashName,
            hashString = hashString,
            deviceId = DeviceUtils.getDeviceId(context),
            token = token,
        )

        call.enqueue(object : Callback<HashResponse> {
            override fun onResponse(
                call: Call<HashResponse>,
                response: Response<HashResponse>
            ) {

                if(response.code()== 403){

                    handleTokenRefresh(preferenceManager) {
                        hashData(
                            appVersion,
                            hashName,
                            hashString,
                            context,
                            preferenceManager,
                            onResult
                        )
                    }
                }

                if (response.isSuccessful && response.body() != null){
                    val hash = response.body()?.data
                    onResult(hash)
                }else{
                    onResult(null)
                }
            }

            override fun onFailure(call: Call<HashResponse>, t: Throwable) {
                onResult(null)
            }
        })
    }



    private val _transaction = MutableLiveData<CreateTransactionResponse>()
    val transaction: LiveData<CreateTransactionResponse> = _transaction

    fun initiateTransaction(request: InitiateTransactionRequest, context: Context, preferenceManager: PreferenceManager) {

        val token = "Bearer ${preferenceManager.getAccessToken() ?: ""}"


        incrementLoader()
        val call = RetrofitClient.apiCall.initiateTransaction(
            appVersion = Constent.APP_VERSION,
            request = request,
            deviceId = DeviceUtils.getDeviceId(context),
            token = token
        )
        call.enqueue(object : Callback<CreateTransactionResponse> {
            override fun onResponse(
                call: Call<CreateTransactionResponse>,
                response: Response<CreateTransactionResponse>
            ) {

                Log.d("TAG", "onFailure: $response")
                Log.d("TAG", "onFailure: ${response.body()?.message}")

                if (response.code()== 403){
                    decrementLoader()
                    handleTokenRefresh(preferenceManager){
                        initiateTransaction(request, context, preferenceManager)
                    }
                }

                decrementLoader()
                if (response.isSuccessful && response.body()!= null){
                    _transaction.value= response.body()
                }else{
                    _transaction.value= CreateTransactionResponse(
                        data = null,
                        message = "no data found!",
                        status = false
                    )
                }

            }

            override fun onFailure(call: Call<CreateTransactionResponse>, t: Throwable) {

                Log.d("TAG", "onFailure: $t")
                decrementLoader()
                _transaction.value= CreateTransactionResponse(
                   data = null,
                    message = "network error",
                    status = false
                )
            }

        })
    }






    private val _transactionDetails = MutableLiveData<TransactionsDetailsResponse>()
    val transactionDetails: LiveData<TransactionsDetailsResponse> = _transactionDetails


    fun transactionDetails(transactionId: String, context: Context, preferenceManager: PreferenceManager) {

        incrementLoader()
        val token = "Bearer ${preferenceManager.getAccessToken() ?: ""}"
        val call = RetrofitClient.apiCall.transactionDetails(
            appVersion = Constent.APP_VERSION,
            mobileTransactionId = transactionId,
            deviceId = DeviceUtils.getDeviceId(context),
            token = token
        )

        call.enqueue(object : Callback<TransactionsDetailsResponse> {
            override fun onResponse(
                call: Call<TransactionsDetailsResponse>,
                response: Response<TransactionsDetailsResponse>
            ) {

                if (response.code()== 403) {
                    decrementLoader()
                    handleTokenRefresh(preferenceManager) {
                        transactionDetails(transactionId, context, preferenceManager)
                    }
                }

                decrementLoader()
                if (response.isSuccessful && response.body() != null){
                    _transactionDetails.value= response.body()
                }else{
                    _transactionDetails.value= TransactionsDetailsResponse(
                        data = null,
                        message = "no data found!",
                        status = false
                    )
                }

            }

            override fun onFailure(call: Call<TransactionsDetailsResponse>, t: Throwable) {
                decrementLoader()
                _transactionDetails.value= TransactionsDetailsResponse(
                    data = null,
                    message = "network error",
                    status = false
                )
            }
        })
    }






    private val _sendOtp = MutableLiveData<SendOtpResponse>()
    val sendOtp: LiveData<SendOtpResponse> = _sendOtp

    fun sendOtp(request: SendOtpRequest, loginMobileNumber: String, context: Context, preferenceManager: PreferenceManager) {

        if (loginMobileNumber == Constent.TEST_MOBILE_NUMBER) {
            _sendOtp.value = dummyResponse()
            return
        }

        val token = "Bearer ${preferenceManager.getAccessToken() ?: ""}"


        incrementLoader()

        val call = RetrofitClient.apiCall.sendOtp(
            appVersion = Constent.APP_VERSION,
            deviceId = DeviceUtils.getDeviceId(context),
            token = token,
            request = request
        )
        call.enqueue(object : Callback<SendOtpResponse> {
            override fun onResponse(
                call: Call<SendOtpResponse>,
                response: Response<SendOtpResponse>
            ) {
                if (response.code() == 403) {
                    decrementLoader()
                    handleTokenRefresh(preferenceManager) {
                        sendOtp(request, loginMobileNumber, context, preferenceManager)
                    }
                    return
                }

                decrementLoader()
                Log.d("TAG", "sendOtp: ${response.body()}")
                if(response.isSuccessful && response.body() != null){
                    _sendOtp.value = response.body()
                }else{
                    _sendOtp.value = SendOtpResponse(
                        data = null,
                        message = "no data found!",
                        success = false
                    )
                }

            }
            override fun onFailure(
                call: Call<SendOtpResponse>, t: Throwable) {
                decrementLoader()
                Log.d("TAG", "onFailure send otp: $t")
                _sendOtp.value= SendOtpResponse(
                    data = null,
                    message = "network error",
                    success = false
                )
            }
        })
    }


    private fun dummyResponse():SendOtpResponse{
        return SendOtpResponse(
            data = null,
            message = "Otp send Successfully",
            success = true,
            userId = 123456,
            responseCode = 1
        )
    }


    private val _otpVerification = MutableLiveData<OtpVerificationResponse>()
    val otpVerification: LiveData<OtpVerificationResponse> = _otpVerification

    fun otpVerification(request: VerifyOtpRequest, loginMobileNumber: String, context: Context, preferenceManager: PreferenceManager) {

        if (loginMobileNumber == Constent.TEST_MOBILE_NUMBER) {
            _otpVerification.value = dummyOtpVerificationResponse()
            return
        }

        val token = "Bearer ${preferenceManager.getAccessToken() ?: ""}"

        incrementLoader()
        val call = RetrofitClient.apiCall.otpVerification(
            appVersion = Constent.APP_VERSION,
            deviceId = DeviceUtils.getDeviceId(context),
            token = token,
            request = request
        )
        call.enqueue(object : Callback<OtpVerificationResponse> {
            override fun onResponse(
                call: Call<OtpVerificationResponse>,
                response: Response<OtpVerificationResponse>
            ) {
                if (response.code() == 403) {
                    decrementLoader()
                    handleTokenRefresh(preferenceManager) {
                        otpVerification(request, loginMobileNumber, context, preferenceManager)
                    }
                    return
                }

                decrementLoader()
                if(response.isSuccessful && response.body() != null){
                    _otpVerification.value = response.body()
                }else{
                    _otpVerification.value = OtpVerificationResponse(
                        data = null,
                        message = "no data found!",
                        success = false
                    )
                }

            }
            override fun onFailure(
                call: Call<OtpVerificationResponse>, t: Throwable) {
                decrementLoader()
                _otpVerification.value = OtpVerificationResponse(
                    data = null,
                    message = "network error",
                    success = false
                )
            }
        })
    }



    private val _otpVerificationForGrievance = MutableLiveData<OtpVerificationResponse>()
    val otpVerificationGrievance: LiveData<OtpVerificationResponse> = _otpVerificationForGrievance

    fun otpVerificationForGrievance(request: VerifyOtpRequest,loginMobileNumber: String, context: Context, preferenceManager: PreferenceManager) {

        val token = "Bearer ${preferenceManager.getAccessToken() ?: ""}"

        if (loginMobileNumber==Constent.TEST_MOBILE_NUMBER){
            _otpVerificationForGrievance.value=dummyOtpVerificationResponse()
            return
        }

        incrementLoader()
        val call = RetrofitClient.apiCall.registerGrievanceVerifyOtp(
            appVersion = Constent.APP_VERSION,
            request = request,
            token = token,
            deviceId = DeviceUtils.getDeviceId(context)
        )
        call.enqueue(object : Callback<OtpVerificationResponse> {
            override fun onResponse(
                call: Call<OtpVerificationResponse>,
                response: Response<OtpVerificationResponse>
            ) {

                Log.d("TAG", "onrespone otp verification: $response")
                if (response.code()== 403){
                    decrementLoader()
                    handleTokenRefresh(preferenceManager){
                        otpVerificationForGrievance(request,loginMobileNumber, context, preferenceManager)
                    }
                }


                decrementLoader()
                if (response.isSuccessful && response.body() !=null){
                    _otpVerificationForGrievance.value= response.body()
                }else{
                    _otpVerificationForGrievance.value= OtpVerificationResponse(
                        data = null,
                        message = "no data found!",
                        success = false
                    )
                }

            }
            override fun onFailure(
                call: Call<OtpVerificationResponse>, t: Throwable) {

                Log.d("TAG", "onFailure otp verication: $t")
                decrementLoader()
                _otpVerificationForGrievance.value= OtpVerificationResponse(
                    data = null,
                    message = "error",
                    success = false
                )
            }
        })
    }


    private fun dummyOtpVerificationResponse():OtpVerificationResponse{
        return OtpVerificationResponse(
            data = null,
            message = "Otp verify Successfully",
            success = true,
            responseCode = 1,
            userId = 123456
        )
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
                val body = response.body()
                when {

                    response.isSuccessful -> { //HTTP 200
                        if (body != null) {
                            _signUpData.value = body
                        } else {
                            _signUpData.value = SignUpResponse(
                                message = "No response found",
                                status = false,
                                responseCode = 0
                            )
                        }
                    }

//                    response.code() == 401 -> {
//                        _signUpData.value = SignUpResponse(
//                            message = "Session expired, please login again",
//                            status = false,
//                            responseCode = 401
//                        )
//                    }

                    // Other
                    else -> {
                        _signUpData.value = SignUpResponse(
                            message = "Unknown error",
                            status = false,
                            responseCode = response.code()
                        )
                    }
                }

            }
            override fun onFailure(
                call: Call<SignUpResponse>, t: Throwable) {
                decrementLoader()
                _signUpData.value = SignUpResponse(
                    message = "network error",
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
                val body = response.body()

                when {

                    response.isSuccessful -> { //HTTP 200
                        if (body != null) {
                            _otpVerificationMail.value = body
                        } else {
                            _otpVerificationMail.value= VerifyOtpMailResponse(
                                data = null,
                                message = "No Response Found",
                                status = false,
                                responseCode = 0
                            )
                        }
                    }

//                    response.code() == 401 -> {
//                        _signUpData.value = SignUpResponse(
//                            message = "Session expired, please login again",
//                            status = false,
//                            responseCode = 401
//                        )
//                    }

                    // Other
                    else -> {
                        _otpVerificationMail.value= VerifyOtpMailResponse(
                            data = null,
                            message = "Unknown error",
                            status = false,
                            responseCode = 0
                        )
                    }
                }

            }
            override fun onFailure(
                call: Call<VerifyOtpMailResponse>, t: Throwable) {
                decrementLoader()
                _otpVerificationMail.value= VerifyOtpMailResponse(
                    data = null,
                    message = "network error",
                    status = false,
                    responseCode = 0
                )
            }
        })
    }




    private val _signIn = MutableLiveData<SignInResponse>()
    val signIn: LiveData<SignInResponse> = _signIn

    fun signIn(request: SignInRequest) {

        Log.d("TAG", "signIn: $request")

        if (request.username== Constent.TEST_MOBILE_NUMBER && request.hash== Constent.TEST_PASSWORD){
            _signIn.value= SignInResponse(
                data = SignIn(
                    accessToken = "1234",
                    refreshToken = "1234",
                    emailId = "1@gmail.com",
                    userType = "admin"
                ),
                message = "Login Successful",
                status = true,
                responseCode = 1,
            )
            return
        }

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

                val body=response.body()
                when{
                    response.isSuccessful -> { //HTTP 200
                        if (body != null) {
                            _signIn.value = body
                        }
                        else {
                            _signIn.value = SignInResponse(
                                data = null,
                                message = "No Response Found",
                                status = false,
                                responseCode = 0
                            )
                        }
                    }
                    else -> {
                        _signIn.value = SignInResponse(
                            data = null,
                            message = "Unknown error",
                            status = false,
                            responseCode = 0
                        )
                    }
                }


            }
            override fun onFailure(
                call: Call<SignInResponse>, t: Throwable) {
                decrementLoader()
                Log.d("TAG", "onFailure: $t")
                _signIn.value= SignInResponse(
                    data = null,
                    message = "error",
                    status = false,
                    responseCode = 0
                )
            }
        })
    }



    private val _challenge = MutableLiveData<ChallengeResponse>()
    val challenge: LiveData<ChallengeResponse> = _challenge

    fun getChallenge(request: ChallengeRequest) {
        incrementLoader()
        val call = RetrofitClient.apiCall.getChallenge(
            appVersion = Constent.APP_VERSION,
            request = request
        )
        call.enqueue(object : Callback<ChallengeResponse> {
            override fun onResponse(
                call: Call<ChallengeResponse>,
                response: Response<ChallengeResponse>
            ) {

                decrementLoader()
                val body= response.body()

                Log.d("TAG", "onResponse: $body")

                when {
                    response.isSuccessful -> { //HTTP 200
                        if (body != null) {
                            _challenge.value = body
                        }
                        else {
                            _challenge.value = ChallengeResponse(
                                responseCode = 0,
                                data = null,
                                message = "No Response Found",
                                status = false
                            )
                        }
                    }
                    else -> {
                        _challenge.value = ChallengeResponse(
                            responseCode = 0,
                            data = null,
                            message = "Unknown error",
                            status = false
                        )
                    }
                }

            }

            override fun onFailure(call: Call<ChallengeResponse>, t: Throwable) {
                Log.d("TAG", "onResponse: $t")
                decrementLoader()
                _challenge.value = ChallengeResponse(
                    responseCode = 0,
                    data = null,
                    message = "network error",
                    status = false
                )
            }
        })
    }


    private val _forgotPassword = MutableLiveData<ForgotPasswordResponse>()
    val forgotPassword: LiveData<ForgotPasswordResponse> = _forgotPassword

    fun forgotPassword(request: ForgotPasswordRequest) {
        incrementLoader()
        val call = RetrofitClient.apiCall.forgotPassword(
            appVersion = Constent.APP_VERSION,
            request = request
        )
        call.enqueue(object : Callback<ForgotPasswordResponse> {
            override fun onResponse(
                call: Call<ForgotPasswordResponse>,
                response: Response<ForgotPasswordResponse>
            ) {
                decrementLoader()
                val body= response.body()

                when {
                    response.isSuccessful -> { //HTTP 200
                        if (body != null) {
                            _forgotPassword.value = body
                        }
                        else {
                            _forgotPassword.value = ForgotPasswordResponse(
                                status = false,
                                message = "No Response Found",
                                responseCode = 0
                            )
                        }
                    }
                    else -> {
                        _forgotPassword.value = ForgotPasswordResponse(
                            status = false,
                            message = "Unknown error",
                            responseCode = 0
                        )
                    }
                }

            }

            override fun onFailure(call: Call<ForgotPasswordResponse>, t: Throwable) {
                decrementLoader()
                _forgotPassword.value = ForgotPasswordResponse(
                    status = false,
                    message = "network error",
                    responseCode = 0
                )
            }
        })
    }

    private val _verifyForgotPasswordOtp = MutableLiveData<VerifyForgotPasswordOtpResponse>()
    val verifyForgotPasswordOtp: LiveData<VerifyForgotPasswordOtpResponse> = _verifyForgotPasswordOtp

    fun verifyForgotPasswordOtp(request: VerifyForgotPasswordOtpRequest) {
        incrementLoader()
        val call = RetrofitClient.apiCall.verifyForgotPasswordOtp(
            appVersion = Constent.APP_VERSION,
            request = request
        )
        call.enqueue(object : Callback<VerifyForgotPasswordOtpResponse> {
            override fun onResponse(
                call: Call<VerifyForgotPasswordOtpResponse>,
                response: Response<VerifyForgotPasswordOtpResponse>
            ) {
                decrementLoader()
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    _verifyForgotPasswordOtp.value = body
                } else {
                    _verifyForgotPasswordOtp.value = VerifyForgotPasswordOtpResponse(
                        status = false,
                        responseCode = 0,
                        message = body?.message ?: "Unknown error"
                    )
                }
            }

            override fun onFailure(call: Call<VerifyForgotPasswordOtpResponse>, t: Throwable) {
                decrementLoader()
                _verifyForgotPasswordOtp.value = VerifyForgotPasswordOtpResponse(
                    status = false,
                    responseCode = 0,
                    message = "network error"
                )
            }
        })
    }


    private val _grievanceData = MutableLiveData<FetchGrievanceResponse>()
    val grievanceData: LiveData<FetchGrievanceResponse> = _grievanceData
    fun fetchGrievanceData(context: Context, preferenceManager: PreferenceManager) {

        val token = "Bearer ${preferenceManager.getAccessToken() ?: ""}"

        incrementLoader()
        val call = RetrofitClient.apiCall.fetchGrievance(
            appVersion = Constent.APP_VERSION,
            deviceId = DeviceUtils.getDeviceId(context),
            token = token
        )
        call.enqueue(object : Callback<FetchGrievanceResponse> {
            override fun onResponse(
                call: Call<FetchGrievanceResponse>,
                response: Response<FetchGrievanceResponse>
            ) {
                if (response.code() == 403) {
                    decrementLoader()
                    handleTokenRefresh(preferenceManager) {
                        fetchGrievanceData(context, preferenceManager)
                    }
                    return
                }

                decrementLoader()

                if (response.isSuccessful && response.body() != null){
                    _grievanceData.value= response.body()
                }else{
                    _grievanceData.value= FetchGrievanceResponse(
                        data = null,
                        message = "no data found!",
                        success = false
                    )
                }

            }
            override fun onFailure(
                call: Call<FetchGrievanceResponse>, t: Throwable) {
                decrementLoader()
                _grievanceData.value= FetchGrievanceResponse(
                    data = null,
                    message = "network error",
                    success = false,
                    responseCode = 0
                )
            }
        })
    }


    private val _saveGrievance = MutableLiveData<SaveGrievanceResponse>()
    val saveGrievance: LiveData<SaveGrievanceResponse> = _saveGrievance

    fun saveGrievanceData(
        ulbId: String,
        zoneId: String,
        wardId: String,
        mohallaId: String,
        categoryId: String,
        subCategoryId: String,
        landmark: String,
        description: String,
        name: String,
        fatherName: String,
        mobileNo: String,
        email: String,
        address: String,
        file: File?,
        context: Context,
        preferenceManager: PreferenceManager
    ) {
        incrementLoader()

        val ulbIdBody = ulbId.toRequestBody("text/plain".toMediaTypeOrNull())
        val zoneIdBody = zoneId.toRequestBody("text/plain".toMediaTypeOrNull())
        val wardIdBody = wardId.toRequestBody("text/plain".toMediaTypeOrNull())
        val mohallaIdBody = mohallaId.toRequestBody("text/plain".toMediaTypeOrNull())
        val categoryIdBody = categoryId.toRequestBody("text/plain".toMediaTypeOrNull())
        val subCategoryIdBody = subCategoryId.toRequestBody("text/plain".toMediaTypeOrNull())
        val landmarkBody = landmark.toRequestBody("text/plain".toMediaTypeOrNull())
        val descriptionBody = description.toRequestBody("text/plain".toMediaTypeOrNull())
        val nameBody = name.toRequestBody("text/plain".toMediaTypeOrNull())
        val fatherNameBody = fatherName.toRequestBody("text/plain".toMediaTypeOrNull())
        val mobileNoBody = mobileNo.toRequestBody("text/plain".toMediaTypeOrNull())
        val emailBody = email.toRequestBody("text/plain".toMediaTypeOrNull())
        val addressBody = address.toRequestBody("text/plain".toMediaTypeOrNull())

        val filePart = file?.let {
            val requestFile = it.asRequestBody("image/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("file", it.name, requestFile)
        }

        val token = "Bearer ${preferenceManager.getAccessToken() ?: ""}"

        val call = RetrofitClient.apiCall.saveGrievance(
            appVersion = Constent.APP_VERSION,
            ulbId = ulbIdBody,
            zoneId = zoneIdBody,
            wardId = wardIdBody,
            mohallaId = mohallaIdBody,
            categoryId = categoryIdBody,
            subCategoryId = subCategoryIdBody,
            landmark = landmarkBody,
            description = descriptionBody,
            name = nameBody,
            fatherName = fatherNameBody,
            mobileNo = mobileNoBody,
            email = emailBody,
            address = addressBody,
            file = filePart,
            deviceId = DeviceUtils.getDeviceId(context),
            token = token,
        )
        call.enqueue(object : Callback<SaveGrievanceResponse> {
            override fun onResponse(
                call: Call<SaveGrievanceResponse>,
                response: Response<SaveGrievanceResponse>
            ) {

                if (response.code() == 403) {
                    decrementLoader()
                    handleTokenRefresh(preferenceManager) {
                        saveGrievanceData(
                            ulbId,
                            zoneId,
                            wardId,
                            mohallaId,
                            categoryId,
                            subCategoryId,
                            landmark,
                            description,
                            name,
                            fatherName,
                            mobileNo,
                            email,
                            address,
                            file,
                            context,
                            preferenceManager
                        )
                    }
                    return
                }

                decrementLoader()
                Log.d("TAG", "onResponse save grievance: ${response.body()}")
                if (response.isSuccessful && response.body()!=null){
                    _saveGrievance.value= response.body()
                }else{
                    _saveGrievance.value= SaveGrievanceResponse(
                        success = false,
                        message = "no data found!"
                    )
                }


            }

            override fun onFailure(call: Call<SaveGrievanceResponse>, t: Throwable) {
                decrementLoader()
                Log.d("TAG", "onResponse save grivance: ${t}")
                _saveGrievance.value = SaveGrievanceResponse(
                    success = false,
                    message = t.message
                )
            }
        })
    }

    private val _grievanceStatus = MutableLiveData<GrievanceStatusResponse>()
    val grievanceStatus: LiveData<GrievanceStatusResponse> = _grievanceStatus

    fun fetchGrievanceStatus(grievanceNo: String, context: Context, preferenceManager: PreferenceManager) {
        val token = "Bearer ${preferenceManager.getAccessToken() ?: ""}"
        incrementLoader()
        val request = mapOf("grievanceNo" to grievanceNo)
        RetrofitClient.apiCall.getGrievanceStatus(
            appVersion = Constent.APP_VERSION,
            deviceId = DeviceUtils.getDeviceId(context),
            token = token,
            request = request
        ).enqueue(object : Callback<GrievanceStatusResponse> {
            override fun onResponse(
                call: Call<GrievanceStatusResponse>,
                response: Response<GrievanceStatusResponse>
            ) {
                if (response.code() == 403) {
                    decrementLoader()
                    handleTokenRefresh(preferenceManager) {
                        fetchGrievanceStatus(grievanceNo, context, preferenceManager)
                    }
                    return
                }

                decrementLoader()
                if (response.isSuccessful && response.body() != null) {
                    _grievanceStatus.value = response.body()
                } else {
                    _grievanceStatus.value = GrievanceStatusResponse(
                        success = false,
                        message = "Failed to fetch status",
                        data = null
                    )
                }
            }

            override fun onFailure(call: Call<GrievanceStatusResponse>, t: Throwable) {
                decrementLoader()
                _grievanceStatus.value = GrievanceStatusResponse(
                    success = false,
                    message = "Error: ${t.message}",
                    data = null
                )
            }
        })
    }

    private val _grievanceDetails = MutableLiveData<GrievanceDetailsResponse>()
    val grievanceDetails: LiveData<GrievanceDetailsResponse> = _grievanceDetails

    fun fetchGrievanceDetails(email: String, context: Context, preferenceManager: PreferenceManager) {
        val token = "Bearer ${preferenceManager.getAccessToken() ?: ""}"
        incrementLoader()
        val requestBody = mapOf("email_id" to email)
        RetrofitClient.apiCall.getGrievanceDetails(
            appVersion = Constent.APP_VERSION,
            deviceId = DeviceUtils.getDeviceId(context),
            token = token,
            emailId = requestBody
        ).enqueue(object : Callback<GrievanceDetailsResponse> {
            override fun onResponse(
                call: Call<GrievanceDetailsResponse>,
                response: Response<GrievanceDetailsResponse>
            ) {
                if (response.code() == 403) {
                    decrementLoader()
                    handleTokenRefresh(preferenceManager) {
                        fetchGrievanceDetails(email, context, preferenceManager)
                    }
                    return
                }

                decrementLoader()
                if (response.isSuccessful && response.body() != null) {
                    _grievanceDetails.value = response.body()
                } else {
                    _grievanceDetails.value = GrievanceDetailsResponse(
                        success = false,
                        message = "Failed to fetch grievances",
                        data = null
                    )
                }
            }

            override fun onFailure(call: Call<GrievanceDetailsResponse>, t: Throwable) {
                decrementLoader()
                _grievanceDetails.value = GrievanceDetailsResponse(
                    success = false,
                    message = "Error: ${t.message}",
                    data = null
                )
            }
        })
    }

    private val _transactionsByEmail = MutableLiveData<TransactionsByEmailResponse>()
    val transactionsByEmail: LiveData<TransactionsByEmailResponse> = _transactionsByEmail

    fun getTransactionsByEmail(email: String, context: Context, preferenceManager: PreferenceManager) {
        val token = "Bearer ${preferenceManager.getAccessToken() ?: ""}"
        incrementLoader()
        val emailMap = mapOf("email_id" to email)
        val call = RetrofitClient.apiCall.getTransactionsByEmail(
            appVersion = Constent.APP_VERSION,
            deviceId = DeviceUtils.getDeviceId(context),
            token = token,
            emailId = emailMap
        )
        call.enqueue(object : Callback<TransactionsByEmailResponse> {
            override fun onResponse(
                call: Call<TransactionsByEmailResponse>,
                response: Response<TransactionsByEmailResponse>
            ) {
                if (response.code() == 403) {
                    decrementLoader()
                    handleTokenRefresh(preferenceManager) {
                        getTransactionsByEmail(email, context, preferenceManager)
                    }
                    return
                }

                decrementLoader()
                if (response.isSuccessful && response.body()!=null){
                    _transactionsByEmail.value = response.body()
                }else{
                    _transactionsByEmail.value = TransactionsByEmailResponse(
                        status = false,
                        message = "no data found!",
                        data = null
                    )
                }

            }

            override fun onFailure(call: Call<TransactionsByEmailResponse>, t: Throwable) {
                decrementLoader()
                _transactionsByEmail.value = TransactionsByEmailResponse(
                    status = false,
                    message = t.message,
                    data = null
                )
            }
        })
    }

}