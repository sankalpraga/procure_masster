package com.techcognics.procuremasster.data.remote.auctionpackage.bidsubmit

data class BidPriceRequest(
    val rfqId: Int,
    val itemId: Int,
    val bidPrice: Double

)
