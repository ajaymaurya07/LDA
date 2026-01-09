package com.example.lda.houseTax.propertTaxAssessment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.lifecycle.ViewModelProvider
import com.example.lda.R
import com.example.lda.databinding.FragmentFirstScreenBinding
import com.example.lda.databinding.FragmentSixthBinding
import com.example.lda.houseTax.viewmodel.SharedViewModel


class FirstScreenFragment : Fragment() {

    private lateinit var viewModel: SharedViewModel
    var _binding: FragmentFirstScreenBinding ?=null
    val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstScreenBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        val rgRoadWidth =binding.rgRoadWidth
        val rgConstruction= binding.rgConstruction

        rgRoadWidth.setOnCheckedChangeListener { _, checkedId ->
            val roadWidth = when (checkedId) {
                rgRoadWidth.getChildAt(0).id -> 1   // < 12 m
                rgRoadWidth.getChildAt(1).id -> 2   // 12–24 m
                rgRoadWidth.getChildAt(2).id -> 3   // > 24 m
                else -> null
            }
            viewModel.roadWidth.value = roadWidth
        }

        rgConstruction.setOnCheckedChangeListener { _, checkedId ->
            val constructionType = when (checkedId) {
                rgConstruction.getChildAt(0).id -> "RCC" // RCC / RBC
                rgConstruction.getChildAt(1).id -> "KACHA" // KACHA
                else -> null
            }
            viewModel.constructionType.value=constructionType

        }

        viewModel.roadWidth.observe(viewLifecycleOwner) { value ->
            when (value) {
                1 -> rgRoadWidth.check(rgRoadWidth.getChildAt(0).id)
                2 -> rgRoadWidth.check(rgRoadWidth.getChildAt(1).id)
                3 -> rgRoadWidth.check(rgRoadWidth.getChildAt(2).id)
            }
        }

        viewModel.constructionType.observe(viewLifecycleOwner) { value ->
            when (value) {
                "RCC" -> rgConstruction.check(rgConstruction.getChildAt(0).id)
                "KACHA" -> rgConstruction.check(rgConstruction.getChildAt(1).id)
            }
        }

        viewModel.zone.value=binding.etZone.text.toString()
        viewModel.ward.value=binding.etWard.text.toString()
        viewModel.chk.value=binding.etChk.text.toString()



        return binding.root
    }
}
