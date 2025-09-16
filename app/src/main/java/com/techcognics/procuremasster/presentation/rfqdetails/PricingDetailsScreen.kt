package com.techcognics.procuremasster.presentation.rfqdetails

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class ItemBidPricing(
    val rfqId: Int,
    val itemNumber: String,
    val description: String,
    val uom: String,
    val itemId: Int,
    val quantity: Int,
    val price: Double? = null,
    val discount: Double? = null,
    val tax: Double? = null,
    val dueDate: String? = null,
    val moq: Int? = null,
    val warranty: String? = null,
    val deliveryTime: String? = null,
    val currency: String
)

@Composable
fun PricingDetailsScreen(
    rfqNumber: String,
    description: String,
    currency: String,
    issueDate: String,
    submitDate: String,
    items: List<ItemBidPricing>
) {
    Column(Modifier.fillMaxSize().padding(12.dp)) {
        // Header
        Text("RFQ: $rfqNumber", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Text(description, style = MaterialTheme.typography.bodySmall)
        Spacer(Modifier.height(4.dp))

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Currency: $currency", style = MaterialTheme.typography.bodySmall)
            Column(horizontalAlignment = Alignment.End) {
                Text("Issue: $issueDate", style = MaterialTheme.typography.bodySmall)
                Text("Submit: $submitDate", style = MaterialTheme.typography.bodySmall)
            }
        }

        Spacer(Modifier.height(12.dp))

        // Items list
        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(items) { item ->
                RfqItemCardView(item)
            }
        }
    }
}
@Composable
fun RfqItemCardView(item: ItemBidPricing) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(3.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(Modifier.padding(14.dp)) {
            Text("Item: ${item.itemNumber}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(6.dp))
            Text(item.description, style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(10.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column(Modifier.weight(1f)) {
                    Text("UoM: ${item.uom}", style = MaterialTheme.typography.bodySmall)
                    Text("Qty: ${item.quantity}", style = MaterialTheme.typography.bodyMedium)
                }
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Text("Price: ${item.price ?: "—"}", style = MaterialTheme.typography.bodySmall)
                    Text("Discount: ${item.discount ?: "—"}", style = MaterialTheme.typography.bodySmall)
                    Text("Tax: ${item.tax ?: "—"}", style = MaterialTheme.typography.bodySmall)
                    Text("MOQ: ${item.moq ?: "—"}", style = MaterialTheme.typography.bodySmall)
                    Text("Warranty: ${item.warranty ?: "—"}", style = MaterialTheme.typography.bodySmall)
                    Text("Delivery: ${item.deliveryTime ?: "—"}", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}
//
//@Composable
//fun PricingDetailsCardScreen(
//    rfqNumber: String,
//    description: String,
//    currency: String,
//    issueDate: String,
//    submitDate: String,
//    items: List<RfqItemUi>,
//    onEditClick: (RfqItemUi) -> Unit = {}
//) {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(12.dp)
//    ) {
//        // ---- Header (shown once) ----
//        Text("RFQ: $rfqNumber", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
//        Text(description, style = MaterialTheme.typography.bodySmall)
//        Spacer(Modifier.height(4.dp))
//
//        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//            Text("Currency: $currency", style = MaterialTheme.typography.bodySmall)
//            Column {
//                Text("Issue: $issueDate", style = MaterialTheme.typography.bodySmall)
//                Text("Submit: $submitDate", style = MaterialTheme.typography.bodySmall)
//            }
//        }
//
//        Spacer(Modifier.height(12.dp))
//
//        // ---- Items shown as expandable cards ----
//        LazyColumn(
//            modifier = Modifier.fillMaxSize(),
//            verticalArrangement = Arrangement.spacedBy(8.dp)
//        ) {
//            items(items) { item ->
//                RfqItemExpandableCard(item = item, onEditClick = { onEditClick(item) })
//            }
//        }
//    }
//}
//
//@Composable
//fun RfqItemExpandableCard(item: RfqItemUi, onEditClick: () -> Unit) {
//    var expanded by remember { mutableStateOf(false) }
//
//    Card(
//        modifier = Modifier.fillMaxWidth(),
//        elevation = CardDefaults.cardElevation(2.dp)
//    ) {
//        Column(Modifier.padding(12.dp)) {
//            // --- Header Row ---
//            Row(
//                Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Column {
//                    Text("Item: ${item.itemNo}", fontWeight = FontWeight.Bold)
//                    Text(item.description, style = MaterialTheme.typography.bodySmall)
//                    Text("Qty: ${item.qty} ${item.uom}", style = MaterialTheme.typography.bodySmall)
//                }
//                Row {
//                    IconButton(onClick = { expanded = !expanded }) {
//                        Icon(
//                            imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
//                            contentDescription = "Expand"
//                        )
//                    }
//                    IconButton(onClick = onEditClick) {
//                        Icon(Icons.Default.Edit, contentDescription = "Edit", tint = MaterialTheme.colorScheme.primary)
//                    }
//                }
//            }
//
//            // --- Expanded Content ---
//            if (expanded) {
//                Spacer(Modifier.height(8.dp))
//                OutlinedTextField(
//                    value = "",
//                    onValueChange = {},
//                    label = { Text("Price") },
//                    modifier = Modifier.fillMaxWidth()
//                )
//                Spacer(Modifier.height(6.dp))
//                OutlinedTextField(
//                    value = "",
//                    onValueChange = {},
//                    label = { Text("Discount %") },
//                    modifier = Modifier.fillMaxWidth()
//                )
//                Spacer(Modifier.height(6.dp))
//                OutlinedTextField(
//                    value = "",
//                    onValueChange = {},
//                    label = { Text("Tax %") },
//                    modifier = Modifier.fillMaxWidth()
//                )
//                Spacer(Modifier.height(6.dp))
//                OutlinedTextField(
//                    value = "",
//                    onValueChange = {},
//                    label = { Text("Warranty") },
//                    modifier = Modifier.fillMaxWidth()
//                )
//            }
//        }
//    }
//}

//@Preview(showBackground = true, widthDp = 420, heightDp = 800)
//@Composable
//fun PricingDetailsCardScreen_Preview() {
//    val items = listOf(
//        RfqItemUi("I0001", "DISK", "NOS", 200),
//        RfqItemUi("I0002", "CABLE", "NOS", 20)
//    )
//
//    MaterialTheme {
//        PricingDetailsCardScreen(
//            rfqNumber = "100920250201",
//            description = "RFQ FOR HARDWARE",
//            currency = "INR",
//            issueDate = "2025-09-10",
//            submitDate = "2025-09-12",
//            items = items
//        )
//    }
//}
