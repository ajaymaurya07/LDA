package com.example.lda.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class PropertyDetailsResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("responseCode")
	val responseCode: Int? = null
)

data class BillDetails(

	@field:SerializedName("sewerTaxArrear")
	val sewerTaxArrear: String? = null,

	@field:SerializedName("otherTaxMonthlyInterest")
	val otherTaxMonthlyInterest: String? = null,

	@field:SerializedName("houseTaxDiscount")
	val houseTaxDiscount: String? = null,

	@field:SerializedName("waterChargeAdvance")
	val waterChargeAdvance: String? = null,

	@field:SerializedName("houseTaxAdvance")
	val houseTaxAdvance: String? = null,

	@field:SerializedName("finYear")
	val finYear: String? = null,

	@field:SerializedName("othertaxNetAmount")
	val othertaxNetAmount: String? = null,

	@field:SerializedName("sewerTaxDiscount")
	val sewerTaxDiscount: String? = null,

	@field:SerializedName("sewerTaxAdvance")
	val sewerTaxAdvance: String? = null,

	@field:SerializedName("waterChargeMonthlyInterest")
	val waterChargeMonthlyInterest: String? = null,

	@field:SerializedName("houseTaxArrear")
	val houseTaxArrear: String? = null,

	@field:SerializedName("sewerTaxInterest")
	val sewerTaxInterest: String? = null,

	@field:SerializedName("waterTaxMonthlyInterest")
	val waterTaxMonthlyInterest: String? = null,

	@field:SerializedName("waterTaxArrear")
	val waterTaxArrear: String? = null,

	@field:SerializedName("otherTaxArrear")
	val otherTaxArrear: String? = null,

	@field:SerializedName("houseCurrentTax")
	val houseCurrentTax: String? = null,

	@field:SerializedName("waterCurrentTax")
	val waterCurrentTax: String? = null,

	@field:SerializedName("waterTaxInterest")
	val waterTaxInterest: String? = null,

	@field:SerializedName("netPayble")
	val netPayble: String? = null,

	@field:SerializedName("netDemand")
	val netDemand: String? = null,

	@field:SerializedName("otherCurrentTax")
	val otherCurrentTax: String? = null,

	@field:SerializedName("otherTaxInterest")
	val otherTaxInterest: String? = null,

	@field:SerializedName("sewerTaxMonthlyInterest")
	val sewerTaxMonthlyInterest: String? = null,

	@field:SerializedName("billNo")
	val billNo: String? = null,

	@field:SerializedName("waterChargeDiscount")
	val waterChargeDiscount: String? = null,

	@field:SerializedName("waterTaxAdvance")
	val waterTaxAdvance: String? = null,

	@field:SerializedName("otherTaxAdvance")
	val otherTaxAdvance: String? = null,

	@field:SerializedName("waterTaxNetAmount")
	val waterTaxNetAmount: String? = null,

	@field:SerializedName("waterTaxDiscount")
	val waterTaxDiscount: String? = null,

	@field:SerializedName("sewerTaxNetAmount")
	val sewerTaxNetAmount: String? = null,

	@field:SerializedName("billDate")
	val billDate: String? = null,

	@field:SerializedName("waterChargeArrear")
	val waterChargeArrear: String? = null,

	@field:SerializedName("waterChargeNetAmount")
	val waterChargeNetAmount: String? = null,

	@field:SerializedName("houseTaxMonthlyInterest")
	val houseTaxMonthlyInterest: String? = null,

	@field:SerializedName("houseTaxInterest")
	val houseTaxInterest: String? = null,

	@field:SerializedName("sewerCurrentTax")
	val sewerCurrentTax: String? = null,

	@field:SerializedName("houseTaxNetAmount")
	val houseTaxNetAmount: String? = null,

	@field:SerializedName("otherTaxDiscount")
	val otherTaxDiscount: String? = null,

	@field:SerializedName("waterChargeInterest")
	val waterChargeInterest: String? = null,

	@field:SerializedName("waterChargeCurrent")
	val waterChargeCurrent: String? = null
)

data class OwnerDetails(

	@field:SerializedName("fatherName")
	val fatherName: String? = null,

	@field:SerializedName("ownerName")
	val ownerName: String? = null,

	@field:SerializedName("mobileNo")
	val mobileNo: String? = null
)

data class PropertyDetails(

	@field:SerializedName("wardName")
	val wardName: String? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("propertyUseAs")
	val propertyUseAs: String? = null,

	@field:SerializedName("propertyType")
	val propertyType: String? = null,

	@field:SerializedName("houseNo")
	val houseNo: String? = null,

	@field:SerializedName("zoneName")
	val zoneName: String? = null,

	@field:SerializedName("chukNo")
	val chukNo: String? = null,

	@field:SerializedName("mohallaName")
	val mohallaName: String? = null,

	@field:SerializedName("totalArea")
	val totalArea: String? = null
)


