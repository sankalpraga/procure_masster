package com.techcognics.procuremasster.presentation.auction.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController


@Composable
fun AuctionViewScreen(rfqId: Int, navController: NavHostController) {
    // Load auction details by rfqId from ViewModel or repository

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Auction Details", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(20.dp))

        Text("RFQ ID: $rfqId", style = MaterialTheme.typography.bodyLarge)

        // Add detailed data UI here

        Spacer(Modifier.height(20.dp))

        Button(onClick = { navController.popBackStack() }) {
            Text("Back")
        }
    }
}
