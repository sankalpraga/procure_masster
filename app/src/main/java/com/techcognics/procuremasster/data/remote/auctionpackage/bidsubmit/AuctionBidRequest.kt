package com.techcognics.procuremasster.data.remote.auctionpackage.bidsubmit

data class AuctionBidRequest(
    val id: Int,
    val quantity: Int,
    val uom: Map<String, Any>, // Or your actual Unit-Of-Measurement type
    val auctionStartPrice: Double,
    val lastPurchasePrice: Double,
    val bidDecrementalValue: Double,
    val price: Double
)
