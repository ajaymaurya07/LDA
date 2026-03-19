package com.example.lda.houseTax

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
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
import com.example.lda.houseTax.data.database.AppDatabase
import com.example.lda.houseTax.data.database.entity.BillEntity
import com.example.lda.houseTax.utils.AlertDate
import com.example.lda.houseTax.utils.PreferenceManager
import com.example.lda.houseTax.viewmodel.PropertyDetailsViewmodel
import com.example.lda.serviceactivity.MutationService
import com.example.lda.serviceactivity.WaterSewerageServiceActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DashboradFragment : Fragment() {

    private var _binding: FragmentDashboradBinding? = null

    private val binding get() = _binding!!
    lateinit var viewModel : PropertyDetailsViewmodel
    private lateinit var sliderHandler: Handler
    private lateinit var sliderRunnable: Runnable
    lateinit var sharedPreferences: PreferenceManager



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboradBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[PropertyDetailsViewmodel::class.java]
        sharedPreferences = PreferenceManager(requireContext())
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClicks()
        setupSlider()

        if (sharedPreferences.getUserType() == "admin") {
            binding.etPid.text = "Admin"
            binding.propertySearch.root.visibility = View.VISIBLE
        } else {
            binding.propertySearch.root.visibility = View.GONE
            binding.etPid.text = "PID: ${viewModel.pid.value}"
        }


    }

    

    private fun setupClicks() {
        binding.propertyTaxCard.cardRoot.setOnClickListener {
            val intent = Intent(requireActivity(), PropertyDetailsActivity::class.java)
            startActivity(intent)
        }


        binding.propertyTaxAssesmentCard.cardPropertyAssesment.setOnClickListener {
            startActivity(Intent(requireActivity(), PropertyTaxAssessment::class.java))
        }

        binding.trackGrivanceCard.cardPropertyGrivance.setOnClickListener {
            startActivity(Intent(requireActivity(), MutationService::class.java).putExtra("text","Track Grivance"))
        }

        binding.propertySearch.cardPropertySearch.setOnClickListener {
            startActivity(Intent(requireActivity(), PropertySearchActivity::class.java))
        }

        binding.arvHistoryCard.cardArvHistory.setOnClickListener {
//            startActivity(Intent(requireActivity(), ArvHistoryActivity::class.java))
        }

        binding.mutationCard.cardMutation.setOnClickListener {
//            startActivity(Intent(requireActivity(), MutationService::class.java).putExtra("text","Name Transfer/Mutation"))
        }

        binding.waterSewerageCard.cardWaterSewerage.setOnClickListener {
//            startActivity(Intent(requireActivity(), WaterSewerageServiceActivity::class.java).putExtra("text","Water & Sewerage"))
        }
    }

    private fun setupSlider() {

        val billDate = viewModel.dataList.value?.data?.billDetails?.billDate // bill date "dd-mm-yyyy"
        val paymentDate= viewModel.dataList.value?.data?.currReceiptDetails?.getOrNull(0)?.paymentDate  // current receipt details payment date "-" or "dd-mm-yyyy"
        val financialYear= viewModel.dataList.value?.data?.billDetails?.finYear // current financial year

        Log.d("TAG", "setupSlider: $billDate")

        CoroutineScope(Dispatchers.IO).launch {
            val dao = AppDatabase.getDatabase(requireContext()).billDao()
            val count = dao.getCount()
            if (count == 0 && !billDate.isNullOrBlank() && !paymentDate.isNullOrBlank() && !financialYear.isNullOrBlank()) {
                dao.insertBill(
                    BillEntity(
                        financialYear = financialYear,
                        billDate = billDate,
                        paymentDate = paymentDate
                    )
                )
            }
        }

        val sliderList = mutableListOf<SliderItem>()
        if (paymentDate=="-") {
            val alertData = AlertDate.getPaymentMessage(billDate)
            if (alertData != null) {
                sliderList.add(SliderItem(R.drawable.ic_alert, alertData.title, alertData.message, alertData.color))
            }
        }
        sliderList.add(SliderItem(R.drawable.ic_alert, "Fast Online Payment", "Make instant payments using UPI or Debit Card.", Color.parseColor("#3F51B5")))


        binding.sliderViewPager.adapter = SliderAdapter(sliderList)

        sliderHandler = Handler(Looper.getMainLooper())

        sliderRunnable = object : Runnable {
            override fun run() {
                if (_binding == null) return
                val next = (binding.sliderViewPager.currentItem + 1) % sliderList.size
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
