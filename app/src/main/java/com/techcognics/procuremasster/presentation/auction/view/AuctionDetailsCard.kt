package com.techcognics.procuremasster.presentation.auction.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.techcognics.procuremasster.data.remote.auctionpackage.view.AuctionViewResponse

@Composable
fun AuctionDetailsCard(
    auction: AuctionViewResponse,
    modifier: Modifier = Modifier
) {
    val primary = MaterialTheme.colorScheme.primary
    val secondary = MaterialTheme.colorScheme.secondary
    val onPrimary = MaterialTheme.colorScheme.onPrimary
    val surfaceVariant = MaterialTheme.colorScheme.surfaceVariant

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 6.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            // Header
            Text(
                text = "${auction.auctionNo ?: ""} / ${auction.auctionTitle} (${auction.auctionStartDateTime.take(10)})",
                style = MaterialTheme.typography.titleLarge,
                color = primary
            )
            Spacer(Modifier.height(10.dp))

            // Key details with color highlights
            DetailRowAccent("Auction Type", auction.auctionType, background = surfaceVariant)
            DetailRowAccent("Status", auction.status, background = secondary, contentColor = onPrimary)
            DetailRowAccent("Buyer Name", auction.nameOfBuyer, background = surfaceVariant)
            DetailRowAccent("Open Date & Time", auction.auctionStartDateTime.replace("T", " "), background = surfaceVariant)
            DetailRowAccent("Close Date & Time", auction.auctionEndDateTime.replace("T", " "), background = surfaceVariant)

            Spacer(Modifier.height(16.dp))
            Text("Items", style = MaterialTheme.typography.titleMedium, color = primary)
            Spacer(Modifier.height(6.dp))

            auction.items.forEach { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = MaterialTheme.shapes.small,
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                ) {
                    Column(Modifier.padding(12.dp)) {
                        DetailRowAccent("Item Number", item.itemNumber, background = surfaceVariant)
                        DetailRowAccent("Description", item.description, background = surfaceVariant)
                        DetailRowAccent("Unit", item.uom.name, background = surfaceVariant)
                        DetailRowAccent("Qty", item.quantity.toString(), background = surfaceVariant)
                        DetailRowAccent("Last Purchase", item.lastPurchasePrice.toString(), background = surfaceVariant)
                        DetailRowAccent("Bid decrement", item.bidDecrementalValue.toString(), background = surfaceVariant)
                    }
                }
            }
        }
    }
}

@Composable
fun DetailRowAccent(
    label: String,
    value: String,
    background: Color,
    contentColor: Color = MaterialTheme.colorScheme.onSurface
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
            .padding(horizontal = 2.dp)
            .background(background, shape = MaterialTheme.shapes.extraSmall),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = MaterialTheme.typography.bodySmall, color = contentColor, modifier = Modifier.padding(4.dp))
        Text(value, style = MaterialTheme.typography.bodySmall, color = contentColor, modifier = Modifier.padding(4.dp))
    }
}
