package com.example.lda.model

import com.google.gson.annotations.SerializedName

data class CaseDetailsByCnrResponse(

	@field:SerializedName("status_message")
	val statusMessage: String? = null,

	@field:SerializedName("result")
	val result: CaseItemDetails? = null,

	@field:SerializedName("status_code")
	val statusCode: String? = null
)

data class SubordinateCourtInformation(

	@field:SerializedName("Case_Decision_Date")
	val caseDecisionDate: String? = null,

	@field:SerializedName("CNR_Number")
	val cNRNumber: String? = null,

	@field:SerializedName("Case_Number")
	val caseNumber: String? = null,

	@field:SerializedName("Court_Name")
	val courtName: String? = null
)

data class CategoryDetails(

	@field:SerializedName("Sub_Category")
	val subCategory: String? = null,

	@field:SerializedName("CNR_Number")
	val cNRNumber: String? = null,

	@field:SerializedName("Category")
	val category: String? = null
)

data class CaseItemDetails(

	@field:SerializedName("case_details")
	val caseDetails: CaseDetails? = null,

	@field:SerializedName("category_details")
	val categoryDetails: CategoryDetails? = null,

	@field:SerializedName("case_status")
	val caseStatus: CaseStatus? = null,

	@field:SerializedName("history_of_case_hearing")
	val historyOfCaseHearing: List<HistoryOfCaseHearingItem?>? = null,

	@field:SerializedName("respondent_and_advocate")
	val respondentAndAdvocate: RespondentAndAdvocate? = null,

	@field:SerializedName("interim_order")
	val interimOrder: List<InterimOrderItem?>? = null,

	@field:SerializedName("subordinate_court_information")
	val subordinateCourtInformation: SubordinateCourtInformation? = null,

	@field:SerializedName("petitioner_and_advocate")
	val petitionerAndAdvocate: PetitionerAndAdvocate? = null,

	@field:SerializedName("ia_details")
	val iaDetails: List<IaDetailsItem?>? = null
)

data class PetitionerAndAdvocate(

	@field:SerializedName("CNR_Number")
	val cNRNumber: String? = null,

	@field:SerializedName("Petitioner_Name")
	val petitionerName: String? = null,

	@field:SerializedName("Petitioner_Advocates")
	val petitionerAdvocates: String? = null
)

data class RespondentAndAdvocate(

	@field:SerializedName("CNR_Number")
	val cNRNumber: String? = null,

	@field:SerializedName("Respondent_Name")
	val respondentName: String? = null,

	@field:SerializedName("Respondent_Advocates")
	val respondentAdvocates: String? = null
)

data class CaseStatus(

	@field:SerializedName("CNR_Number")
	val cNRNumber: String? = null,

	@field:SerializedName("Case_Status")
	val caseStatus: String? = null,

	@field:SerializedName("Objection_Status")
	val objectionStatus: String? = null,

	@field:SerializedName("Date_Of_Decision_parsed")
	val dateOfDecisionParsed: Any? = null,

	@field:SerializedName("Disposition_Name")
	val dispositionName: String? = null,

	@field:SerializedName("Date_Of_Decision")
	val dateOfDecision: String? = null
)

