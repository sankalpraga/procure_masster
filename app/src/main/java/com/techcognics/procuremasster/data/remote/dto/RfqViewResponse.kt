package com.techcognics.procuremasster.data.remote.dto

import com.google.gson.annotations.SerializedName

data class RfqViewResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("rfqNumber")val rfqNumber: String,
    @SerializedName("rfqDescription")val rfqDescription: String,
    @SerializedName("nameOfBuyer")val nameOfBuyer: String,
    @SerializedName("offerSubmissionDate")val offerSubmissionDate: String,

    @SerializedName("deliveryAddress")val deliveryAddress: String?,
    @SerializedName("status")val status: String,
    @SerializedName("currency")val currency: String,

    @SerializedName("createdDate")val createdDate: String,

    @SerializedName("items")val items: List<RfqItemResponse>,
    val rfqsAttachments: List<RfqAttachment> = emptyList()

)

data class RfqItemResponse(
    @SerializedName("itemNumber")val itemNumber: String,
    @SerializedName("description")val description: String,
    @SerializedName("uom")val uom: UomResponse,
    @SerializedName("quantity")val quantity: Int,

)

data class UomResponse(
    @SerializedName("id")val id: Int,
    @SerializedName("name")val name: String
)


data class RfqAttachment(
    val id: Int,
    val fileName: String
)
