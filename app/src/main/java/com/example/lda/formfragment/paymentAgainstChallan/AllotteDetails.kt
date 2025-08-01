package com.example.lda.formfragment.paymentAgainstChallan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import com.example.lda.R
import com.example.lda.formfragment.interfacePart.FragmentChangeLister
import com.example.lda.formfragment.mutation.PropertyDetailsMutation
import com.example.lda.utils.setupDropdown
import com.google.android.material.button.MaterialButton


class AllotteDetails : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_allotte_details, container, false)

        val saveNextBtn: MaterialButton = view.findViewById(R.id.save_next_btn)

        saveNextBtn.setOnClickListener {
            // Call activity method to replace fragment
            (activity as? FragmentChangeLister)?.replaceWith(PaymentType(),"paymentTypeCardView")
        }

        return view
    }


}