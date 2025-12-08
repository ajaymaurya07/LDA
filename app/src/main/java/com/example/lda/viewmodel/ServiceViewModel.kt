package com.example.lda.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lda.eCourtUi.utils.HearingItem
import com.example.lda.eCourtUi.utils.HearingType

class ServiceViewModel:ViewModel() {

    private val allServiceData = listOf(
        HearingItem("Case No. 101/2025", "25 Sep 2025", HearingType.UPCOMING),
        HearingItem("Case No. 102/2025", "30 Sep 2025", HearingType.UPCOMING),
        HearingItem("Case No. 101/2025", "25 Sep 2025", HearingType.UPCOMING),
        HearingItem("Case No. 102/2025", "30 Sep 2025", HearingType.UPCOMING),
        HearingItem("Case No. 101/2025", "25 Sep 2025", HearingType.UPCOMING),
        HearingItem("Case No. 102/2025", "30 Sep 2025", HearingType.UPCOMING),
        HearingItem("Case No. 201/2025", "05 Oct 2025", HearingType.INTERIM),
        HearingItem("Case No. 202/2025", "10 Oct 2025", HearingType.INTERIM),
        HearingItem("Case No. 301/2025", "15 Oct 2025", HearingType.FINAL),
        HearingItem("Case No. 201/2025", "05 Oct 2025", HearingType.INTERIM),
        HearingItem("Case No. 202/2025", "10 Oct 2025", HearingType.INTERIM),
        HearingItem("Case No. 301/2025", "15 Oct 2025", HearingType.FINAL),
        HearingItem("Case No. 201/2025", "05 Oct 2025", HearingType.INTERIM),
        HearingItem("Case No. 202/2025", "10 Oct 2025", HearingType.INTERIM),
        HearingItem("Case No. 301/2025", "15 Oct 2025", HearingType.FINAL),
        HearingItem("Case No. 201/2025", "05 Oct 2025", HearingType.INTERIM),
        HearingItem("Case No. 202/2025", "10 Oct 2025", HearingType.INTERIM),
        HearingItem("Case No. 301/2025", "15 Oct 2025", HearingType.FINAL),
    )

    val serviceData= MutableLiveData<List<HearingItem>>()
    val selectedCard=MutableLiveData<HearingType>()

    fun FilterServiceData(type: HearingType){
        serviceData.value=allServiceData.filter { it.type == type }
        selectedCard.value = type
    }



}