package com.example.lda.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.lda.constent.Constent
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
import com.example.lda.model.UlbDataResponse
import com.example.lda.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel:ViewModel() {

    // Track number of active API calls
    private val _loadingCount = MutableLiveData(0)
    val isLoading: LiveData<Boolean> = _loadingCount.map { it > 0 }

    // Increment when API starts
    fun incrementLoader() {
        _loadingCount.value = (_loadingCount.value ?: 0) + 1
    }

    // Decrement when API finishes
    fun decrementLoader() {
        val current = _loadingCount.value ?: 0
        if (current > 0) _loadingCount.value = current - 1
    }


    val auth=MutableLiveData<LoginResponse>()
    fun getAuthenticate(userId:String,password:String,version: String){
        incrementLoader()
        val call = RetrofitClient.apiCall.authUser(Constent.API_KEY,userId,password,version)
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                auth.value=response.body()
                decrementLoader()
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                decrementLoader()
                auth.value = LoginResponse(
                    statusCode = "404",
                    statusMessage = "Internet connection issue, please try again",
                    result = null
                )
            }
        })
    }


    val caseStatusCount = MutableLiveData<CaseStatusCountResponse>()
    fun getCaseStatusCount(userId: String, version: String, userType: String,department:String) {
        incrementLoader()
        val call = RetrofitClient.apiCall.caseStatusCount(Constent.API_KEY, userId, version, userType,department)
        call.enqueue(object : Callback<CaseStatusCountResponse> {
            override fun onResponse(
                call: Call<CaseStatusCountResponse>,
                response: Response<CaseStatusCountResponse>
            ) {
                decrementLoader()
                caseStatusCount.value = response.body()
            }
            override fun onFailure(call: Call<CaseStatusCountResponse>, t: Throwable) {
                decrementLoader()
                caseStatusCount.value = CaseStatusCountResponse(
                    statusCode = "404",
                    statusMessage = "Internet connection issue, please try again",
                    result = null
                )
            }
        })
    }


    val allCases = MutableLiveData<AllCaseDetailsResponse>()
    fun getAllCaseWithStatus(userId: String, version: String, status: String, userType: String,department:String) {
        incrementLoader()
        val call = RetrofitClient.apiCall.allCaseDetailsByStatus(Constent.API_KEY,userId,version,status,userType,department )
        call.enqueue(object : Callback<AllCaseDetailsResponse> {
            override fun onResponse(
                call: Call<AllCaseDetailsResponse>,
                response: Response<AllCaseDetailsResponse>
            ) {
                decrementLoader()
                allCases.value=response.body()
            }

            override fun onFailure(call: Call<AllCaseDetailsResponse>, t: Throwable) {
                decrementLoader()
                allCases.value = AllCaseDetailsResponse(
                    statusCode = "404",
                    statusMessage = "Internet connection issue, please try again",
                    result = null
                )
            }

        })
    }


    val distinctInterimOrderCategoryListCount = MutableLiveData<InterimOrderCategoryResponse>()
    fun getDistinctInterimOrderCategory(userId: String, version: String,past: String,department:String,caseType: String) {
        incrementLoader()
        val call = RetrofitClient.apiCall.distinctInterimOrderCategoryCount(Constent.API_KEY,userId,version,past,department,caseType )
        call.enqueue(object : Callback<InterimOrderCategoryResponse> {
            override fun onResponse(
                call: Call<InterimOrderCategoryResponse>,
                response: Response<InterimOrderCategoryResponse>
            ) {
                decrementLoader()
                distinctInterimOrderCategoryListCount.value=response.body()
            }

            override fun onFailure(call: Call<InterimOrderCategoryResponse>, t: Throwable) {
                decrementLoader()
                distinctInterimOrderCategoryListCount.value = InterimOrderCategoryResponse(
                    statusCode = "404",
                    statusMessage = "Internet connection issue, please try again",
                    result = null
                )
            }

        })
    }



    val nextHearingCountData = MutableLiveData<NextHearingResponse>()
    fun getNextHearingCount(userId: String, version: String,past: String,caseType:String,department:String) {
        incrementLoader()
        val call = RetrofitClient.apiCall.nextHearingCount(Constent.API_KEY,userId,version,past,caseType,department )
        call.enqueue(object : Callback<NextHearingResponse> {
            override fun onResponse(
                call: Call<NextHearingResponse>,
                response: Response<NextHearingResponse>
            ) {
                decrementLoader()
                nextHearingCountData.value=response.body()
            }

            override fun onFailure(call: Call<NextHearingResponse>, t: Throwable) {
                decrementLoader()
                nextHearingCountData.value = NextHearingResponse(
                    statusCode = "404",
                    statusMessage = "Internet connection issue, please try again",
                    result = null
                )
            }

        })
    }


    val caseDetailsByCnrListData = MutableLiveData<CaseDetailsByCnrResponse>()
    fun getCaseDetailsByCnr(userId: String, version: String,userType: String,department:String,cnrNumber:String) {
        incrementLoader()
        val call = RetrofitClient.apiCall.caseDetailsByCn(Constent.API_KEY,userId,version,userType,department,cnrNumber )
        call.enqueue(object : Callback<CaseDetailsByCnrResponse> {
            override fun onResponse(
                call: Call<CaseDetailsByCnrResponse>,
                response: Response<CaseDetailsByCnrResponse>
            ) {
                decrementLoader()
                caseDetailsByCnrListData.value=response.body()
            }

            override fun onFailure(call: Call<CaseDetailsByCnrResponse>, t: Throwable) {
                decrementLoader()
                caseDetailsByCnrListData.value = CaseDetailsByCnrResponse(
                    statusCode = "404",
                    statusMessage = "Internet connection issue, please try again",
                    result = null
                )
            }

        })
    }



    val RecentInterimOrderCaseDetailByTypetData = MutableLiveData<RecentInterimOrderCaseDetailByTypeResponse>()
    fun getRecenetInterimOrderCaseDetailsByType(userId: String, version: String,department:String,interimOrderType:String,past: String,caseType: String) {
        incrementLoader()
        val call = RetrofitClient.apiCall.recenetInterimOrderCaseDetailsByType(Constent.API_KEY,userId,version,department,interimOrderType,past,caseType )
        call.enqueue(object : Callback<RecentInterimOrderCaseDetailByTypeResponse> {
            override fun onResponse(
                call: Call<RecentInterimOrderCaseDetailByTypeResponse>,
                response: Response<RecentInterimOrderCaseDetailByTypeResponse>
            ) {
                decrementLoader()
                RecentInterimOrderCaseDetailByTypetData.value=response.body()
            }

            override fun onFailure(call: Call<RecentInterimOrderCaseDetailByTypeResponse>, t: Throwable) {
                decrementLoader()
                RecentInterimOrderCaseDetailByTypetData.value = RecentInterimOrderCaseDetailByTypeResponse(
                    statusCode = "404",
                    statusMessage = "Internet connection issue, please try again",
                )
            }

        })
    }



    val iaDetailsCountData = MutableLiveData<IaDetailsCountResponse>()
    fun getIaDetailsCount(userId: String, version: String,department:String) {
        incrementLoader()
        val call = RetrofitClient.apiCall.iaDetailsCount(Constent.API_KEY,userId,version,department)
        call.enqueue(object : Callback<IaDetailsCountResponse> {
            override fun onResponse(
                call: Call<IaDetailsCountResponse>,
                response: Response<IaDetailsCountResponse>
            ) {
               decrementLoader()
                iaDetailsCountData.value=response.body()
            }

            override fun onFailure(call: Call<IaDetailsCountResponse>, t: Throwable) {
               decrementLoader()
                iaDetailsCountData.value = IaDetailsCountResponse(
                    statusCode = "404",
                    statusMessage = "Internet connection issue, please try again",
                )
            }

        })
    }




    val iaCaseDetailsData = MutableLiveData<IaCaseDetailsResponse>()
    fun getIaCaseDetails(userId: String, version: String,userType: String,department:String,category:String) {
        incrementLoader()
        val call = RetrofitClient.apiCall.iaCaseDetails(Constent.API_KEY,userId,version,userType,department,category)
        call.enqueue(object : Callback<IaCaseDetailsResponse> {
            override fun onResponse(
                call: Call<IaCaseDetailsResponse>,
                response: Response<IaCaseDetailsResponse>
            ) {
                decrementLoader()
                iaCaseDetailsData.value=response.body()
            }

            override fun onFailure(call: Call<IaCaseDetailsResponse>, t: Throwable) {
                decrementLoader()
                iaCaseDetailsData.value = IaCaseDetailsResponse(
                    statusCode = "404",
                    statusMessage = "Internet connection issue, please try again",
                )
            }

        })
    }

    val caseDetailsByDesignationData = MutableLiveData<IaCaseDetailsResponse>()
    fun getCaseDetailsByDesignation(userId: String, version: String,userType: String,department:String,designation:String) {
        incrementLoader()
        val call = RetrofitClient.apiCall.caseDetailsByDesignation(Constent.API_KEY,userId,version,userType,department,designation)
        call.enqueue(object : Callback<IaCaseDetailsResponse> {
            override fun onResponse(
                call: Call<IaCaseDetailsResponse>,
                response: Response<IaCaseDetailsResponse>
            ) {
                decrementLoader()
                caseDetailsByDesignationData.value=response.body()
            }

            override fun onFailure(call: Call<IaCaseDetailsResponse>, t: Throwable) {
                decrementLoader()
                caseDetailsByDesignationData.value = IaCaseDetailsResponse(
                    statusCode = "404",
                    statusMessage = "Internet connection issue, please try again",
                )
            }

        })
    }



    val finalOrderCountData = MutableLiveData<FinalOrderCountResponse>()
    fun getFinalOrderCount(userId: String, version: String,past: String,department:String) {
        incrementLoader()
        val call = RetrofitClient.apiCall.finalOrderCount(Constent.API_KEY,userId,version,past,department)
        call.enqueue(object : Callback<FinalOrderCountResponse> {
            override fun onResponse(
                call: Call<FinalOrderCountResponse>,
                response: Response<FinalOrderCountResponse>
            ) {
                decrementLoader()
                finalOrderCountData.value=response.body()
            }

            override fun onFailure(call: Call<FinalOrderCountResponse>, t: Throwable) {
                decrementLoader()
                finalOrderCountData.value = FinalOrderCountResponse(
                    statusCode = "404",
                    statusMessage = "Internet connection issue, please try again",
                )
            }

        })
    }



    val finalOrderDetailsData = MutableLiveData<FinalOrderDetailResponse>()
    fun getFinalOrderDetails(userId: String, version: String,past: String,department:String,finalOrderType:String) {
        incrementLoader()
        val call = RetrofitClient.apiCall.finalOrderDetails(Constent.API_KEY,userId,version,past,department,finalOrderType)
        call.enqueue(object : Callback<FinalOrderDetailResponse> {
            override fun onResponse(
                call: Call<FinalOrderDetailResponse>,
                response: Response<FinalOrderDetailResponse>
            ) {
                decrementLoader()
                finalOrderDetailsData.value=response.body()
            }

            override fun onFailure(call: Call<FinalOrderDetailResponse>, t: Throwable) {
                decrementLoader()
                finalOrderDetailsData.value = FinalOrderDetailResponse(
                    statusCode = "404",
                    statusMessage = "Internet connection issue, please try again",
                )
            }

        })
    }


    val caseDetailsCaFiledNotFiledAndRejoinderNotFiledData = MutableLiveData<NotCaRejoinderFiledResponse>()
    fun getCaseDetailsCaFiledNotFiledAndRejoinderNotFiled(userId: String, version: String,useType: String,department:String,category:String) {
        incrementLoader()
        val call = RetrofitClient.apiCall.caseDetailsCaFiledNotFiledAndRejoinderNotFiled(Constent.API_KEY,userId,version,useType,department,category)
        call.enqueue(object : Callback<NotCaRejoinderFiledResponse> {
            override fun onResponse(
                call: Call<NotCaRejoinderFiledResponse>,
                response: Response<NotCaRejoinderFiledResponse>
            ) {
                decrementLoader()
                caseDetailsCaFiledNotFiledAndRejoinderNotFiledData.value=response.body()
            }

            override fun onFailure(call: Call<NotCaRejoinderFiledResponse>, t: Throwable) {
                decrementLoader()
                caseDetailsCaFiledNotFiledAndRejoinderNotFiledData.value = NotCaRejoinderFiledResponse(
                    statusCode = "404",
                    statusMessage = "Internet connection issue, please try again",
                )
            }

        })
    }





}