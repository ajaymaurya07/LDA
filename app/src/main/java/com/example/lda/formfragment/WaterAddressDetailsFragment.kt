package com.example.lda.formfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.lda.databinding.FragmentWaterAddressDetailsBinding
import com.example.lda.formfragment.interfacePart.FragmentChangeLister

class WaterAddressDetailsFragment : Fragment() {
    private var _binding: FragmentWaterAddressDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentWaterAddressDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSpinners()
        setupCheckboxLogic()

        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.btnNext.setOnClickListener {
            (activity as? FragmentChangeLister)?.replaceWith(WaterConnectionDetailsFragment(), "connectionStep")
        }
    }

    private fun setupSpinners() {
        val zones = arrayOf("Zone 1", "Zone 2", "Zone 3", "Zone 4", "Zone 5")
        val wards = arrayOf("Ward 1", "Ward 2", "Ward 3", "Ward 4", "Ward 5")
        val mohallas = arrayOf("Mohalla A", "Mohalla B", "Mohalla C", "Mohalla D")

        val zoneAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, zones)
        binding.spinnerZone.setAdapter(zoneAdapter)
        binding.spinnerCommZone.setAdapter(zoneAdapter)

        val wardAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, wards)
        binding.spinnerWard.setAdapter(wardAdapter)
        binding.spinnerCommWard.setAdapter(wardAdapter)

        val mohallaAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, mohallas)
        binding.spinnerMohalla.setAdapter(mohallaAdapter)
        binding.spinnerCommMohalla.setAdapter(mohallaAdapter)
    }

    private fun setupCheckboxLogic() {
        binding.cbSameAsAbove.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.layoutCommunicationAddress.visibility = View.GONE
                // Optionally copy values here if needed for submission
            } else {
                binding.layoutCommunicationAddress.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
