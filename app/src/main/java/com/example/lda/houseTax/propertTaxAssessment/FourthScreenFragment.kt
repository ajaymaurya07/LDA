package com.example.lda.houseTax.propertTaxAssessment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.lda.databinding.FragmentSixthBinding
import com.example.lda.houseTax.viewmodel.SharedViewModel


class FourthScreenFragment : Fragment() {

    private var _binding: FragmentSixthBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SharedViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSixthBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        observeData()

        return binding.root
    }

    private fun observeData() {

        viewModel.calculationResult.observe(requireActivity()){
            binding.etMrvOwner.setText("${it.mrvOwner}")
            binding.etMrvRented.setText("${it.mrvRented}")
            binding.etArvOwner.setText("${it.arvOwner}")
            binding.etArvRented.setText("${it.arvRented}")
            binding.etDepreciation.setText("${it.depreciation}")
            binding.etAppreciation.setText("${it.appreciation}")
            binding.etFinalArvOwner.setText("${it.finalArvOwner}")
            binding.etFinalArvRented.setText("${it.finalArvRented}")
            binding.etNetOwnerTax.setText("${it.ownerTax}")
            binding.etNetRentedTax.setText("${it.rentedTax}")
            binding.etTotalArv.setText("${it.finalArvOwner + it.finalArvRented}")
            binding.etTotalAssessmentTax.setText("${it.totalTax}")
        }
    }



}