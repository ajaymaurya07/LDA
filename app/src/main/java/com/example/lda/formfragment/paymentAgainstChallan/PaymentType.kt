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

        setupDropdown(requireContext(), paymentType, paymentTypeList)


        saveNextBtn.setOnClickListener {

        }

        return view
    }


}