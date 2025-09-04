package com.techcognics.procuremasster.domain.repository

import com.techcognics.procuremasster.data.remote.RFQ
import com.techcognics.procuremasster.data.remote.RFQDetailResponse
import java.sql.Date

interface RfqRepository{

    suspend fun getRfqsInBetween(startDate: String, endDate: String):
            List<RFQ>

    suspend fun getRfqById(rfqId: Int):
            RFQDetailResponse

}