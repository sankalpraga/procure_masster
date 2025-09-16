package com.techcognics.procuremasster.presentation.rfqdetails

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.techcognics.procuremasster.presentation.base.UiState
import com.techcognics.procuremasster.presentation.common.DateRangePickerModal
import com.techcognics.procuremasster.presentation.designsystem.UiStateHandler
import java.text.SimpleDateFormat
import java.util.*

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun RFQScreen(
    navController: NavHostController,
    viewModel: RFQViewModel = hiltViewModel()
) {
    val uiState by viewModel.listState.collectAsState(initial = UiState.Idle)
    val startDate by viewModel.startDate.collectAsState()
    val endDate by viewModel.endDate.collectAsState()

    var showDatePicker by remember { mutableStateOf(false) }
    val sdf = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }


    LaunchedEffect(Unit) {
       val today = Calendar.getInstance()
        val lastMonth = Calendar.getInstance().apply { add(Calendar.MONTH, -1) }
        val formattedToday = sdf.format(today.time)
        val formattedLastMonth = sdf.format(lastMonth.time)

        viewModel.updateDateRange(formattedLastMonth, formattedToday)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // 🔹 Button to open DateRangePickerModal
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { showDatePicker = true }
        ) {
            Text("Select Date Range\n$startDate → $endDate")
        }

        Spacer(Modifier.height(16.dp))

        UiStateHandler(
            state = uiState,
            onSuccess = { rfqs ->
                if (rfqs.isEmpty()) {
                    Text("No RFQs available")
                } else {
                    LazyColumn {
                        items(rfqs.size) { index ->
                            val rfq = rfqs[index]
                            RfqCard(
                                rfq = rfq,
                                onView = { selected ->
                                    navController.navigate("rfq_view/${selected.id}")
                                } ,
                                        onBid = { selected ->
                                    navController.navigate("rfq_stepper/${selected.rfqNumber}") // ✅ opens bid screen
                                }

                            )
                        }
                    }

                }
            },
            onRetry = { viewModel.updateDateRange(startDate, endDate) }
        )
    }

    // 🔹 Show modal only when triggered
    if (showDatePicker) {
        DateRangePickerModal(
            onDateRangeSelected = { range ->
                val (start, end) = range
                if (start != null && end != null) {
                    val formattedStart = sdf.format(Date(start))
                    val formattedEnd = sdf.format(Date(end))
                    viewModel.updateDateRange(formattedStart, formattedEnd)
                }
            },
            onDismiss = { showDatePicker = false }
        )
    }
}
