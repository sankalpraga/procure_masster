package com.techcognics.procuremasster.domain.repository

import com.techcognics.procuremasster.data.Auction.AuctionResponse
import com.techcognics.procuremasster.data.Auction.AuctionResponseItem

interface AuctionRepository {

    suspend fun getAuctionDetails(): AuctionResponse
}