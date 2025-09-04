package com.techcognics.procuremasster.presentation.rfqdetails

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.techcognics.procuremasster.data.remote.RFQ
import com.techcognics.procuremasster.data.remote.dto.RfqItem
import com.techcognics.procuremasster.data.remote.dto.Uom
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RfqViewScreen(
    navController: NavHostController, // 🔹 Used for navigation back
    rfq: RFQ                          // 🔹 The RFQ object including its items
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("RFQ Details") }, // 🔹 Title bar
                navigationIcon = {
                    // 🔹 Back button that pops navigation stack
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding) // Scaffold padding
                .padding(16.dp)   // Screen inner padding
        ) {
            // 🔹 RFQ Number and description
            Text("RFQ #${rfq.rfqNumber}", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(8.dp))
            Text(rfq.rfqDescription, style = MaterialTheme.typography.bodyMedium)

            Spacer(Modifier.height(12.dp))

            // 🔹 Buyer / Created Date / Status row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Buyer", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(rfq.nameOfBuyer, style = MaterialTheme.typography.bodySmall)
                }
                Column {
                    Text("Created", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(rfq.createdDate, style = MaterialTheme.typography.bodySmall)
                }
                Column {
                    Text("Status", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(rfq.status, style = MaterialTheme.typography.bodySmall)
                }
            }

            Spacer(Modifier.height(16.dp))

            // 🔹 Items header
            Text("Items", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))

            // 🔹 List of RFQ items
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(rfq.items) { item ->
                    ItemBox(item)
                }
            }
        }
    }
}

@Composable
fun ItemBox(item: RfqItem) {
    // 🔹 Each RFQ item shown as a card box
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text("Item: ${item.itemNumber}", style = MaterialTheme.typography.titleMedium)
            Text(item.description, style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(4.dp))
            Text("Quantity: ${item.quantity}", style = MaterialTheme.typography.bodySmall)
//            Text("UoM: ${item.uom.uomName}", style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRfqViewScreen() {
    val mockRfq = RFQ(
        id = 1,
        rfqNumber = "010920251238",
        rfqDescription = "RFQ for Raw Materials",
        nameOfBuyer = "Bhagesh Chincholi",
        offerSubmissionDate = "2025-09-01",
        deliveryAddress = "City Vista Pune",
        status = "OPEN",
        stageStatus = "APPROVED",
        createdDate = "2025-09-01",
        uniqueId = "3_121",
        rfqRefId = "3",
        bidStatus = "Bid Submitted",
        currency = "INR",
        items = listOf(
            RfqItem("ITEM-001", "STEELS", 100, Uom(1, "BOX")),
            RfqItem("ITEM-002", "RODS", 200, Uom(2, "PCS"))
        )
    )
    RfqViewScreen(navController = rememberNavController(), rfq = mockRfq)
}
