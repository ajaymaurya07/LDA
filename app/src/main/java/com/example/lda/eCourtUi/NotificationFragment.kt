package com.example.lda.eCourtUi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lda.databinding.FragmentNotificationBinding
import com.example.lda.houseTax.data.TransactionAdapter
import com.example.lda.houseTax.transationHistory.DetailTransactionHistoryActivity
import com.example.lda.houseTax.utils.PreferenceManager
import com.example.lda.houseTax.viewmodel.PaymentViewModel
import com.example.lda.utils.LoderHelper
import com.example.lda.utils.dataClass.TransactionItem

class NotificationFragment : Fragment() {

    private lateinit var binding: FragmentNotificationBinding
    private lateinit var adapter: TransactionAdapter
    private lateinit var viewModel: PaymentViewModel
    private lateinit var sharedPreferences: PreferenceManager
    private lateinit var loaderHelper: LoderHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        viewModel = ViewModelProvider(this)[PaymentViewModel::class.java]
        sharedPreferences = PreferenceManager(requireContext())
        loaderHelper = LoderHelper(requireActivity())
        
        setupRecyclerView()
        observeViewModel()
        
        sharedPreferences.getEmail()?.let { email ->
            viewModel.getTransactionsByEmail(email, requireContext(), sharedPreferences)
        } ?: run {
            Toast.makeText(requireContext(), "Email not found", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupRecyclerView() {
        adapter = TransactionAdapter(emptyList()) { transaction ->
            val intent = Intent(requireContext(), DetailTransactionHistoryActivity::class.java)
            intent.putExtra("data", transaction)
            startActivity(intent)
        }

        binding.rvTransactions.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@NotificationFragment.adapter
        }
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                loaderHelper.startLoadingDialog("Fetching transactions...")
            } else {
                loaderHelper.dismissDialog()
            }
        }

        viewModel.transactionsByEmail.observe(viewLifecycleOwner) { response ->
            if (response?.status == true) {
                val transactions = response.data?.map { data ->
                    TransactionItem(
                        title = "Tax Payment",
                        amount = "₹${data.paymentAmount ?: "0.00"}",
                        status = data.transactionStatus ?: "N/A",
                        billNo = data.billNo ?: "-",
                        propertyId = data.propertyId ?: "N/A",
                        txnId = data.txnId ?: "N/A",
                        date = data.dateTime ?: "N/A",
                        financialYear = data.financialYear ?: "N/A",
                        bankRefNo = data.bankRefNo ?: "N/A",
                        paymentMode = data.paymentMode ?: "N/A"
                    )
                } ?: emptyList()
                
                adapter.updateList(transactions)
                
                if (transactions.isEmpty()) {
                    Toast.makeText(requireContext(), "No transactions found", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), response?.message ?: "Failed to fetch transactions", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
