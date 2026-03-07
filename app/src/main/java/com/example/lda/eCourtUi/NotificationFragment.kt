package com.example.lda.eCourtUi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lda.databinding.FragmentNotificationBinding
import com.example.lda.houseTax.data.TransactionAdapter
import com.example.lda.houseTax.transationHistory.DetailTransactionHistoryActivity
import com.example.lda.utils.dataClass.TransactionItem
class NotificationFragment : Fragment() {

    private lateinit var binding: FragmentNotificationBinding
    private lateinit var adapter: TransactionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {

        val list = listOf(

            TransactionItem(
                title = "Tax Payment",
                amount = "₹300.00",
                status = "SUCCESS",
                billNo = "E9972526746394",
                propertyId = "0999705005000018M",
                txnId = "TXN202601241835299017",
                date = "24 Jan 2026, 6:35 PM",
                financialYear = "2024-2025",
                bankRefNo = "921970026078975000",
                paymentMode = "UPI"
            ),

            TransactionItem(
                title = "Tax Payment",
                amount = "₹450.00",
                status = "FAILED",
                billNo = "E1234567890",
                propertyId = "12345000000001M",
                txnId = "TXN202601251200111222",
                date = "25 Jan 2026, 12:00 PM",
                financialYear = "2024-2025",
                bankRefNo = "921970026078975000",
                paymentMode = "Debit Card"
            ),

            TransactionItem(
                title = "Tax Payment",
                amount = "₹1,200.00",
                status = "PENDING",
                billNo = "E7788990011",
                propertyId = "567890123400001M",
                txnId = "TXN202601260910334455",
                date = "26 Jan 2026, 9:10 AM",
                financialYear = "2024-2025",
                bankRefNo = "921970026078975000",
                paymentMode = "Debit Card"
            ),

            TransactionItem(
                title = "Tax Payment",
                amount = "₹980.00",
                status = "SUCCESS",
                billNo = "E4455667788",
                propertyId = "223344556677889M",
                txnId = "TXN202601261430556677",
                date = "26 Jan 2026, 2:30 PM",
                financialYear = "2024-2025",
                bankRefNo = "921970026078975000",
                paymentMode = "Debit Card"
            ),

            TransactionItem(
                title = "Tax Payment",
                amount = "₹210.00",
                status = "FAILED",
                billNo = "E1112223334",
                propertyId = "111222333444555M",
                txnId = "TXN202601270845112233",
                date = "27 Jan 2026, 8:45 AM",
                financialYear = "2024-2025",
                bankRefNo = "921970026078975000",
                paymentMode = "Debit Card"

            ),

            TransactionItem(
                title = "Tax Payment",
                amount = "₹2,500.00",
                status = "SUCCESS",
                billNo = "E9998887776",
                propertyId = "998877665544332M",
                txnId = "TXN202601271200998877",
                date = "27 Jan 2026, 12:00 PM",
                financialYear = "2024-2025",
                bankRefNo = "921970026078975000",
                paymentMode = "Debit Card"
            ),

            TransactionItem(
                title = "Tax Payment",
                amount = "₹150.00",
                status = "PENDING",
                billNo = "E5566778899",
                propertyId = "445566778899001M",
                txnId = "TXN202601280930665544",
                date = "28 Jan 2026, 9:30 AM",
                financialYear = "2024-2025",
                bankRefNo = "921970026078975000",
                paymentMode = "Debit Card"
            ),


        )

        adapter = TransactionAdapter(list){
            val intent= Intent(requireContext(),DetailTransactionHistoryActivity::class.java)
            intent.putExtra("data",it)
            startActivity(intent)
        }

        binding.rvTransactions.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTransactions.adapter = adapter
    }
}