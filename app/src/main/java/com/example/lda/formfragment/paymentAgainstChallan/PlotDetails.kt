package com.example.lda.formfragment.paymentAgainstChallan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.EditText
import com.example.lda.R
import com.example.lda.formfragment.interfacePart.FragmentChangeLister
import com.example.lda.utils.AlertDialogHelper
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

        val sectorName: AutoCompleteTextView = view.findViewById(R.id.sector_name)

        // required textview
        val plotNo: EditText = view.findViewById(R.id.plot_no)


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

        setupDropdown(requireContext(), sectorName, schemeNameList)

        saveNextBtn.setOnClickListener {
            val check=requiredFieldCheck(sectorName.text.toString(),
                plotNo.text.toString()
            )

            if (check){
                return@setOnClickListener
            }
            // Call activity method to replace fragment
            (activity as? FragmentChangeLister)?.replaceWith(AllotteDetails(),"allotteeDetailsCardView")
        }

        return view
    }

    fun requiredFieldCheck(schemeName:String,fileNo:String):Boolean{

        if(schemeName.isEmpty() || fileNo.isEmpty()){
            AlertDialogHelper.showAlertDialog(
                requireContext(),
                "Alert Message",
                "Plz Add All Mandatory(*) Fields Data!",
                "Ok", { dialog, which ->
                    dialog.dismiss()
                },
                "Cancel", { dialog, which ->
                    // Action to take when user clicks 'No'
                    dialog.dismiss() // Close the dialog
                }
            )
            return true
        }
        return false

    }


}