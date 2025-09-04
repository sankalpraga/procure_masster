package com.techcognics.procuremasster.presentation.rfqdetails

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.techcognics.procuremasster.presentation.common.DateRangePickerModal
import com.techcognics.procuremasster.presentation.designsystem.UiStateHandler
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun RFQScreen(
    navController: NavHostController,
    viewModel: RFQViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val startDate by viewModel.startDate.collectAsState()
    val endDate by viewModel.endDate.collectAsState()

    var showDatePicker by remember { mutableStateOf(false) }
    val sdf = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }

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
                            RfqCard(rfq = rfqs[index])
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
