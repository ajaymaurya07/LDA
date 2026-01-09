package com.example.lda.houseTax.propertTaxAssessment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.lda.databinding.FragmentSecondScreenBinding
import com.example.lda.houseTax.viewmodel.SharedViewModel


class SecondScreenFragment : Fragment() {

    private var _binding: FragmentSecondScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: SharedViewModel




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        _binding = FragmentSecondScreenBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]


        val rgPropertyType = binding.rgPropertyType
        rgPropertyType.setOnCheckedChangeListener { _, checkedId ->

            val propertyType = when (checkedId) {
                rgPropertyType.getChildAt(0).id -> "Residency"   // Residency
                rgPropertyType.getChildAt(1).id -> "Non-Residency"   // Non-Residency
                else -> null
            }

            viewModel.propertyType.value=propertyType

        }


        viewModel.ownerName.value=binding.etOwnerName.text.toString()
        viewModel.ownerFatherName.value=binding.etFatherName.text.toString()
        viewModel.houseNo.value=binding.etHouseNo.text.toString()
        viewModel.propertyId.value=binding.etPropertyId.text.toString()
        viewModel.mobileNo.value=binding.etPhoneNo.text.toString()

        return binding.root
    }


}