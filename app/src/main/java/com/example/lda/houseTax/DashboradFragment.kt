package com.example.lda.houseTax

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lda.R
import com.example.lda.databinding.FragmentDashboradBinding
import com.example.lda.houseTax.data.SliderAdapter
import com.example.lda.houseTax.data.SliderItem

class DashboradFragment : Fragment() {

    private var _binding: FragmentDashboradBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboradBinding.inflate(inflater, container, false)


        binding.propertyTaxCard.cardRoot.setOnClickListener {
            val intent = Intent(activity, PropertyDetailsActivity::class.java)
            startActivity(intent)
        }
        binding.propertyTaxAssesmentCard.cardPropertyAssesment.setOnClickListener {
            val intent = Intent(activity, PropertyTaxAssessment::class.java)
            startActivity(intent)
        }

        binding.trackGrivanceCard.cardPropertyGrivance.setOnClickListener {
            val intent = Intent(activity, TrackGrivanceActivity::class.java)
            startActivity(intent)
        }

        binding.paymentHistoryCard.cardPaymentHistory.setOnClickListener {
            val intent = Intent(activity, PaymentHistoryActivity::class.java)
            startActivity(intent)
        }

        val sliderList = listOf(
            SliderItem(
                R.drawable.ic_alert,
                "Last Date Reminder",
                "The last date to pay House Tax is 31 March 2025."
            ),

            SliderItem(
                R.drawable.ic_alert,
                "Fast Online Payment",
                "Make instant payments using UPI or Debit Card."
            )
        )


        val adapter = SliderAdapter(sliderList)
        binding.sliderViewPager.adapter = adapter

        // Auto slide every 3 seconds
        val handler = Handler(Looper.getMainLooper())
        val runnable = object : Runnable {
            override fun run() {
                val next = (binding.sliderViewPager.currentItem + 1) % sliderList.size
                binding.sliderViewPager.setCurrentItem(next, true)
                handler.postDelayed(this, 10000)
            }
        }

        handler.postDelayed(runnable, 3000)




        return binding.root
    }



}