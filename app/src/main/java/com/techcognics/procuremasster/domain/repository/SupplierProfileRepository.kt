//package com.techcognics.procuremasster.domain.repository
//
//import com.techcognics.procuremasster.data.remote.dto.CategoryDto
//import com.techcognics.procuremasster.data.remote.dto.CityDto
//import com.techcognics.procuremasster.data.remote.dto.CountryDto
//import com.techcognics.procuremasster.data.remote.dto.CurrencyDetailsItem
//import com.techcognics.procuremasster.data.remote.dto.IndustryDto
//import com.techcognics.procuremasster.data.remote.dto.ItemProcessDto
//import com.techcognics.procuremasster.data.remote.dto.StateDto
//import com.techcognics.procuremasster.data.remote.dto.SubCategoryDto
//import com.techcognics.procuremasster.data.remote.dto.SubIndustryDto
//import com.techcognics.procuremasster.data.remote.dto.SupplierProfileDto
//import retrofit2.Response
//
//interface SupplierProfileRepository {
//
//    suspend fun fetchSupplierRecordByUserId(id: Int): SupplierProfileDto
//
//    suspend fun getCountries(): List<CountryDto>
//
//    suspend fun getStates(countryId: Int): List<StateDto>
//
//    suspend fun getCities(stateId: Int): List<CityDto>
//
//    suspend fun getCategoryRecords(): List<CategoryDto>
//
//    suspend fun getSubCategories(categoryId: Int): List<SubCategoryDto>
//
//    suspend fun getItemProcessRecords(subCategoryId: Int): List<ItemProcessDto>
//
//    suspend fun getIndustries(): List<IndustryDto>
//
//    suspend fun getSubIndustries(industryId: Int): List<SubIndustryDto>
//
//    suspend fun getCurrencyDetails(): List<CurrencyDetailsItem>
//
//    suspend fun updateProfile(profile: SupplierProfileDto): Response<Unit>
//}
