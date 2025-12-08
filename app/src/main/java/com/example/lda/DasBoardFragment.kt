package com.example.lda

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.lda.constent.Constent
import com.example.lda.databinding.FragmentDasBoardBinding
import com.example.lda.eCourtUi.SummaryDashboard
import com.example.lda.eCourtUi.utils.CaseDetailsHelper
import com.example.lda.eCourtUi.viewAllActivity.ViewAllRecentFinalOrderActivity
import com.example.lda.eCourtUi.utils.DropDownHelper
import com.example.lda.eCourtUi.utils.ProgressBar
import com.example.lda.eCourtUi.utils.SharedPrefHelper
import com.example.lda.model.DesignationWiseCaseCount
import com.example.lda.model.IaDetailsCount
import com.example.lda.model.NextHearingResult
import com.example.lda.utils.AlertDialogHelper
import com.example.lda.viewmodel.LoginViewModel


class DasBoardFragment : Fragment() {
    private var _binding: FragmentDasBoardBinding? = null
    private val binding get() = _binding!!
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var progressDialog: ProgressBar
    private var nextHearingList: List<NextHearingResult?>? = null
    lateinit var userId:String
    lateinit var userType:String
    lateinit var department:String
    private var defaultSelectedCaseTypeUpComingHearing="CAPL"
    private var defaultDaysSelectedForUpComingHearing="Month"
    private var defaultDaysSelectedForInterimOrder="Month"
    private var defaultSelectedCaseTypeInterimOrder="CAPL"
    private var defaultDaysSelectedForFinalOrder="Month"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDasBoardBinding.inflate(inflater, container, false)
        loginViewModel = ViewModelProvider(requireActivity())[LoginViewModel::class.java]


        caseStatusCountObserver()
        distinctInterimOrderCategoryObserver()
        nextHearingCountObserver()
        iADetailsCountObserver()
        finalOrderCountObserver()

        userId = SharedPrefHelper.getUserId(requireContext()).toString()
        userType = SharedPrefHelper.getUserType(requireContext()).toString()
        department = SharedPrefHelper.getDepartment(requireContext()).toString()


        progressDialog = ProgressBar(requireActivity())

        if (CaseDetailsHelper.isNetworkAvailable(requireContext())){
            if (userId.isNotEmpty() && userType.isNotEmpty() && department.isNotEmpty()) {

                loginViewModel.getCaseStatusCount(userId, Constent.VERSION, userType,department)

                loginViewModel.getDistinctInterimOrderCategory(userId,Constent.VERSION,defaultDaysSelectedForInterimOrder,department,defaultSelectedCaseTypeInterimOrder)

                loginViewModel.getNextHearingCount(userId,Constent.VERSION,defaultDaysSelectedForUpComingHearing,defaultSelectedCaseTypeUpComingHearing,department)


                loginViewModel.getIaDetailsCount(userId,Constent.VERSION,department)

                loginViewModel.getFinalOrderCount(userId,Constent.VERSION,defaultDaysSelectedForFinalOrder,department)

            }
        }else{
            AlertDialogHelper.showAlertDialog(
                requireContext(),
                "Alert Message",
                "No internet connection. Please try again later.",
                "Ok", { dialog, which ->
                    dialog.dismiss()
                },
                "Cancel", { dialog, which ->
                    dialog.dismiss()
                }
            )
        }



        // Case Type Dropdown for upcoming hearing
        val itemsCaseType =DropDownHelper.itemsCaseType
        DropDownHelper.setupDropdown(requireContext(), binding.upcomingHearingCaseType, itemsCaseType, 0){ selectedItem ->
            defaultSelectedCaseTypeUpComingHearing=selectedItem
            loginViewModel.getNextHearingCount(userId,Constent.VERSION,defaultDaysSelectedForUpComingHearing,selectedItem,department)
        }

        // Case Type Dropdown for interim order
        DropDownHelper.setupDropdown(requireContext(), binding.recentInterimCaseType, itemsCaseType, 0){ selectedItem ->
            defaultSelectedCaseTypeInterimOrder=selectedItem
            loginViewModel.getDistinctInterimOrderCategory(userId,Constent.VERSION,defaultDaysSelectedForInterimOrder,department,defaultSelectedCaseTypeInterimOrder)
        }

