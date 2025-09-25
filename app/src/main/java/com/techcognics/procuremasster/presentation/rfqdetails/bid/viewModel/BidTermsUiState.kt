package com.techcognics.procuremasster.presentation.rfqdetails.bid.viewModel

import com.techcognics.procuremasster.data.remote.dto.ItemBidingPricing

data class BidTermsUiState(
    val isLoading: Boolean = false,
    val rfqDescription: String = "",
    val currency: String = "",
    val rfqIssueDate: String = "",
    val offerSubmissionDate: String = "",
    val itemPricingList: List<ItemBidingPricing> = emptyList(),

    // bid term fields
    val packingForwarding: String = "",
    val freightCharges: String = "",
    val paymentSchedule: String = "",
    val specialInstructionToBid: String = "",
    val comments: String = "",

    val isSaving: Boolean = false,
    val error: String? = null
)