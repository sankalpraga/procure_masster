package com.techcognics.procuremasster.data.remote

import com.techcognics.procuremasster.data.AccountResponse
import com.techcognics.procuremasster.data.remote.dto.Country
import com.techcognics.procuremasster.data.remote.dto.LoginRequest
import com.techcognics.procuremasster.data.remote.dto.LoginResponse
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

    @GET("api/rfq/fetchRfqRecordsByRfqId/{rfqId}")
    suspend fun getRfqById(
        @Path("rfqId") rfqId: Int
    ): RFQDetailResponse

    @GET("api/location/country")
    suspend fun getCountries(): List<Country>
//
//    @GET("api/supplierDepository/fetchSupplierRecordByUserId/{}")
//    suspend fun getSupplierRecord(): List<fetchSupplierRecordByUserId>

}
