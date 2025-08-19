package com.example.lda

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lda.databinding.FragmentHomeBinding
import com.example.lda.formfragment.marketing.MarketingActivity
import com.example.lda.formfragment.others.OthersActivity
import com.example.lda.formfragment.parking.ParkingActivity
import com.example.lda.formfragment.survey.PropertyOccupancyActivity
import com.example.lda.formfragment.survey.SurveyActivity
import com.example.lda.serviceactivity.MutationService

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.mutation.setOnClickListener {
            context?.let {
                val intent = Intent(it, MutationService::class.java)
                intent.putExtra("text","Name Transfer/Mutation")
                startActivity(intent)
            }
        }
        binding.freehold.setOnClickListener {
            context?.let {
                val intent = Intent(it, MutationService::class.java)
                intent.putExtra("text","Freehold")
                startActivity(intent)
            }
        }
        binding.parking.setOnClickListener {
            context?.let {
                val intent = Intent(it, ParkingActivity::class.java)
                intent.putExtra("text","Parking")
                startActivity(intent)
            }
        }

        binding.others.setOnClickListener {
            context?.let {
                val intent = Intent(it, OthersActivity::class.java)
                intent.putExtra("text","Others")
                startActivity(intent)
            }
        }

        binding.paymentAgainstChallan.setOnClickListener {
            context?.let {
                val intent = Intent(it, MutationService::class.java)
                intent.putExtra("text","Payment Against Challan")
                startActivity(intent)
            }
        }

        binding.leaseCollection.setOnClickListener {
            context?.let {
                val intent = Intent(it, SurveyActivity::class.java)
                startActivity(intent)
            }
        }

        binding.marketing.setOnClickListener {
            context?.let {
                val intent = Intent(it, MarketingActivity::class.java)
                startActivity(intent)
            }
        }

        binding.mortgage.setOnClickListener {
            context?.let {
                val intent = Intent(it, MutationService::class.java)
                intent.putExtra("text","Martgage")
                startActivity(intent)
            }
        }


        return binding.root
    }
}