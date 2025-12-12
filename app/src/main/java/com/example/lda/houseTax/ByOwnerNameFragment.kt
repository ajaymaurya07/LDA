package com.example.lda.houseTax

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lda.databinding.FragmentByOwnerNameBinding


class ByOwnerNameFragment : Fragment() {

    private var _binding: FragmentByOwnerNameBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentByOwnerNameBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSearch.setOnClickListener {

            val ownerName = binding.etOwnerName.text.toString()
            val fatherName = binding.etFatherName.text.toString()
            val mobile = binding.etMobile.text.toString()

            // 👉 Open PaymentActivity
            val intent = Intent(requireContext(), OtpActivity::class.java)

            // Optional: Pass values
            intent.putExtra("owner_name", ownerName)
            intent.putExtra("father_name", fatherName)
            intent.putExtra("mobile_no", mobile)

            startActivity(intent)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
