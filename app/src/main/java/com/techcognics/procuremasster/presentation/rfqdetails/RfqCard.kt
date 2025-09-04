package com.techcognics.procuremasster.presentation.rfqdetails

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.techcognics.procuremasster.data.remote.RFQ

@Composable
fun RfqCard(
    rfq: RFQ,
    modifier: Modifier = Modifier,
    onView: (RFQ) -> Unit = {}
) {
    var showMenu by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 6.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            // 🔹 Top Row: RFQ Number on left, Status + Menu on right
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "RFQ #${rfq.rfqNumber}",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.width(1.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AssistChip(
                        onClick = { /* no-op */ },
                        label = { Text(rfq.status) },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = when {
                                rfq.status.equals("OPEN", true) -> MaterialTheme.colorScheme.primaryContainer
                                rfq.status.equals("CLOSED", true) -> MaterialTheme.colorScheme.errorContainer
                                else -> MaterialTheme.colorScheme.secondaryContainer
                            },
                            labelColor = when {
                                rfq.status.equals("OPEN", true) -> MaterialTheme.colorScheme.onPrimaryContainer
                                rfq.status.equals("CLOSED", true) -> MaterialTheme.colorScheme.onErrorContainer
                                else -> MaterialTheme.colorScheme.onSecondaryContainer
                            }
                        )
                    )

                    Box {
                        IconButton(onClick = { showMenu = true }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "More actions")
                        }
                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("View") },
                                onClick = {
                                    showMenu = false
                                    onView(rfq)
                                }
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            // 🔹 Description
            Text(rfq.rfqDescription, style = MaterialTheme.typography.bodyMedium)

            Spacer(Modifier.height(6.dp))

            // 🔹 Compact Info Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Buyer", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(rfq.nameOfBuyer, style = MaterialTheme.typography.bodySmall)
                }
                Column {
                    Text("Issue", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(rfq.createdDate, style = MaterialTheme.typography.bodySmall)
                }
                Column {
                    Text("Submission", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(rfq.offerSubmissionDate, style = MaterialTheme.typography.bodySmall)
                }
            }

            Spacer(Modifier.height(6.dp))

            Text(
                "Bid Status: ${rfq.bidStatus ?: "-"}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRfqCard() {
    val mockRfq = RFQ(
        id = 1,
        rfqNumber = "010920251238",
        rfqDescription = "RFQ for Raw Materials",
        nameOfBuyer = "Bhagesh Chincholi",
        offerSubmissionDate = "2025-09-01",
        deliveryAddress = "City Vista Pune",
        status = "CLOSED",
        stageStatus = "APPROVED",
        createdDate = "2025-09-01",
        uniqueId = "3_121",
        rfqRefId = "3",
        bidStatus = "Bid Submitted",
        currency = "INR"
    )
    RfqCard(rfq = mockRfq)
}
