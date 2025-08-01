package com.example.lda.formfragment.mutation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.EditText
import com.example.lda.R
import com.example.lda.formfragment.interfacePart.FragmentChangeLister
import com.example.lda.utils.DatePickerUtils
import com.example.lda.utils.setupDropdown
import com.google.android.material.button.MaterialButton


class PropertyDetailsMutation : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_property_details_mutation, container, false)

        // Access the AutoCompleteTextView
        val dateEditText: EditText = view.findViewById(R.id.date)
        val allotteePrefix: AutoCompleteTextView = view.findViewById(R.id.allottee_prefix)
        val landUses: AutoCompleteTextView = view.findViewById(R.id.land_use)
        val schemeName: AutoCompleteTextView = view.findViewById(R.id.scheme_name)
        val SaveNextBtn:MaterialButton = view.findViewById(R.id.save_next_btn)


        // Set options in dropdown
        val allotteePrefixList = listOf("Mr.", "Miss.", "Smt.","Shri.","Lt.")
        val landUseList = listOf("Residential", "Commercial", "Office", "Community", "Transport", "Plot", "Kiosk", "House", "Shop", "Other Open Area", "Amusements And Parks", "Industries", "Bazaar Street", "Viniyamatikaran", "Mishrit(Mixed)", "Agriculture area", "Baadh Prabhavit Kshetra", "Jalashay", "Chak road & chak naali", "School (Saikshik)", "Kuda Nistaran kendra", "No Construction Zone", "Nagariya nirmit kshetra", "Services", "Aarakshit ksehtra", "Kendriya Kriyaye", "Sansthagat", "Samudayik kendra")
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



        setupDropdown(requireContext(), allotteePrefix, allotteePrefixList)
        setupDropdown(requireContext(), landUses, landUseList)
        setupDropdown(requireContext(), schemeName, schemeNameList)



        DatePickerUtils.attachDatePicker(requireContext(), dateEditText)


        SaveNextBtn.setOnClickListener {
            // Call activity method to replace fragment
            (activity as? FragmentChangeLister)?.replaceWith(SupportingDocument(),"supportingDocumentCardView")
        }


        return view
    }


}