package com.techcognics.procuremasster.presentation.common

import android.content.Context
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*

/**
 * A reusable composable that opens a Material Date Range Picker
 * and shows the selected start & end dates.
 *
 * @param initialStart default start date (yyyy-MM-dd)
 * @param initialEnd default end date (yyyy-MM-dd)
 * @param modifier UI modifier
 * @param onRangeSelected callback when a date range is selected
 */
@Composable
fun DateRangePicker(
    initialStart: String,
    initialEnd: String,
    modifier: Modifier = Modifier,
    onRangeSelected: (String, String) -> Unit
) {
    val context = LocalContext.current
    var startDate by remember { mutableStateOf(initialStart) }
    var endDate by remember { mutableStateOf(initialEnd) }

    OutlinedButton(
        modifier = modifier,
        onClick = {
            showDateRangePicker(context) { start, end ->
                startDate = start
                endDate = end
                onRangeSelected(start, end)
            }
        }
    ) {
        Text(
            if (startDate.isNotEmpty() && endDate.isNotEmpty())
                "📅 $startDate → $endDate"
            else
                "Pick Date Range"
        )
    }
}

/**
 * Internal util that shows Material Date Range Picker
 */
private fun showDateRangePicker(
    context: Context,
    onRangeSelected: (String, String) -> Unit
) {
    val constraints = CalendarConstraints.Builder().build()

    val picker = MaterialDatePicker.Builder.dateRangePicker()
        .setTitleText("Select Date Range")
        .setCalendarConstraints(constraints)
        .build()

    (context as? FragmentActivity)?.let { activity ->
        picker.show(activity.supportFragmentManager, "date_range_picker")
        picker.addOnPositiveButtonClickListener { selection ->
            val start = selection.first ?: return@addOnPositiveButtonClickListener
            val end = selection.second ?: return@addOnPositiveButtonClickListener

            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val startDate = sdf.format(Date(start))
            val endDate = sdf.format(Date(end))

            onRangeSelected(startDate, endDate)
        }
    }
}
