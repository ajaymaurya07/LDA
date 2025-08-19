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
import com.example.lda.formfragment.mutation.PropertyDetailsMutation
import com.example.lda.utils.AlertDialogHelper
import com.example.lda.utils.phoneEmailValidater.InputValidator
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

        // required textview
        val allotteeName: EditText = view.findViewById(R.id.allottee_name)
        val email: EditText = view.findViewById(R.id.email)
        val phoneNo: EditText = view.findViewById(R.id.phone_no)



        saveNextBtn.setOnClickListener {

            val phoneText = phoneNo.text.toString()
            val emailText = email.text.toString()

            val isValidPhoneNumber = InputValidator.isValidPhone(phoneText)
            val isValidMailId = InputValidator.isValidEmail(emailText)

            val check=requiredFieldCheck(
                allotteeName.text.toString(),
                email.text.toString(),
                phoneNo.text.toString()
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
            (activity as? FragmentChangeLister)?.replaceWith(PaymentType(),"paymentTypeCardView")
        }

        return view
    }

    fun requiredFieldCheck(schemeName:String,fileNo:String,phoneNo:String):Boolean{

        if(schemeName.isEmpty() || fileNo.isEmpty() || phoneNo.isEmpty()){

            return true
        }
        return false

    }


}