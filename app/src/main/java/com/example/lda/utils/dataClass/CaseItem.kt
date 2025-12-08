package com.example.lda.utils.dataClass

import android.os.Parcel
import android.os.Parcelable

data class CaseItem(
    val caseNo: String,
    val hearingDate: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(caseNo)
        parcel.writeString(hearingDate)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<CaseItem> {
        override fun createFromParcel(parcel: Parcel): CaseItem = CaseItem(parcel)
        override fun newArray(size: Int): Array<CaseItem?> = arrayOfNulls(size)
    }
}


