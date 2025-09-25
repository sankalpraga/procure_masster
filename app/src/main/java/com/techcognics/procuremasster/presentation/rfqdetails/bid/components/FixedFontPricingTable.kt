package com.techcognics.procuremasster.presentation.rfqdetails.bid.components


import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.techcognics.procuremasster.data.remote.dto.ItemBidingPricing

@Composable
fun FixedFontPricingTable(items: List<ItemBidingPricing>) {
    val scrollState = rememberScrollState()
    val tableFont = TextStyle(
        fontSize = 14.sp,
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Normal
    )
    Column(
        Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState)
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp)
    ) {
        // Table headers
        Row(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.08f))
                .padding(vertical = 12.dp)
        ) {
            TableCell("Item No", weight = 1f, textStyle = tableFont, fontWeight = FontWeight.Bold)
            TableCell("Description", weight = 3f, textStyle = tableFont, fontWeight = FontWeight.Bold)
            TableCell("UOM", weight = 1f, textStyle = tableFont, fontWeight = FontWeight.Bold)
            TableCell("Qty", weight = 1f, textStyle = tableFont, fontWeight = FontWeight.Bold)
        }
        Divider()

        // Table rows
        LazyColumn(
            Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(items) { item ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                ) {
                    TableCell(item.itemNumber, weight = 1f, textStyle = tableFont)
                    TableCell(item.description, weight = 3f, textStyle = tableFont)
                    TableCell(item.uom.name, weight = 1f, textStyle = tableFont)
                    TableCell(item.quantity.toString(), weight = 1f, textStyle = tableFont)
                }
                Divider()
            }
        }
    }
}

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float,
    textStyle: TextStyle,
    fontWeight: FontWeight = FontWeight.Normal
) {
    Text(
        text,
        Modifier.weight(weight).padding(horizontal = 6.dp),
        style = textStyle.copy(fontWeight = fontWeight)
    )
}
