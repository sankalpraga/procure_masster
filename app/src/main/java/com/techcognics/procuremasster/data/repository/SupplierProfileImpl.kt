package com.techcognics.procuremasster.data.repository

import com.techcognics.procuremasster.data.remote.ApiService
import com.techcognics.procuremasster.data.remote.Category
import com.techcognics.procuremasster.data.remote.City
import com.techcognics.procuremasster.data.remote.Country
import com.techcognics.procuremasster.data.remote.CurrencyDetailsItem
import com.techcognics.procuremasster.data.remote.Industry
import com.techcognics.procuremasster.data.remote.ItemProces
import com.techcognics.procuremasster.data.remote.State
import com.techcognics.procuremasster.data.remote.SubCategory
import com.techcognics.procuremasster.data.remote.SubIndustry
import com.techcognics.procuremasster.data.remote.profile.SupplierResponse
import javax.inject.Inject

// change package / imports as per your project
class SupplierRepository @Inject constructor(
    private val api: ApiService   // whatever your Retrofit interface is called
) {

    suspend fun getSupplierByUserId(userId: Long): SupplierResponse =
        api.fetchSupplierRecordByUserId(userId)

    // ▼ dropdown APIs – use your existing Retrofit functions ▼
    suspend fun getIndustryList(): List<Industry> =
        api.fetchIndustryRecords()

    suspend fun getSubIndustryList(): List<SubIndustry> =
        api.fetchAllSubIndustryRecords()

    suspend fun getCategoryList(): List<Category> =
        api.fetchCategoryRecords()

    suspend fun getSubCategoryList(): List<SubCategory> =
        api.fetchAllSubCategoryRecords()

    suspend fun getItemProcessList(): List<ItemProces> =
        api.fetchAllItemProcessRecords()

    suspend fun getCountryList(): List<Country> =
        api.country()

    suspend fun getStateList(countryId: Int): List<State> =
        api.state(countryId)

    suspend fun getCityList(stateId: Int): List<City> =
        api.city(stateId)

    suspend fun getCurrencyList(): List<CurrencyDetailsItem> =
        api.fetchAllCurrencyDetails()
}
