package com.techcognics.procuremasster.data.remote

import com.techcognics.procuremasster.data.AccountResponse
import com.techcognics.procuremasster.data.Auction.AuctionResponse
import com.techcognics.procuremasster.data.Auction.AuctionResponseItem
import com.techcognics.procuremasster.data.remote.dto.BidResponse
import com.techcognics.procuremasster.data.remote.dto.BidSaveRequest
import com.techcognics.procuremasster.data.remote.dto.BidSaveResponse
import com.techcognics.procuremasster.data.remote.dto.FreightTerms
import com.techcognics.procuremasster.data.remote.dto.LoginRequest
import com.techcognics.procuremasster.data.remote.dto.LoginResponse
import com.techcognics.procuremasster.data.remote.dto.RfqViewResponse
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("authenticate")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET("account")
    suspend fun getAccount(): AccountResponse

    @GET("supplierDepository/fetchActiveRfqsBidInBetween")
    suspend fun fetchRfqInBetween(
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String
    ): List<RFQ>

    @GET("rfq/fetchRfqRecordsByRfqId/{id}")
    suspend fun getRfqById(
        @Path("id") id: Int
    ): RfqViewResponse

    // 🔹 PDF download
    @GET("rfq/generateRfqPdfReport/{rfqId}")
    suspend fun generateRfqPdf(
        @Path("rfqId") rfqId: Int
    ): ResponseBody

    // 🔹 Attachment download
    @GET("rfqsAttachment/downloadAttachment/{attachmentId}")
    suspend fun downloadAttachment(
        @Path("attachmentId") attachmentId: Int
    ): ResponseBody

    //Bid Page
    @GET("supplierDepository/fetchItemBidsDetails/{rfqNumber}")
    suspend fun getBidDetails(@Path("rfqNumber") rfqNumber: String): BidResponse

    //freight
    @GET("freightTermsResource/fetchAllFreightTermsDetails")
    suspend fun getFreightTerms(): List<FreightTerms>

    @POST("rfqSuppliersResource/save")
    suspend fun saveBid(@Body request: BidSaveRequest): ResponseBody

    // Upload attachment (correct REST endpoint for your backend)
    @Multipart
    @POST("rfqsAttachment/upload/{rfqId}")
    suspend fun uploadAttachment(
        @Path("rfqId") rfqId: Int,
        @Part file: MultipartBody.Part
    ): ResponseBody


    //Auction
    @GET("supplierDepository/fetchAuctionDetails")
    suspend fun getAuctionDetails(): AuctionResponseItem
}
