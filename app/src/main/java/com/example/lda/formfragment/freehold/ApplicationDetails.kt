package com.example.lda.formfragment.freehold

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.EditText
import com.example.lda.R
import com.example.lda.formfragment.interfacePart.FragmentChangeLister
import com.example.lda.formfragment.mutation.PropertyDetailsMutation
import com.example.lda.utils.AlertDialogHelper
import com.example.lda.utils.phoneEmailValidater.InputValidator
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

        // required textview
        val applicantName=view.findViewById<EditText>(R.id.applicant_name)
        val applicantFatherName=view.findViewById<EditText>(R.id.applicant_father_name)
        val applicantAddress=view.findViewById<EditText>(R.id.applicant_address)
        val applicantPropertyNo=view.findViewById<EditText>(R.id.applicant_property_no)
        val categoryName=view.findViewById<EditText>(R.id.category_name)
        val block=view.findViewById<EditText>(R.id.block)
        val areaOfHouse=view.findViewById<EditText>(R.id.area_of_house)
        val totalCostOfProperty=view.findViewById<EditText>(R.id.total_cost_of_property)
        val mobileNo=view.findViewById<EditText>(R.id.mobile_number)
        val emailId=view.findViewById<EditText>(R.id.email_id)



        val applicationTypeList= listOf("DA Scheme","Rehabilitation")

        setupDropdown(requireContext(), applicationType, applicationTypeList)

        mutationBtn.setOnClickListener {

            val phoneText = mobileNo.text.toString()
            val emailText = emailId.text.toString()

            val isValidPhoneNumber = InputValidator.isValidPhone(phoneText)
            val isValidMailId = InputValidator.isValidEmail(emailText)

            val check=requiredFieldCheck(applicantName.text.toString(),
                applicantFatherName.text.toString(),
                applicantAddress.text.toString(),
                applicantPropertyNo.text.toString(),
                categoryName.text.toString(),
                block.text.toString(),
                areaOfHouse.text.toString(),
                totalCostOfProperty.text.toString(),
                mobileNo.text.toString(),
                emailId.text.toString(),
                applicationType.text.toString()
            )

            fun showErrorDialog(message: String) {
                AlertDialogHelper.showAlertDialog(
                    requireContext(),
                    "Alert Message",
                    message,
                    "Ok", { dialog, _ -> dialog.dismiss() },
                    "Cancel", { dialog, _ -> dialog.dismiss() }
                )
            }

            if (phoneText.isNotEmpty() && !isValidPhoneNumber) {
                showErrorDialog("Phone Number Invalid!")
                return@setOnClickListener  // यहीं पर रुक जाएगा
            }

            if (emailText.isNotEmpty() && !isValidMailId) {
                showErrorDialog("Email Id Invalid!")
                return@setOnClickListener
            }

            if (check) {
                showErrorDialog("Plz Add All Mandatory(*) Fields Data!")
                return@setOnClickListener
            }
            // Call activity method to replace fragment
            (activity as? FragmentChangeLister)?.replaceWith(PropertyDetails(),"propertyDetailsCardView")
        }

        return view
    }



    fun requiredFieldCheck(applicantName:String,applicantFatherName:String,applicantAddress:String,
                           applicantPropertyNo:String,categoryName:String,block:String,
                           areaOfHouse:String,totalCostOfProperty:String,mobileNo:String,
                           emailId:String,applicationType:String):Boolean{


        if(applicantName.isEmpty() || applicantFatherName.isEmpty() || applicantAddress.isEmpty() ||
            applicantPropertyNo.isEmpty() || categoryName.isEmpty() || block.isEmpty() ||
            areaOfHouse.isEmpty() || totalCostOfProperty.isEmpty() || mobileNo.isEmpty() ||
            emailId.isEmpty() || applicationType.isEmpty()){

            return true
        }
        return false

    }


}