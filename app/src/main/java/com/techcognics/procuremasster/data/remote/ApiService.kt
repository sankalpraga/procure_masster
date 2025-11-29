package com.techcognics.procuremasster.data.remote

import com.techcognics.procuremasster.data.AccountResponse
import com.techcognics.procuremasster.data.remote.auctionpackage.AuctionResponseItem
import com.techcognics.procuremasster.data.remote.auctionpackage.bidsubmit.SaveBidRequest
import com.techcognics.procuremasster.data.remote.auctionpackage.bidsubmit.SupplierBidDetailsItem
import com.techcognics.procuremasster.data.remote.auctionpackage.view.AuctionViewResponse
import com.techcognics.procuremasster.data.remote.dto.BidResponse
import com.techcognics.procuremasster.data.remote.dto.BidSaveRequest
import com.techcognics.procuremasster.data.remote.dto.FreightTerms
import com.techcognics.procuremasster.data.remote.dto.LoginRequest
import com.techcognics.procuremasster.data.remote.dto.LoginResponse
import com.techcognics.procuremasster.data.remote.dto.RfqViewResponse
import com.techcognics.procuremasster.data.remote.profile.SupplierResponse
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // ---------- AUTH ----------
    @POST("authenticate")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET("account")
    suspend fun getAccount(): AccountResponse

    // ---------- RFQ ----------
    @GET("supplierDepository/fetchActiveRfqsBidInBetween")
    suspend fun fetchRfqInBetween(
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String
    ): List<RFQ>

    @GET("rfq/fetchRfqRecordsByRfqId/{id}")
    suspend fun getRfqById(
        @Path("id") id: Int
    ): RfqViewResponse

    @GET("rfq/generateRfqPdfReport/{rfqId}")
    suspend fun generateRfqPdf(
        @Path("rfqId") rfqId: Int
    ): ResponseBody

    @GET("rfqsAttachment/downloadAttachment/{attachmentId}")
    suspend fun downloadAttachment(
        @Path("attachmentId") attachmentId: Int
    ): ResponseBody

    // ---------- BID ----------
    @GET("supplierDepository/fetchItemBidsDetails/{rfqNumber}")
    suspend fun getBidDetails(
        @Path("rfqNumber") rfqNumber: String
    ): BidResponse

    @GET("freightTermsResource/fetchAllFreightTermsDetails")
    suspend fun getFreightTerms(): List<FreightTerms>

    @POST("rfqSuppliersResource/save")
    suspend fun saveBid(@Body request: BidSaveRequest): ResponseBody

    @Multipart
    @POST("rfqsAttachment/upload/{rfqId}")
    suspend fun uploadAttachment(
        @Path("rfqId") rfqId: Int,
        @Part file: MultipartBody.Part
    ): ResponseBody

    // ---------- AUCTION ----------
    @GET("supplierDepository/fetchAuctionDetails")
    suspend fun getAuctionDetails(): List<AuctionResponseItem>

    @GET("rfq/fetchRfqRecordsByRfqId/{rfqId}")
    suspend fun getRfqByIdAuction(
        @Path("rfqId") rfqId: Int
    ): AuctionViewResponse

    @GET("supplierDepository/generateBidHistory/{rfqId}")
    suspend fun getBidHistory(
        @Path("rfqId") rfqId: Int
    ): ResponseBody

    @GET("supplierDepository/fetchSupplierBidAuction/{rfqId}")
    suspend fun getBidAuction(
        @Path("rfqId") rfqId: Int
    ): List<SupplierBidDetailsItem>

    @POST("supplierDepository/saveBid/{rfqSupplierRfqId}")
    suspend fun saveBid(
        @Path("rfqSupplierRfqId") rfqId: Int,
        @Body bidList: List<SaveBidRequest>
    ): Response<Unit>

    // ---------- SUPPLIER PROFILE MAIN RECORD ----------
//    @GET("supplierDepository/fetchSupplierRecordByUserId/{userId}")
//    suspend fun getSupplierByUserId(
//        @Path("userId") userId: Long
//    ): SupplierResponse

    // ---------- DROPDOWN APIs FOR SUPPLIER PROFILE ----------

    // Industry
    @GET("industry/fetchIndustryRecords")
    suspend fun fetchIndustryRecords(): List<Industry>

    // Sub Industry
    @GET("subIndustry/fetchAllSubIndustryRecords")
    suspend fun fetchAllSubIndustryRecords(): List<SubIndustry>

    // Category
    @GET("category/fetchCategoryRecords")
    suspend fun fetchCategoryRecords(): List<Category>

    // Sub Category
    @GET("subCategory/fetchAllSubCategoryRecords")
    suspend fun fetchAllSubCategoryRecords(): List<SubCategory>

    // Item Process
    @GET("itemProcess/fetchAllItemProcessRecords")
    suspend fun fetchAllItemProcessRecords(): List<ItemProces>

    // Country list
    @GET("location/country")
    suspend fun country(): List<Country>

    // State list for a country
    @GET("location/state/{countryId}")
    suspend fun state(
        @Path("countryId") countryId: Int
    ): List<State>

    // City list for a state
    @GET("location/city/{stateId}")
    suspend fun city(
        @Path("stateId") stateId: Int
    ): List<City>

    // Currency list
    // Currency list
    @GET("location/fetchAllCurrencyDetails")
    suspend fun fetchAllCurrencyDetails(): List<CurrencyDetailsItem>

    // ---------- SUPPLIER PROFILE MAIN RECORD ----------
    @GET("supplierDepository/fetchSupplierRecordByUserId/{userId}")
    suspend fun fetchSupplierRecordByUserId(
        @Path("userId") userId: Long
    ): SupplierResponse





}
