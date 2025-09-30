package com.techcognics.procuremasster.presentation.auction

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.techcognics.procuremasster.data.remote.auctionpackage.AuctionResponseItem
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun getAuctionStatusLabel(auction: AuctionResponseItem): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
            val now = LocalDateTime.now()
            val startTime = LocalDateTime.parse(auction.auctionStartDateTime, formatter)
            val endTime = LocalDateTime.parse(auction.auctionEndDateTime, formatter)

            return if (auction.status.equals("OPEN", true) && now.isAfter(startTime) && now.isBefore(endTime)) {
                "LIVE"
            } else {
                auction.status
            }
        }

@Composable
fun AuctionCard(
    auction: AuctionResponseItem,
    modifier: Modifier = Modifier,
    onView: (AuctionResponseItem) -> Unit = {},
    onBidHistory: (AuctionResponseItem) -> Unit = {},
    onSubmitBid: (AuctionResponseItem) -> Unit = {}
) {
    var showMenu by remember { mutableStateOf(false) }
    val statusLabel = getAuctionStatusLabel(auction)

    val formatterInput = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    val formatterOutput = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    val formattedStartDateTime = try {
        LocalDateTime.parse(auction.auctionStartDateTime, formatterInput).format(formatterOutput)
    } catch (e: Exception) {
        auction.auctionStartDateTime // fallback to original if parse fails
    }

    val formattedEndDateTime = try {
        LocalDateTime.parse(auction.auctionEndDateTime, formatterInput).format(formatterOutput)
    } catch (e: Exception) {
        auction.auctionEndDateTime // fallback to original if parse fails
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = auction.auctionNo,
                    style = MaterialTheme.typography.titleMedium
                )

                AssistChip(
                    onClick = { /* no-op */ },
                    label = { Text(statusLabel) },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = when (statusLabel) {
                            "LIVE" -> MaterialTheme.colorScheme.primaryContainer
                            "OPEN" -> MaterialTheme.colorScheme.errorContainer
                            "CLOSED" -> MaterialTheme.colorScheme.errorContainer
                            else -> MaterialTheme.colorScheme.secondaryContainer
                        },
                        labelColor = when (statusLabel) {
                            "LIVE" -> MaterialTheme.colorScheme.onPrimaryContainer
                            "OPEN" -> MaterialTheme.colorScheme.onErrorContainer
                            "CLOSED" -> MaterialTheme.colorScheme.onErrorContainer
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
                                onView(auction)
                            }
                        )
                        if (auction.status.equals("CLOSED", true)) {
                            DropdownMenuItem(
                                text = { Text("Bid History") },
                                onClick = {
                                    showMenu = false
                                    onBidHistory(auction)
                                }
                            )
                        }
                        if (statusLabel == "LIVE") {
                            DropdownMenuItem(
                                text = { Text("Submit Bid") },
                                onClick = {
                                    showMenu = false
                                    onSubmitBid(auction)
                                }
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            Text(
                style = MaterialTheme.typography.labelMedium,
                text = auction.auctionTitle
            )

            Spacer(Modifier.height(6.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Buyer: ",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Start
                    )
                    Text(
                        auction.buyerName,
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.End
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Start date & Time: ",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Start
                    )
                    Text(
                        formattedStartDateTime,
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.End
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "End date & Time: ",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Start
                    )
                    Text(
                        formattedEndDateTime,
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.End
                    )
                }
            }

            Spacer(Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "CATEGORY: ",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    textAlign = TextAlign.Start
                )
                Text(
                    " ${auction.category}",
                    style = MaterialTheme.typography.labelMedium,
                    textAlign = TextAlign.End,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}
