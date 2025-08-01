package com.example.lda.formfragment.freehold

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.EditText
import com.example.lda.R
import com.example.lda.formfragment.interfacePart.FragmentChangeLister
import com.example.lda.formfragment.mutation.SupportingDocument
import com.example.lda.utils.DatePickerUtils
import com.example.lda.utils.setupDropdown
import com.google.android.material.button.MaterialButton


class PropertyDetails : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_property_details, container, false)

        val mutationBtn: MaterialButton = view.findViewById(R.id.save_next_btn)
        val dateEditText: EditText = view.findViewById(R.id.date)

        // Access the AutoCompleteTextView
        val currentUseProperty: AutoCompleteTextView = view.findViewById(R.id.current_use_property)
        val usePropertyAllotment: AutoCompleteTextView = view.findViewById(R.id.use_land_allotment)
        val usePropertyPlan: AutoCompleteTextView = view.findViewById(R.id.property_master_plan)
        val isPropertyMortgage:AutoCompleteTextView = view.findViewById(R.id.is_proprty_mortgage)

        val currentUsePropertyList= listOf("Residential","Non Residential","Others")
        val usePropertyAllotmentList= listOf("Residential","Non Residential","Others")
        val usePropertyPlanList=listOf("Residential", "Commercial", "Office", "Community", "Transport", "Plot", "Kiosk", "House", "Shop", "Other Open Area", "Amusements And Parks", "Industries", "Bazaar Street", "Viniyamatikaran", "Mishrit(Mixed)", "Agriculture area", "Baadh Prabhavit Kshetra", "Jalashay", "Chak road & chak naali", "School (Saikshik)", "Kuda Nistaran kendra", "No Construction Zone", "Nagariya nirmit kshetra", "Services", "Aarakshit ksehtra", "Kendriya Kriyaye", "Sansthagat", "Samudayik kendra")
        val isPropertyMortgageList=listOf("Yes","No")


        setupDropdown(requireContext(), currentUseProperty, currentUsePropertyList)
        setupDropdown(requireContext(), usePropertyAllotment, usePropertyAllotmentList)
        setupDropdown(requireContext(), usePropertyPlan, usePropertyPlanList)
        setupDropdown(requireContext(), isPropertyMortgage, isPropertyMortgageList)

        DatePickerUtils.attachDatePicker(requireContext(), dateEditText)


        mutationBtn.setOnClickListener {
            // Call activity method to replace fragment
            (activity as? FragmentChangeLister)?.replaceWith(SupportingDocument(),"supportingDocumentCardView")
        }

        return view
    }


}