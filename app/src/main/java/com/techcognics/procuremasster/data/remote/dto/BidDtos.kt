package com.techcognics.procuremasster.data.remote.dto

import android.net.Uri

data class Attachment(
    val uri: Uri,
    val name: String,
    val size: Long
)

data class FreightTerms(
    val id: Int,
    val name: String? = null  // Usually nullable
)

data class ItemBidingPricing(

    val id: Int? = null,           // Backend accepts null for new bid items
    val itemNumber: String,
    val description: String,
    val rfqSupplierRfqId: Int? = null,
    val itemId: Int,
    val price: Double? = null,
    val lastBid: Double? = null,
    val discount: Double? = null,
    val tax: Double? = null,
    val dueDate: String? = null,
    val moq: Int,
    val warranty: String? = null,
    val deliveryTime: String? = null,
    val currency: String,
    val bidDateTime: String? = null,
    val auctionBidStatus: String? = null,
    val jhiUserRef: String? = null,
    val quantity: Int,
    val auctionEndDateTime: String? = null,
    val uom: Uom,
    val rfqId: Int                  // REQUIRED: must match the RFQ top-level ID
)

data class BidSaveRequest(
    val rfqId: Int,
    val supplierDepositoryId: Int,
    val packingForwarding: Double,
    val freightTerms: FreightTerms,           // Must send as object, not String!
    val freightCharges: Double,
    val paymentSchedule: String,
    val specialInstructionToBid: String,
    val comments: String,
    val status: String = "PUBLISH",
    val reasons: String? = null,
    val itemBidingPricingList: List<ItemBidingPricing>
)

data class BidSaveResponse(
    val message: String? = null,
    val status: String? = null
)

data class BidResponse(
    val comments: Any?,
    val currency: String,
    val freightCharges: Any?,
    val freightTerms: Int,
    val itemBidingPricingList: List<ItemBidingPricing>,
    val packingForwarding: Double,
    val paymentSchedule: Any?,
    val rfqId: Int,
    val specialInstructionToBid: Any?,
    val supplierDepositoryId: Int
)
