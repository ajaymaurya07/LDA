package com.example.lda.houseTax

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.lda.databinding.FragmentLocationBasedBinding


class LocationBasedFragment : Fragment() {

    private var _binding: FragmentLocationBasedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocationBasedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDropdowns()

        binding.btnSearchLocation.setOnClickListener {
            val zone = binding.etZone.text.toString()
            val ward = binding.etWard.text.toString()
            val mohalla = binding.etMohalla.text.toString()
            val chuck = binding.etChuck.text.toString()
            val house = binding.etHouse.text.toString()

            // NO validation - as you said
            println("Search -> Zone:$zone, Ward:$ward, Mohalla:$mohalla, Chuck:$chuck, House:$house")
            val intent = Intent(requireContext(), OtpActivity::class.java)
            startActivity(intent)

        }
    }

    private fun setupDropdowns() {
        val zones = listOf("Zone 1", "Zone 2", "Zone 3")
        val wards = listOf("Ward 1", "Ward 2", "Ward 3")
        val mohallas = listOf("Mohalla A", "Mohalla B")
        val chucks = listOf("Chuck 1", "Chuck 2")
        val houses = listOf("House 10", "House 20")

        binding.etZone.setAdapter(ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, zones))
        binding.etWard.setAdapter(ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, wards))
        binding.etMohalla.setAdapter(ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, mohallas))
        binding.etChuck.setAdapter(ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, chucks))
        binding.etHouse.setAdapter(ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, houses))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
