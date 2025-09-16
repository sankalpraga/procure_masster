package com.techcognics.procuremasster.presentation.rfqdetails

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.pager.*
import com.techcognics.procuremasster.data.remote.dto.RfqItemResponse
import com.techcognics.procuremasster.data.remote.dto.RfqViewResponse
import com.techcognics.procuremasster.presentation.base.UiState
import com.techcognics.procuremasster.presentation.openPdf
import com.techcognics.procuremasster.presentation.saveFileToDownloads
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun RfqViewScreenTabbed(
    navController: NavHostController,
    rfqId: Int,
    viewModel: RFQViewModel = hiltViewModel()
) {
    val detailState by viewModel.detailState.collectAsState()
    val tabs = listOf("Details", "Items")

    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(rfqId) {
        if (rfqId > 0) viewModel.loadRfqById(rfqId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("RFQ Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // 🔹 Tabs
            TabRow(selectedTabIndex = pagerState.currentPage) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
                        selected = pagerState.currentPage == index,
                        onClick = { scope.launch { pagerState.animateScrollToPage(index) } }
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            // 🔹 Content
            when (val state = detailState) {
                is UiState.Success<RfqViewResponse> -> {
                    val rfq = state.data
                    HorizontalPager(
                        count = tabs.size,
                        state = pagerState,
                        modifier = Modifier.fillMaxSize()
                    ) { page ->
                        when (page) {
                            0 -> DetailsTab(rfq)
                            1 -> ItemListWithSearch(rfq.items)
                        }
                    }
                }
                is UiState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is UiState.Error -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                        Spacer(Modifier.height(8.dp))
                        Text("Retrying...", style = MaterialTheme.typography.bodySmall)
                    }
                    LaunchedEffect(Unit) { viewModel.loadRfqById(rfqId) }
                }
                else -> Unit
            }
        }
    }
}

@Composable
private fun DetailsTab(rfq: RfqViewResponse) {
    val context = LocalContext.current
    val downloadViewModel: RFQDownloadViewModel = hiltViewModel()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // 🔹 RFQ Header + Compact Download button
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(Modifier.weight(1f)) {
                    Text("RFQ ${rfq.rfqNumber}", style = MaterialTheme.typography.titleMedium)
                    Text(rfq.rfqDescription, style = MaterialTheme.typography.bodySmall)
                }

                TextButton(  // 👈 Compact download
                    onClick = {
                        downloadViewModel.downloadRfqPdf(rfq.id) { bytes ->
                            val file = saveFileToDownloads(
                                context,
                                "${rfq.id}-RFQ-Report.pdf",
                                bytes
                            )
                            openPdf(context, file)
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Download,
                        contentDescription = "Download",
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.width(4.dp))
                    Text("Download", color = MaterialTheme.colorScheme.primary)
                }
            }
        }

        // 🔹 Info Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(
                    Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    InfoRow("Buyer", rfq.nameOfBuyer)
                    InfoRow("Created", rfq.createdDate)
                    InfoRow("Status", rfq.status)
                    InfoRow("Items", rfq.items.size.toString())
                    InfoRow("Delivery", rfq.deliveryAddress ?: "-")
                }
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String?) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value ?: "-",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun ItemListWithSearch(items: List<RfqItemResponse>) {
    var query by remember { mutableStateOf("") }

    val filtered = items.filter {
        it.itemNumber.contains(query, ignoreCase = true) ||
                it.description.contains(query, ignoreCase = true)
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Search items...") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(filtered) { item ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(Modifier.padding(12.dp)) {
                        InfoRow("Item Number", item.itemNumber)
                        InfoRow("Description", item.description)
                        InfoRow("Qty", item.quantity.toString())
                        InfoRow("Unit", item.uom.name)
                    }
                }
            }
        }
    }
}
