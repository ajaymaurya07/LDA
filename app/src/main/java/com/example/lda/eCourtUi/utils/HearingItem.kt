package com.example.lda.eCourtUi.utils

data class HearingItem(
    val caseTitle: String,
    val hearingDate: String,
    val type: HearingType? // upcoming / interim / final
)

enum class HearingType {
    UPCOMING,
    INTERIM,
    FINAL
}


