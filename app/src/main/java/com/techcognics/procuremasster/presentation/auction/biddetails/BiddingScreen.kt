package com.techcognics.procuremasster.presentation.auction.biddetails

import android.app.PendingIntent
import android.content.Intent
import android.Manifest
import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.techcognics.procuremasster.data.remote.auctionpackage.bidsubmit.SupplierBidDetailsItem
import com.techcognics.procuremasster.presentation.auction.AuctionViewModel
import com.techcognics.procuremasster.presentation.base.UiState
import kotlinx.coroutines.delay
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import androidx.compose.material3.Snackbar
import com.techcognics.procuremasster.MainActivity


@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun BiddingScreen(
    rfqId: Int,
    navController: NavHostController,
    viewModel: AuctionViewModel = hiltViewModel()
) {
    val bidDetailsState by viewModel.bidDetailsItem.collectAsState()
    var selectedItem by remember { mutableStateOf<SupplierBidDetailsItem?>(null) }
    var bidValue by remember { mutableStateOf("") }
    var submitting by remember { mutableStateOf(false) }
    var errorText by remember { mutableStateOf<String?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(rfqId) { viewModel.loadBidDetails(rfqId) }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                Snackbar(
                    containerColor = Color.Red,
                    contentColor = Color.White,
                    snackbarData = data
                )
            }
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth()
            ) {
                when (val state = bidDetailsState) {
                    is UiState.Loading -> CircularProgressIndicator()
                    is UiState.Error -> Text("Failed to load bids: ${state.message}", color = MaterialTheme.colorScheme.error)
                    is UiState.Success -> {
                        val bidList = (state as UiState.Success<List<SupplierBidDetailsItem>>).data ?: emptyList()
                        if (bidList.isEmpty()) {
                            Text("No bids available.")
                        } else {
                            val firstItem = bidList.first()
                            // TIMER and notifications are always visible
                            AuctionTimeNotificationTimer(
                                endDateTime = firstItem.auctionEndDateTime,
                                auctionNumber = firstItem.auctionNumber,
                                snackbarHostState = snackbarHostState,
                                rfqId = firstItem.id // <--- THIS IS THE KEY
                            )
                            Spacer(Modifier.height(16.dp))
                            Text("Items List", style = MaterialTheme.typography.headlineSmall)
                            Spacer(Modifier.height(8.dp))
                            bidList.forEach { item ->
                                BidItemCardSimple(
                                    item = item,
                                    onEdit = {
                                        selectedItem = item
                                        bidValue = ""
                                        errorText = null
                                    }
                                )
                                Spacer(Modifier.height(5.dp))
                            }
                        }
                    }
                    else -> { }
                }
            }
        }

        // MODAL: Shows edit bid dialog if an item is selected
        selectedItem?.let { item ->
            AlertDialog(
                onDismissRequest = { if (!submitting) selectedItem = null },
                title = { Text("Bid for ${item.itemNumber}") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = bidValue,
                            onValueChange = { bidValue = it },
                            enabled = !submitting,
                            label = { Text("Enter your new bid") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(Modifier.height(10.dp))
                        Text("Auction Start Price: ₹${item.auctionStartPrice}")
                        Text("Bid Incremental: ${item.bidDecrementalValue}")
                        Text("Last Bid Price: ₹${item.lastBidPrice ?: "-"}")
                        Text("New Bid (Server): ₹${item.bidPrice ?: "-"}")
                        Text(
                            "Submit Bid: ₹${bidValue.ifBlank { "-" }}",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        if (errorText != null) {
                            Spacer(Modifier.height(6.dp))
                            Text(errorText ?: "", color = MaterialTheme.colorScheme.error)
                        }
                    }
                },
                confirmButton = {
                    Button(
                        enabled = !submitting,
                        onClick = {
                            val price = bidValue.toDoubleOrNull()
                            if (price == null) {
                                errorText = "Enter a valid number"
                                return@Button
                            }
                            submitting = true
                            errorText = null
                            viewModel.submitBid(
                                rfqId = rfqId,
                                item = item,
                                userBidPrice = price,
                                onSuccess = {
                                    submitting = false
                                    selectedItem = null
                                    viewModel.loadBidDetails(rfqId)
                                },
                                onError = {
                                    submitting = false
                                    errorText = it
                                }


                            )
                        }
                    ) {
                        if (submitting)
                            CircularProgressIndicator(
                                Modifier.height(16.dp).padding(end = 8.dp),
                                strokeWidth = 2.dp
                            )
                        Text("Submit")
                    }
                },
                dismissButton = {
                    Button(
                        enabled = !submitting,
                        onClick = { selectedItem = null }
                    ) { Text("Cancel") }
                }
            )
        }
    }
}


