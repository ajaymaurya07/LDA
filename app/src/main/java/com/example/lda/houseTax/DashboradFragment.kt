package com.example.lda.houseTax

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.lda.R
import com.example.lda.databinding.FragmentDashboradBinding
import com.example.lda.houseTax.data.SliderAdapter
import com.example.lda.houseTax.data.SliderItem
import com.example.lda.houseTax.paymentDetails.ArvHistoryActivity
import com.example.lda.houseTax.viewmodel.PropertyDetailsViewmodel
import com.google.gson.Gson

class DashboradFragment : Fragment() {

    private var _binding: FragmentDashboradBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel : PropertyDetailsViewmodel
    private lateinit var sliderHandler: Handler
    private lateinit var sliderRunnable: Runnable





    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboradBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[PropertyDetailsViewmodel::class.java]
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClicks()
        setupSlider()
        binding.etPid.text = "PID: ${viewModel.pid.value}"

    }

    

    private fun setupClicks() {
        binding.propertyTaxCard.cardRoot.setOnClickListener {
            val response = viewModel.dataList.value
            if (response?.success == true) {
                val json = Gson().toJson(response.data)
                val intent = Intent(requireActivity(), PropertyDetailsActivity::class.java)
                intent.putExtra("property_data_json", json)
                intent.putExtra("pid",viewModel.pid.value)
                startActivity(intent)
            } else {
                Toast.makeText(requireContext(), response?.message ?: "Data not available", Toast.LENGTH_SHORT).show()
            }
        }


        binding.propertyTaxAssesmentCard.cardPropertyAssesment.setOnClickListener {
            startActivity(Intent(requireActivity(), PropertyTaxAssessment::class.java))
        }

        binding.trackGrivanceCard.cardPropertyGrivance.setOnClickListener {
            startActivity(Intent(requireActivity(), TrackGrivanceActivity::class.java))
        }

        binding.paymentHistoryCard.cardPaymentHistory.setOnClickListener {
            startActivity(Intent(requireActivity(), PaymentHistoryActivity::class.java))
        }

        binding.arvHistoryCard.cardArvHistory.setOnClickListener {
            startActivity(Intent(requireActivity(), ArvHistoryActivity::class.java))
        }
    }

    private fun setupSlider() {

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

        binding.sliderViewPager.adapter = SliderAdapter(sliderList)

        sliderHandler = Handler(Looper.getMainLooper())

        sliderRunnable = object : Runnable {
            override fun run() {
                if (_binding == null) return

                val next =
                    (binding.sliderViewPager.currentItem + 1) % sliderList.size
                binding.sliderViewPager.setCurrentItem(next, true)

                sliderHandler.postDelayed(this, 10000)
            }
        }

        sliderHandler.postDelayed(sliderRunnable, 3000)
    }



    override fun onDestroyView() {
        super.onDestroyView()

        if (::sliderHandler.isInitialized) {
            sliderHandler.removeCallbacks(sliderRunnable)
        }

        _binding = null
    }




}