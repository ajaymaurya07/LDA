package com.example.lda.houseTax

import android.R
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.lda.databinding.FragmentByMobileNumberBinding
import com.example.lda.houseTax.viewmodel.SharedViewModel
import com.example.lda.model.UlbItem
import com.example.lda.utils.dataClass.PropertySearchRequest

class ByMobileNumberFragment : Fragment() {

    private var _binding: FragmentByMobileNumberBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: SharedViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentByMobileNumberBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeUlbList()

        binding.btnSearchMobile.setOnClickListener {
            val ulbId = viewModel.selectedUlb.value?.ulbId.orEmpty()
            val mobileNo = binding.etMobile.text.toString().trim()

            if (ulbId.isEmpty() || mobileNo.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Please select a ULB and enter the property name and mobile number.",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            viewModel.propertySearchRequest = PropertySearchRequest(
                propertyId = "",
                ownerName = "",
                fatherName = "",
                mobileNo = mobileNo,
                zoneId = "",
                wardId = "",
                mohallaId = "",
                chukNo = "",
                houseNo = "",
                ulbId = ulbId,
                searchType = "MOBILE"
            )
            viewModel.propertySearch()

        }
    }


    // for ulb
    private fun observeUlbList() {
        viewModel.ulbList.observe(viewLifecycleOwner) { list ->
            if (list.isNotEmpty()) {
                setupUlbDropdown(list)
            }
        }
    }
    private fun setupUlbDropdown(list: List<UlbItem>) {
        val ulbNames = list.map { it.ulbName }
        val adapter = ArrayAdapter(requireContext(), R.layout.simple_list_item_1, ulbNames)
        binding.etUlb.setAdapter(adapter)
        binding.etUlb.setOnItemClickListener { _, _, position, _ ->
            val selectedUlb = list[position]
            viewModel.setSelectedUlb(selectedUlb)

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
