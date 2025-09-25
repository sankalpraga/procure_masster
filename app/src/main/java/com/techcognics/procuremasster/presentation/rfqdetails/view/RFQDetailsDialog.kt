package com.techcognics.procuremasster.presentation.rfqdetails.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.techcognics.procuremasster.data.remote.RFQ
import com.techcognics.procuremasster.data.remote.dto.RfqItem

@Composable
fun RfqDetailsDialog(
    rfq: RFQ,
    items: List<RfqItem>,
    onDismiss: () -> Unit,
    onDownload: () -> Unit = {}
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {}, // handled by close button
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {

                // 🔹 Header Row: Title + Close button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("RFQ #${rfq.rfqNumber}", style = MaterialTheme.typography.titleMedium)
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(Modifier.height(8.dp))

                // 🔹 RFQ description
                Text(rfq.rfqDescription, style = MaterialTheme.typography.bodyMedium)

                Spacer(Modifier.height(12.dp))

                // 🔹 Buyer + Date + Status
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
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

                // 🔹 Download Button
                Button(
                    onClick = onDownload,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Download Attachments")
                }

                Spacer(Modifier.height(16.dp))

                // 🔹 Items List
                Text("Items", style = MaterialTheme.typography.titleSmall)
                Spacer(Modifier.height(8.dp))

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.heightIn(max = 300.dp) // limit height for scroll
                ) {
                    items(items) { item ->
                        ItemBox(item)
                    }
                }
            }
        }
    )
}

@Composable
fun ItemBox(item: RfqItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text("Item: ${item.itemNumber}", style = MaterialTheme.typography.labelLarge)
            Text("Description: ${item.description}", style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(4.dp))
            Text("Quantity: ${item.quantity}", style = MaterialTheme.typography.bodySmall)
//            Text("UoM: ${item.uom.uomName}", style = MaterialTheme.typography.bodySmall)
        }
    }
}
