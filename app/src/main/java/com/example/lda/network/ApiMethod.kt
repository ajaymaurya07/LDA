package com.example.lda.network

import com.example.lda.model.AllCaseDetailsResponse
import com.example.lda.model.CaseDetailsByCnrResponse
import com.example.lda.model.CaseStatusCountResponse
import com.example.lda.model.FinalOrderCountResponse
import com.example.lda.model.FinalOrderDetailResponse
import com.example.lda.model.IaCaseDetailsResponse
import com.example.lda.model.IaDetailsCountResponse
import com.example.lda.model.InterimOrderCategoryResponse
import com.example.lda.model.LoginResponse
import com.example.lda.model.NextHearingResponse
import com.example.lda.model.NotCaRejoinderFiledResponse
import com.example.lda.model.RecentInterimOrderCaseDetailByTypeResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST


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
        @Field("user_id") userId: String,
        @Field("app_version") version: String,
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
        @Field("user_id") userId: String,
        @Field("app_version") version: String,
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




}