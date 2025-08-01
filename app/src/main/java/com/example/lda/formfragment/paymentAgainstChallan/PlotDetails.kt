package com.example.lda.formfragment.paymentAgainstChallan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import com.example.lda.R
import com.example.lda.formfragment.interfacePart.FragmentChangeLister
import com.example.lda.utils.setupDropdown
import com.google.android.material.button.MaterialButton


class PlotDetails : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_plot_details, container, false)

        val saveNextBtn: MaterialButton = view.findViewById(R.id.save_next_btn)

        val schemeName: AutoCompleteTextView = view.findViewById(R.id.sector_name)


        val schemeNameList = listOf(
            "sector 1",
            "sector 2",
            "sector 3",
            "sector 4",
            "sector 5",
            "sector 6",
            "sector 7",
            "sector 8",
            "sector 9"
        )

        setupDropdown(requireContext(), schemeName, schemeNameList)

        saveNextBtn.setOnClickListener {
            // Call activity method to replace fragment
            (activity as? FragmentChangeLister)?.replaceWith(AllotteDetails(),"allotteeDetailsCardView")
        }

        return view
    }


}