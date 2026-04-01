package com.example.lda.houseTax.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lda.constent.Constent
import com.example.lda.houseTax.utils.PreferenceManager
import com.example.lda.model.BillDetails
import com.example.lda.model.CurrReceiptDetailsItem
import com.example.lda.model.Data
import com.example.lda.model.OwnerDetails
import com.example.lda.model.PrevReceiptDetailsItem
import com.example.lda.model.PropertyDetails
import com.example.lda.model.PropertyDetailsResponse
import com.example.lda.network.RetrofitClient
import com.example.lda.utils.DeviceUtils
import com.example.lda.utils.dataClass.PropertyDetailsRequest
import com.example.lda.viewmodel.BaseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PropertyDetailsViewmodel: BaseViewModel() {

    // for ulb data
    private val _dataList = MutableLiveData<PropertyDetailsResponse>()
    val dataList: LiveData<PropertyDetailsResponse> = _dataList

    val pid= MutableLiveData<String>()

    var propertyDetailsRequest = PropertyDetailsRequest()

    fun propertyDetailsData(loginMobileNumber: String, context: Context, token: String) {

        Log.d("TAG", "propertyDetailsData: called")

        if (loginMobileNumber == Constent.TEST_MOBILE_NUMBER) {
            _dataList.value = getFullDummyPropertyDetails()
            return
        }

        val token = "Bearer $token"
        val preferenceManager = PreferenceManager(context)

        incrementLoader()
        val call = RetrofitClient.apiCall.propertyDetails(
            appVersion = Constent.APP_VERSION,
            deviceId = DeviceUtils.getDeviceId(context),
            token = token,
            request = propertyDetailsRequest)
        call.enqueue(object : Callback<PropertyDetailsResponse> {
            override fun onResponse(
                call: Call<PropertyDetailsResponse>,
                response: Response<PropertyDetailsResponse>
            ) {

                Log.d("TAG", "onResponse: $response")

                if (response.code() == 403) {
                    decrementLoader()
                    handleTokenRefresh(preferenceManager) {
                        val newToken = preferenceManager.getAccessToken() ?: ""
                        propertyDetailsData(loginMobileNumber, context, newToken)
                    }
                    return
                }

                decrementLoader()
                if (response.isSuccessful && response.body()?.success == true) {
                    _dataList.value = response.body()
                }
                 else {
                    _dataList.value=PropertyDetailsResponse(
                        data = null,
                        success = false,
                        message = "No Data Found",
                        responseCode = 0
                    )
                }
            }

            override fun onFailure(call: Call<PropertyDetailsResponse>, t: Throwable) {
                decrementLoader()
                _dataList.value=PropertyDetailsResponse(
                    data = null,
                    success = false,
                    message = "network error.",
                    responseCode = 0
                )
            }

        })
    }



    private fun getFullDummyPropertyDetails(): PropertyDetailsResponse {

        val billDetails = BillDetails(
            sewerTaxArrear = "500",
            otherTaxMonthlyInterest = "50",
            houseTaxDiscount = "100",
            waterChargeAdvance = "200",
            houseTaxAdvance = "300",
            finYear = "2024-25",
            othertaxNetAmount = "800",
            sewerTaxDiscount = "40",
            sewerTaxAdvance = "150",
            waterChargeMonthlyInterest = "30",
            houseTaxArrear = "1000",
            sewerTaxInterest = "60",
            waterTaxMonthlyInterest = "25",
            waterTaxArrear = "700",
            otherTaxArrear = "400",
            houseCurrentTax = "5000",
            waterCurrentTax = "3000",
            waterTaxInterest = "20",
            netPayble = "13500",
            netDemand = "15000",
            otherCurrentTax = "1200",
            otherTaxInterest = "15",
            sewerTaxMonthlyInterest = "18",
            billNo = "BILL-1001",
            waterChargeDiscount = "50",
            waterTaxAdvance = "250",
            otherTaxAdvance = "100",
            waterTaxNetAmount = "2800",
            waterTaxDiscount = "70",
            sewerTaxNetAmount = "1900",
            billDate = "27-02-2026",
            waterChargeArrear = "300",
            waterChargeNetAmount = "2500",
            houseTaxMonthlyInterest = "35",
            houseTaxInterest = "45",
            sewerCurrentTax = "2000",
            houseTaxNetAmount = "4800",
            otherTaxDiscount = "25",
            waterChargeInterest = "10",
            waterChargeCurrent = "1500"
        )

        val ownerDetails = OwnerDetails(
            fatherName = "Suresh Kumar",
            ownerName = "Ramesh Kumar",
            mobileNo = "7394961460"
        )

        val propertyDetails = PropertyDetails(
            wardName = "Ward 12",
            address = "Alambagh, Lucknow",
            propertyUseAs = "Residential",
            propertyType = "Pucca",
            houseNo = "H-21",
            zoneName = "Zone A",
            chukNo = "C-11",
            mohallaName = "Krishna Nagar",
            totalArea = "1200"
        )

        val currReceipt = listOf(
            CurrReceiptDetailsItem(
                sewerTaxPaidAmount = "-",
                otherTaxPaidAmount = "-",
                waterTaxNetAmount = "-",
                otherTaxNetAmount = "-",
                paymentMode = "-",
                chequeNo = "-",
                waterTaxDiscount = "-",
                receiptDate = "-",
                sewerTaxNetAmount = "-",
                sewerTaxDiscount = "-",
                waterChargePaidAmount = "-",
                propertyTaxPaidAmount = "-",
                propertyTaxDiscount = "-",
                propertyTaxNetAmount = "-",
                waterChargeNetAmount = "-",
                receiptNo = "-",
                otherTaxDiscount = "-",
                paymentDate = "-",
                challanId = "-",
                billNo = "-",
                waterChargeDiscount = "-",
                waterTaxPaidAmount = "-"
            )
        )

        val prevReceipt = listOf(
            PrevReceiptDetailsItem(
                sewerTaxPaidAmount = "400",
                otherTaxPaidAmount = "200",
                waterTaxNetAmount = "2500",
                otherTaxNetAmount = "900",
                paymentMode = "Cash",
                chequeNo = "NA",
                waterTaxDiscount = "40",
                receiptDate = "15-04-2023",
                sewerTaxNetAmount = "1700",
                sewerTaxDiscount = "30",
                waterChargePaidAmount = "1300",
                propertyTaxPaidAmount = "4500",
                propertyTaxDiscount = "90",
                propertyTaxNetAmount = "4400",
                waterChargeNetAmount = "2300",
                receiptNo = "RCPT-OLD-001",
                otherTaxDiscount = "20",
                paymentDate = "15-04-2023",
                challanId = "CH-0901",
                billNo = "BILL-0901",
                waterChargeDiscount = "40",
                waterTaxPaidAmount = "2500"
            )
        )

        val data = Data(
            billDetails = billDetails,
            ownerDetails = ownerDetails,
            propertyDetails = propertyDetails,
            currReceiptDetails = currReceipt,
            prevReceiptDetails = prevReceipt
        )

        return PropertyDetailsResponse(
            data = data,
            success = true,
            message = "Data Loaded Successfully",
            responseCode = 200
        )
    }

}