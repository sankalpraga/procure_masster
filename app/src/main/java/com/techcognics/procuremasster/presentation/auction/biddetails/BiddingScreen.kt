package com.techcognics.procuremasster.presentation.auction.biddetails

import android.Manifest
import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
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
import androidx.compose.foundation.layout.FlowRow

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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
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

                        // If you have a timer component, keep it here or remove if not needed
                         AuctionTimeNotificationTimer(
                             endDateTime = firstItem.auctionEndDateTime,
                             auctionNumber = firstItem.auctionNumber,
                             snackbarHostState = snackbarHostState
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 24.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.padding(10.dp)
            )
        }
    }

    // EDIT MODAL: Shows full current bid info and the submitted value before submit
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
                        viewModel.submitBidPrice(
                            rfqId = rfqId,
                            itemId = item.itemId,
                            bidPrice = price,
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

//@Composable
//fun BiddingScreen(
//    rfqId: Int,
//    navController: NavHostController,
//    viewModel: AuctionViewModel = hiltViewModel()
//) {
//    val bidDetailsState by viewModel.bidDetailsItem.collectAsState()
//    var selectedItem by remember { mutableStateOf<SupplierBidDetailsItem?>(null) }
//    var bidValue by remember { mutableStateOf("") }
//    var submitting by remember { mutableStateOf(false) }
//    var errorText by remember { mutableStateOf<String?>(null) }
//    val snackbarHostState = remember { SnackbarHostState() }
//
//    LaunchedEffect(rfqId) { viewModel.loadBidDetails(rfqId) }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(MaterialTheme.colorScheme.background)
//    ) {
//        Column(
//            modifier = Modifier
//                .padding(16.dp)
//                .verticalScroll(rememberScrollState())
//                .fillMaxWidth()
//        ) {
//            when (val state = bidDetailsState) {
//                is UiState.Loading -> CircularProgressIndicator()
//                is UiState.Error -> Text("Failed to load bids: ${state.message}", color = MaterialTheme.colorScheme.error)
//                is UiState.Success -> {
//                    val bidList = (state as UiState.Success<List<SupplierBidDetailsItem>>).data ?: emptyList()
//                    if (bidList.isEmpty()) {
//                        Text("No bids available.")
//                    } else {
//                        val firstItem = bidList.first()
//                        AuctionTimeNotificationTimer(
//                            endDateTime = firstItem.auctionEndDateTime,
//                            auctionNumber = firstItem.auctionNumber,
//                            snackbarHostState = snackbarHostState
//                        )
//                        Spacer(Modifier.height(16.dp))
//                        Text("Items List", style = MaterialTheme.typography.headlineSmall)
//                        Spacer(Modifier.height(8.dp))
//                        bidList.forEach { item ->
//                            BidItemCardSimple(
//                                item = item,
//                                onEdit = {
//                                    selectedItem = item
//                                    bidValue = ""
//                                    errorText = null
//                                }
//                            )
//                            Spacer(Modifier.height(5.dp))
//                        }
//                    }
//                }
//                else -> { }
//            }
//        }
//        // Always floating at the bottom
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(bottom = 24.dp),
//            contentAlignment = Alignment.BottomCenter
//        ) {
//            SnackbarHost(
//                hostState = snackbarHostState,
//                modifier = Modifier.padding(10.dp)
//            )
//        }
//    }
//
//    if (selectedItem != null) {
//        AlertDialog(
//            onDismissRequest = { if (!submitting) selectedItem = null },
//            title = { Text("Bid for ${selectedItem?.itemNumber}") },
//            text = {
//                Column {
//                    OutlinedTextField(
//                        value = bidValue,
//                        onValueChange = { bidValue = it },
//                        enabled = !submitting,
//                        label = { Text("Enter your new bid") },
//                        singleLine = true,
//                        modifier = Modifier.fillMaxWidth()
//                    )
//                    if (errorText != null) {
//                        Spacer(Modifier.height(6.dp))
//                        Text(errorText!!, color = MaterialTheme.colorScheme.error)
//                    }
//                }
//            },
//            confirmButton = {
//                Button(
//                    enabled = !submitting,
//                    onClick = {
//                        val price = bidValue.toDoubleOrNull()
//                        if (price == null) {
//                            errorText = "Enter a valid number"
//                            return@Button
//                        }
//                        submitting = true
//                        errorText = null
//                        viewModel.submitBidPrice(
//                            rfqId = rfqId,
//                            itemId = selectedItem!!.itemId,
//                            bidPrice = price,
//                            onSuccess = {
//                                submitting = false
//                                selectedItem = selectedItem?.copy(bidPrice = price)
//                                // (Optionally trigger recomposition here)
//                                selectedItem = null
//                                viewModel.loadBidDetails(rfqId)
//                            },
//                            onError = {
//                                submitting = false
//                                errorText = it
//                            }
//                        )
//                    }
//                ) {
//                    if (submitting)
//                        CircularProgressIndicator(
//                            Modifier.height(16.dp).padding(end = 8.dp),
//                            strokeWidth = 2.dp
//                        )
//                    Text("Submit")
//                }
//            },
//            dismissButton = {
//                Button(
//                    enabled = !submitting,
//                    onClick = { selectedItem = null }
//                ) { Text("Cancel") }
//            }
//        )
//    }
//}
//
//
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
    snackbarHostState: SnackbarHostState
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
                showAuctionTimerNotification(context, notBlankAuctionNo, minutes)
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
fun showAuctionTimerNotification(context: Context, auctionNumber: String, minutes: Long) {
    val channelId = "auction_timer_channel"
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(channelId, "Auction Timer", NotificationManager.IMPORTANCE_HIGH)
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }
    val notification = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.ic_dialog_info)
        .setContentTitle("Auction $auctionNumber")
        .setContentText("Only $minutes minute(s) left to bid!")
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .build()
    NotificationManagerCompat.from(context).notify(minutes.toInt(), notification)
}



