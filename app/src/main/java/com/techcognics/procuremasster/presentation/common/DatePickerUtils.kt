package com.techcognics.procuremasster.presentation.common

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.runtime.Composable
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Calendar
import java.util.Date


fun showDatePicker(context: Context,
                   initialDate: String? = null,
                   onDateSelected: (String) -> Unit) {
    val calender = Calendar.getInstance()
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    if (!initialDate.isNullOrEmpty()) {
        try {
            val parsed = simpleDateFormat.parse(initialDate)
            calender.time = parsed ?: Date()
        } catch (_: Exception) {
        }
    }

    val year = calender.get(Calendar.YEAR)
    val month = calender.get(Calendar.MONTH)
    val day = calender.get(Calendar.DAY_OF_MONTH)

    DatePickerDialog(context, { _, y, m, d ->
        val pickedDate = String.format("%04d-%02d-%02d", y, m + 1, d)
        onDateSelected(pickedDate)
    }, year, month, day).show()

}