package com.example.lda.houseTax

import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.lda.databinding.FragmentByMobileNumberBinding
import com.example.lda.houseTax.utils.PreferenceManager
import com.example.lda.houseTax.viewmodel.SharedViewModel
import com.example.lda.model.UlbItem
import com.example.lda.utils.DeviceUtils
import com.example.lda.utils.dataClass.PropertySearchRequest

class ByMobileNumberFragment : Fragment() {

    private var _binding: FragmentByMobileNumberBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: SharedViewModel
    private lateinit var preferenceManager: PreferenceManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentByMobileNumberBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        preferenceManager = PreferenceManager(requireContext())
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
            viewModel.propertySearch(
                loginMobileNumber = preferenceManager.getLoginMobileNumber().toString(),
                deviceId = DeviceUtils.getDeviceId(requireContext()),
                preferenceManager = preferenceManager
            )

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

        val sortedList = list.sortedBy { it.ulbName?.lowercase() }

        val adapter = object : ArrayAdapter<UlbItem>(
            requireContext(),
            R.layout.simple_list_item_1,
            sortedList.toMutableList()
        ) {

            override fun getFilter(): Filter {
                return object : Filter() {

                    override fun performFiltering(constraint: CharSequence?): FilterResults {
                        val results = FilterResults()

                        val filteredList = if (constraint.isNullOrEmpty()) {
                            sortedList
                        } else {
                            sortedList.filter {
                                it.ulbName
                                    ?.lowercase()
                                    ?.contains(constraint.toString().lowercase()) ?: false
                            }
                        }

                        results.values = filteredList
                        results.count = filteredList.size
                        return results
                    }

                    override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                        clear()
                        addAll(results?.values as List<UlbItem>)
                        notifyDataSetChanged()
                    }
                }
            }
        }

        binding.etUlb.setAdapter(adapter)
        binding.etUlb.threshold = 1

        binding.etUlb.setOnItemClickListener { parent, _, position, _ ->
            val selectedUlb = parent.getItemAtPosition(position) as UlbItem
            Log.d("TAG", "setupUlbDropdown: ${selectedUlb.ulbId}")
            viewModel.setSelectedUlb(selectedUlb)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
