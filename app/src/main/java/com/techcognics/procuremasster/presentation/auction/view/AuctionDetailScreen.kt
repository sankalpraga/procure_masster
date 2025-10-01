package com.techcognics.procuremasster.presentation.auction.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.techcognics.procuremasster.presentation.auction.AuctionViewModel


@Composable
fun AuctionDetailScreen(
    rfqId: Int,
    navController: NavHostController,
    viewModel: AuctionViewModel = hiltViewModel()
) {

    val scrollState = rememberScrollState()

    val auctionDetails by viewModel.auctionDetails.collectAsState()
    LaunchedEffect(rfqId) { viewModel.loadAuctionDetails(rfqId) }

    Column(
        Modifier
            .fillMaxSize()
            .padding(12.dp)
            .verticalScroll(scrollState)

    ) {
        auctionDetails?.let { auction ->
            AuctionDetailsCard(auction)
        } ?: Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Loading auction details...")
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = MaterialTheme.typography.bodySmall)
        Text(value, style = MaterialTheme.typography.bodySmall)
    }
}

//
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.navigation.NavHostController
//import com.techcognics.procuremasster.presentation.auction.AuctionViewModel
//
//
//@Composable
//fun AuctionViewScreen(
//    rfqId: Int,
//    navController: NavHostController,
//    viewModel: AuctionViewModel = hiltViewModel()
//) {
//    val auctionDetailsState by viewModel.auctionDetails.collectAsState()
//
//    LaunchedEffect(rfqId) {
//        viewModel.loadAuctionDetails(rfqId)
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        auctionDetailsState?.let { auction ->
//
//            Text(
//                text = "Auction Details",
//                style = MaterialTheme.typography.headlineMedium
//            )
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 6.dp),
//                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//            ) {
//                Column(modifier = Modifier.padding(16.dp)) {
//                    Text(text = "Auction No: ${auction.auctionNo ?: "N/A"}", style = MaterialTheme.typography.titleMedium)
//                    Text(text = "Title: ${auction.auctionTitle}", style = MaterialTheme.typography.titleSmall)
//                    Text(text = "Buyer: ${auction.nameOfBuyer}", style = MaterialTheme.typography.bodyMedium)
//                    Text(text = "Category: ${auction.category}", style = MaterialTheme.typography.bodyMedium)
//                    Text(text = "Start Time: ${auction.auctionStartDateTime.replace('T', ' ')}", style = MaterialTheme.typography.bodySmall)
//                    Text(text = "End Time: ${auction.auctionEndDateTime.replace('T', ' ')}", style = MaterialTheme.typography.bodySmall)
//                    Text(text = "Status: ${auction.status}", style = MaterialTheme.typography.bodyMedium)
//                }
//            }
//        } ?: run {
//            Text("Loading auction details...")
//        }
//    }
//}
//
////package com.techcognics.procuremasster.presentation.auction.view
////
////import androidx.compose.foundation.layout.Column
////import androidx.compose.foundation.layout.Spacer
////import androidx.compose.foundation.layout.fillMaxSize
////import androidx.compose.foundation.layout.height
////import androidx.compose.foundation.layout.padding
////import androidx.compose.material3.Button
////import androidx.compose.material3.MaterialTheme
////import androidx.compose.material3.Text
////import androidx.compose.runtime.Composable
////import androidx.compose.ui.Modifier
////import androidx.compose.ui.unit.dp
////import androidx.navigation.NavHostController
////
////
////@Composable
////fun AuctionViewScreen(rfqId: Int, navController: NavHostController) {
////
////    Column(
////        modifier = Modifier
////            .fillMaxSize()
////            .padding(16.dp)
////    ) {
////        Text("Auction Details", style = MaterialTheme.typography.headlineMedium)
////
////        Spacer(Modifier.height(20.dp))
////
////        Text("RFQ ID: $rfqId", style = MaterialTheme.typography.bodyLarge)
////
////        // Add detailed data UI here
////
////        Spacer(Modifier.height(20.dp))
////
////        Button(onClick = { navController.popBackStack() }) {
////            Text("Back")
////        }
////    }
////}
