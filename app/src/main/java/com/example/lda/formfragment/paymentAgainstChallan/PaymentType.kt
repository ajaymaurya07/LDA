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


class PaymentType : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_payment_type, container, false)
        val saveNextBtn: MaterialButton = view.findViewById(R.id.save_next_btn)
        val paymentType: AutoCompleteTextView = view.findViewById(R.id.payment_type)
        val modeOfPayment: AutoCompleteTextView = view.findViewById(R.id.mode_of_payment)
        val amount:EditText=view.findViewById(R.id.amount)

        val paymentTypeList = listOf(
            "REGISTRATION MONEY FOR FLAT",
            "COST FOR FLAT",
            "INTEREST OF COST FOR FLAT",
            "INITIAL DEPOSIT FOR FLAT",
            "INTEREST OF INITIAL DEPOSIT FOR FLAT",
            "INSTALMENT FOR FLAT",
            "PENALTY OF INSTALMENT FOR FLAT",
            "LUMPSUM AMOUNT FOR FLAT",
            "INTEREST ON LUMPSUM AMOUNT FOR FLAT",
            "RETENTION MONEY FOR FLAT",
            "GROUND RENT FOR FLAT",
            "INTEREST OF GROUND RENT FOR FLAT",
            "SERVICE CHARGE FOR FLAT",
            "INTEREST OF SERVICE CHARGE FOR FLAT",
            "ANY OTHER CHARGE FOR FLAT",
            "INTEREST OF ANY OTHER CHARGE FOR FLAT",
            "CANCELLATION/SURRENDER CHARGE FOR FLAT",
            "CONVERSION CHARGES LEASEHOLD TO FREEHOLD",
            "PROCESSING FEE FOR CONVERSION OF FLATS"
        )

        val modeOfPaymentList= listOf("RTGS/NEFT","Net Banking/SBI NEFT")

        setupDropdown(requireContext(), paymentType, paymentTypeList)
        setupDropdown(requireContext(), modeOfPayment, modeOfPaymentList)


        saveNextBtn.setOnClickListener {

            val check=requiredFieldCheck(paymentType.text.toString(),
                modeOfPayment.text.toString(),
                amount.text.toString()
            )

            if (check){
                return@setOnClickListener
            }
            AlertDialogHelper.showAlertDialog(
                requireContext(),
                "Alert Message",
                "Challan Generated Successfully!",
                "Ok", { dialog, which ->
                    dialog.dismiss()
                    backActivity()
                },
                "Cancel", { dialog, which ->
                    dialog.dismiss()
                    backActivity()
                }
            )

        }

        return view
    }

    fun requiredFieldCheck(schemeName:String,fileNo:String,amount:String):Boolean{

        if(schemeName.isEmpty() || fileNo.isEmpty() || amount.isEmpty()){
            AlertDialogHelper.showAlertDialog(
                requireContext(),
                "Alert Message",
                "Plz Add All Mandatory(*) Fields Data!",
                "Ok", { dialog, which ->
                    dialog.dismiss()
                },
                "Cancel", { dialog, which ->
                    dialog.dismiss()
                }
            )
            return true
        }
        return false

    }

    fun backActivity(){
        val fm = requireActivity().supportFragmentManager
        if (fm.backStackEntryCount >= 4) {
            repeat(4) { fm.popBackStack() }
        } else {
            requireActivity().finish() // If less than 3 in stack, just finish
        }
    }


}