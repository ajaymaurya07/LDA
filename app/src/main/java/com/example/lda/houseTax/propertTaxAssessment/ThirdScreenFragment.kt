package com.example.lda.houseTax.propertTaxAssessment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.lda.R
import com.example.lda.databinding.FragmentFourthScreenBinding
import com.example.lda.houseTax.viewmodel.SharedViewModel
import com.google.android.material.textfield.TextInputEditText
import java.util.Calendar


class ThirdScreenFragment : Fragment() {

    private var _binding:FragmentFourthScreenBinding?=null
    private val binding get() = _binding!!

    private lateinit var etRentArea: TextInputEditText
    private lateinit var etOwnArea: TextInputEditText
    private lateinit var etTotalArea: TextInputEditText
    private lateinit var etConstructionYear: TextInputEditText
    private lateinit var etAgeOfConstruction: TextInputEditText
    private lateinit var etAreaRate: TextInputEditText
    private lateinit var viewModel: SharedViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding= FragmentFourthScreenBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]


        // Initialize views
        etRentArea = binding.etRentArea
        etOwnArea = binding.etOwnArea
        etTotalArea = binding.etTotalArea
        etConstructionYear= binding.etConstructionYear
        etAgeOfConstruction= binding.etAgeOfStructure
        etAreaRate= binding.etAreaRate


        val constructionYear=2010

        etConstructionYear.setText("$constructionYear")
        val age=getAgeOfConstruction(constructionYear)
        etAgeOfConstruction.setText("$age")

        viewModel.age.value=age

        val areaRate=viewModel.areaRate.value
        etAreaRate.setText("$areaRate")


        etRentArea.addTextChangedListener {
            viewModel.rentArea.value = it.toString()
        }

        etOwnArea.addTextChangedListener {
            viewModel.ownArea.value = it.toString()
        }


        viewModel.ageOfConstruction.value=age
        viewModel.constructionYear.value=binding.etConstructionYear.text.toString()

        val rgAreaType = binding.rgAreaType
        rgAreaType.check(R.id.rbCovered)

        setupAreaCalculation()

        return binding.root
    }

    // calculate total area
    private fun setupAreaCalculation() {

        val watcher = object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?, start: Int, count: Int, after: Int
            ) {}

            override fun onTextChanged(
                s: CharSequence?, start: Int, before: Int, count: Int
            ) {
                calculateTotalArea()
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        etRentArea.addTextChangedListener(watcher)
        etOwnArea.addTextChangedListener(watcher)
    }

    private fun calculateTotalArea() {
        val rent = etRentArea.text?.toString()?.toDoubleOrNull() ?: 0.0
        val own = etOwnArea.text?.toString()?.toDoubleOrNull() ?: 0.0

        val total = rent + own
        etTotalArea.setText(total.toString())
    }

    fun getAgeOfConstruction(constructionYear: Int): Int {

        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val age = currentYear - constructionYear

        return age
    }
}