data class Data(

	@field:SerializedName("billDetails")
	val billDetails: BillDetails? = null,

	@field:SerializedName("ownerDetails")
	val ownerDetails: OwnerDetails? = null,

	@field:SerializedName("propertyDetails")
	val propertyDetails: PropertyDetails? = null,

	@field:SerializedName("currReceiptDetails")
	val currReceiptDetails: List<CurrReceiptDetailsItem?>? = null,

	@field:SerializedName("prevReceiptDetails")
	val prevReceiptDetails: List<PrevReceiptDetailsItem?>? = null
)

data class PrevReceiptDetailsItem(

	@field:SerializedName("sewerTaxPaidAmount")
	val sewerTaxPaidAmount: String? = null,

	@field:SerializedName("otherTaxPaidAmount")
	val otherTaxPaidAmount: String? = null,

	@field:SerializedName("waterTaxNetAmount")
	val waterTaxNetAmount: String? = null,

	@field:SerializedName("otherTaxNetAmount")
	val otherTaxNetAmount: String? = null,

	@field:SerializedName("paymentMode")
	val paymentMode: String? = null,

	@field:SerializedName("chequeNo")
	val chequeNo: String? = null,

	@field:SerializedName("waterTaxDiscount")
	val waterTaxDiscount: String? = null,

	@field:SerializedName("receiptDate")
	val receiptDate: String? = null,

	@field:SerializedName("sewerTaxNetAmount")
	val sewerTaxNetAmount: String? = null,

	@field:SerializedName("sewerTaxDiscount")
	val sewerTaxDiscount: String? = null,

	@field:SerializedName("waterChargePaidAmount")
	val waterChargePaidAmount: String? = null,

	@field:SerializedName("propertyTaxPaidAmount")
	val propertyTaxPaidAmount: String? = null,

	@field:SerializedName("propertyTaxDiscount")
	val propertyTaxDiscount: String? = null,

	@field:SerializedName("propertyTaxNetAmount")
	val propertyTaxNetAmount: String? = null,

	@field:SerializedName("waterChargeNetAmount")
	val waterChargeNetAmount: String? = null,

	@field:SerializedName("receiptNo")
	val receiptNo: String? = null,

	@field:SerializedName("otherTaxDiscount")
	val otherTaxDiscount: String? = null,

	@field:SerializedName("paymentDate")
	val paymentDate: String? = null,

	@field:SerializedName("challanId")
	val challanId: String? = null,

	@field:SerializedName("billNo")
	val billNo: String? = null,

	@field:SerializedName("waterChargeDiscount")
	val waterChargeDiscount: String? = null,

	@field:SerializedName("waterTaxPaidAmount")
	val waterTaxPaidAmount: String? = null
)

data class CurrReceiptDetailsItem(

	@field:SerializedName("sewerTaxPaidAmount")
	val sewerTaxPaidAmount: String? = null,

	@field:SerializedName("otherTaxPaidAmount")
	val otherTaxPaidAmount: String? = null,

	@field:SerializedName("waterTaxNetAmount")
	val waterTaxNetAmount: String? = null,

	@field:SerializedName("otherTaxNetAmount")
	val otherTaxNetAmount: String? = null,

	@field:SerializedName("paymentMode")
	val paymentMode: String? = null,

	@field:SerializedName("chequeNo")
	val chequeNo: String? = null,

	@field:SerializedName("waterTaxDiscount")
	val waterTaxDiscount: String? = null,

	@field:SerializedName("receiptDate")
	val receiptDate: String? = null,

	@field:SerializedName("sewerTaxNetAmount")
	val sewerTaxNetAmount: String? = null,

	@field:SerializedName("sewerTaxDiscount")
	val sewerTaxDiscount: String? = null,

	@field:SerializedName("waterChargePaidAmount")
	val waterChargePaidAmount: String? = null,

	@field:SerializedName("propertyTaxPaidAmount")
	val propertyTaxPaidAmount: String? = null,

	@field:SerializedName("propertyTaxDiscount")
	val propertyTaxDiscount: String? = null,

	@field:SerializedName("propertyTaxNetAmount")
	val propertyTaxNetAmount: String? = null,

	@field:SerializedName("waterChargeNetAmount")
	val waterChargeNetAmount: String? = null,

	@field:SerializedName("receiptNo")
	val receiptNo: String? = null,

	@field:SerializedName("otherTaxDiscount")
	val otherTaxDiscount: String? = null,

	@field:SerializedName("paymentDate")
	val paymentDate: String? = null,

	@field:SerializedName("challanId")
	val challanId: String? = null,

	@field:SerializedName("billNo")
	val billNo: String? = null,

	@field:SerializedName("waterChargeDiscount")
	val waterChargeDiscount: String? = null,

	@field:SerializedName("waterTaxPaidAmount")
	val waterTaxPaidAmount: String? = null
)
