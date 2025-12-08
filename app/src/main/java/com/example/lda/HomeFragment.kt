package com.example.lda

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.lda.constent.Constent
import com.example.lda.databinding.FragmentHomeBinding
import com.example.lda.eCourtUi.HearingActivity
import com.example.lda.eCourtUi.SummaryDashboard
import com.example.lda.eCourtUi.listner.ItemClickListner
import com.example.lda.eCourtUi.utils.DatePickerHelper
import com.example.lda.eCourtUi.utils.DropDownHelper
import com.example.lda.eCourtUi.utils.HearingItem
import com.example.lda.eCourtUi.utils.SharedPrefHelper
import com.example.lda.utils.dataClass.CaseItem
import com.example.lda.viewmodel.LoginViewModel

class HomeFragment : Fragment(), ItemClickListner {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var loginViewModel: LoginViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        loginViewModel = ViewModelProvider(requireActivity())[LoginViewModel::class.java]
        val userId = SharedPrefHelper.getUserId(requireContext())
        val userType = SharedPrefHelper.getUserType(requireContext())

        if (!userId.isNullOrEmpty() && !userType.isNullOrEmpty()) {
            loginViewModel.getCaseStatusCount(userId, Constent.VERSION, userType,"PWD")
        }


        // set date calender
        DatePickerHelper.setFromDate(requireContext(),binding.upcommingFromDate,binding.upcommingFromDateLL)
        DatePickerHelper.setToDate(requireContext(), binding.upcommingToDate,binding.upcommingToDateLl)

        DatePickerHelper.setPastFromDate(requireContext(),binding.pastFromDate,binding.pastFromDateLl)
        DatePickerHelper.setPastToDate(requireContext(),binding.pastToDate,binding.pastToDateLl)


        // add dropdown data
        val items = listOf("All Case Type", "Civil Case", "Criminal Case", "Family Case", "Other")
        DropDownHelper.setupDropdown(requireContext(), binding.autocompleteCaseType, items)
        DropDownHelper.setupDropdown(requireContext(), binding.pastAutocompleteCaseType, items)

        val totalCaseList = listOf(
            CaseItem("Crn Number:DLCT020123452023", "Case Type:Cr Case"),
            CaseItem("Crn Number:MHKO020009312015", "Case Type:R.C.S"),
            CaseItem("Crn Number:DLST020314162024", "Case Type:Cr Case"),
            CaseItem("Crn Number:DLCT020078912023", "Case Type:CC NI ACT"),
            CaseItem("Crn Number:DLST010056782024", "Case Type:Bail Matters"),

        )

        val pendingCaseList = listOf(
            CaseItem("Crn Number:DLCT020123452023", "Case Type:Cr Case"),
            CaseItem("Crn Number:MHKO020009312015", "Case Type:R.C.S"),
            CaseItem("Crn Number:DLST020314162024", "Case Type:Cr Case"),

        )

        val upcomingHearingList = listOf(
            CaseItem("Crn Number:DLCT020123452023", "Next Hearing: 15 Nov 2025"),
            CaseItem("Crn Number:MHKO020009312015", "Next Hearing: 18 Oct 2025"),
            CaseItem("Crn Number:DLST020314162024", "Next Hearing: 20 Oct 2025"),
            CaseItem("Crn Number:DLCT020078912023", "Next Hearing: 25 Oct 2025"),


        )


        val caNotFiledList = listOf(
            CaseItem("Crn Number:DLCT020123452023", "Case Type:Cr Case"),
            CaseItem("Crn Number:MHKO020009312015", "Case Type:R.C.S"),
            CaseItem("Crn Number:DLST020314162024", "Case Type:Cr Case"),

        )



        val interimOrderList = listOf(
            CaseItem("Crn Number:DLCT020123452023", "Case Type:Cr Case"),
            CaseItem("Crn Number:MHKO020009312015", "Case Type:R.C.S"),
            CaseItem("Crn Number:DLST020314162024", "Case Type:Cr Case"),

        )

