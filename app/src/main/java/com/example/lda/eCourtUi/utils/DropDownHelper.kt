package com.example.lda.eCourtUi.utils

import android.content.Context
import android.util.DisplayMetrics
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView

object DropDownHelper {

    fun setupDropdown(
        context: Context,
        view: AutoCompleteTextView,
        items: List<String>,
        defaultIndex: Int? = null,
        widthPercentage: Float = 1.0f ,// default 100%
        onItemSelected: ((String) -> Unit)? = null
    ) {
        val adapter = ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, items)
        view.setAdapter(adapter)
        val displayMetrics: DisplayMetrics = context.resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        view.dropDownWidth = (screenWidth * widthPercentage).toInt()

        // Set default value if any
        defaultIndex?.let {
            if (it in items.indices) {
                view.setText(items[it], false)
            }
        }

        // Show dropdown on click
        view.setOnClickListener {
            view.showDropDown()
        }
        //trigger callback when item selected
        view.setOnItemClickListener { parent, _, position, _ ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            onItemSelected?.invoke(selectedItem)
        }
    }
    val itemsCaseType =listOf("CAPL","WRIA", "WRIC", "WPIL", "FAPL", "SPLA", "SAPL", "SPLAD", "FAFO", "A482", "ARPLD", "A227", "FAFOD", "SAPLD", "STRE", "ARPL", "WRIB", "CMRA", "WTAX", "CRLR", "COPP", "FAPLD", "ARCO", "CLRE", "IAPLD", "ARBT", "CMRAD", "ABAIL", "CROB", "IAPL", "CRLA", "CRLP")

    val itemsDays = listOf("Week", "Month", "Day")
}

