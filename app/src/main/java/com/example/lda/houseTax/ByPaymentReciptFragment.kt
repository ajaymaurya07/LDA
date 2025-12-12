package com.example.lda.houseTax

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lda.databinding.FragmentByPaymentReciptBinding


class ByPaymentReciptFragment : Fragment() {

    private var _binding: FragmentByPaymentReciptBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentByPaymentReciptBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSearchPayment.setOnClickListener {

            val receiptNo = binding.etReceipt.text.toString()
            val chequeNo = binding.etCheque.text.toString()
            val cardTxn = binding.etCard.text.toString()
            val bhimTxn = binding.etBhim.text.toString()

            // 👉 Open PaymentActivity
            val intent = Intent(requireContext(), OtpActivity::class.java)

            // Optional: Pass entered values
            intent.putExtra("receipt_no", receiptNo)
            intent.putExtra("cheque_no", chequeNo)
            intent.putExtra("card_txn", cardTxn)
            intent.putExtra("bhim_txn", bhimTxn)

            startActivity(intent)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
