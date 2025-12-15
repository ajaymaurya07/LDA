package com.example.lda.houseTax.propertTaxAssessment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.lda.R
import com.example.lda.houseTax.viewmodel.SharedViewModel
import com.google.android.material.textfield.TextInputEditText


class ThirdScreenFragment : Fragment() {

    private lateinit var etRentArea: TextInputEditText
    private lateinit var etOwnArea: TextInputEditText
    private lateinit var etTotalArea: TextInputEditText
    private lateinit var viewModel: SharedViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_fourth_screen, container, false)

        viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]


        // Initialize views
        etRentArea = view.findViewById(R.id.etRentArea)
        etOwnArea = view.findViewById(R.id.etOwnArea)
        etTotalArea = view.findViewById(R.id.etTotalArea)

        etRentArea.addTextChangedListener {
            viewModel.rentArea.value = it.toString()
        }

        etOwnArea.addTextChangedListener {
            viewModel.ownArea.value = it.toString()
        }

        setupAreaCalculation()

        return view
    }

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
}
