package com.example.lda.houseTax

import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.lda.R
import com.example.lda.constent.Constent
import com.example.lda.databinding.ActivityPayment2Binding
import com.example.lda.eCourtUi.utils.SystemBarsHelper.applySafeAreaInsets
import com.example.lda.houseTax.data.InitiateTransactionRequest
import com.example.lda.houseTax.paymentDetails.ArvHistoryActivity
import com.example.lda.houseTax.paymentModeActivity.SbiTestActivity
import com.example.lda.houseTax.paymentStatus.PaymentFailedActivity
import com.example.lda.houseTax.paymentStatus.PaymentPendingActivity
import com.example.lda.houseTax.paymentStatus.PaymentSuccessActivity
import com.example.lda.houseTax.utils.PreferenceManager
import com.example.lda.houseTax.viewmodel.PaymentViewModel
import com.example.lda.model.Data
import com.example.lda.utils.LoderHelper
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.payu.base.models.ErrorResponse
import com.payu.base.models.PayUPaymentParams
import com.payu.checkoutpro.PayUCheckoutPro
import com.payu.checkoutpro.utils.PayUCheckoutProConstants.CP_HASH_NAME
import com.payu.checkoutpro.utils.PayUCheckoutProConstants.CP_HASH_STRING
import com.payu.ui.model.listeners.PayUCheckoutProListener
import com.payu.ui.model.listeners.PayUHashGenerationListener
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class PaymentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPayment2Binding
    lateinit var viewModel: PaymentViewModel
    private lateinit var loderHelper: LoderHelper
    private lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= DataBindingUtil.setContentView(this,R.layout.activity_payment2)


        enableEdgeToEdge()
        applySafeAreaInsets(
            rootView = findViewById(R.id.root),
            statusBarColor = getColor(R.color.primary),
            lightStatusBar = true,
        )


        viewModel=ViewModelProvider(this)[PaymentViewModel::class.java]
        loderHelper= LoderHelper(this)
        preferenceManager = PreferenceManager(this)

        val json = intent.getStringExtra("property_data_json")
        val pid = intent.getStringExtra("pid")

        val data: Data? = json?.let { Gson().fromJson(it, Data::class.java) }


        val toolbar = findViewById<ImageView>(R.id.navBack)
        toolbar.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        var isExpanded = false

        binding.layoutHeader.setOnClickListener {
            isExpanded = !isExpanded

            if (isExpanded) {
                binding.layoutDetails.visibility = View.VISIBLE
                binding.imgArrow.animate().rotation(180f).setDuration(200).start()
            } else {
                binding.layoutDetails.visibility = View.GONE
                binding.imgArrow.animate().rotation(0f).setDuration(200).start()
            }
        }


        binding.btnAddGrievance.setOnClickListener {
            val intent = Intent(this, GravianceActivity::class.java)
            startActivity(intent)
        }


        binding.btnArvHistory.setOnClickListener {
            val intent = Intent(this, ArvHistoryActivity::class.java)
            startActivity(intent)
        }

        binding.btnPaymentHistory.setOnClickListener {
            val intent = Intent(this, PaymentHistoryActivity::class.java)
            intent.putExtra("property_data_json", json)
            startActivity(intent)
        }

        val ulbId=preferenceManager.getUlbId()
        val arvValue=preferenceManager.getArvValue()

        val billDetails=data?.billDetails
        val billNo=billDetails?.billNo
        val financialYear=billDetails?.finYear
        val houseTax=billDetails?.houseTaxNetAmount
        val waterTax=billDetails?.waterTaxNetAmount
        val sewerTax=billDetails?.sewerTaxNetAmount
        val otherTax=billDetails?.othertaxNetAmount
        val waterCharge=billDetails?.waterChargeNetAmount
        val netDemand=billDetails?.netDemand
        val netPayable=billDetails?.netPayble

        val ownerDetails=data?.ownerDetails
        val ownerName=ownerDetails?.ownerName
        val fatherName=ownerDetails?.fatherName
        val mobileNumber=ownerDetails?.mobileNo



        binding.btnPayTax.setOnClickListener {

            val mobile_id= "MOBTXN${System.currentTimeMillis()}"


            val request = InitiateTransactionRequest(
                mobile_transaction_id =mobile_id,
                mobile_transaction_timestamp = getCurrentTime(),

                bill_no = billNo!!,   // payment screen show
                property_id = pid!!,
                ulb_id = ulbId!!,      // // property details show
                financial_year = financialYear!!,

                ownerName = ownerName!!, // save in property details
                fatherName = fatherName!!,
                mobileNo = mobileNumber!!,

                property_tax = houseTax!!,   // payment screen show
                water_tax = waterTax!!,
                sewer_tax = sewerTax!!,
                other_tax = otherTax!!,
                water_charge = waterCharge!!,

                net_demand = netDemand!!,
                net_payable = netPayable!!,

                totalArv = arvValue!!,

                user_id = preferenceManager.getUserId().toString()

            )

            openPaymentBottomSheet(request)

        }

        transactionObserver()




        binding.btnPrintDetails.setOnClickListener {
            generatePropertyPdf()
        }





        binding.tvHeader.text="PID: $pid"
        binding.tvBillDate.text= billDetails?.billDate
        binding.tvBillNumber.text=billNo
        binding.tvFinancialYear.text= financialYear
        binding.tvTotalArv.text= arvValue
        binding.tvHouseTaxNetAmount.text=houseTax
        binding.tvWaterTaxNetAmount.text=waterTax
        binding.tvSewerTaxNetAmount.text=sewerTax
        binding.tvOtherTaxNetAmount.text=otherTax
        binding.tvWaterChargeNetAmount.text=waterCharge
        binding.tvNetDemand.text=netDemand
        binding.tvNetPayable.text=netPayable


        val propertyDetails=data?.propertyDetails
        binding.tvPropertyId.text=pid
        binding.tvZone.text=propertyDetails?.zoneName
        binding.tvWardName.text=propertyDetails?.wardName
        binding.tvMohalla.text=propertyDetails?.mohallaName
        binding.tvOwnershipType.text=propertyDetails?.propertyType
        binding.tvHouseNo.text=propertyDetails?.houseNo
        binding.tvAddress.text=propertyDetails?.address
        binding.tvArea.text=propertyDetails?.totalArea
        binding.tvRoadWidth.text=propertyDetails?.propertyUseAs
        binding.tvYearlyTax2.text=propertyDetails?.chukNo

        binding.tvOwnerName.text=ownerName
        binding.tvOwnerMobile.text=mobileNumber
        binding.tvOwnerFatherName.text=fatherName


        
    }


    private fun startPayUPayment(request: InitiateTransactionRequest){
            preferenceManager.saveMobileTransactionId(request.mobile_transaction_id)
            viewModel.initiateTransaction(request)
    }


    private fun startSbiPayment(request: InitiateTransactionRequest){
        val intent = Intent(this, SbiTestActivity::class.java)
        startActivity(intent)
    }



    private fun openPaymentBottomSheet( request: InitiateTransactionRequest) {

        val dialog = BottomSheetDialog(this, R.style.BottomSheetTheme)
        val view = layoutInflater.inflate(R.layout.bottom_sheet_payment_diaog, null)
        dialog.setContentView(view)


        val payuLayout = view.findViewById<View>(R.id.btnPayPayu)
        payuLayout.setOnClickListener {
            dialog.dismiss()
            startPayUPayment(request)
        }

        val sbiLayout=view.findViewById<View>(R.id.btnPaySbi)
        sbiLayout.setOnClickListener {
            dialog.dismiss()
            startSbiPayment(request)
        }




        dialog.setOnShowListener {
            val bottomSheet = dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)

            bottomSheet?.let {
                val layoutParams = it.layoutParams
                layoutParams.height = (resources.displayMetrics.heightPixels * 0.65).toInt()
                it.layoutParams = layoutParams

                val behavior = BottomSheetBehavior.from(it)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
                behavior.skipCollapsed = true
            }
        }

        dialog.show()
    }



    private fun transactionObserver() {

        viewModel.isLoading.observe(this){
            if (it){
                loderHelper.startLoadingDialog("Please wait, we’re initiating your payment…")
            }else{
                loderHelper.dismissDialog()
            }
        }

        viewModel.transaction.observe(this) { response ->


            Log.d("TAG", "transactionObserver: $response")

            if (response?.status != true) {
                Toast.makeText(this, response?.message ?: "Payment can’t be processed right now. Please try again later.", Toast.LENGTH_LONG).show()
                return@observe
            }
            val data = response.data ?: return@observe
            if (
                data.txnid.isNullOrBlank() ||
                data.amount.isNullOrBlank() ||
                data.productinfo.isNullOrBlank() ||
                data.firstname.isNullOrBlank() ||
                data.email.isNullOrBlank() ||
                data.phone.isNullOrBlank() ||
                data.key.isNullOrBlank() ||
                data.surl.isNullOrBlank() ||
                data.furl.isNullOrBlank()
            ) {
                Toast.makeText(this, "Payment can’t be processed right now. Please try again later.", Toast.LENGTH_LONG).show()
                return@observe
            }


            startPayment(
                txnId = data.txnid,
                amount = data.amount,
                productInfo = data.productinfo,
                name = data.firstname,
                email = data.email,
                phone = data.phone,
                key = data.key,
                surl = data.surl,
                furl = data.furl
            )
        }



        viewModel.transactionDetails.observe(this) { response ->

            if (response == null) {
                Toast.makeText(this, "We’re verifying your payment status. Please check again after some time.", Toast.LENGTH_SHORT).show()
                return@observe
            }

            val amount = response.data?.netPayable ?: ""
            val txnId = response.data?.txnid ?: ""
            val dateTime = response.data?.mobileTransactionTimestamp ?: ""
            val paymentMode = response.data?.paymentMode.toString()


            if (response.status == true ) {
                when (response.data?.paymentStatus) {
                    "SUCCESS" -> {
                        openPaymentStatusScreen("SUCCESS", amount, txnId, dateTime, paymentMode)
                        preferenceManager.clearMobileTransactionId()
                    }

                    "FAILED" -> {
                        openPaymentStatusScreen("FAILED", amount, txnId, dateTime, paymentMode)
                        preferenceManager.clearMobileTransactionId()
                    }

                    "PENDING" -> {
                        openPaymentStatusScreen("PENDING", amount, txnId, dateTime, paymentMode)
                    }
                }

            } else {
                val message= response.message ?: "We couldn’t verify your payment status at this time. Please check again later."
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun openPaymentStatusScreen(
        status: String,
        amount: String,
        txnId: String,
        dateTime: String,
        paymentMode: String
    ) {
        val intent = when (status) {
            "SUCCESS" -> Intent(this, PaymentSuccessActivity::class.java)
            "FAILED" -> Intent(this, PaymentFailedActivity::class.java)
            else -> Intent(this, PaymentPendingActivity::class.java)
        }

        intent.putExtra("amount", amount)
        intent.putExtra("txnId", txnId)
        intent.putExtra("dateTime", dateTime)
        intent.putExtra("paymentMode", paymentMode)
        intent.putExtra("status", status)

        startActivity(intent)
        finish()
    }



    private fun getCurrentTime(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(Date())
    }

    private fun startPayment(
        txnId: String,
        amount: String,
        productInfo: String,
        name: String,
        email: String,
        phone: String,
        key: String,
        surl: String,
        furl: String
    ) {


        val paymentParams = PayUPaymentParams.Builder()
            .setAmount(amount)                   // Must be with .00
            .setIsProduction(false)                // Test environment
            .setKey(key)                      // PayU Test Merchant Key
            .setProductInfo(productInfo)
            .setFirstName(name)
            .setEmail(email)
            .setPhone(phone)
            .setTransactionId(txnId)
            .setSurl(surl)
            .setFurl(furl)
            .setUserCredential(email)
            .build()


        PayUCheckoutPro.open(
            this, paymentParams,
            object : PayUCheckoutProListener {

                override fun generateHash(
                    map: HashMap<String, String?>,
                    hashGenerationListener: PayUHashGenerationListener
                ) {
                    val hashName = map[CP_HASH_NAME] ?: return
                    val hashString = map[CP_HASH_STRING] ?: return

                    viewModel.hashData(appVersion = Constent.APP_VERSION, hashName = hashName, hashString = hashString){ serverHash ->

                        if (serverHash.isNullOrEmpty()) return@hashData

                        val resultMap = HashMap<String, String?>()
                        resultMap[hashName] = serverHash
                        hashGenerationListener.onHashGenerated(resultMap)
                    }
                }

                override fun onPaymentSuccess(response: Any) {
                    handlePaymentCallback()
                }


                override fun onPaymentFailure(response: Any) {
                    handlePaymentCallback()
                }

                override fun onPaymentCancel(isTxnInitiated: Boolean) {
                    Toast.makeText(this@PaymentActivity, "Payment Cancelled", Toast.LENGTH_LONG).show()
                }

                override fun onError(errorResponse: ErrorResponse) {
                    val msg = errorResponse.errorMessage ?: "Unknown Error"
                    Toast.makeText(this@PaymentActivity, msg, Toast.LENGTH_LONG).show()

                }
                override fun setWebViewProperties(webView: WebView?, bank: Any?) {

                }

            }
        )

    }


    private fun handlePaymentCallback() {
        val transactionId = preferenceManager.getMobileTransactionId()


        if (transactionId.isNullOrBlank()) {
            Toast.makeText(this, "Unable to fetch transaction reference", Toast.LENGTH_LONG).show()
            return
        }

        viewModel.transactionDetails(transactionId)
    }





    private fun generatePropertyPdf() {
        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
        val page = pdfDocument.startPage(pageInfo)
        val canvas = page.canvas

        val paint = Paint()
        paint.color = Color.BLACK
        paint.textSize = 16f
        paint.isFakeBoldText = true

        var y = 50

        // ============================
        //   PROPERTY DETAILS SECTION
        // ============================
        canvas.drawText("PROPERTY DETAILS", 200f, y.toFloat(), paint)
        y += 40

        paint.isFakeBoldText = false

        canvas.drawText("Property ID: ${binding.tvPropertyId.text}", 50f, y.toFloat(), paint)
        y += 28

        canvas.drawText("Reference No: ${binding.tvRefNo.text}", 50f, y.toFloat(), paint)
        y += 28

        canvas.drawText("Assessment Date: ${binding.tvAssessmentDate.text}", 50f, y.toFloat(), paint)
        y += 28

        canvas.drawText("Zone No: ${binding.tvZone.text}", 50f, y.toFloat(), paint)
        y += 28

        canvas.drawText("Ward: ${binding.tvWardName.text}", 50f, y.toFloat(), paint)
        y += 28

        canvas.drawText("Mohalla: ${binding.tvMohalla.text}", 50f, y.toFloat(), paint)
        y += 28

        canvas.drawText("Owner Name: ${binding.tvOwnerName.text}", 50f, y.toFloat(), paint)
        y += 28

        canvas.drawText("House No: ${binding.tvHouseNo.text}", 50f, y.toFloat(), paint)
        y += 28

        canvas.drawText("Address: ${binding.tvAddress.text}", 50f, y.toFloat(), paint)
        y += 28

        canvas.drawText("Total Area: ${binding.tvArea.text}", 50f, y.toFloat(), paint)
        y += 28

        canvas.drawText("Road Width: ${binding.tvRoadWidth.text}", 50f, y.toFloat(), paint)
        y += 28

        canvas.drawText("Yearly Tax: ${binding.tvYearlyTax2.text}", 50f, y.toFloat(), paint)
        y += 35

        // Divider Line
        paint.strokeWidth = 2f
        canvas.drawLine(40f, y.toFloat(), 550f, y.toFloat(), paint)
        y += 40


        // ============================
        //        TAX SUMMARY
        // ============================
        paint.isFakeBoldText = true
        paint.textSize = 17f
        canvas.drawText("TAX SUMMARY", 220f, y.toFloat(), paint)
        y += 40

        paint.isFakeBoldText = false
        paint.textSize = 16f

        canvas.drawText("Total ARV: ${binding.tvTotalArv.text}", 50f, y.toFloat(), paint)
        y += 28

//        canvas.drawText("Yearly Tax: ${binding.tvYearlyTax.text}", 50f, y.toFloat(), paint)
//        y += 28
//
//        canvas.drawText("Current Tax: ${binding.tvCurrentTax.text}", 50f, y.toFloat(), paint)
//        y += 28
//
//        canvas.drawText("Arrear: ${binding.tvArrear.text}", 50f, y.toFloat(), paint)
//        y += 28
//
//        canvas.drawText("Interest: ${binding.tvInterest.text}", 50f, y.toFloat(), paint)
//        y += 35

        // Highlight total
        paint.isFakeBoldText = true
        paint.textSize = 18f
        paint.color = Color.BLUE

//        canvas.drawText("Total Tax Due: ${binding.tvTotalTaxDue.text}", 50f, y.toFloat(), paint)
        y += 40

        // Finish page
        pdfDocument.finishPage(page)

        // ===== SAVE PDF TO DOWNLOAD FOLDER =====
        val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val file = File(directory, "Property_Details_${System.currentTimeMillis()}.pdf")

        try {
            pdfDocument.writeTo(FileOutputStream(file))
            Toast.makeText(this, "PDF Downloaded: ${file.name}", Toast.LENGTH_LONG).show()

            openPdf(file) // auto open

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error: " + e.message, Toast.LENGTH_LONG).show()
        }

        pdfDocument.close()
    }




    private fun openPdf(file: File) {
        val uri = FileProvider.getUriForFile(
            this,
            "${packageName}.provider",
            file
        )

        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(uri, "application/pdf")
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION

        try {
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "No PDF Viewer Installed", Toast.LENGTH_LONG).show()
        }
    }



}