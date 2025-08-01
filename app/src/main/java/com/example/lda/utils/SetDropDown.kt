package com.example.lda.utils


import android.content.Context
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView


fun setupDropdown(
    context: Context,
    autoCompleteTextView: AutoCompleteTextView,
    options: List<String>
) {
    val adapter = ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, options)
    autoCompleteTextView.setAdapter(adapter)
}