        // time Dropdown for interim order
        val itemsDays = DropDownHelper.itemsDays
        DropDownHelper.setupDropdown(requireContext(), binding.intrimOrderDays, itemsDays, 1){
            defaultDaysSelectedForInterimOrder=it
            loginViewModel.getDistinctInterimOrderCategory(userId,Constent.VERSION,defaultDaysSelectedForInterimOrder,department,defaultSelectedCaseTypeInterimOrder)
        }

        // time Dropdown for upcoming hearing
        DropDownHelper.setupDropdown(requireContext(), binding.upcommingHearingDays, itemsDays, 1){
            defaultDaysSelectedForUpComingHearing=it
            loginViewModel.getNextHearingCount(userId,Constent.VERSION,it,defaultSelectedCaseTypeUpComingHearing,department)
        }

        // time dropdown final order
        DropDownHelper.setupDropdown(requireContext(), binding.finalOrderDays, itemsDays, 1){
            defaultDaysSelectedForFinalOrder=it
            loginViewModel.getFinalOrderCount(userId,Constent.VERSION,defaultDaysSelectedForFinalOrder,department)
        }



        binding.vieAllRecentFinalOrderButton.setOnClickListener {
            val intent = Intent(requireContext(), ViewAllRecentFinalOrderActivity::class.java)
            startActivity(intent)
        }

        loginViewModel.isLoading.observe(requireActivity()) { isLoading ->
            if (isLoading) {
                progressDialog.startLoadingDialog("Loading Data......")
            } else {
                progressDialog.dismissDialog()
            }
        }



