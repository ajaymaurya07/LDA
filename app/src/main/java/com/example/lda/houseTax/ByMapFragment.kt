package com.example.lda.houseTax

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lda.R
import com.example.lda.databinding.FragmentByMapBinding
import com.example.lda.databinding.FragmentByMobileNumberBinding

class ByMapFragment : Fragment() {

    private var _binding: FragmentByMapBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentByMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Search button click → PaymentActivity
        binding.btnSearchMap.setOnClickListener {

            val intent = Intent(requireContext(), OtpActivity::class.java)
            startActivity(intent)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}