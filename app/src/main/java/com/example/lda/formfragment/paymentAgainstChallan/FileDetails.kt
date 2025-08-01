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


class FileDetails : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=  inflater.inflate(R.layout.fragment_file_details, container, false)
        val saveNextBtn: MaterialButton = view.findViewById(R.id.save_next_btn)

        val schemeName: AutoCompleteTextView = view.findViewById(R.id.scheme_name)


        val schemeNameList = listOf(
            "swarn jaynti nagar vistaar",
            "pala road kashi ram nagar yojna",
            "Swarn jaynti nagar yojna",
            "Lucknow Road Vikas Nagar Yojna",
            "shanti niketan avasiya yojna",
            "avantika Phase-2",
            "Avantika Phase-1",
            "Brij Vihar Yojna",
            "Transport Nagar Phase-1"
        )

        setupDropdown(requireContext(), schemeName, schemeNameList)

        saveNextBtn.setOnClickListener {
            // Call activity method to replace fragment
            (activity as? FragmentChangeLister)?.replaceWith(PlotDetails(),"plotDetailsCardView")
        }

        return view
    }


}