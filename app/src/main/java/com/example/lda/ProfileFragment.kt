package com.example.lda

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lda.databinding.FragmentProfileBinding
import com.example.lda.eCourtUi.LoginActivity.LoginActivity
import com.example.lda.eCourtUi.utils.SharedPrefHelper


class ProfileFragment : Fragment() {
    var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentProfileBinding.inflate(inflater,container,false)

        val userId = SharedPrefHelper.getUserId(requireContext()).toString()
        val userName = SharedPrefHelper.getUserName(requireContext()).toString()
        val userType = SharedPrefHelper.getUserType(requireContext()).toString()
        val mobileNumber = SharedPrefHelper.getMobileNumber(requireContext()).toString()
        val department = SharedPrefHelper.getDepartment(requireContext()).toString()

        binding.userId.text="User ID: $userId"
        binding.userName.text="User Name: $userName"
        binding.userType.text="User Type: $userType"
        binding.mobileNumber.text="Mobile Number: $mobileNumber"
        binding.department.text="Department: $department"


        binding.logout.setOnClickListener {
            SharedPrefHelper.clearAll(requireContext())
            val intent= Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        return binding.root
    }


}