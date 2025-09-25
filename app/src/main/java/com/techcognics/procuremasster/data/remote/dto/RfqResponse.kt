package com.techcognics.procuremasster.data.remote.dto

data class RfqResponse(
    val rfqNumber: String,
    val rfqDescription: String?,
    val nameOfBuyer: String,
    val issueDate: String,
    val offerSubmissionDate: String,
    val status: String,
    val bidStatus: String,
    val items: List<RfqItem> = emptyList()
)

data class RfqItem(
    val itemNumber: String,
    val description: String,
    val quantity: Int,
    val uom: Uom
)

//data class Uom(
//    val id: Int,
//    val name: String
//)
