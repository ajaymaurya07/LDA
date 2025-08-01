package com.example.lda.formfragment.freehold

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


class ApplicationDetails : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_application_details, container, false)
        val mutationBtn: MaterialButton = view.findViewById(R.id.save_next_btn)

        val applicationType: AutoCompleteTextView = view.findViewById(R.id.application_type)

        val applicationTypeList= listOf("DA Scheme","Rehabilitation")

        setupDropdown(requireContext(), applicationType, applicationTypeList)

        mutationBtn.setOnClickListener {
            // Call activity method to replace fragment
            (activity as? FragmentChangeLister)?.replaceWith(PropertyDetails(),"propertyDetailsCardView")
        }

        return view
    }


}