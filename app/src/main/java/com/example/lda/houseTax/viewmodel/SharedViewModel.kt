package com.example.lda.houseTax.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.lda.constent.Constent
import com.example.lda.houseTax.data.AreaAndStructureDetails
import com.example.lda.houseTax.data.PropertyDetails
import com.example.lda.houseTax.data.PropertyTaxCalculation
import com.example.lda.model.MohallaItem
import com.example.lda.model.MohallaListResponse
import com.example.lda.model.PropertyItem
import com.example.lda.model.PropertySearchResponse
import com.example.lda.model.UlbDataResponse
import com.example.lda.model.UlbItem
import com.example.lda.model.WardItem
import com.example.lda.model.WardListResponse
import com.example.lda.model.ZoneItem
import com.example.lda.model.ZoneListResponse
import com.example.lda.network.RetrofitClient
import com.example.lda.utils.dataClass.PropertySearchRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SharedViewModel : ViewModel() {

    // fragment three
    val rentArea = MutableLiveData<String?>()
    val ownArea = MutableLiveData<String?>()
    val areaRate= MutableLiveData<Double?>()
    val age= MutableLiveData<Int?>()
    val constructionYear=MutableLiveData<String?>()
    val ageOfConstruction = MutableLiveData<Int?>()

    // first fragment
    val roadWidth = MutableLiveData<Int?>()
    val constructionType = MutableLiveData<String?>()
    val zone = MutableLiveData<String?>()
    val ward = MutableLiveData<String?>()
    val chk = MutableLiveData<String?>()



    // second fragment
    val propertyType=MutableLiveData<String?>()
    val ownerName=MutableLiveData<String?>()
    val ownerFatherName=MutableLiveData<String?>()
    val houseNo=MutableLiveData<String?>()
    val propertyId=MutableLiveData<String?>()
    val mobileNo=MutableLiveData<String?>()



    // FINAL calculated object
    val calculationResult = MutableLiveData<PropertyTaxCalculation>()
    val propertyDetails= MutableLiveData<PropertyDetails>()
    val areaAndStructureDetails= MutableLiveData<AreaAndStructureDetails>()





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




    // api error message
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage




    // for ulb data
    private val _ulbList = MutableLiveData<List<UlbItem>>()
    val ulbList: LiveData<List<UlbItem>> = _ulbList

    private val _selectedUlb = MutableLiveData<UlbItem?>()
    val selectedUlb: LiveData<UlbItem?> = _selectedUlb

    fun setSelectedUlb(ulb: UlbItem) {
        _selectedUlb.value = ulb
    }

    fun clearSelectedUlb() {
        _selectedUlb.value = null
    }


    fun ulbData( loginMobileNumber: String ) {

        if (loginMobileNumber == Constent.TEST_MOBILE_NUMBER) {
            _ulbList.value = getDummyUlbData()
            _errorMessage.value = null
            return
        }

        incrementLoader()
        val call = RetrofitClient.apiCall.ulbData(Constent.APP_VERSION)
        call.enqueue(object : Callback<UlbDataResponse> {
            override fun onResponse(
                call: Call<UlbDataResponse>,
                response: Response<UlbDataResponse>
            ) {
                decrementLoader()
                if (response.isSuccessful && response.body()?.success == true) {
                    _ulbList.value = response.body()?.data?.filterNotNull() ?: emptyList()
                    _errorMessage.value = null
                } else {
                    _ulbList.value = emptyList()
                    _errorMessage.value = response.body()?.message ?: "Data Not Found!"
                }
            }

            override fun onFailure(call: Call<UlbDataResponse>, t: Throwable) {
                decrementLoader()
                _ulbList.value = emptyList()
                _errorMessage.value = t.localizedMessage ?: "Zone API failed"
            }

        })
    }

    private fun getDummyUlbData(): List<UlbItem> {
        return listOf(
            UlbItem(
                districtId = "1",
                ulbType = "Nagar Nigam",
                districtName = "Lucknow",
                ulbId = "101",
                ulbName = "Lucknow Nagar Nigam"
            ),
            UlbItem(
                districtId = "2",
                ulbType = "Nagar Palika",
                districtName = "Kanpur",
                ulbId = "102",
                ulbName = "Kanpur Nagar Palika"
            )

        )
    }









    // Only required DATA (not full response)
    private val _zoneList = MutableLiveData<List<ZoneItem>>()
    val zoneList: LiveData<List<ZoneItem>> = _zoneList

    private val _selectedZone = MutableLiveData<ZoneItem?>()
    val selectedZone: LiveData<ZoneItem?> = _selectedZone

    fun setSelectedZone(ulb: ZoneItem) {
        _selectedZone.value = ulb
    }

    fun clearZoneList() {
        _zoneList.value = emptyList()
    }
    fun clearSelectedZone() {
        _selectedZone.value = null
    }
    fun zoneData(ulbId: String) {
        incrementLoader()
        val call = RetrofitClient.apiCall.zoneList(Constent.APP_VERSION, ulbId)
        call.enqueue(object : Callback<ZoneListResponse> {
            override fun onResponse(
                call: Call<ZoneListResponse>,
                response: Response<ZoneListResponse>
            ) {
                decrementLoader()
                if (response.isSuccessful && response.body()?.success == true) {
                    _zoneList.value = response.body()?.data?.filterNotNull() ?: emptyList()
                    _errorMessage.value = null
                } else {
                    _zoneList.value = emptyList()
                    _errorMessage.value = response.body()?.message ?: "Data Not Found!"
                }
            }

            override fun onFailure(call: Call<ZoneListResponse>, t: Throwable) {
                decrementLoader()
                _zoneList.value = emptyList()
                _errorMessage.value = t.localizedMessage ?: "Zone API failed"
            }

        })
    }








    // for ward data

    private val _wardList = MutableLiveData<List<WardItem>>()
    val wardList: LiveData<List<WardItem>> = _wardList

    private val _selectedWard = MutableLiveData<WardItem?>()
    val selectedWard: LiveData<WardItem?> = _selectedWard

    fun setSelectedWard(ulb: WardItem) {
        _selectedWard.value = ulb
    }

    fun clearWardList() {
        _wardList.value = emptyList()
    }

    fun wardData(ulbId: String,zoneId:String) {
        incrementLoader()
        val call = RetrofitClient.apiCall.wardList(Constent.APP_VERSION, ulbId,zoneId)
        call.enqueue(object : Callback<WardListResponse> {
            override fun onResponse(
                call: Call<WardListResponse>,
                response: Response<WardListResponse>
            ) {
                decrementLoader()
                if (response.isSuccessful && response.body()?.success == true) {
                    _wardList.value = response.body()?.data?.filterNotNull() ?: emptyList()
                    _errorMessage.value = null
                } else {
                    _wardList.value = emptyList()
                    _errorMessage.value = response.body()?.message ?: "Data Not Found!"
                }
            }

            override fun onFailure(call: Call<WardListResponse>, t: Throwable) {
                decrementLoader()
                _wardList.value = emptyList()
                _errorMessage.value = t.localizedMessage ?: "Zone API failed"
            }

        })
    }











    // for mohalla data

    private val _mohallaList = MutableLiveData<List<MohallaItem>>()
    val mohallaList: LiveData<List<MohallaItem>> = _mohallaList

    private val _selectedMohalla = MutableLiveData<MohallaItem?>()
    val selectedMohalla: LiveData<MohallaItem?> = _selectedMohalla

    fun setSelectedMohalla(mohalla: MohallaItem) {
        _selectedMohalla.value = mohalla
    }

    fun clearMohallaList() {
        _mohallaList.value = emptyList()
    }

    fun mohallaData(ulbId: String,zoneId:String,wardId:String) {
        incrementLoader()
        val call = RetrofitClient.apiCall.mohallaList(Constent.APP_VERSION, ulbId,zoneId,wardId)
        call.enqueue(object : Callback<MohallaListResponse> {
            override fun onResponse(
                call: Call<MohallaListResponse>,
                response: Response<MohallaListResponse>
            ) {
                decrementLoader()
                if (response.isSuccessful && response.body()?.success == true) {
                    _mohallaList.value = response.body()?.data?.filterNotNull() ?: emptyList()
                    _errorMessage.value = null
                } else {
                    _mohallaList.value = emptyList()
                    _errorMessage.value = response.body()?.message ?: "Data Not Found!"
                }
            }

            override fun onFailure(call: Call<MohallaListResponse>, t: Throwable) {
                decrementLoader()
                _mohallaList.value = emptyList()
                _errorMessage.value = t.localizedMessage ?: "Zone API failed"
            }

        })
    }






    var propertySearchRequest = PropertySearchRequest()

    private val _propertyList = MutableLiveData<List<PropertyItem>>()
    val propertyList: LiveData<List<PropertyItem>> = _propertyList


    // property search
    fun propertySearch(loginMobileNumber: String){

        if (loginMobileNumber == Constent.TEST_MOBILE_NUMBER) {
            _propertyList.value = getDummyPropertyData()
            return
        }

        incrementLoader()
        val call = RetrofitClient.apiCall.propertySearch(
            authorization = Constent.APP_VERSION,
            request = propertySearchRequest)

        call.enqueue(object : Callback<PropertySearchResponse> {
            override fun onResponse(
                call: Call<PropertySearchResponse>,
                response: Response<PropertySearchResponse>
            ) {

                decrementLoader()
                if (response.isSuccessful && response.body()?.success == true) {
                    _propertyList.value = response.body()?.data?.filterNotNull() ?: emptyList()
//                    _errorMessage.value = null
                } else {
//                    Log.d("TAG", "PRoperty search: ${response.body()}")
                    _propertyList.value = emptyList()
//                    _errorMessage.value = response.body()?.message ?: "Data Not Found!"
                }
            }

            override fun onFailure(call: Call<PropertySearchResponse>, t: Throwable) {
                decrementLoader()
                _propertyList.value = emptyList()
//                _errorMessage.value = t.localizedMessage ?: "Zone API failed"
            }

        })
    }


    private fun getDummyPropertyData(): List<PropertyItem> {
        return listOf(

            PropertyItem(
                oldPropertyId = "OLD-1001",
                address = "Alambagh, Lucknow",
                ownerName = "Ramesh Kumar",
                totalArv = 12500.50,
                propertyType = "Residential",
                fatherHusbandName = "Suresh Kumar",
                finYear = "2024-25",
                houseNo = "H-12",
                chukNo = "C-45",
                propertyId = "PROP-1001",
                billNo = "BILL-9001",
                totalArea = "1200 Sq.ft"
            ),

            PropertyItem(
                oldPropertyId = "OLD-1002",
                address = "Swaroop Nagar, Kanpur",
                ownerName = "Amit Sharma",
                totalArv = 18500.00,
                propertyType = "Commercial",
                fatherHusbandName = "Mahesh Sharma",
                finYear = "2024-25",
                houseNo = "H-45",
                chukNo = "C-12",
                propertyId = "PROP-1002",
                billNo = "BILL-9002",
                totalArea = "2200 Sq.ft"
            )
        )
    }

}
