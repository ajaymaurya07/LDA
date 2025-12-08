package com.example.lda.eCourtUi.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.lda.model.ResultCaseItem

@Entity(tableName = "cases")
data class CaseEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val cnrNumber: String?,
    val caseStatus: String?,
    val registrationNumber: String?,
    val filingDate: String?,
    val nextHearing: String?,
    val petitionerName: String?,
    val respondentName: String?,
    val designation: String?
) {
    companion object {
        fun fromResponse(item: ResultCaseItem): CaseEntity {
            return CaseEntity(
                cnrNumber = item.cnrNumber,
                caseStatus = item.caseStatus,
                registrationNumber = item.registrationNumber,
                filingDate = item.filingDate,
                nextHearing = item.nextHearing,
                petitionerName = item.petitionerName,
                respondentName = item.respondentName,
                designation = item.designation
            )
        }
    }
}

