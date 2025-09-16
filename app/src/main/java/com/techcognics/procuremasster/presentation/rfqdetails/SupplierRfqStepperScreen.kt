package com.techcognics.procuremasster.presentation.rfqdetails

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SupplierRfqStepperScreen(rfqNumber: String) {
    val stepState = rememberSaveable { mutableIntStateOf(0) }

    RFQStepperLayout(
        currentStep = stepState,
        onSubmit = {
            // TODO: Later API call for submit
        }
    ) { current ->
        when (current) {
            0 -> PricingDetailsScreen(
                rfqNumber = rfqNumber,
                description = "Request for quotation for IT hardware components including cables, servers",
                currency = "AUD",
                issueDate = "2025-08-26",
                submitDate = "2025-08-26",
                items = listOf(
                    ItemBidPricing(150, "GT-CBL-102", "CAT6 Ethernet Cable – 10 meters, Shielded", "PCS", 287, 500, null, null, null, null, null, null, null, "AUD"),
                    ItemBidPricing(150, "GT-SVR-2100", "2U Rackmount Server – 32GB RAM, 2TB SSD", "PCS", 288, 15, null, null, null, null, null, null, null, "AUD"),
                    ItemBidPricing(150, "GT-MON-24IPS", "24” IPS LED Monitor – Full HD, HDMI input", "PCS", 289, 40, null, null, null, null, null, null, null, "AUD"),
                    ItemBidPricing(150, "GT-KBM-120", "Wireless Keyboard & Mouse Combo – USB", "SETS", 290, 60, null, null, null, null, null, null, null, "AUD")
                )
            )
            1 -> BidTermsScreen()
            else -> AttachmentsScreen()
        }
    }
}

@Preview(showBackground = true, widthDp = 420, heightDp = 800)
@Composable
fun SupplierRfqStepperScreen_Preview() {
    MaterialTheme {
        SupplierRfqStepperScreen(rfqNumber = "260820251135")
    }
}
