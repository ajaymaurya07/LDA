package com.example.lda.eCourtUi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lda.adaptor.CaseAdaptor
import com.example.lda.databinding.FragmentNotificationBinding
import com.example.lda.utils.dataClass.CaseItem

class NotificationFragment : Fragment() {

    lateinit var binding: FragmentNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNotificationBinding.inflate(inflater, container, false)


        val recyclerView = binding.recyclerView

        val caseList = listOf(
            CaseItem("Case No. 101/2025", "Next Hearing: 10 Oct 2025"),
            CaseItem("Case No. 105/2025", "CA not Filed"),
            CaseItem("Case No. 103/2025", "Interim Order: 13 Oct 2025"),
            CaseItem("Case No. 104/2025", "Rejoinder not Filed")
        )

//        val adapter = CaseAdaptor(caseList)
//        recyclerView.layoutManager = LinearLayoutManager(requireContext())
//        recyclerView.adapter = adapter


        return binding.root
    }


}