package com.techcognics.procuremasster.data.repository

import com.techcognics.procuremasster.data.remote.auctionpackage.AuctionResponseItem
import com.techcognics.procuremasster.data.remote.ApiService
import com.techcognics.procuremasster.data.remote.auctionpackage.view.AuctionViewResponse
import com.techcognics.procuremasster.domain.repository.AuctionRepository
import okhttp3.ResponseBody
import javax.inject.Inject

class AuctionRepositoryImpl @Inject constructor(
    private val api: ApiService): AuctionRepository {

    override suspend fun getAuctionDetails(): List<AuctionResponseItem> {
        return api.getAuctionDetails()
    }

    override suspend fun getBidHistory(rfqId: Int): ResponseBody {
        return api.getBidHistory(rfqId)
    }

    override suspend fun getRfqByIdAuction(rfqId: Int): AuctionViewResponse {
        return api.getRfqByIdAuction(rfqId)
    }

}