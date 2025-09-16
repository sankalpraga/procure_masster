//package com.techcognics.procuremasster.presentation.rfqdetails
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Edit
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//
//@Composable
//fun RfqCardCompact(
//    rfqNumber: String,
//    description: String,
//    currency: String,
//    issueDate: String,
//    submitDate: String,
////    items: List<RfqItemUi>,
//    onActionClick: () -> Unit = {}
//) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(6.dp),
//        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
//    ) {
//        Column(modifier = Modifier.padding(10.dp)) {
//
//            // --- Header Row with RFQ + Action ---
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Column {
//                    Text("RFQ: $rfqNumber", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
//                    Text(description, style = MaterialTheme.typography.bodySmall)
//                }
//                IconButton(onClick = onActionClick) {
//                    Icon(Icons.Default.Edit, contentDescription = "Edit")
//                }
//            }
//
//            Spacer(Modifier.height(4.dp))
//
//            // --- Meta Row ---
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Text("Currency: $currency", style = MaterialTheme.typography.bodySmall)
//                Column(horizontalAlignment = Alignment.End) {
//                    Text("Issue: $issueDate", style = MaterialTheme.typography.bodySmall)
//                    Text("Submit: $submitDate", style = MaterialTheme.typography.bodySmall)
//                }
//            }
//
//            Spacer(Modifier.height(6.dp))
//
//            // --- Items (compact) ---
//            items.forEach { item ->
//                Row(
//                    Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 2.dp),
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    Text(item.itemNo, style = MaterialTheme.typography.bodySmall)
//                    Text(item.description, style = MaterialTheme.typography.bodySmall, modifier = Modifier.weight(1f))
//                    Text("${item.qty} ${item.uom}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Medium)
//                }
//            }
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun RfqCardCompact_Preview() {
//    val items = listOf(
//        RfqItemUi("I0001", "DISK", "NOS", 200),
//        RfqItemUi("I0002", "CABLE", "NOS", 20)
//    )
//
//    MaterialTheme {
//        RfqCardCompact(
//            rfqNumber = "100920250201",
//            description = "RFQ FOR HARDWARE",
//            currency = "INR",
//            issueDate = "2025-09-10",
//            submitDate = "2025-09-12",
//            items = items
//        )
//    }
//}
