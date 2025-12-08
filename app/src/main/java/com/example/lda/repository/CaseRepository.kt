package com.example.lda.repository

import androidx.lifecycle.MutableLiveData
import com.example.lda.constent.Constent
import com.example.lda.eCourtUi.db.AppDatabase
import com.example.lda.eCourtUi.db.CaseEntity
import com.example.lda.model.AllCaseDetailsResponse
import com.example.lda.network.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CaseRepository(private val database: AppDatabase) {

    val isCaseLoading = MutableLiveData<Boolean>()
    val allCases = MutableLiveData<AllCaseDetailsResponse?>()

    fun refreshAllCases(userId: String, version: String, status: String, userType: String,department:String) {
        isCaseLoading.value = true

        val call = RetrofitClient.apiCall.allCaseDetailsByStatus(Constent.API_KEY, userId, version, status, userType,department)

        call.enqueue(object : Callback<AllCaseDetailsResponse> {
            override fun onResponse(call: Call<AllCaseDetailsResponse>, response: Response<AllCaseDetailsResponse>) {
                isCaseLoading.value = false
                if (response.isSuccessful) {
                    val body = response.body()
                    val resultList = body?.result ?: emptyList()

                    val entities: List<CaseEntity> = resultList
                        .filterNotNull()
                        .map { CaseEntity.fromResponse(it) }

                    // Clear and insert in background
                    CoroutineScope(Dispatchers.IO).launch {
                        val dao = database.caseDao()
                        dao.clearAll()
                        if (entities.isNotEmpty()) {
                            dao.insertAll(entities)
                        }
                    }

                    allCases.value = body

                } else {
                    allCases.value = AllCaseDetailsResponse(
                        statusCode = response.code().toString(),
                        statusMessage = response.message(),
                        result = null
                    )
                }
            }

            override fun onFailure(call: Call<AllCaseDetailsResponse>, t: Throwable) {
                isCaseLoading.value = false
                // Network error / No internet
                allCases.value = AllCaseDetailsResponse(
                    statusCode = "404",
                    statusMessage = "Internet connection issue, please try again",
                    result = null
                )
            }
        })
    }

    suspend fun getLocalCases() = withContext(Dispatchers.IO) {
        database.caseDao().getAllCases()
    }
}


