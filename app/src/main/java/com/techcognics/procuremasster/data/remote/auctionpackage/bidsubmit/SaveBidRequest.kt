package com.techcognics.procuremasster.data.remote.auctionpackage.bidsubmit

data class SaveBidRequest(
    val id: Int,
    val quantity: Int,
    val uom: Map<String, Any>,   // Use your actual type if available
    val auctionStartPrice: Double,
    val lastPurchasePrice: Double,
    val bidDecrementalValue: Double,
    val price: Double
)
