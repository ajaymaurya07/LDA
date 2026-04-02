package com.example.lda.network

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
import com.example.lda.model.AllCaseDetailsResponse
import com.example.lda.model.CaseDetailsByCnrResponse
import com.example.lda.model.CaseStatusCountResponse
import com.example.lda.model.CreateTransactionResponse
import com.example.lda.model.FetchGrievanceResponse
import com.example.lda.model.FinalOrderCountResponse
import com.example.lda.model.FinalOrderDetailResponse
import com.example.lda.model.GrievanceDetailsResponse
import com.example.lda.model.GrievanceStatusResponse
import com.example.lda.model.HashResponse
import com.example.lda.model.IaCaseDetailsResponse
import com.example.lda.model.IaDetailsCountResponse
import com.example.lda.model.InterimOrderCategoryResponse
import com.example.lda.model.LoginResponse
import com.example.lda.model.MohallaListResponse
import com.example.lda.model.NextHearingResponse
import com.example.lda.model.NotCaRejoinderFiledResponse
import com.example.lda.model.OtpVerificationResponse
import com.example.lda.model.PropertyDetailsResponse
import com.example.lda.model.PropertySearchResponse
import com.example.lda.model.RecentInterimOrderCaseDetailByTypeResponse
import com.example.lda.model.RefreshTokenResponse
import com.example.lda.model.SaveGrievanceResponse
import com.example.lda.model.SendOtpResponse
import com.example.lda.model.SignInResponse
import com.example.lda.model.SignUpResponse
import com.example.lda.model.TransactionsByEmailResponse
import com.example.lda.model.TransactionsDetailsResponse
import com.example.lda.model.UlbDataResponse
import com.example.lda.model.VerifyOtpMailResponse
import com.example.lda.model.WardListResponse
import com.example.lda.model.ZoneListResponse
import com.example.lda.utils.dataClass.PropertyDetailsRequest
import com.example.lda.utils.dataClass.PropertySearchRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path


interface ApiMethod {

