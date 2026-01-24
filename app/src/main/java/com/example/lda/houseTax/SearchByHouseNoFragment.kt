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
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.lda.databinding.FragmentSearchByHouseNoBinding
import com.example.lda.houseTax.viewmodel.SharedViewModel
import com.example.lda.model.MohallaItem
import com.example.lda.model.UlbItem
import com.example.lda.model.WardItem
import com.example.lda.model.ZoneItem
import com.example.lda.utils.dataClass.PropertySearchRequest


class SearchByHouseNoFragment : Fragment() {

    private var _binding: FragmentSearchByHouseNoBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchByHouseNoBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
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
            viewModel.propertySearch()

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
        // Name list
        val ulbNames = sortedList.map { it.ulbName }
        val adapter = ArrayAdapter(requireContext(), R.layout.simple_list_item_1, ulbNames)
        binding.etUlb.setAdapter(adapter)
        binding.etUlb.threshold = 1

        binding.etUlb.setOnItemClickListener { parent, _, position, _ ->

            viewModel.clearZoneList()

            val selectedName = parent.getItemAtPosition(position).toString()

            val selectedUlb = sortedList.firstOrNull {
                it.ulbName == selectedName
            }

            selectedUlb?.let {
                Log.d("TAG", "setupUlbDropdown: $it")
                viewModel.setSelectedUlb(it)
                viewModel.zoneData(it.ulbId!!)
            }
        }

//        binding.etUlb.setOnItemClickListener { _, _, position, _ ->
//            viewModel.clearZoneList()
//            val selectedUlb = sortedList[position]
//            viewModel.setSelectedUlb(selectedUlb)
//            viewModel.zoneData(selectedUlb.ulbId!!)
//        }
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
        val zoneNames = list.map { it.zoneName }
        val adapter = ArrayAdapter(requireContext(), R.layout.simple_list_item_1, zoneNames)
        binding.etZone.setAdapter(adapter)
        binding.etZone.setOnItemClickListener { _, _, position, _ ->
            viewModel.clearWardList()
            val selectedZone = list[position]
            viewModel.setSelectedZone(selectedZone)
            viewModel.wardData(viewModel.selectedUlb.value?.ulbId!!,selectedZone.zoneId!!)
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
        val wardNames = list.map { it.wardName }
        val adapter = ArrayAdapter(requireContext(), R.layout.simple_list_item_1, wardNames)
        binding.etWard.setAdapter(adapter)
        binding.etWard.setOnItemClickListener { _, _, position, _ ->
            viewModel.clearMohallaList()
            val selectedWard = list[position]
            viewModel.setSelectedWard(selectedWard)
        }
    }






}