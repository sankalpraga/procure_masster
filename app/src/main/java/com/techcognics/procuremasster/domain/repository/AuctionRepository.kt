package com.techcognics.procuremasster.domain.repository

import com.techcognics.procuremasster.data.remote.auctionpackage.AuctionResponseItem
import com.techcognics.procuremasster.data.remote.auctionpackage.bidsubmit.BidPriceRequest
import com.techcognics.procuremasster.data.remote.auctionpackage.bidsubmit.SaveBidRequest
import com.techcognics.procuremasster.data.remote.auctionpackage.bidsubmit.SupplierBidDetailsItem
import com.techcognics.procuremasster.data.remote.auctionpackage.view.AuctionViewResponse
import okhttp3.ResponseBody

interface AuctionRepository {

    suspend fun getAuctionDetails(): List<AuctionResponseItem>

    suspend fun getBidHistory(rfqId: Int): ResponseBody

    suspend fun getRfqByIdAuction(rfqId: Int): AuctionViewResponse

    suspend fun getBidAuction(rfqId: Int): List<SupplierBidDetailsItem>

    suspend fun saveBid(rfqId: Int, bids: List<SaveBidRequest>): Result<Unit>




}