@Composable
fun BidItemCardSimple(
    item: SupplierBidDetailsItem,
    onEdit: (SupplierBidDetailsItem) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(15.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text(
                    item.itemNumber,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    item.status,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    item.itemDescription,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    "Qty: ${item.quantity}",
                    style = MaterialTheme.typography.labelSmall
                )
            }
            IconButton(onClick = { onEdit(item) }) {
                Icon(Icons.Default.Edit, contentDescription = "Edit Bid", tint = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

@Composable
fun BidEditDialog(
    item: SupplierBidDetailsItem,
    bidValue: String,
    onBidValueChange: (String) -> Unit,
    submitting: Boolean,
    errorText: String?,
    onSubmit: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { if (!submitting) onDismiss() },
        title = { Text("Bid for ${item.itemNumber}") },
        text = {
            Column {
                TextField(
                    value = bidValue,
                    onValueChange = onBidValueChange,
                    enabled = !submitting,
                    label = { Text("Enter your new bid") },
                    singleLine = true
                )
                Spacer(Modifier.height(10.dp))
                Text("Auction Start Price: ₹${item.auctionStartPrice}")
                Text("Bid Incremental: ${item.bidDecrementalValue}")
                Text("Last Bid Price: ₹${item.lastBidPrice ?: "-"}")
                Text("New Bid: ₹${item.bidPrice ?: "-"}")
                // Add this line for "Submit Bid:"
                Text(
                    "Submit Bid: ₹${bidValue.ifBlank { "-" }}",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                if (errorText != null) {
                    Spacer(Modifier.height(6.dp))
                    Text(errorText, color = MaterialTheme.colorScheme.error)
                }
            }
        },
        confirmButton = {
            Button(
                enabled = !submitting,
                onClick = onSubmit
            ) {
                if (submitting)
                    CircularProgressIndicator(
                        Modifier.height(16.dp).padding(end = 8.dp),
                        strokeWidth = 2.dp
                    )
                Text("Submit")
            }
        },
        dismissButton = {
            Button(
                enabled = !submitting,
                onClick = onDismiss
            ) { Text("Cancel") }
        }
    )
}


@Composable
fun AuctionTimeNotificationTimer(
    endDateTime: String,
    auctionNumber: String,
    snackbarHostState: SnackbarHostState,
    rfqId: Int // <-- Add this parameter!

) {
    val context = LocalContext.current
    var days by remember { mutableStateOf(0L) }
    var hours by remember { mutableStateOf(0L) }
    var minutes by remember { mutableStateOf(0L) }
    var seconds by remember { mutableStateOf(0L) }
    var notifiedMinutes by remember { mutableStateOf(setOf<Long>()) }

    val auctionEnd = remember(endDateTime) {
        try {
            LocalDateTime.parse(
                endDateTime.replace(" ", "T"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
            )
        } catch (e: Exception) {
            null
        }
    }

    LaunchedEffect(endDateTime, auctionNumber) {
        while (auctionEnd != null) {
            val now = LocalDateTime.now()
            val duration = Duration.between(now, auctionEnd)
            if (duration.isNegative || duration.isZero) {
                days = 0L; hours = 0L; minutes = 0L; seconds = 0L
                break
            }
            days = duration.toDays()
            hours = duration.toHours() % 24
            minutes = duration.toMinutes() % 60
            seconds = duration.seconds % 60

            val notifyList = listOf(5L, 4L, 3L, 2L, 1L)
            val notBlankAuctionNo = auctionNumber.ifBlank { "Unknown" }
            if (hours == 0L && days == 0L && minutes in notifyList && !notifiedMinutes.contains(minutes) && seconds == 0L) {
                snackbarHostState.showSnackbar(
                    "Auction No. $notBlankAuctionNo: Only $minutes minute(s) left!"
                )
                showAuctionTimerNotification(context, rfqId , auctionNumber, minutes)

//                showAuctionTimerNotification(context, notBlankAuctionNo, minutes)
                notifiedMinutes = notifiedMinutes + minutes
            }
            delay(1000)
        }
    }

    Box {
        AuctionTimerBlocks(days, hours, minutes, seconds)
    }
}

@Composable
fun AuctionTimerBlocks(days: Long, hours: Long, minutes: Long, seconds: Long) {
    val isFiveMin = (days == 0L && hours == 0L && minutes <= 5L)
    val transition = rememberInfiniteTransition()

    val animatedColor by transition.animateColor(
        initialValue = Color(0xFF43BD6B),
        targetValue = if (isFiveMin) Color.Red else Color(0xFF43BD6B),
        animationSpec = infiniteRepeatable(
            animation = tween(500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val colorToUse = if (isFiveMin) animatedColor else Color(0xFF43BD6B)
    val labelColor = if (isFiveMin) Color.Red else Color(0xFF43BD6B)

    val items = listOf(
        days to "DAYS",
        hours to "HOURS",
        minutes to "MINUTES",
        seconds to "SECONDS"
    )

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterEnd
    ) {
        Row(verticalAlignment = Alignment.Bottom) {
            items.forEach { (value, label) ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.width(54.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .size(width = 54.dp, height = 54.dp)
                            .padding(horizontal = 1.dp),
                        colors = CardDefaults.cardColors(containerColor = colorToUse),
                        elevation = CardDefaults.cardElevation(3.dp),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "%02d".format(value),
                                style = MaterialTheme.typography.headlineSmall,
                                color = Color.White,
                                maxLines = 1
                            )
                        }
                    }
                    Spacer(Modifier.height(3.dp))
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelSmall,
                        color = labelColor,
                        maxLines = 1
                    )
                }
                Spacer(Modifier.width(6.dp))
            }
        }
    }
}

@RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
fun showAuctionTimerNotification(
    context: Context,
    rfqId: Int,                            // Pass rfqId, not just auction number
    auctionNumber: String,
    minutes: Long
) {
    val channelId = "auction_timer_channel"
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(channelId, "Auction Timer", NotificationManager.IMPORTANCE_HIGH)
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }

    // Intent to open MainActivity and include correct rfqId
    val intent = Intent(context, MainActivity::class.java).apply {
        putExtra("rfqId", rfqId ?: -1)           // Use -1 as "missing"
        putExtra("timer_alert", true)
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }

    val pendingIntent = PendingIntent.getActivity(
        context, 0, intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val notification = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.ic_dialog_info)
        .setContentTitle("Auction $auctionNumber")
        .setContentText("Only $minutes minute(s) left to bid!")
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setContentIntent(pendingIntent)       // Set notification click action
        .setAutoCancel(true)
        .build()
    NotificationManagerCompat.from(context).notify(minutes.toInt(), notification)
}







