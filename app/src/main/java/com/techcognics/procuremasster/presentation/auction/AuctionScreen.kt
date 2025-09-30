package com.techcognics.procuremasster.presentation.auction

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.techcognics.procuremasster.presentation.base.UiState
import com.techcognics.procuremasster.presentation.designsystem.UiStateHandler
import com.techcognics.procuremasster.presentation.openPdf
import com.techcognics.procuremasster.presentation.saveFileToDownloads
import androidx.compose.foundation.lazy.items

@Composable
fun AuctionScreen(
    navController: NavHostController,
    viewModel: AuctionViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val uiState by viewModel.listState.collectAsState(initial = UiState.Idle)
    var showOnlyOpen by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.weight(1f))  // Fills all available space to push Switch to right
            Switch(
                checked = showOnlyOpen,
                onCheckedChange = { showOnlyOpen = it },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.primary
                )
            )
        }


        Spacer(Modifier.height(16.dp))

        UiStateHandler(
            state = uiState,
            onSuccess = { auctions ->
                val displayAuctions = if (showOnlyOpen) {
                    auctions.filter { it.status.equals("OPEN", ignoreCase = true) }
                } else {
                    auctions
                }

                if (displayAuctions.isEmpty()) {
                    Text("No Auctions available")
                } else {
                    LazyColumn {
                        items(displayAuctions) { auction ->
                            AuctionCard(
                                auction,
                                onView = { selectedAuction ->
                                    navController.navigate("auction_view/${selectedAuction.rfqId}")
                                },
                                onBidHistory = {
                                    viewModel.downloadBidHistory(auction.rfqId) { bytes ->
                                        val file = saveFileToDownloads(
                                            context,
                                            "${auction.rfqId}-RFQ-Report.pdf",
                                            bytes
                                        )
                                        openPdf(context, file)
                                    }
                                },
                                onSubmitBid = { selected ->
                                    navController.navigate("auction_submit/${selected.rfqId}")
                                }
                            )
                        }
                    }
                }
            }
        )
    }
}


