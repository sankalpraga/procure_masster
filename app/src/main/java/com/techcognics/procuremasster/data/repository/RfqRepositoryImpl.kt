package com.techcognics.procuremasster.data.repository

import com.techcognics.procuremasster.data.remote.ApiService
import com.techcognics.procuremasster.data.remote.RFQ
import com.techcognics.procuremasster.data.remote.RFQDetailResponse
import com.techcognics.procuremasster.domain.repository.RfqRepository
import javax.inject.Inject

class RfqRepositoryImpl @Inject constructor(
    private val api: ApiService
): RfqRepository {
    override suspend fun getRfqsInBetween(startDate: String, endDate: String): List<RFQ> {
        return api.fetchRfqInBetween(startDate, endDate)
    }
    override suspend fun getRfqById(rfqId: Int): RFQDetailResponse {
        // detail
        return api.getRfqById(rfqId)
    }
}