    @FormUrlEncoded
    @POST("api/app_auth")
    fun authUser(
        @Header("api_key") apiKey:String,
        @Field("user_id") userId: String,
        @Field("password") password: String,
        @Field("app_version") version: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("api/getCaseStatusCount")
    fun caseStatusCount(
        @Header("api_key") apiKey:String,
        @Field("user_id") userId: String,
        @Field("app_version") version: String,
        @Field("user_type") userType:String,
        @Field("department") department:String
    ):Call<CaseStatusCountResponse>

    @FormUrlEncoded
    @POST("api/getAllCaseDetailsByStatus")
    fun allCaseDetailsByStatus(
        @Header("api_key") apiKey:String,
        @Field("user_id") userId: String,
        @Field("app_version") version: String,
        @Field("status") status:String,
        @Field("user_type") userType:String,
        @Field("department") department:String
    ):Call<AllCaseDetailsResponse>

    @FormUrlEncoded
    @POST("api/getRecenetInterimOrderTypeCount")
    fun distinctInterimOrderCategoryCount(
        @Header("api_key") apiKey:String,
        @Field("user_id") userId: String,
        @Field("app_version") version: String,
        @Field("past") past:String,
        @Field("department") department:String,
        @Field("case_type") caseType:String,
    ):Call<InterimOrderCategoryResponse>


    @FormUrlEncoded
    @POST("api/getCaseDetailsByCnr")
    fun caseDetailsByCn(
        @Header("api_key") apiKey:String,
        @Header("app_version") version: String,
        @Field("user_id") userId: String,
        @Field("user_type") userType:String,
        @Field("department") department:String,
        @Field("cnr_number") cnrNumber:String
    ):Call<CaseDetailsByCnrResponse>


    @FormUrlEncoded
    @POST("api/getNextHearingCount")
    fun nextHearingCount(
        @Header("api_key") apiKey:String,
        @Field("user_id") userId: String,
        @Field("app_version") version: String,
        @Field("past") past:String,
        @Field("case_type") caseType:String,
        @Field("department") department:String
    ):Call<NextHearingResponse>


    @FormUrlEncoded
    @POST("api/getRecenetInterimOrderCaseDetailsByType")
    fun recenetInterimOrderCaseDetailsByType(
        @Header("api_key") apiKey:String,
        @Field("user_id") userId: String,
        @Field("app_version") version: String,
        @Field("department") department:String,
        @Field("interim_order_type") interimOrderType:String,
        @Field("past") past:String,
        @Field("case_type") caseType:String

    ):Call<RecentInterimOrderCaseDetailByTypeResponse>


    @FormUrlEncoded
    @POST("api/getdashboardCount")
    fun iaDetailsCount(
        @Header("api_key") apiKey:String,
        @Field("user_id") userId: String,
        @Field("app_version") version: String,
        @Field("department") department:String,

    ):Call<IaDetailsCountResponse>

    @FormUrlEncoded
    @POST("api/getCaseDetailsByIACategory")
    fun iaCaseDetails(
        @Header("api_key") apiKey:String,
        @Header("app_version") version: String,
        @Field("user_id") userId: String,
        @Field("user_type") userType: String,
        @Field("department") department:String,
        @Field("category") category:String,

    ):Call<IaCaseDetailsResponse>

    @FormUrlEncoded
    @POST("api/getCaseDetailsByDesignation")
    fun caseDetailsByDesignation(
        @Header("api_key") apiKey:String,
        @Field("user_id") userId: String,
        @Field("app_version") version: String,
        @Field("user_type") userType: String,
        @Field("department") department:String,
        @Field("designation") designation:String,

    ):Call<IaCaseDetailsResponse>


    @FormUrlEncoded
    @POST("api/getDistinctFinalOrderActionCount")
    fun finalOrderCount(
        @Header("api_key") apiKey:String,
        @Field("user_id") userId: String,
        @Field("app_version") version: String,
        @Field("past") past:String,
        @Field("department") department:String

    ):Call<FinalOrderCountResponse>


    @FormUrlEncoded
    @POST("api/getRecenetFinalOrderCaseDetailsByType")
    fun finalOrderDetails(
        @Header("api_key") apiKey:String,
        @Field("user_id") userId: String,
        @Field("app_version") version: String,
        @Field("past") past:String,
        @Field("department") department:String,
        @Field("final_order_type") finalOrderType:String,

    ):Call<FinalOrderDetailResponse>


    @FormUrlEncoded
    @POST("api/getCaseDetailsCaFiledNotFiled")
    fun caseDetailsCaFiledNotFiledAndRejoinderNotFiled(
        @Header("api_key") apiKey:String,
        @Field("user_id") userId: String,
        @Field("app_version") version: String,
        @Field("user_type") userType:String,
        @Field("department") department:String,
        @Field("category") category:String,

        ):Call<NotCaRejoinderFiledResponse>




    @GET("api/House_tax/ulbdata")
    fun ulbData(
        @Header("X-App-Version") appVersion: Int,
        @Header("X-Device-Id") deviceId: String,
        @Header("Authorization") token: String
    ): Call<UlbDataResponse>


    @GET("api/House_tax/zonedata/{ulbId}")
    fun zoneList(
        @Header("X-App-Version") appVersion: Int,
        @Header("X-Device-Id") deviceId: String,
        @Header("Authorization") token: String,
        @Path("ulbId") ulbId: String
    ): Call<ZoneListResponse>


    @GET("api/House_tax/warddata/{ulbId}/{zoneId}")
    fun wardList(
        @Header("X-App-Version") appVersion: Int,
        @Header("X-Device-Id") deviceId: String,
        @Header("Authorization") token: String,
        @Path("ulbId") ulbId: String,
        @Path("zoneId") zoneId: String
    ): Call<WardListResponse>

    @GET("api/House_tax/mohalladata/{ulbId}/{zoneId}/{wardId}")
    fun mohallaList(
        @Header("X-App-Version") appVersion: Int,
        @Header("X-Device-Id") deviceId: String,
        @Header("Authorization") token: String,
        @Path("ulbId") ulbId: String,
        @Path("zoneId") zoneId: String,
        @Path("wardId") wardId: String
    ): Call<MohallaListResponse>


    @Headers(
        "Content-Type: application/json",
        "Accept: application/json")
    @POST("api/House_tax/propertysearch")
    fun propertySearch(
        @Header("X-App-Version") appVersion: Int,
        @Header("X-Device-Id") deviceId: String,
        @Header("Authorization") token: String,
        @Body request: PropertySearchRequest
    ): Call<PropertySearchResponse>


    @Headers(
        "Content-Type: application/json",
        "Accept: application/json")
    @POST("api/House_tax/propertydetails")
    fun propertyDetails(
        @Header("X-App-Version") appVersion: Int,
        @Header("X-Device-Id") deviceId: String,
        @Header("Authorization") token: String,
        @Body request: PropertyDetailsRequest
    ): Call<PropertyDetailsResponse>




    @FormUrlEncoded
    @POST("api/Payment/generate_hash")
    fun hash(
        @Header("X-App-Version") appVersion: Int,
        @Field("hashName") hashName: String,
        @Field("hashString") hashString: String,
    ): Call<HashResponse>




    @POST("api/Payment/create_transaction")
    fun initiateTransaction(
        @Header("X-App-Version") appVersion: Int,
        @Body request: InitiateTransactionRequest
    ): Call<CreateTransactionResponse>



    @FormUrlEncoded
    @POST("api/payment/getTransactionDetails")
    fun transactionDetails(
        @Header("X-App-Version") appVersion: Int,
        @Field("mobile_transaction_id") mobileTransactionId: String,
    ): Call<TransactionsDetailsResponse>


    @Headers(
        "Content-Type: application/json",
        "Accept: application/json")
    @POST("api/house_tax/sendOtp")
    fun sendOtp(
        @Header("X-App-Version") appVersion: Int,
        @Header("X-Device-Id") deviceId: String,
        @Header("Authorization") token: String,
        @Body request: SendOtpRequest
    ): Call<SendOtpResponse>

    @Headers(
        "Content-Type: application/json",
        "Accept: application/json")
    @POST("api/house_tax/verifyOtp")
    fun otpVerification(
        @Header("X-App-Version") appVersion: Int,
        @Header("X-Device-Id") deviceId: String,
        @Header("Authorization") token: String,
        @Body request: VerifyOtpRequest
    ): Call<OtpVerificationResponse>


    @POST("api/house_tax/signup")
    fun signUp(
        @Header("X-App-Version") appVersion: Int,
        @Body request: SignUpRequest
    ): Call<SignUpResponse>


    @POST("api/house_tax/verifyOtpEmail")
    fun otpVerificationMail(
        @Header("X-App-Version") appVersion: Int,
        @Body request: VerifyOtpMailRequest
    ): Call<VerifyOtpMailResponse>


    @POST("api/house_tax/login")
    fun signIn(
        @Header("X-App-Version") appVersion: Int,
        @Body request: SignInRequest
    ): Call<SignInResponse>


    @POST("api/house_tax/get_challenge")
    fun getChallenge(
        @Header("X-App-Version") appVersion: Int,
        @Body request: ChallengeRequest
    ): Call<ChallengeResponse>

    @POST("api/house_tax/forgot_password_request")
    fun forgotPassword(
        @Header("X-App-Version") appVersion: Int,
        @Body request: ForgotPasswordRequest
    ): Call<ForgotPasswordResponse>

    @POST("api/house_tax/verify_forgot_password_otp")
    fun verifyForgotPasswordOtp(
        @Header("X-App-Version") appVersion: Int,
        @Body request: VerifyForgotPasswordOtpRequest
    ): Call<VerifyForgotPasswordOtpResponse>


    @Headers(
        "Content-Type: application/json",
        "Accept: application/json")
    @GET("api/House_tax/grievanceCategory")
    fun fetchGrievance(
        @Header("X-App-Version") appVersion: Int,
        @Header("X-Device-Id") deviceId: String,
        @Header("Authorization") token: String
    ): Call<FetchGrievanceResponse>



    @POST("api/house_tax/registerGrievanceAfterOtp")
    fun registerGrievanceVerifyOtp(
        @Header("X-App-Version") appVersion: Int,
        @Body request: VerifyOtpRequest
    ): Call<OtpVerificationResponse>


    @Multipart
    @POST("api/house_tax/saveGrievance")
    fun saveGrievance(
        @Header("X-App-Version") appVersion: Int,
        @Header("X-Device-Id") deviceId: String,
        @Header("Authorization") token: String,
        @Part("ulbId") ulbId: RequestBody,
        @Part("zoneId") zoneId: RequestBody,
        @Part("wardId") wardId: RequestBody,
        @Part("mohallaId") mohallaId: RequestBody,
        @Part("categoryId") categoryId: RequestBody,
        @Part("subCategoryId") subCategoryId: RequestBody,
        @Part("landmark") landmark: RequestBody,
        @Part("description") description: RequestBody,
        @Part("name") name: RequestBody,
        @Part("fatherName") fatherName: RequestBody,
        @Part("mobileNo") mobileNo: RequestBody,
        @Part("email") email: RequestBody,
        @Part("address") address: RequestBody,
        @Part file: MultipartBody.Part?
    ): Call<SaveGrievanceResponse>


    @Headers(
        "Content-Type: application/json",
        "Accept: application/json")
    @POST("api/payment/get_transactions_by_email")
    fun getTransactionsByEmail(
        @Header("X-App-Version") appVersion: Int,
        @Body emailId: Map<String, String>
    ): Call<TransactionsByEmailResponse>

    @Headers(
        "Content-Type: application/json",
        "Accept: application/json")
    @POST("api/house_tax/getGrievanceDetails")
    fun getGrievanceDetails(
        @Header("X-App-Version") appVersion: Int,
        @Header("X-Device-Id") deviceId: String,
        @Header("Authorization") token: String,
        @Body emailId: Map<String, String>
    ): Call<GrievanceDetailsResponse>

    @Headers(
        "Content-Type: application/json",
        "Accept: application/json")
    @POST("api/house_tax/getGrievanceStatus")
    fun getGrievanceStatus(
        @Header("X-App-Version") appVersion: Int,
        @Header("X-Device-Id") deviceId: String,
        @Header("Authorization") token: String,
        @Body request: Map<String, String>
    ): Call<GrievanceStatusResponse>

    @POST("api/house_tax/refreshToken")
    fun refreshToken(
        @Header("X-App-Version") appVersion: Int,
        @Body body: Map<String, String>
    ): Call<RefreshTokenResponse>


}
