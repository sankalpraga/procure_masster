package com.techcognics.procuremasster.domain.repository

import com.techcognics.procuremasster.data.remote.RFQ
import com.techcognics.procuremasster.data.remote.dto.RFQBidResponse
import com.techcognics.procuremasster.data.remote.dto.RfqViewResponse
import okhttp3.ResponseBody

interface RfqRepository{

    suspend fun getRfqsInBetween(startDate: String, endDate: String):
            List<RFQ>

    suspend fun getRfqById(id: Int): RfqViewResponse

    suspend fun downloadPdf(rfqId: Int): ResponseBody   // <--- add this
    suspend fun downloadAttachment(attachmentId: Int): ResponseBody
    suspend fun getBidDetails(rfqNumber: String): RFQBidResponse

}