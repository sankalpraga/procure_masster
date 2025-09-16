package com.techcognics.procuremasster.data.remote

import com.techcognics.procuremasster.data.AccountResponse
import com.techcognics.procuremasster.data.remote.dto.Country
import com.techcognics.procuremasster.data.remote.dto.LoginRequest
import com.techcognics.procuremasster.data.remote.dto.LoginResponse
import com.techcognics.procuremasster.data.remote.dto.RFQBidResponse
import com.techcognics.procuremasster.data.remote.dto.RfqViewResponse
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
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

    // ✅ This was outside by mistake — moved inside
    @GET("supplierDepository/fetchItemBidsDetails/{rfqNumber}")
    suspend fun getBidDetails(
        @Path("rfqNumber") rfqNumber: String
    ): RFQBidResponse
}
