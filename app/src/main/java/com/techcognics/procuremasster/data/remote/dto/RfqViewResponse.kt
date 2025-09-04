package com.techcognics.procuremasster.data.remote.dto

// 🔹 Root RFQ response from API
data class RfqViewResponse(
    val rfqNumber: String,
    val rfqDescription: String,
    val nameOfBuyer: String,
    val createdDate: String,
    val status: String,
    val items: List<RfqItemResponse>
)

// 🔹 RFQ Item inside the RFQ
data class RfqItemResponse(
    val itemNumber: String,
    val description: String,
    val quantity: Int,
    val uom: UomResponse
)

// 🔹 UOM object inside item
data class UomResponse(
    val uomName: String
)