data class CaseDetails(

	@field:SerializedName("CNR_Number")
	val cNRNumber: String? = null,

	@field:SerializedName("District_Name")
	val districtName: String? = null,

	@field:SerializedName("Stage_Of_Case")
	val stageOfCase: String? = null,

	@field:SerializedName("Registration_Date")
	val registrationDate: String? = null,

	@field:SerializedName("Case_Type")
	val caseType: String? = null,

	@field:SerializedName("court_number")
	val courtNumber: Any? = null,

	@field:SerializedName("Next_Hearing_Date_parsed")
	val nextHearingDateParsed: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("Judicial_Branch")
	val judicialBranch: String? = null,

	@field:SerializedName("State_Name")
	val stateName: String? = null,

	@field:SerializedName("CauseList_Type")
	val causeListType: String? = null,

	@field:SerializedName("Court_Name")
	val courtName: String? = null,

	@field:SerializedName("Filing_Date")
	val filingDate: String? = null,

	@field:SerializedName("department")
	val department: String? = null,

	@field:SerializedName("Under_Act")
	val underAct: String? = null,

	@field:SerializedName("Registration_Date_parsed")
	val registrationDateParsed: String? = null,

	@field:SerializedName("Under_Section")
	val underSection: String? = null,

	@field:SerializedName("Next_Hearing_Date")
	val nextHearingDate: String? = null,

	@field:SerializedName("First_Hearing_Date_parsed")
	val firstHearingDateParsed: String? = null,

	@field:SerializedName("Registration_Number")
	val registrationNumber: String? = null,

	@field:SerializedName("First_Hearing_Date")
	val firstHearingDate: String? = null,

	@field:SerializedName("Filing_Number")
	val filingNumber: String? = null,

	@field:SerializedName("Coram")
	val coram: String? = null,

	@field:SerializedName("State")
	val state: String? = null,

	@field:SerializedName("judges_name")
	val judgesName: Any? = null,

	@field:SerializedName("Bench_Type")
	val benchType: String? = null,

	@field:SerializedName("Filing_date_parsed")
	val filingDateParsed: String? = null,

	@field:SerializedName("designation")
	val designation: String? = null,

	@field:SerializedName("District")
	val district: String? = null
)

data class HistoryOfCaseHearingItem(

	@field:SerializedName("CNR_Number")
	val cNRNumber: String? = null,

	@field:SerializedName("Business_On_Date")
	val businessOnDate: String? = null,

	@field:SerializedName("ID")
	val iD: String? = null,

	@field:SerializedName("Cause_List_Type")
	val causeListType: String? = null,

	@field:SerializedName("Purpose_Of_Listing")
	val purposeOfListing: String? = null,

	@field:SerializedName("Hearing_Date")
	val hearingDate: String? = null,

	@field:SerializedName("Judge")
	val judge: String? = null
)

data class InterimOrderItem(

	@field:SerializedName("CNR_Number")
	val cNRNumber: String? = null,

	@field:SerializedName("Order_Details_URL")
	val orderDetailsURL: String? = null,

	@field:SerializedName("interim_explicit_deadline")
	val interimExplicitDeadline: Any? = null,

	@field:SerializedName("interim_notes")
	val interimNotes: Any? = null,

	@field:SerializedName("Order_Date")
	val orderDate: String? = null,

	@field:SerializedName("Order_Details_Text")
	val orderDetailsText: Any? = null,

	@field:SerializedName("Order_Date_parsed")
	val orderDateParsed: String? = null,

	@field:SerializedName("order_type_flag")
	val orderTypeFlag: Any? = null,

	@field:SerializedName("compliance_due_date")
	val complianceDueDate: Any? = null,

	@field:SerializedName("order_flags")
	val orderFlags: Any? = null,

	@field:SerializedName("interim_calculated_deadline")
	val interimCalculatedDeadline: Any? = null,

	@field:SerializedName("interim_action")
	val interimAction: Any? = null,

	@field:SerializedName("Order_Number")
	val orderNumber: String? = null,

	@field:SerializedName("ID")
	val iD: String? = null
)

data class IaDetailsItem(

	@field:SerializedName("CNR_Number")
	val cNRNumber: String? = null,

	@field:SerializedName("Party")
	val party: String? = null,

	@field:SerializedName("Next_Date")
	val nextDate: String? = null,

	@field:SerializedName("Classification")
	val classification: String? = null,

	@field:SerializedName("ID")
	val iD: String? = null,

	@field:SerializedName("IA_Status")
	val iAStatus: String? = null,

	@field:SerializedName("IA_Number")
	val iANumber: String? = null,

	@field:SerializedName("Date_Of_Filing")
	val dateOfFiling: String? = null
)