        return binding.root
    }


    private fun caseStatusCountObserver(){


        loginViewModel.caseStatusCount.observe(requireActivity()){
            if (it?.statusCode=="200"){
                binding.tvTotalCases.text= it.result?.totalCases.toString()
                binding.tvTotalPendingCases.text= it.result?.pendingCases.toString()
                binding.tvTotalDisposedCases.text= it.result?.disposedCases.toString()
            }else{
                AlertDialogHelper.showAlertDialog(
                    requireContext(),
                    "Alert Message",
                    it.statusMessage.toString(),
                    "Ok", { dialog, which ->
                        dialog.dismiss()
                    },
                    "Cancel", { dialog, which ->
                        dialog.dismiss()
                    }
                )
            }

        }
        binding.totalCaseDetailsCard.setOnClickListener {
            if (binding.tvTotalCases.text!="0"){
                val intent = Intent(requireContext(), SummaryDashboard::class.java)
                intent.putExtra("title", "Total Cases")
                intent.putExtra("status", "Archived")
                startActivity(intent)
            }else{
                Toast.makeText(requireContext(), "Total Case data Not available!", Toast.LENGTH_SHORT).show()
            }

        }
        binding.totalPendingCaseCard.setOnClickListener {
            if (binding.tvTotalPendingCases.text!="0"){
                val intent = Intent(requireContext(), SummaryDashboard::class.java)
                intent.putExtra("title", "Total Pending Cases")
                intent.putExtra("status", "Pending")
                startActivity(intent)
            }else{
                Toast.makeText(requireContext(), "Total Pending data Not available!", Toast.LENGTH_SHORT).show()
            }

        }

        binding.totalDisposeCaseCard.setOnClickListener {
            if (binding.tvTotalDisposedCases.text!="0"){
                val intent = Intent(requireContext(), SummaryDashboard::class.java)
                intent.putExtra("title", "Total Disposed Cases")
                intent.putExtra("status", "Disposed")
                startActivity(intent)
            }else{
                Toast.makeText(requireContext(), "Total Disposed data Not available!", Toast.LENGTH_SHORT).show()
            }

        }
    }


    private fun distinctInterimOrderCategoryObserver(){


        loginViewModel.distinctInterimOrderCategoryListCount.observe(requireActivity()){
            if (it?.statusCode=="200"){
                binding.personAppearanceCount.text=it.result?.personAppearanceCount
                binding.fileAffidavitCount.text=it.result?.fileAffidavitCount
                binding.fileCounterAffidavitCount.text=it.result?.fileCounterAffidavitCount
                binding.makePaymentCount.text=it.result?.makePaymentCount
                binding.issueNoticeCount.text=it.result?.issueNoticeCount
                binding.otherCount.text=it.result?.otherCount
            }else{
                it.statusMessage?.let {
                    AlertDialogHelper.showAlertDialog(
                        requireContext(),
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
        binding.issueNoticeCard.setOnClickListener {
            val intent = Intent(requireContext(), SummaryDashboard::class.java)
            intent.putExtra("title", "Issue Notice")
            intent.putExtra("tab", "Issue_Notice")
            intent.putExtra("case_type", defaultSelectedCaseTypeInterimOrder)
            intent.putExtra("time", defaultDaysSelectedForInterimOrder)
            startActivity(intent)
        }
        binding.personAppearanceCard.setOnClickListener {
            val intent = Intent(requireContext(), SummaryDashboard::class.java)
            intent.putExtra("title", "Person Appearance")
            intent.putExtra("tab", "Person Appearance")
            intent.putExtra("case_type", defaultSelectedCaseTypeInterimOrder)
            intent.putExtra("time", defaultDaysSelectedForInterimOrder)
            startActivity(intent)
        }
        binding.makePaymentCard.setOnClickListener {
            val intent = Intent(requireContext(), SummaryDashboard::class.java)
            intent.putExtra("title", "Make Payment")
            intent.putExtra("tab", "Make Payment")
            intent.putExtra("case_type", defaultSelectedCaseTypeInterimOrder)
            intent.putExtra("time", defaultDaysSelectedForInterimOrder)
            startActivity(intent)
        }
        binding.fileCounterAffidavitCard.setOnClickListener {
            val intent = Intent(requireContext(), SummaryDashboard::class.java)
            intent.putExtra("title", "File Counter Affidavit")
            intent.putExtra("tab", "File Counter Affidavit")
            intent.putExtra("case_type", defaultSelectedCaseTypeInterimOrder)
            intent.putExtra("time", defaultDaysSelectedForInterimOrder)
            startActivity(intent)
        }
        binding.fileAffidavitCard.setOnClickListener {
            val intent = Intent(requireContext(), SummaryDashboard::class.java)
            intent.putExtra("title", "File Affidavit")
            intent.putExtra("tab", "File Affidavit")
            intent.putExtra("case_type", defaultSelectedCaseTypeInterimOrder)
            intent.putExtra("time", defaultDaysSelectedForInterimOrder)
            startActivity(intent)
        }
        binding.otherCard.setOnClickListener {
            val intent = Intent(requireContext(), SummaryDashboard::class.java)
            intent.putExtra("title", "Other")
            intent.putExtra("tab", "Other")
            intent.putExtra("case_type", defaultSelectedCaseTypeInterimOrder)
            intent.putExtra("time", defaultDaysSelectedForInterimOrder)
            startActivity(intent)
        }

    }



    private fun nextHearingCountObserver() {


        loginViewModel.nextHearingCountData.observe(requireActivity()) { response ->
            if (response?.statusCode == "200") {
                nextHearingList = response.result // <-- store the list for later use
                binding.hearingCount.text = response.result?.size.toString()
            }else{
                response.statusMessage?.let {
                    AlertDialogHelper.showAlertDialog(
                        requireContext(),
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

        // click listener
        binding.upcomingHearingCard.setOnClickListener {
            nextHearingList?.let {
                val intent = Intent(requireContext(), SummaryDashboard::class.java)
                intent.putParcelableArrayListExtra("case_list", ArrayList(it))
                intent.putExtra("title", "UpComing Hearing")
                startActivity(intent)
            } ?: run {
                Toast.makeText(requireContext(), "No hearing data available", Toast.LENGTH_SHORT).show()
            }
        }
    }



    private fun iADetailsCountObserver() {

        loginViewModel.iaDetailsCountData.observe(requireActivity()) { response ->
            if (response?.statusCode == "200") {

                val iaDetailsCount = response.result?.iaDetailsCount
                val designationWiseCaseCount = response.result?.designationWiseCaseCount

                if (iaDetailsCount != null) {
                    binding.caFiledCount.text = iaDetailsCount.ca_filed
                    binding.caNotFiledCount.text = iaDetailsCount.ca_not_filed
                    binding.rejoinderFiledCount.text = iaDetailsCount.rejoinder_filed
                    binding.rejoinderNotFiledCount.text = iaDetailsCount.rejoinder_not_filed
                    binding.otherIaCount.text = iaDetailsCount.other
                }
                if (designationWiseCaseCount != null) {
                    binding.chairmanCount.text=designationWiseCaseCount.Chairman
                    binding.additionalCommissionerCount.text=designationWiseCaseCount.aDDITIONALCOMMISSIONER
                    binding.otherDesignationCount.text=designationWiseCaseCount.other
                }

            } else {
                // Handle non-200 status or null response
                val message = response?.statusMessage ?: "Something went wrong. Please try again."
                AlertDialogHelper.showAlertDialog(
                    requireContext(),
                    "Alert Message",
                    message,
                    "Ok", { dialog, _ -> dialog.dismiss() },
                    "Cancel", { dialog, _ -> dialog.dismiss() }
                )
            }
        }

        // click listener
        binding.caFiledCard.setOnClickListener {
            val intent = Intent(requireContext(), SummaryDashboard::class.java)
            intent.putExtra("title", "Ca Filed")
            intent.putExtra("ia_details_tab", "ca_filed")
            startActivity(intent)
        }
        binding.caNotFiledCard.setOnClickListener {
            val intent = Intent(requireContext(), SummaryDashboard::class.java)
            intent.putExtra("title", "Ca Not Filed")
            intent.putExtra("ia_details_tab", "ca_not_filed")
            startActivity(intent)
        }
        binding.rejoinderFiledCard.setOnClickListener {
            val intent = Intent(requireContext(), SummaryDashboard::class.java)
            intent.putExtra("title", "Rejoinder Filed")
            intent.putExtra("ia_details_tab", "rejoinder_filed")
            startActivity(intent)
        }
        binding.rejoinderNotFiledCard.setOnClickListener {
            val intent = Intent(requireContext(), SummaryDashboard::class.java)
            intent.putExtra("title", "Rejoinder Not Filed")
            intent.putExtra("ia_details_tab", "rejoinder_not_filed")
            startActivity(intent)
        }
        binding.otherIaCard.setOnClickListener {
            val intent = Intent(requireContext(), SummaryDashboard::class.java)
            intent.putExtra("title", "Other")
            intent.putExtra("ia_details_tab", "Other")
            startActivity(intent)
        }

        binding.chairmanCard.setOnClickListener {
            val intent = Intent(requireContext(), SummaryDashboard::class.java)
            intent.putExtra("title", "Chairman")
            intent.putExtra("designation_details_tab", "Chairman")
            startActivity(intent)
        }
        binding.additionalCommissionerCard.setOnClickListener {
            val intent = Intent(requireContext(), SummaryDashboard::class.java)
            intent.putExtra("title", "ADDITIONAL COMMISSIONER")
            intent.putExtra("designation_details_tab", "ADDITIONAL COMMISSIONER")
            startActivity(intent)
        }
        binding.otherDesignationCard.setOnClickListener {
            val intent = Intent(requireContext(), SummaryDashboard::class.java)
            intent.putExtra("title", "Other")
            intent.putExtra("designation_details_tab", "Other")
            startActivity(intent)
        }


    }


    private fun finalOrderCountObserver() {


        loginViewModel.finalOrderCountData.observe(requireActivity()) { response ->
            if (response?.statusCode == "200") {
                val finalOrderCount = response.result
                if (finalOrderCount!=null){
                    val finalOrderMap= CaseDetailsHelper.finalOrderDetailMap(finalOrderCount)
                    CaseDetailsHelper.populateFinalOrderDetails(requireActivity(), binding.finalOrderContainer, finalOrderMap,defaultDaysSelectedForFinalOrder)
                }else {
                    // Handle case where API returned null for iaDetailsCount
                    AlertDialogHelper.showAlertDialog(
                        requireContext(),
                        "Data Missing",
                        "IA details are not available for this case.",
                        "Ok", { dialog, _ -> dialog.dismiss() },
                        "Cancel", { dialog, _ -> dialog.dismiss() }
                    )
                }
            }else{
                response.statusMessage?.let {
                    AlertDialogHelper.showAlertDialog(
                        requireContext(),
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