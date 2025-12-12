package com.example.lda.houseTax

import android.R
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.lda.databinding.FragmentSearchByHouseNoBinding


class SearchByHouseNoFragment : Fragment() {

    private var _binding: FragmentSearchByHouseNoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchByHouseNoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupZoneDropdown()
        setupWardDropdown()

        binding.btnSearchHouse.setOnClickListener {

            val zone = binding.etZone.text.toString()
            val ward = binding.etWard.text.toString()
            val houseNo = binding.etHouseNo.text.toString()

            val intent = Intent(requireContext(), OtpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupZoneDropdown() {
        val zones = listOf("Zone 1", "Zone 2", "Zone 3", "Zone 4")

        val zoneAdapter = ArrayAdapter(
            requireContext(),
            R.layout.simple_list_item_1,
            zones
        )
        binding.etZone.setAdapter(zoneAdapter)
    }

    private fun setupWardDropdown() {
        val wards = listOf("Ward 1", "Ward 2", "Ward 3", "Ward 4", "Ward 5")

        val wardAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            wards
        )
        binding.etWard.setAdapter(wardAdapter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}