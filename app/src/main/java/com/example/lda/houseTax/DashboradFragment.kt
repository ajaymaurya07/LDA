package com.example.lda.houseTax

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lda.databinding.FragmentDashboradBinding

class DashboradFragment : Fragment() {

    private var _binding: FragmentDashboradBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboradBinding.inflate(inflater, container, false)

        binding.quickPaymentCard.setOnClickListener {
            val intent = Intent(activity, PropertySearchActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }



}