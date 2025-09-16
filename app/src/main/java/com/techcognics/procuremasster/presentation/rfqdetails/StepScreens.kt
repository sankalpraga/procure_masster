package com.techcognics.procuremasster.presentation.rfqdetails

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

//data class RfqItemUi(
//    val itemNo: String,
//    val description: String,
//    val uom: String,
//    val qty: Int
//)
//
//@Composable
//fun PricingDetailsScreen(rfqNumber: String) {
//    val items = listOf(
//        RfqItemUi("I0001", "DISK", "NOS", 200),
//        RfqItemUi("I0002", "CABLE", "NOS", 20)
//    )
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(8.dp)
//    ) {
//        // Compact Header
//        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//            Text("RFQ: $rfqNumber", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
//            Text("Currency: INR", style = MaterialTheme.typography.bodySmall)
//        }
//        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//            Text("Desc: RFQ FOR HARDWARE", style = MaterialTheme.typography.bodySmall)
//            Column(horizontalAlignment = Alignment.End) {
//                Text("Issue: 2025-09-10", style = MaterialTheme.typography.bodySmall)
//                Text("Submit: 2025-09-12", style = MaterialTheme.typography.bodySmall)
//            }
//        }
//
//        Spacer(Modifier.height(8.dp))
//
//        // Items Table
//        ItemsTableCompact(items)
//    }
//}

//@Composable
//fun ItemsTableCompact(items: List<RfqItemUi>) {
//    Column(Modifier.fillMaxWidth()) {
//        Row(
//            Modifier
//                .fillMaxWidth()
//                .border(1.dp, Color.Gray)
//                .padding(vertical = 4.dp, horizontal = 6.dp)
//        ) {
//            TableCell("Item", true, Modifier.weight(0.2f))
//            TableCell("Desc", true, Modifier.weight(0.4f))
//            TableCell("UoM", true, Modifier.weight(0.2f))
//            TableCell("Qty", true, Modifier.weight(0.2f))
//        }
//
//        LazyColumn {
//            items(items) { item ->
//                Row(
//                    Modifier
//                        .fillMaxWidth()
//                        .border(0.5.dp, Color.LightGray)
//                        .padding(vertical = 4.dp, horizontal = 6.dp),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    TableCell(item.itemNo, false, Modifier.weight(0.2f))
//                    TableCell(item.description, false, Modifier.weight(0.4f))
//                    TableCell(item.uom, false, Modifier.weight(0.2f))
//                    TableCell(item.qty.toString(), false, Modifier.weight(0.2f))
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun TableCell(text: String, header: Boolean, modifier: Modifier = Modifier) {
//    Text(
//        text = text,
//        modifier = modifier,
//        fontWeight = if (header) FontWeight.Bold else FontWeight.Normal,
//        style = MaterialTheme.typography.bodySmall,
//        maxLines = 1
//    )
//}

@Composable
fun BidTermsScreen() {
    Column(Modifier.padding(12.dp)) {
        Text("Bid Terms & Conditions", style = MaterialTheme.typography.titleMedium)
    }
}

@Composable
fun AttachmentsScreen() {
    Column(Modifier.padding(12.dp)) {
        Text("Attachments", style = MaterialTheme.typography.titleMedium)
    }
}

//@Preview(showBackground = true, widthDp = 420)
//@Composable
//fun PricingDetailsScreen_Preview() {
//    MaterialTheme {
//        PricingDetailsScreen(rfqNumber = "100920250201")
//    }
//}

@Preview(showBackground = true, widthDp = 360)
@Composable
fun BidTermsScreen_Preview() {
    MaterialTheme { BidTermsScreen() }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
fun AttachmentsScreen_Preview() {
    MaterialTheme { AttachmentsScreen() }
}
