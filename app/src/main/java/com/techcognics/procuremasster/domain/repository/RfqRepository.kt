package com.techcognics.procuremasster.domain.repository

import android.content.Context
import android.net.Uri
import com.techcognics.procuremasster.data.remote.RFQ
import com.techcognics.procuremasster.data.remote.dto.BidResponse
import com.techcognics.procuremasster.data.remote.dto.BidSaveRequest
import com.techcognics.procuremasster.data.remote.dto.BidSaveResponse
import com.techcognics.procuremasster.data.remote.dto.FreightTerms
import com.techcognics.procuremasster.data.remote.dto.RfqViewResponse
import okhttp3.MultipartBody
import okhttp3.ResponseBody

interface RfqRepository {

    suspend fun getRfqsInBetween(startDate: String, endDate: String): List<RFQ>

    suspend fun getRfqById(id: Int): RfqViewResponse

    suspend fun downloadPdf(rfqId: Int): ResponseBody

    suspend fun downloadAttachment(attachmentId: Int): ResponseBody

    suspend fun getBidDetails(rfqNumber: String): BidResponse

    suspend fun getFreightTerms(): List<FreightTerms>

    suspend fun saveBid(request: BidSaveRequest): ResponseBody

    suspend fun uploadAttachment(rfqId: Int, file: MultipartBody.Part): ResponseBody

    suspend fun uploadAttachmentFromUri(rfqId: Int, fileUri: Uri, context: Context): ResponseBody
}
