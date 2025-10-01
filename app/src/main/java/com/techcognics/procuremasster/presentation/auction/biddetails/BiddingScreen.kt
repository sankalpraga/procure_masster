package com.techcognics.procuremasster.presentation.auction.biddetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.techcognics.procuremasster.data.remote.auctionpackage.bidsubmit.SupplierBidDetailsItem
import com.techcognics.procuremasster.presentation.auction.AuctionViewModel
import com.techcognics.procuremasster.presentation.base.UiState

@Composable
fun BiddingScreen(
    rfqId: Int,
    navController: NavHostController,
    viewModel: AuctionViewModel = hiltViewModel()
) {
    val bidDetailsState by viewModel.bidDetailsItem.collectAsState()
    var selectedItem by remember { mutableStateOf<SupplierBidDetailsItem?>(null) }
    var bidValue by remember { mutableStateOf("") }
    var submitting by remember { mutableStateOf(false) }
    var errorText by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(rfqId) { viewModel.loadBidDetails(rfqId) }

    Column(Modifier.padding(16.dp)) {
        Text("Your Bids", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))
        when (val state = bidDetailsState) {
            is UiState.Loading -> CircularProgressIndicator()
            is UiState.Error -> Text("Failed to load bids: ${(state as UiState.Error).message}", color = MaterialTheme.colorScheme.error)
            is UiState.Success -> {
                val bidList = (state as UiState.Success<List<SupplierBidDetailsItem>>).data ?: emptyList()
                if (bidList.isEmpty()) {
                    Text("No bids available.")
                } else {
                    LazyColumn {
                        items(bidList.size) { idx ->
                            val item = bidList[idx]
                            BidItemCard(item = item, onEdit = {
                                selectedItem = item
                                bidValue = ""
                                errorText = null
                            })
                        }
                    }
                }
            }
            else -> { /* Idle or empty state */ }
        }
    }

    // Dialog for entering new bid
    if (selectedItem != null) {
        AlertDialog(
            onDismissRequest = { if (!submitting) selectedItem = null },
            title = { Text("Bid for ${selectedItem?.itemNumber}") },
            text = {
                Column {
                    Text("Description: ${selectedItem?.itemDescription ?: ""}")
                    Spacer(Modifier.height(8.dp))
                    TextField(
                        value = bidValue,
                        onValueChange = { bidValue = it },
                        enabled = !submitting,
                        label = { Text("Enter your new bid") },
                        singleLine = true
                    )
                    if (errorText != null) {
                        Spacer(Modifier.height(6.dp))
                        Text(errorText!!, color = MaterialTheme.colorScheme.error)
                    }
                }
            },
            confirmButton = {
                Button(
                    enabled = !submitting,
                    onClick = {
                        val price = bidValue.toDoubleOrNull()
                        if (price == null) {
                            errorText = "Enter a valid number"
                            return@Button
                        }
                        submitting = true
                        errorText = null
                        viewModel.submitBidPrice(
                            rfqId = rfqId,
                            itemId = selectedItem!!.itemId,
                            bidPrice = price,
                            onSuccess = {
                                submitting = false
                                selectedItem = null
                                viewModel.loadBidDetails(rfqId)
                            },
                            onError = {
                                submitting = false
                                errorText = it
                            }
                        )
                    }
                ) {
                    if (submitting)
                        CircularProgressIndicator(Modifier.height(16.dp).padding(end = 8.dp), strokeWidth = 2.dp)
                    Text("Submit")
                }
            },
            dismissButton = {
                Button(
                    enabled = !submitting,
                    onClick = { selectedItem = null }
                ) { Text("Cancel") }
            }
        )
    }
}

@Composable
fun BidItemCard(item: SupplierBidDetailsItem, onEdit: (SupplierBidDetailsItem) -> Unit) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text(item.itemNumber, style = MaterialTheme.typography.titleMedium)
                Text(item.itemDescription, style = MaterialTheme.typography.bodyMedium)
                Text(item.status, style = MaterialTheme.typography.bodyMedium)
//                Text(item.ranking, style = MaterialTheme.typography.bodyMedium)
            }
            IconButton(onClick = { onEdit(item) }) {
                Icon(Icons.Default.Edit, contentDescription = "Edit Bid")
            }
        }
    }
}