        val rejoinderNotFiledList = listOf(
            CaseItem("Crn Number:DLCT020123452023", "Case Type:Cr Case"),
            CaseItem("Crn Number:MHKO020009312015", "Case Type:R.C.S"),
            CaseItem("Crn Number:DLST020314162024", "Case Type:Cr Case"),

        )

        val caFiledList = listOf(
            CaseItem("Crn Number:DLCT020123452023", "Case Type:Cr Case"),
            CaseItem("Crn Number:MHKO020009312015", "Case Type:R.C.S"),
            CaseItem("Crn Number:DLST020314162024", "Case Type:Cr Case"),
            CaseItem("Crn Number:DLCT020078912023", "Case Type:CC NI ACT"),
            CaseItem("Crn Number:DLST010056782024", "Case Type:Bail Matters"),
        )

        val rejoinderFiledList = listOf(
            CaseItem("Crn Number:DLCT020123452023", "Case Type:Cr Case"),
            CaseItem("Crn Number:MHKO020009312015", "Case Type:R.C.S"),
            CaseItem("Crn Number:DLST020314162024", "Case Type:Cr Case"),
            CaseItem("Crn Number:DLCT020078912023", "Case Type:CC NI ACT"),

        )



        binding.totalCaseDetailsCard.setOnClickListener {
            val intent = Intent(requireContext(), SummaryDashboard::class.java)
            intent.putParcelableArrayListExtra("case_list", ArrayList(totalCaseList))
            intent.putExtra("title", "Total Cases")
            startActivity(intent)
        }
        binding.totalPendingCaseCard.setOnClickListener {
            val intent = Intent(requireContext(), SummaryDashboard::class.java)
            intent.putParcelableArrayListExtra("case_list", ArrayList(pendingCaseList))
            intent.putExtra("title", "Total Pending Cases")
            startActivity(intent)
        }
        binding.upcommingHearingCard.setOnClickListener {
            val intent = Intent(requireContext(), SummaryDashboard::class.java)
            intent.putParcelableArrayListExtra("case_list", ArrayList(upcomingHearingList))
            intent.putExtra("title", "UpComing Hearing")
            startActivity(intent)
        }
        binding.caNotFiledCard.setOnClickListener {
            val intent = Intent(requireContext(), SummaryDashboard::class.java)
            intent.putParcelableArrayListExtra("case_list", ArrayList(caNotFiledList))
            intent.putExtra("title", "CA Not Filed")
            startActivity(intent)
        }
        binding.interimOrderCard.setOnClickListener {
            val intent = Intent(requireContext(), SummaryDashboard::class.java)
            intent.putParcelableArrayListExtra("case_list", ArrayList(interimOrderList))
            intent.putExtra("title", "Interim Order")
            startActivity(intent)
        }
        binding.rejoinderNotFiledCard.setOnClickListener {
            val intent = Intent(requireContext(), SummaryDashboard::class.java)
            intent.putParcelableArrayListExtra("case_list", ArrayList(rejoinderNotFiledList))
            intent.putExtra("title", "Rejoinder Not Filed")
            startActivity(intent)
        }
        binding.caFiledCard.setOnClickListener {
            val intent = Intent(requireContext(), SummaryDashboard::class.java)
            intent.putParcelableArrayListExtra("case_list", ArrayList(caFiledList))
            intent.putExtra("title", "CA Filed")
            startActivity(intent)
        }
        binding.rejoinderFiledCard.setOnClickListener {
            val intent = Intent(requireContext(), SummaryDashboard::class.java)
            intent.putParcelableArrayListExtra("case_list", ArrayList(rejoinderFiledList))
            intent.putExtra("title", "Rejoinder Filed")
            startActivity(intent)
        }

        return binding.root
    }





    override fun onItemClick(item: HearingItem) {
        val intent = Intent(requireContext(), HearingActivity::class.java)
        startActivity(intent)
    }


}