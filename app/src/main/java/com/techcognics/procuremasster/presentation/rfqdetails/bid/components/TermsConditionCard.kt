package com.techcognics.procuremasster.presentation.rfqdetails.bid.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.techcognics.procuremasster.data.remote.dto.BidResponse

@Composable
fun TermsConditionsCard(bid: BidResponse) {
    Column(Modifier.padding(16.dp)) {
        Text("Packing & Forwarding: ${bid.packingForwarding}")
        Text("Freight Terms: ${bid.freightTerms}")
        // Add editable fields here
    }
}

