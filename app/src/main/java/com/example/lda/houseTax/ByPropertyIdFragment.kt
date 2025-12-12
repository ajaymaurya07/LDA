package com.example.lda.houseTax

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lda.databinding.FragmentByPropertyIdBinding

class ByPropertyIdFragment : Fragment() {

    private var _binding: FragmentByPropertyIdBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentByPropertyIdBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSearchProperty.setOnClickListener {

            val propertyId = binding.etPropertyId.text.toString()

            // 👉 Navigate to PaymentActivity
            val intent = Intent(requireContext(), OtpActivity::class.java)

            // optional: send entered property id to next screen
            intent.putExtra("property_id", propertyId)

            startActivity(intent)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
