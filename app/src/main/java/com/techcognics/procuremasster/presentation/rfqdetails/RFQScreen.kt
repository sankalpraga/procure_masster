package com.techcognics.procuremasster.presentation.rfqdetails

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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

    var showOnlyOpen by remember { mutableStateOf(false) }

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
        // --- Row with Date Picker and Toggle Switch ---
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                modifier = Modifier.weight(1f),
                onClick = { showDatePicker = true }
            ) {
                Text("$startDate → $endDate")
//                Text("Select Date Range\n$startDate → $endDate")
            }
            Spacer(Modifier.width(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
//                Text("Open Only", style = MaterialTheme.typography.bodySmall)
                Spacer(Modifier.width(4.dp))
                Switch(
                    checked = showOnlyOpen,
                    onCheckedChange = { showOnlyOpen = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colorScheme.primary
                    )
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        // ---- Main List ----
        UiStateHandler(
            state = uiState,
            onSuccess = { rfqs ->
                // Filter list based on switch
                val displayRfqs = if (showOnlyOpen) {
                    rfqs.filter { it.status.equals("open", ignoreCase = true) }
                } else {
                    rfqs
                }
                if (displayRfqs.isEmpty()) {
                    Text("No RFQs available")
                } else {
                    LazyColumn {
                        items(displayRfqs.size) { index ->
                            val rfq = displayRfqs[index]
                            RfqCard(
                                rfq = rfq,
                                onView = { selected ->
                                    navController.navigate("rfq_view/${selected.id}")
                                },
                                onBid = { selected ->
                                    navController.navigate("rfqStepper/${selected.rfqNumber}")
                                }
                            )
                        }
                    }
                }
            },
            onRetry = { viewModel.updateDateRange(startDate, endDate) }
        )
    }

    // Date picker modal
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
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        // --- Row with Date Picker and Toggle Switch ---
//        Row(
//            Modifier.fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Button(
//                modifier = Modifier.weight(1f),
//                onClick = { showDatePicker = true }
//            ) {
//                Text("Select Date Range\n$startDate → $endDate")
//            }
//            Spacer(Modifier.width(16.dp))
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                Text("Open Only", style = MaterialTheme.typography.bodySmall)
//                Spacer(Modifier.width(4.dp))
//                Switch(
//                    checked = showOnlyOpen,
//                    onCheckedChange = { showOnlyOpen = it },
//                    colors = SwitchDefaults.colors(
//                        checkedThumbColor = MaterialTheme.colorScheme.primary
//                    )
//                )
//            }
//        }
//
//        Spacer(Modifier.height(16.dp))

//        UiStateHandler(
//            state = uiState,
//            onSuccess = { rfqs ->
//                if (rfqs.isEmpty()) {
//                    Text("No RFQs available")
//                } else {
//                    LazyColumn {
//                        items(rfqs.size) { index ->
//                            val rfq = rfqs[index]
//                            RfqCard(
//                                rfq = rfq,
//                                onView = { selected ->
//                                    navController.navigate("rfq_view/${selected.id}")
//                                } ,
//                                onBid = { selected ->
//                                    println("🟢 Bid clicked → rfqNumber=${selected.rfqNumber}, id=${selected.id}")
//                                    navController.navigate("rfqStepper/${selected.rfqNumber}")
//
//                                }
//
//
//
//
//                            )
//                        }
//                    }
//
//                }
//            },
//            onRetry = { viewModel.updateDateRange(startDate, endDate) }
//        )
//    }
//
//    // 🔹 Show modal only when triggered
//    if (showDatePicker) {
//        DateRangePickerModal(
//            onDateRangeSelected = { range ->
//                val (start, end) = range
//                if (start != null && end != null) {
//                    val formattedStart = sdf.format(Date(start))
//                    val formattedEnd = sdf.format(Date(end))
//                    viewModel.updateDateRange(formattedStart, formattedEnd)
//                }
//            },
//            onDismiss = { showDatePicker = false }
//        )
//    }
//}
