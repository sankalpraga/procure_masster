//package com.techcognics.procuremasster.presentation.rfqdetails
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.rememberNavController
//import com.techcognics.procuremasster.data.remote.dto.RfqViewResponse
//import com.techcognics.procuremasster.presentation.base.UiState
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun RfqViewScreen(
//    navController: NavHostController,
//    rfqId: Int,
//    viewModel: RFQViewModel = hiltViewModel()
//) {
//    val state by viewModel.detailState.collectAsState(initial = UiState.Idle)
//
//    LaunchedEffect(rfqId) {
//        println("➡️ LaunchedEffect: Loading RFQ with id=$rfqId")
//        if (rfqId > 0) {
//            viewModel.loadRfqById(rfqId)
//        } else {
//          println("Invalid rfqId = $rfqId (navigation issue)")
//        }
//    }
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("RFQ Details") },
//                navigationIcon = {
//                    IconButton(onClick = { navController.popBackStack() }) {
//                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
//                    }
//                }
//            )
//        }
//    ) { padding ->
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(padding),
//            contentAlignment = Alignment.TopCenter
//        ) {
//            when (val uiState = state) {
//                is UiState.Idle -> {
//                    Text("⚪ Idle: Waiting to load RFQ")
//                }
//                is UiState.Loading -> {
//                    Column(
//                        modifier = Modifier.fillMaxSize(),
//                        verticalArrangement = Arrangement.Center,
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
//                        CircularProgressIndicator()
//                        Spacer(Modifier.height(8.dp))
//                        Text("⏳ Loading RFQ...")
//                    }
//                }
//                is UiState.Error -> {
//                    Column(
//                        modifier = Modifier.fillMaxSize(),
//                        verticalArrangement = Arrangement.Center,
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
//                        Text("❌ Error: ${uiState.message}", color = MaterialTheme.colorScheme.error)
//                    }
//                }
//                is UiState.Success -> {
//                    val rfq = uiState.data
//
//                    LazyColumn(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(16.dp),
//                        verticalArrangement = Arrangement.spacedBy(12.dp)
//                    ) {
//                        item {
//                            Text("RFQ Number: ${rfq.rfqNumber}")
//                            Text("Description: ${rfq.rfqDescription}")
//                            Text("Buyer: ${rfq.nameOfBuyer}")
//                            Text("Created: ${rfq.createdDate}")
//                            Text("Status: ${rfq.status}")
//                            Spacer(Modifier.height(16.dp))
//                            Text("Items", style = MaterialTheme.typography.titleMedium)
//                        }
//
//                        items(rfq.items) { item ->
//                            Card(
//                                modifier = Modifier.fillMaxWidth(),
//                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
//                            ) {
//                                Column(Modifier.padding(12.dp)) {
//                                    Text("Item Number: ${item.itemNumber}", style = MaterialTheme.typography.titleSmall)
//                                    Text("Description: ${item.description}", style = MaterialTheme.typography.bodyMedium)
//                                    Text("Qty: ${item.quantity}", style = MaterialTheme.typography.bodySmall)
//                                    Text("Unit: ${item.uom}", style = MaterialTheme.typography.bodySmall)
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
////
////@OptIn(ExperimentalMaterial3Api::class)
////@Preview(showBackground = true, showSystemUi = true)
////@Composable
////fun PreviewRfqViewScreen() {
////    val navController = rememberNavController()
////
////    val mockData = RfqViewResponse(
////        id = 1,
////        rfqNumber = "010920251238",
////        rfqDescription = "RFQ for Raw Materials",
////        nameOfBuyer = "Bhagesh Chincholi",
////        createdDate = "2025-09-01",
////        status = "CLOSED",
////        items = listOf(
////            RfqItemView(
////                itemNumber = "ITEM-001",
////                description = "STEELS",
////                quantity = 100,
////                uomName = "BOX"
////            ),
////            RfqItemView(
////                itemNumber = "ITEM-002",
////                description = "RODS",
////                quantity = 100,
////                uomName = "BOX"
////            )
////        ),
////        offerSubmissionDate = TODO(),
////        deliveryAddress = TODO(),
////        currency = TODO()
////    )
////
////    // Fake Success state UI (bypassing ViewModel)
////    Scaffold(
////        topBar = { TopAppBar(title = { Text("RFQ Details") }) }
////    ) { padding ->
////        LazyColumn(
////            modifier = Modifier
////                .fillMaxSize()
////                .padding(padding)
////                .padding(16.dp),
////            verticalArrangement = Arrangement.spacedBy(12.dp)
////        ) {
////            item {
////                Text("✅ RFQ Loaded!", style = MaterialTheme.typography.titleLarge)
////                Spacer(Modifier.height(12.dp))
////                Text("RFQ Number: ${mockData.rfqNumber}")
////                Text("Description: ${mockData.rfqDescription}")
////                Text("Buyer: ${mockData.nameOfBuyer}")
////                Text("Created: ${mockData.createdDate}")
////                Text("Status: ${mockData.status}")
////                Spacer(Modifier.height(16.dp))
////                Text("Items", style = MaterialTheme.typography.titleMedium)
////            }
////
////            items(mockData.items) { item ->
////                Card(
////                    modifier = Modifier.fillMaxWidth(),
////                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
////                ) {
////                    Column(Modifier.padding(12.dp)) {
////                        Text("Item Number: ${item.itemNumber}", style = MaterialTheme.typography.titleSmall)
////                        Text("Description: ${item.description}", style = MaterialTheme.typography.bodyMedium)
////                        Text("Qty: ${item.quantity}", style = MaterialTheme.typography.bodySmall)
////                        Text("Unit: ${item.uomName}", style = MaterialTheme.typography.bodySmall)
////                    }
////                }
////            }
////        }
////    }
////}
////
////
////
