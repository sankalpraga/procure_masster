package com.techcognics.procuremasster.data.repository

import com.techcognics.procuremasster.data.remote.ApiService
import com.techcognics.procuremasster.data.remote.RFQ
import com.techcognics.procuremasster.data.remote.dto.RFQBidResponse
import com.techcognics.procuremasster.data.remote.dto.RfqViewResponse
import com.techcognics.procuremasster.domain.repository.RfqRepository
import okhttp3.ResponseBody
import javax.inject.Inject

class RfqRepositoryImpl @Inject constructor(
    private val api: ApiService
): RfqRepository {
    override suspend fun getRfqsInBetween(startDate: String, endDate: String): List<RFQ> {
        return api.fetchRfqInBetween(startDate, endDate)
    }

    override suspend fun getRfqById(id: Int): RfqViewResponse {
        return api.getRfqById(id)
    }

    override suspend fun getBidDetails(rfqNumber: String): RFQBidResponse {
        return api.getBidDetails(rfqNumber)
    }

    override suspend fun downloadPdf(rfqId: Int): ResponseBody = api.generateRfqPdf(rfqId)

    override suspend fun downloadAttachment(attachmentId: Int): ResponseBody =
        api.downloadAttachment(attachmentId)
}