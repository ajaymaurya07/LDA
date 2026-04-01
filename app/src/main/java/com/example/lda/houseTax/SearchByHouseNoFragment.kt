package com.example.lda.houseTax

import android.R
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
import com.example.lda.databinding.FragmentSearchByHouseNoBinding
import com.example.lda.houseTax.utils.PreferenceManager
import com.example.lda.houseTax.viewmodel.SharedViewModel
import com.example.lda.model.UlbItem
import com.example.lda.model.WardItem
import com.example.lda.model.ZoneItem
import com.example.lda.utils.DeviceUtils
import com.example.lda.utils.dataClass.PropertySearchRequest


class SearchByHouseNoFragment : Fragment() {

    private var _binding: FragmentSearchByHouseNoBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: SharedViewModel
    private lateinit var preferenceManager: PreferenceManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchByHouseNoBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        preferenceManager = PreferenceManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeUlbList()
        observeZoneList()
        observeWardList()



        binding.btnSearchHouse.setOnClickListener {
            val ulbId = viewModel.selectedUlb.value?.ulbId.orEmpty()
            val zoneId = viewModel.selectedZone.value?.zoneId.orEmpty()
            val wardId = viewModel.selectedWard.value?.wardId.orEmpty()
            val houseNo = binding.etHouseNo.text.toString().trim()

            Log.d("TAG", "onViewCreated: $ulbId ,$zoneId ,$wardId ,$houseNo")
            if (ulbId.isEmpty() || zoneId.isEmpty() || wardId.isEmpty()  || houseNo.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Please select a ULB,Zone,Ward and enter House Number.",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            Log.d("TAG", "onViewCreated: $ulbId ,$zoneId ,$wardId")
            viewModel.propertySearchRequest = PropertySearchRequest(
                propertyId = "",
                ownerName = "",
                fatherName = "",
                mobileNo = "",
                zoneId = zoneId,
                wardId = wardId,
                mohallaId = "",
                chukNo = "",
                houseNo = houseNo,
                ulbId = ulbId,
                searchType = "HOUSE"
            )
            viewModel.propertySearch(preferenceManager.getLoginMobileNumber().toString())

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
        // Sort list A-Z by ulbName
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

            viewModel.clearZoneList()

            val selectedUlb = parent.getItemAtPosition(position) as UlbItem

            viewModel.setSelectedUlb(selectedUlb)

            selectedUlb.ulbId?.let {
                viewModel.zoneData(
                    loginMobileNumber = preferenceManager.getLoginMobileNumber().toString(),
                    ulbId = it,
                    deviceId = DeviceUtils.getDeviceId(requireContext()),
                    preferenceManager = preferenceManager
                )
            }
        }

    }





    // for zone list
    private fun observeZoneList() {
        viewModel.zoneList.observe(viewLifecycleOwner) { list ->
            setupZoneDropdown(list)
        }
    }
    private fun setupZoneDropdown(list: List<ZoneItem>) {

        if (list.isEmpty()) {
            binding.etZone.setText("", false)
            binding.etZone.setAdapter(null)
            return
        }

        val sortedList = list.sortedBy { it.zoneName?.lowercase() }

        val adapter = object : ArrayAdapter<ZoneItem>(
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
                                it.zoneName
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
                        addAll(results?.values as List<ZoneItem>)
                        notifyDataSetChanged()
                    }
                }
            }
        }

        binding.etZone.setAdapter(adapter)
        binding.etZone.threshold = 1

        binding.etZone.setOnItemClickListener { parent, _, position, _ ->

            viewModel.clearWardList()

            val selectedZone = parent.getItemAtPosition(position) as ZoneItem

            viewModel.setSelectedZone(selectedZone)

            val ulbId = viewModel.selectedUlb.value?.ulbId
            val zoneId = selectedZone.zoneId

            if (ulbId != null && zoneId != null) {
                viewModel.wardData(
                    loginMobileNumber = preferenceManager.getLoginMobileNumber().toString(),
                    ulbId = ulbId,
                    zoneId = zoneId,
                    deviceId = DeviceUtils.getDeviceId(requireContext()),
                    preferenceManager = preferenceManager
                )
            }
        }
    }






    // for ward list
    private fun observeWardList(){
        viewModel.wardList.observe(viewLifecycleOwner) { list ->
            setupWardDropdown(list)
        }
    }
    private fun setupWardDropdown(list: List<WardItem>) {

        if (list.isEmpty()) {
            binding.etWard.setText("", false)
            binding.etWard.setAdapter(null)
            return
        }

        val sortedList = list.sortedBy { it.wardName?.lowercase() }

        val adapter = object : ArrayAdapter<WardItem>(
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
                                it.wardName
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
                        addAll(results?.values as List<WardItem>)
                        notifyDataSetChanged()
                    }
                }
            }
        }

        binding.etWard.setAdapter(adapter)
        binding.etWard.threshold = 1

        binding.etWard.setOnItemClickListener { parent, _, position, _ ->

            viewModel.clearMohallaList()

            val selectedWard = parent.getItemAtPosition(position) as WardItem

            viewModel.setSelectedWard(selectedWard)
        }
    }

}