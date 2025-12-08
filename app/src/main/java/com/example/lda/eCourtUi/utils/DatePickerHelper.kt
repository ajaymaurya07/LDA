package com.example.lda.eCourtUi.utils


import android.app.DatePickerDialog
import android.content.Context
import android.widget.LinearLayout
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*


object DatePickerHelper {

    private val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())


    fun setFromDate(context: Context, fromDateEditText: TextView,fromDateEditTextLayout: LinearLayout) {
        val currentDate = Date()
        fromDateEditText.setText(dateFormat.format(currentDate))

        fromDateEditTextLayout.setOnClickListener {
            val cal = Calendar.getInstance()
            cal.time = currentDate

            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val day = cal.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                context,
                { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                    val selectedCal = Calendar.getInstance()
                    selectedCal.set(selectedYear, selectedMonth, selectedDayOfMonth)
                    val selectedDate = selectedCal.time
                    fromDateEditText.setText(dateFormat.format(selectedDate))
                },
                year,
                month,
                day
            )

            // ✅ Past date allowed, Future disable
            datePickerDialog.datePicker.minDate = currentDate.time


            datePickerDialog.show()
        }
    }

    fun setToDate(context: Context, toDateEditText: TextView,toDateEditTextLayout: LinearLayout) {
        val currentDate = Date()

        val calendar = Calendar.getInstance()
        calendar.time = currentDate
        calendar.add(Calendar.DAY_OF_YEAR, 7)
        val afterWeekDate = calendar.time
        toDateEditText.setText(dateFormat.format(afterWeekDate))

        toDateEditTextLayout.setOnClickListener {
            val cal = Calendar.getInstance()
            cal.time = afterWeekDate

            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val day = cal.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                context,
                { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                    val selectedCal = Calendar.getInstance()
                    selectedCal.set(selectedYear, selectedMonth, selectedDayOfMonth)
                    val selectedDate = selectedCal.time
                    toDateEditText.setText(dateFormat.format(selectedDate))
                },
                year,
                month,
                day
            )

            // ✅ Past date allowed, Future disable
            datePickerDialog.datePicker.minDate = currentDate.time

            datePickerDialog.show()
        }
    }

    fun setPastToDate(context: Context, fromDateEditText: TextView,fromDateEditTextLayout: LinearLayout) {
        val currentDate = Date()
        fromDateEditText.setText(dateFormat.format(currentDate))

        fromDateEditTextLayout.setOnClickListener {
            val cal = Calendar.getInstance()
            cal.time = currentDate

            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val day = cal.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                context,
                { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                    val selectedCal = Calendar.getInstance()
                    selectedCal.set(selectedYear, selectedMonth, selectedDayOfMonth)
                    val selectedDate = selectedCal.time
                    fromDateEditText.setText(dateFormat.format(selectedDate))
                },
                year,
                month,
                day
            )

            // ✅ Past date allowed, Future disable
            datePickerDialog.datePicker.maxDate = currentDate.time

            datePickerDialog.show()
        }
    }


    fun setPastFromDate(context: Context, editText: TextView,editTextLayout: LinearLayout) {
        val currentDate = Date()

        val calendar = Calendar.getInstance()
        calendar.time = currentDate
        calendar.add(Calendar.DAY_OF_YEAR, -7)
        val weekBeforeDate = calendar.time
        editText.setText(dateFormat.format(weekBeforeDate))

        editTextLayout.setOnClickListener {
            val cal = Calendar.getInstance()
            cal.time = weekBeforeDate

            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val day = cal.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                context,
                { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                    val selectedCal = Calendar.getInstance()
                    selectedCal.set(selectedYear, selectedMonth, selectedDayOfMonth)
                    val selectedDate = selectedCal.time
                    editText.setText(dateFormat.format(selectedDate))
                },
                year,
                month,
                day
            )

            datePickerDialog.datePicker.maxDate = currentDate.time
            datePickerDialog.show()
        }
    }
}

