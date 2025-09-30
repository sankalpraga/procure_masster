package com.techcognics.procuremasster.data.remote.auctionpackage

data class Item(
    val auctionStartPrice: Double,
    val bidDecrementalValue: Double,
    val createdBy: Any,
    val createdDate: Any,
    val description: String,
    val id: Int,
    val itemNumber: String,
    val lastPurchasePrice: Double,
    val lineNumber: String,
    val price: Any,
    val quantity: Int,
    val uom: Uom,
    val updatedBy: Any,
    val updatedDate: Any
)