package com.example.lda

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.lda.databinding.FragmentProfileBinding
import com.example.lda.houseTax.LauncherActivity
import com.example.lda.houseTax.utils.PreferenceManager
import com.example.lda.houseTax.viewmodel.PropertyDetailsViewmodel


class ProfileFragment : Fragment() {
    var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel : PropertyDetailsViewmodel
    lateinit var preferanceManager: PreferenceManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentProfileBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(requireActivity())[PropertyDetailsViewmodel::class.java]
        preferanceManager= PreferenceManager(requireContext())

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data=viewModel.dataList.value

        if (data != null) {
            binding.userName.text = data.data?.ownerDetails?.ownerName ?: ""
            binding.mobileNumber.text = "Mobile Number: ${data.data?.ownerDetails?.mobileNo}"
        }
        binding.userId.text = "PID: ${viewModel.pid.value}"


        binding.logout.setOnClickListener {
            preferanceManager.clearPropertyId()
            preferanceManager.login(false)
            val intent = Intent(requireActivity(), LauncherActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
        }
    }


}