//package com.techcognics.procuremasster.presentation.auction.biddetails
//
//import android.Manifest
//import android.app.NotificationChannel
//import android.app.NotificationManager
//import android.content.Context
//import android.os.Build
//import androidx.annotation.RequiresPermission
//import androidx.compose.animation.animateColor
//import androidx.compose.animation.core.LinearEasing
//import androidx.compose.animation.core.RepeatMode
//import androidx.compose.animation.core.infiniteRepeatable
//import androidx.compose.animation.core.rememberInfiniteTransition
//import androidx.compose.animation.core.tween
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Edit
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.core.app.NotificationCompat
//import androidx.core.app.NotificationManagerCompat
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.navigation.NavHostController
//import com.techcognics.procuremasster.presentation.auction.AuctionViewModel
//import com.techcognics.procuremasster.presentation.base.UiState
//import kotlinx.coroutines.delay
//import java.time.Duration
//import java.time.LocalDateTime
//import java.time.format.DateTimeFormatter
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.ui.Modifier
//import com.techcognics.procuremasster.data.remote.auctionpackage.bidsubmit.SupplierBidDetailsItem
//
//@Composable
//fun BiddingScreen(
//    rfqId: Int,
//    navController: NavHostController,
//    viewModel: AuctionViewModel = hiltViewModel()
//) {
//    val bidDetailsState by viewModel.bidDetailsItem.collectAsState()
//    var selectedItem by remember { mutableStateOf<SupplierBidDetailsItem?>(null) }
//    var bidValue by remember { mutableStateOf("") }
//    var submitting by remember { mutableStateOf(false) }
//    var errorText by remember { mutableStateOf<String?>(null) }
//
//    LaunchedEffect(rfqId) { viewModel.loadBidDetails(rfqId) }
//
//    // Use a scrollable column to avoid all space/parent issues
//    Column(
//        modifier = Modifier
//            .padding(16.dp)
//            .fillMaxSize()
//            .verticalScroll(rememberScrollState())
//    ) {
//        when (val state = bidDetailsState) {
//            is UiState.Loading -> CircularProgressIndicator()
//            is UiState.Error -> Text("Failed to load bids: ${state.message}", color = MaterialTheme.colorScheme.error)
//            is UiState.Success -> {
//                val bidList = (state as UiState.Success<List<SupplierBidDetailsItem>>).data ?: emptyList()
//                if (bidList.isEmpty()) {
//                    Text("No bids available.")
//                } else {
//                    val firstItem = bidList.first()
//                    AuctionTimeNotificationTimer(
//                        endDateTime = firstItem.auctionEndDateTime,
//                        auctionNumber = firstItem.auctionNumber
//                    )
//                    Spacer(Modifier.height(16.dp))
//                    Text("Items List", style = MaterialTheme.typography.headlineSmall)
//                    Spacer(Modifier.height(8.dp))
//                    // Show all cards with no special constraints
//                    bidList.forEach { item ->
//                        BidItemCard(
//                            item = item,
//                            onEdit = {
//                                selectedItem = item
//                                bidValue = ""
//                                errorText = null
//                            }
//                        )
//                        Spacer(Modifier.height(5.dp))
//                    }
//                }
//            }
//            else -> { }
//        }
//    }
//
//    if (selectedItem != null) {
//        AlertDialog(
//            onDismissRequest = { if (!submitting) selectedItem = null },
//            title = { Text("Bid for ${selectedItem?.itemNumber}") },
//            text = {
//                Column {
//                    Text("Description: ${selectedItem?.itemDescription ?: ""}")
//                    Spacer(Modifier.height(8.dp))
//                    TextField(
//                        value = bidValue,
//                        onValueChange = { bidValue = it },
//                        enabled = !submitting,
//                        label = { Text("Enter your new bid") },
//                        singleLine = true
//                    )
//                    if (errorText != null) {
//                        Spacer(Modifier.height(6.dp))
//                        Text(errorText!!, color = MaterialTheme.colorScheme.error)
//                    }
//                }
//            },
//            confirmButton = {
//                Button(
//                    enabled = !submitting,
//                    onClick = {
//                        val price = bidValue.toDoubleOrNull()
//                        if (price == null) {
//                            errorText = "Enter a valid number"
//                            return@Button
//                        }
//                        submitting = true
//                        errorText = null
//                        viewModel.submitBidPrice(
//                            rfqId = rfqId,
//                            itemId = selectedItem!!.itemId,
//                            bidPrice = price,
//                            onSuccess = {
//                                submitting = false
//                                selectedItem = null
//                                viewModel.loadBidDetails(rfqId)
//                            },
//                            onError = {
//                                submitting = false
//                                errorText = it
//                            }
//                        )
//                    }
//                ) {
//                    if (submitting)
//                        CircularProgressIndicator(
//                            Modifier.height(16.dp).padding(end = 8.dp),
//                            strokeWidth = 2.dp
//                        )
//                    Text("Submit")
//                }
//            },
//            dismissButton = {
//                Button(
//                    enabled = !submitting,
//                    onClick = { selectedItem = null }
//                ) { Text("Cancel") }
//            }
//        )
//    }
//}
//
//
//@Composable
//fun AuctionTimeNotificationTimer(endDateTime: String, auctionNumber: String) {
//    val context = LocalContext.current
//    var days by remember { mutableStateOf(0L) }
//    var hours by remember { mutableStateOf(0L) }
//    var minutes by remember { mutableStateOf(0L) }
//    var seconds by remember { mutableStateOf(0L) }
//    var notifiedMinutes by remember { mutableStateOf(setOf<Long>()) }
//    val snackbarHostState = remember { SnackbarHostState() }
//
//    val auctionEnd = remember(endDateTime) {
//        try {
//            LocalDateTime.parse(endDateTime.replace(" ", "T"), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))
//        } catch (e: Exception) {
//            null
//        }
//    }
//
//    LaunchedEffect(endDateTime, auctionNumber) {
//        while (auctionEnd != null) {
//            val now = LocalDateTime.now()
//            val duration = Duration.between(now, auctionEnd)
//            if (duration.isNegative || duration.isZero) {
//                days = 0L; hours = 0L; minutes = 0L; seconds = 0L
//                break
//            }
//            days = duration.toDays()
//            hours = duration.toHours() % 24
//            minutes = duration.toMinutes() % 60
//            seconds = duration.seconds % 60
//
//            val notifyList = listOf(5L, 4L, 3L, 2L, 1L)
//            val notBlankAuctionNo = auctionNumber.ifBlank { "Unknown" }
//            if (hours == 0L && days == 0L && minutes in notifyList && !notifiedMinutes.contains(minutes) && seconds == 0L) {
//                snackbarHostState.showSnackbar(
//                    "Auction No. $notBlankAuctionNo: Only $minutes minute(s) left!"
//                )
//                showAuctionTimerNotification(context, notBlankAuctionNo, minutes)
//                notifiedMinutes = notifiedMinutes + minutes
//            }
//            delay(1000)
//        }
//    }
//
//    Box {
//        AuctionTimerBlocks(days, hours, minutes, seconds)
//        BottomAlertSnackbarHost(snackbarHostState)
//    }
//}
//
//@Composable
//fun AuctionTimerBlocks(days: Long, hours: Long, minutes: Long, seconds: Long) {
//    val isFiveMin = (days == 0L && hours == 0L && minutes <= 5L)
//    val transition = rememberInfiniteTransition()
//
//    val animatedColor by transition.animateColor(
//        initialValue = Color(0xFF43BD6B),
//        targetValue = if (isFiveMin) Color.Red else Color(0xFF43BD6B),
//        animationSpec = infiniteRepeatable(
//            animation = tween(500, easing = LinearEasing),
//            repeatMode = RepeatMode.Reverse
//        )
//    )
//
//    val colorToUse = if (isFiveMin) animatedColor else Color(0xFF43BD6B)
//    val labelColor = if (isFiveMin) Color.Red else Color(0xFF43BD6B)
//
//    val items = listOf(
//        days to "DAYS",
//        hours to "HOURS",
//        minutes to "MINUTES",
//        seconds to "SECONDS"
//    )
//
//    Box(
//        modifier = Modifier.fillMaxWidth(),
//        contentAlignment = Alignment.CenterEnd
//    ) {
//        Row(verticalAlignment = Alignment.Bottom) {
//            items.forEach { (value, label) ->
//                Column(
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    modifier = Modifier.width(54.dp)
//                ) {
//                    Card(
//                        modifier = Modifier
//                            .size(width = 54.dp, height = 54.dp)
//                            .padding(horizontal = 1.dp),
//                        colors = CardDefaults.cardColors(containerColor = colorToUse),
//                        elevation = CardDefaults.cardElevation(3.dp),
//                        shape = MaterialTheme.shapes.medium
//                    ) {
//                        Box(
//                            modifier = Modifier.fillMaxSize(),
//                            contentAlignment = Alignment.Center
//                        ) {
//                            Text(
//                                text = "%02d".format(value),
//                                style = MaterialTheme.typography.headlineSmall,
//                                color = Color.White,
//                                maxLines = 1
//                            )
//                        }
//                    }
//                    Spacer(Modifier.height(3.dp))
//                    Text(
//                        text = label,
//                        style = MaterialTheme.typography.labelSmall,
//                        color = labelColor,
//                        maxLines = 1
//                    )
//                }
//                Spacer(Modifier.width(6.dp))
//            }
//        }
//    }
//}
//
//@Composable
//fun BottomAlertSnackbarHost(snackbarHostState: SnackbarHostState) {
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(bottom = 32.dp),
//        contentAlignment = Alignment.BottomCenter
//    ) {
//        SnackbarHost(
//            hostState = snackbarHostState,
//            modifier = Modifier
//                .padding(16.dp)
//                .fillMaxWidth(0.97f),
//            snackbar = { data ->
//                Snackbar(
//                    containerColor = Color.Red,
//                    contentColor = Color.White,
//                    shape = MaterialTheme.shapes.large,
//                    action = {
//                        Button(
//                            onClick = { data.dismiss() },
//                            colors = ButtonDefaults.buttonColors(
//                                containerColor = Color.White,
//                                contentColor = Color.Red
//                            )
//                        ) {
//                            Text("OK")
//                        }
//                    },
//                    modifier = Modifier
//                        .padding(vertical = 8.dp)
//                        .fillMaxWidth()
//                ) {
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Icon(
//                            Icons.Default.Edit,
//                            contentDescription = null,
//                            modifier = Modifier.size(28.dp),
//                            tint = Color.White
//                        )
//                        Spacer(Modifier.width(16.dp))
//                        Column {
//                            Text(
//                                text = data.visuals.message,
//                                style = MaterialTheme.typography.titleMedium,
//                                color = Color.White,
//                            )
//                            Text(
//                                text = "This auction notification needs your attention.",
//                                style = MaterialTheme.typography.bodyMedium,
//                                color = Color.White
//                            )
//                        }
//                    }
//                }
//            }
//        )
//    }
//}
//
//@RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
//fun showAuctionTimerNotification(context: Context, auctionNumber: String, minutes: Long) {
//    val channelId = "auction_timer_channel"
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//        val channel = NotificationChannel(channelId, "Auction Timer", NotificationManager.IMPORTANCE_HIGH)
//        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        manager.createNotificationChannel(channel)
//    }
//    val notification = NotificationCompat.Builder(context, channelId)
//        .setSmallIcon(android.R.drawable.ic_dialog_info)
//        .setContentTitle("Auction $auctionNumber")
//        .setContentText("Only $minutes minute(s) left to bid!")
//        .setPriority(NotificationCompat.PRIORITY_HIGH)
//        .build()
//    NotificationManagerCompat.from(context).notify(minutes.toInt(), notification)
//}
//
//
//
//@Composable
//fun BidItemCard(
//    item: SupplierBidDetailsItem,
//    onEdit: (SupplierBidDetailsItem) -> Unit
//) {
//    var isExpanded by remember { mutableStateOf(false) }
//    val statusColor = when (item.status.lowercase()) {
//        "active", "open" -> Color(0xFF4CAF50)
//        "pending" -> Color(0xFFFF9800)
//        "closed", "expired" -> Color(0xFFE57373)
//        else -> MaterialTheme.colorScheme.primary
//    }
//
//    Card(
//        onClick = { isExpanded = !isExpanded },
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 12.dp, vertical = 8.dp),
//        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
//        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
//        shape = RoundedCornerShape(18.dp)
//    ) {
//        Column(modifier = Modifier.fillMaxWidth().padding(18.dp)) {
//            Row(
//                Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.Top
//            ) {
//                Column(Modifier.weight(1f)) {
//                    Row(verticalAlignment = Alignment.CenterVertically) {
//                        Box(
//                            Modifier
//                                .background(
//                                    brush = androidx.compose.ui.graphics.Brush.horizontalGradient(
//                                        listOf(Color(0xFF6C5CE7), Color(0xFFA29BFE))
//                                    ),
//                                    shape = RoundedCornerShape(9.dp)
//                                )
//                                .padding(horizontal = 14.dp, vertical = 5.dp)
//                        ) {
//                            Text(
//                                text = item.itemNumber,
//                                style = MaterialTheme.typography.labelMedium,
//                                color = Color.White,
//                                fontWeight = FontWeight.Bold
//                            )
//                        }
//                        Spacer(Modifier.width(8.dp))
//                        Box(
//                            Modifier
//                                .background(
//                                    color = statusColor.copy(alpha = 0.18f),
//                                    shape = RoundedCornerShape(13.dp)
//                                )
//                                .padding(horizontal = 10.dp, vertical = 3.dp)
//                        ) {
//                            Text(
//                                text = item.status,
//                                style = MaterialTheme.typography.labelSmall,
//                                color = statusColor,
//                                fontWeight = FontWeight.Medium
//                            )
//                        }
//                    }
//                    Text(
//                        text = item.itemDescription,
//                        style = MaterialTheme.typography.titleMedium,
//                        fontWeight = FontWeight.SemiBold,
//                        maxLines = if (isExpanded) 6 else 2,
//                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
//                        modifier = Modifier.padding(bottom = 14.dp, top = 8.dp)
//                    )
//                }
//                IconButton(
//                    onClick = { onEdit(item) },
//                    modifier = Modifier
//                        .clip(RoundedCornerShape(12.dp))
//                        .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f))
//                        .size(38.dp)
//                ) {
//                    Icon(Icons.Default.Edit, contentDescription = "Edit Bid", tint = MaterialTheme.colorScheme.primary)
//                }
//            }
//            Row(
//                Modifier.fillMaxWidth().padding(vertical = 7.dp),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                InfoChip(
//                    icon = Icons.Default.Edit,
//                    label = "Qty",
//                    value = "${item.quantity}",
//                    color = Color(0xFF00BCD4)
//                )
//                InfoChip(
//                    icon = Icons.Default.Edit,
//                    label = "Start Price",
//                    value = "₹${String.format("%.2f", item.auctionStartPrice)}",
//                    color = Color(0xFF4CAF50)
//                )
//                InfoChip(
//                    icon = Icons.Default.Edit,
//                    label = "Category",
//                    value = item.category,
//                    color = Color(0xFFFF9800)
//                )
//            }
//            if (isExpanded) {
//                Divider(Modifier.padding(vertical = 12.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.22f))
//                Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
//                    DetailRow("Auction Type", item.auctionType)
//                    DetailRow("Company", item.companyName)
//                    DetailRow("Contact Person", item.contactPerson)
//                    DetailRow("Stage Status", item.stageStatus)
//                    DetailRow("RFQ Description", item.rfqDescription)
//                    if (item.bidPrice != null && item.bidPrice.toString().isNotEmpty())
//                        DetailRow("Your Bid", "₹${item.bidPrice}")
//                    if (item.lastBidPrice != null && item.lastBidPrice.toString().isNotEmpty())
//                        DetailRow("Last Bid", "₹${item.lastBidPrice}")
//                }
//            }
//            Row(
//                Modifier.fillMaxWidth().padding(top = 6.dp),
//                horizontalArrangement = Arrangement.Center
//            ) {
//                Text(
//                    text = if (isExpanded) "Hide Details ▲" else "Show More ▼",
//                    style = MaterialTheme.typography.labelMedium,
//                    color = MaterialTheme.colorScheme.primary,
//                    fontWeight = FontWeight.Medium,
//                    fontSize = androidx.compose.ui.unit.TextUnit.Unspecified,
//                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
//                )
//            }
//        }
//    }
//}
//
//@Composable
//private fun InfoChip(
//    icon: androidx.compose.ui.graphics.vector.ImageVector,
//    label: String,
//    value: String,
//    color: Color
//) {
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier
//            .background(color = color.copy(alpha = 0.12f), shape = RoundedCornerShape(12.dp))
//            .padding(11.dp)
//    ) {
//        Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(19.dp))
//        Spacer(Modifier.height(4.dp))
//        Text(label, style = MaterialTheme.typography.labelSmall, color = color, fontWeight = FontWeight.Medium)
//        Text(
//            value,
//            style = MaterialTheme.typography.labelMedium,
//            color = MaterialTheme.colorScheme.onSurface,
//            fontWeight = FontWeight.Bold,
//            maxLines = 1,
//            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
//        )
//    }
//}
//
//@Composable
//private fun DetailRow(label: String, value: String) {
//    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//        Text(label, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f), modifier = Modifier.weight(1f))
//        Text(value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium, modifier = Modifier.weight(2f), textAlign = androidx.compose.ui.text.style.TextAlign.End)
//    }
//}
