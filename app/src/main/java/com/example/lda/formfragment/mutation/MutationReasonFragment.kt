package com.example.lda.formfragment.mutation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import com.example.lda.R
import com.example.lda.formfragment.interfacePart.FragmentChangeLister
import com.example.lda.utils.AlertDialogHelper
import com.example.lda.utils.setupDropdown
import com.google.android.material.button.MaterialButton


class MutationReasonFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout
        val view = inflater.inflate(R.layout.fragment_mutation_reason, container, false)

        // Access the AutoCompleteTextView
        val mutationReason: AutoCompleteTextView = view.findViewById(R.id.mutation_reason)
        val propertyType: AutoCompleteTextView = view.findViewById(R.id.property_type)
        val mutationBtn: MaterialButton = view.findViewById(R.id.mutation_btn)


        // Set options in dropdown
        val mutationList = listOf("Sale of Property", "Death", "Gift")
        val propertyList = listOf("Leasehold", "Freehold")
        setupDropdown(requireContext(), mutationReason, mutationList)
        setupDropdown(requireContext(), propertyType, propertyList)

        mutationBtn.setOnClickListener {

            val mutationReasonValue = mutationReason.text.toString()
            val propertyTypeValue = propertyType.text.toString()
            if (mutationReasonValue.isEmpty() || propertyTypeValue.isEmpty()) {
                // Handle empty fields
                AlertDialogHelper.showAlertDialog(
                    requireContext(),
                    "Alert Message",
                    "Plz add All Mandatory(*) Fields Data!",
                    "Ok", { dialog, which ->
                        dialog.dismiss()
                    },
                    "Cancel", { dialog, which ->
                        // Action to take when user clicks 'No'
                        dialog.dismiss() // Close the dialog
                    }
                )
                return@setOnClickListener
            }
            // Call activity method to replace fragment
            (activity as? FragmentChangeLister)?.replaceWith(PropertyDetailsMutation(),"propertyDetailsCardView")
        }

        return view

    }
}