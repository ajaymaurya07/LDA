package com.example.lda.formfragment.mutation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import com.example.lda.R


class MutationReasonFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout
        val view = inflater.inflate(R.layout.fragment_mutation_reason, container, false)

        // Access the AutoCompleteTextView
        val autoComplete: AutoCompleteTextView = view.findViewById(R.id.autoComplete)

        // Set options in dropdown
        val options = listOf("Option 1", "Option 2", "Option 3")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, options)
        autoComplete.setAdapter(adapter)

        return view



    }


}