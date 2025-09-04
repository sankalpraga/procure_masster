package com.techcognics.procuremasster.data.remote

import android.content.ClipDescription
import androidx.compose.ui.input.pointer.PointerId

data class RFQ(
    val id: Int,
    val rfqNumber: String,
    val rfqDescription: String,
    val nameOfBuyer: String,
    val offerSubmissionDate: String,
    val deliveryAddress: String,
    val status: String,
    val stageStatus: String,
    val createdDate: String,
    val uniqueId: String,
    val rfqRefId: String,
    val bidStatus: String,
    val currency: String
)
