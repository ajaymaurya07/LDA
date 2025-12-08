package com.example.lda.eCourtUi

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lda.R
import com.example.lda.adaptor.CaseAdaptor
import com.example.lda.adaptor.FinalOrderAdaptor
import com.example.lda.adaptor.HearingAdaptor
import com.example.lda.adaptor.IaDetailAdaptor
import com.example.lda.adaptor.NotCaRejAdaptor
import com.example.lda.adaptor.RecentInterimOrderAdaptor
import com.example.lda.constent.Constent
import com.example.lda.databinding.ActivitySummaryDashboardBinding
import com.example.lda.eCourtUi.utils.ProgressBar
import com.example.lda.eCourtUi.utils.SharedPrefHelper
import com.example.lda.eCourtUi.utils.SystemBarsHelper.applySafeAreaInsets
import com.example.lda.model.NextHearingResult
import com.example.lda.utils.AlertDialogHelper
import com.example.lda.viewmodel.LoginViewModel
import com.google.android.material.appbar.MaterialToolbar

class SummaryDashboard : AppCompatActivity() {
    private lateinit var binding: ActivitySummaryDashboardBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var adapter: CaseAdaptor
    private lateinit var recentInterimOrderAdaptor: RecentInterimOrderAdaptor
    private lateinit var hearingAdaptor: HearingAdaptor
    private lateinit var iaDetailAdaptor: IaDetailAdaptor
    private lateinit var notCaRejAdaptor: NotCaRejAdaptor
    private lateinit var finalOrderAdaptor: FinalOrderAdaptor
    private lateinit var progressDialog: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_summary_dashboard)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        progressDialog = ProgressBar(this)


        // shared preference find data
        val userId = SharedPrefHelper.getUserId(this) ?: ""
        val userType = SharedPrefHelper.getUserType(this) ?: ""
        val department = SharedPrefHelper.getDepartment(this) ?: ""

        // Adapters init
        adapter = CaseAdaptor(mutableListOf(),this)
        recentInterimOrderAdaptor = RecentInterimOrderAdaptor(mutableListOf(),this)
        hearingAdaptor = HearingAdaptor(mutableListOf(),this)
        iaDetailAdaptor = IaDetailAdaptor(mutableListOf(),this)
        notCaRejAdaptor = NotCaRejAdaptor(mutableListOf(),this)
        finalOrderAdaptor = FinalOrderAdaptor(mutableListOf(),this)

        // RecyclerView common setup
        binding.recyclerView.layoutManager = LinearLayoutManager(this)


        // find case status from intent
        val caseStatus = intent.getStringExtra("status")
        val title = intent.getStringExtra("title")
        // recent interim order
        val tab = intent.getStringExtra("tab")
        val caseType=intent.getStringExtra("case_type")
        val time=intent.getStringExtra("time")
        // hearing list
        val upcomingHearingList = intent.getParcelableArrayListExtra<NextHearingResult>("case_list")

        // ia details tab
        val iaDetailsTab = intent.getStringExtra("ia_details_tab")
        val designationDetailsTab = intent.getStringExtra("designation_details_tab")
        // final order
        val finalOrderTab = intent.getStringExtra("final_order_tab")
        val selectedTime = intent.getStringExtra("time")




        // toolbar title set
        binding.toolBar.title = title

        if (!tab.isNullOrEmpty() && !caseType.isNullOrEmpty() && !time.isNullOrEmpty()){
            binding.recyclerView.adapter = recentInterimOrderAdaptor
            viewModel.getRecenetInterimOrderCaseDetailsByType(userId,Constent.VERSION,department,tab,time,caseType)
        }
        else if (!caseStatus.isNullOrEmpty()){
            binding.recyclerView.adapter = adapter
            viewModel.getAllCaseWithStatus(userId, Constent.VERSION, caseStatus, userType, department)
        }
        else if (!upcomingHearingList.isNullOrEmpty()){
            binding.recyclerView.adapter = hearingAdaptor
            hearingAdaptor.updateList(upcomingHearingList)
        }
        else if (!iaDetailsTab.isNullOrEmpty() && (iaDetailsTab=="ca_not_filed" || iaDetailsTab=="rejoinder_not_filed")){
            binding.recyclerView.adapter = notCaRejAdaptor
            viewModel.getCaseDetailsCaFiledNotFiledAndRejoinderNotFiled(userId, Constent.VERSION, userType, department,iaDetailsTab)
        }

        else if (!iaDetailsTab.isNullOrEmpty()){
            binding.recyclerView.adapter = iaDetailAdaptor
            viewModel.getIaCaseDetails(userId, Constent.VERSION, userType, department,iaDetailsTab)
        }
        else if (!designationDetailsTab.isNullOrEmpty()){
            binding.recyclerView.adapter = iaDetailAdaptor
            viewModel.getCaseDetailsByDesignation(userId, Constent.VERSION, userType, department,designationDetailsTab)
        }
        else if (!finalOrderTab.isNullOrEmpty() && !selectedTime.isNullOrEmpty()){
            binding.recyclerView.adapter=finalOrderAdaptor
            viewModel.getFinalOrderDetails(userId,Constent.VERSION,selectedTime,department,finalOrderTab)
        }

        observer()

        applySafeAreaInsets(
            rootView = findViewById(R.id.root),
            toolbar = findViewById(R.id.topAppBar),
            statusBarColor = getColor(R.color.primary),
            lightStatusBar = false
        )

        val toolbar = findViewById<MaterialToolbar>(R.id.toolBar)
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        viewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                progressDialog.startLoadingDialog("Loading Data......")
            } else {
                progressDialog.dismissDialog()
            }
        }
    }

    private fun observer() {
        // For All Cases
        viewModel.allCases.observe(this) { response ->
            if (response.statusCode == "200") {
                adapter.updateList(response.result)
            } else{
                response.statusMessage?.let {
                    AlertDialogHelper.showAlertDialog(
                        this,
                        "Alert Message",
                        it,
                        "Ok", { dialog, which ->
                            dialog.dismiss()
                        },
                        "Cancel", { dialog, which ->
                            dialog.dismiss()
                        }
                    )
                }
            }
        }


        // For Recent Interim Orders
        viewModel.RecentInterimOrderCaseDetailByTypetData.observe(this) { response ->
            if (response.statusCode == "200") {
                recentInterimOrderAdaptor.updateList(response.result)
            } else{
                response.statusMessage?.let {
                    AlertDialogHelper.showAlertDialog(
                        this,
                        "Alert Message",
                        it,
                        "Ok", { dialog, which ->
                            dialog.dismiss()
                        },
                        "Cancel", { dialog, which ->
                            dialog.dismiss()
                        }
                    )
                }
            }
        }



        // For Recent ia Details Orders
        viewModel.iaCaseDetailsData.observe(this) { response ->
            if (response.statusCode == "200") {
                iaDetailAdaptor.updateList(response.result)
            } else{
                response.statusMessage?.let {
                    AlertDialogHelper.showAlertDialog(
                        this,
                        "Alert Message",
                        it,
                        "Ok", { dialog, which ->
                            dialog.dismiss()
                        },
                        "Cancel", { dialog, which ->
                            dialog.dismiss()
                        }
                    )
                }
            }
        }


        viewModel.caseDetailsByDesignationData.observe(this) { response ->
            if (response.statusCode == "200") {
                iaDetailAdaptor.updateList(response.result)
            } else{
                response.statusMessage?.let {
                    AlertDialogHelper.showAlertDialog(
                        this,
                        "Alert Message",
                        it,
                        "Ok", { dialog, which ->
                            dialog.dismiss()
                        },
                        "Cancel", { dialog, which ->
                            dialog.dismiss()
                        }
                    )
                }
            }
        }





        // For Recent final Orders
        viewModel.finalOrderDetailsData.observe(this) { response ->
            if (response.statusCode == "200") {
                finalOrderAdaptor.updateList(response.result)
            } else{
                response.statusMessage?.let {
                    AlertDialogHelper.showAlertDialog(
                        this,
                        "Alert Message",
                        it,
                        "Ok", { dialog, which ->
                            dialog.dismiss()
                        },
                        "Cancel", { dialog, which ->
                            dialog.dismiss()
                        }
                    )
                }
            }
        }

        viewModel.caseDetailsCaFiledNotFiledAndRejoinderNotFiledData.observe(this) { response ->
            if (response.statusCode == "200") {
                notCaRejAdaptor.updateList(response.result)
            } else{
                response.statusMessage?.let {
                    AlertDialogHelper.showAlertDialog(
                        this,
                        "Alert Message",
                        it,
                        "Ok", { dialog, which ->
                            dialog.dismiss()
                        },
                        "Cancel", { dialog, which ->
                            dialog.dismiss()
                        }
                    )
                }
            }
        }
    }

}

