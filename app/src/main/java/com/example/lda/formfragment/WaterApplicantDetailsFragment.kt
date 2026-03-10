package com.example.lda.formfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.lda.databinding.FragmentWaterApplicantDetailsBinding
import com.example.lda.formfragment.interfacePart.FragmentChangeLister

class WaterApplicantDetailsFragment : Fragment() {
    private var _binding: FragmentWaterApplicantDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentWaterApplicantDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupTitleSpinner()

        binding.btnNext.setOnClickListener {
            (activity as? FragmentChangeLister)?.replaceWith(WaterAddressDetailsFragment(), "addressStep")
        }
    }

    private fun setupTitleSpinner() {
        val titles = arrayOf("S/o", "D/o", "W/o")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, titles)
        binding.spinnerTitle.setAdapter(adapter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
