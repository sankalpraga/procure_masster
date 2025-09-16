//package com.techcognics.procuremasster.presentation.rfqdetails
//
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Edit
//import androidx.compose.material.icons.filled.ExpandLess
//import androidx.compose.material.icons.filled.ExpandMore
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
////import com.google.accompanist.flowlayout.FlowRow
//
////data class RfqItemUi(
////    val itemNo: String,
////    val description: String,
////    val uom: String,
////    val qty: Int
////)
//
//// ----------------------
//// Style 1: Simple Card with Items List
//// ----------------------
//@Composable
//fun RfqCardStyle1(
//    rfqNumber: String,
//    description: String,
//    issueDate: String,
//    submitDate: String,
//    items: List<RfqItemUi>,
//    onActionClick: () -> Unit
//) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(6.dp),
//        elevation = CardDefaults.cardElevation(2.dp)
//    ) {
//        Column(Modifier.padding(10.dp)) {
//            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//                Column {
//                    Text("RFQ: $rfqNumber", fontWeight = FontWeight.Bold)
//                    Text(description, style = MaterialTheme.typography.bodySmall)
//                }
//                IconButton(onClick = onActionClick) {
//                    Icon(Icons.Default.Edit, contentDescription = "Edit")
//                }
//            }
//
//            Spacer(Modifier.height(6.dp))
//            Text("Issue: $issueDate | Submit: $submitDate", style = MaterialTheme.typography.bodySmall)
//
//            Spacer(Modifier.height(6.dp))
//            items.forEach {
//                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//                    Text(it.itemNo, style = MaterialTheme.typography.bodySmall)
//                    Text(it.description, style = MaterialTheme.typography.bodySmall)
//                    Text("${it.qty} ${it.uom}", fontWeight = FontWeight.Medium)
//                }
//            }
//        }
//    }
//}
//
//// ----------------------
//// Style 2: Expandable Card
//// ----------------------
//@Composable
//fun RfqCardStyle2(
//    rfqNumber: String,
//    description: String,
//    issueDate: String,
//    submitDate: String,
//    items: List<RfqItemUi>,
//    onActionClick: () -> Unit
//) {
//    var expanded by remember { mutableStateOf(false) }
//
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(6.dp),
//        elevation = CardDefaults.cardElevation(2.dp)
//    ) {
//        Column(Modifier.padding(10.dp)) {
//            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//                Column {
//                    Text("RFQ: $rfqNumber", fontWeight = FontWeight.Bold)
//                    Text(description, style = MaterialTheme.typography.bodySmall)
//                }
//                Row {
//                    IconButton(onClick = { expanded = !expanded }) {
//                        Icon(
//                            if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
//                            contentDescription = "Expand"
//                        )
//                    }
//                    IconButton(onClick = onActionClick) {
//                        Icon(Icons.Default.Edit, contentDescription = "Edit")
//                    }
//                }
//            }
//
//            if (expanded) {
//                Spacer(Modifier.height(6.dp))
//                Text("Issue: $issueDate | Submit: $submitDate", style = MaterialTheme.typography.bodySmall)
//
//                Spacer(Modifier.height(6.dp))
//                items.forEach {
//                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//                        Text(it.itemNo, style = MaterialTheme.typography.bodySmall)
//                        Text(it.description, style = MaterialTheme.typography.bodySmall)
//                        Text("${it.qty} ${it.uom}", fontWeight = FontWeight.Medium)
//                    }
//                }
//            }
//        }
//    }
//}
//
//// ----------------------
//// Style 3: Grid Header + Chips
//// ----------------------
//@Composable
//fun RfqCardStyle3(
//    rfqNumber: String,
//    description: String,
//    issueDate: String,
//    submitDate: String,
//    items: List<RfqItemUi>,
//    onActionClick: () -> Unit
//) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(6.dp),
//        elevation = CardDefaults.cardElevation(2.dp)
//    ) {
//        Column(Modifier.padding(10.dp)) {
//            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//                Text("RFQ: $rfqNumber", fontWeight = FontWeight.Bold)
//                IconButton(onClick = onActionClick) {
//                    Icon(Icons.Default.Edit, contentDescription = "Edit")
//                }
//            }
//
//            Text(description, style = MaterialTheme.typography.bodySmall)
//
//            Spacer(Modifier.height(6.dp))
//            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//                Column {
//                    Text("Issue", style = MaterialTheme.typography.labelSmall)
//                    Text(issueDate, style = MaterialTheme.typography.bodySmall)
//                }
//                Column {
//                    Text("Submit", style = MaterialTheme.typography.labelSmall)
//                    Text(submitDate, style = MaterialTheme.typography.bodySmall)
//                }
//            }
//
//            Spacer(Modifier.height(6.dp))
//            FlowRow(mainAxisSpacing = 6.dp, crossAxisSpacing = 4.dp) {
//                items.forEach {
//                    AssistChip(
//                        onClick = {},
//                        label = { Text("${it.description} (${it.qty} ${it.uom})") }
//                    )
//                }
//            }
//        }
//    }
//}
//
//// ----------------------
//// Preview all styles
//// ----------------------
//@Preview(showBackground = true)
//@Composable
//fun RfqCardStyles_Preview() {
//    val items = listOf(
//        RfqItemUi("I0001", "DISK", "NOS", 200),
//        RfqItemUi("I0002", "CABLE", "NOS", 20)
//    )
//
//    Column(Modifier.fillMaxSize().padding(8.dp)) {
//        RfqCardStyle1(
//            rfqNumber = "100920250201",
//            description = "RFQ FOR HARDWARE",
//            issueDate = "2025-09-10",
//            submitDate = "2025-09-12",
//            items = items,
//            onActionClick = {}
//        )
//        RfqCardStyle2(
//            rfqNumber = "100920250201",
//            description = "RFQ FOR HARDWARE",
//            issueDate = "2025-09-10",
//            submitDate = "2025-09-12",
//            items = items,
//            onActionClick = {}
//        )
//        RfqCardStyle3(
//            rfqNumber = "100920250201",
//            description = "RFQ FOR HARDWARE",
//            issueDate = "2025-09-10",
//            submitDate = "2025-09-12",
//            items = items,
//            onActionClick = {}
//        )
//    }
//}
