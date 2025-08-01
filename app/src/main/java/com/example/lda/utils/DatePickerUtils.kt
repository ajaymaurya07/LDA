package com.example.lda.utils

import android.app.DatePickerDialog
import android.content.Context
import android.widget.EditText
import java.util.*

object DatePickerUtils {

    /**
     * Attaches a calendar date picker to the given EditText.
     * When date is selected, it will be shown in dd/MM/yyyy format.
     */
    fun attachDatePicker(context: Context, editText: EditText) {
        editText.isFocusable = false
        editText.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                context,
                { _, selectedYear, selectedMonth, selectedDay ->
                    val formattedDate = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear)
                    editText.setText(formattedDate)
                },
                year, month, day
            )

            datePickerDialog.show()
        }
    }
}