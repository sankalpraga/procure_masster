package com.techcognics.procuremasster.data.repository

import com.techcognics.procuremasster.data.Auction.AuctionResponseItem
import com.techcognics.procuremasster.data.remote.ApiService
import com.techcognics.procuremasster.domain.repository.AuctionRepository
import javax.inject.Inject

class AuctionRepositoryImpl @Inject constructor(
    private val api: ApiService): AuctionRepository {

    override suspend fun getAuctionDetails(): AuctionResponseItem {
        return api.getAuctionDetails()
    }
}