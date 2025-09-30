package com.techcognics.procuremasster.data.remote.auctionpackage

data class AuctionResponseItem(
    val auctionDescription: Any,
    val auctionEndDateTime: String,
    val auctionNo: String,
    val auctionStartDateTime: String,
    val auctionTitle: String,
    val auctionType: String,
    val buyerName: String,
    val category: String,
    val companyAddress: String,
    val companyName: String,
    val contactNumber: String,
    val email: String,
    val items: List<Item>,
    val rfqId: Int,
    val stageStatus: String,
    val status: String
)