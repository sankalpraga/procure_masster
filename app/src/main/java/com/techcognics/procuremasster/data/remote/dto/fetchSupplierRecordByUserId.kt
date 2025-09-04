package com.techcognics.procuremasster.data.remote.dto

data class SupplierProfileDto(
    val id: Int,
    val companyName: String,
    val gstinNumber: String?,
    val companyAddress: String?,
    val website: String?,
    val currency: String?,
    val country: CountryDto?,
    val state: StateDto?,
    val city: CityDto?,
    val pinCode: String?,
    val industry: IndustryDto?,
    val subIndustry: SubIndustryDto?,
    val category: CategoryDto?,
    val subCategory: SubCategoryDto?,
    val itemProcess: List<ItemProcessDto>?,
    val contactPerson: String?,
    val username: String?,
    val user: UserDto?
)

data class CountryDto(val id: Int, val code: String, val name: String)
data class StateDto(val id: Int, val code: String, val name: String, val refId: Int?)
data class CityDto(val id: Int, val code: String, val name: String, val refId: Int?)
data class IndustryDto(val id: Int, val industryName: String)
data class SubIndustryDto(val id: Int, val subIndustryName: String, val industryId: Int)
data class CategoryDto(val id: Int, val categoryName: String)
data class SubCategoryDto(val id: Int, val subCategoryName: String, val categoryId: Int)
data class ItemProcessDto(val id: Int, val itemProcessName: String, val subCategoryId: Int)
data class UserDto(
    val id: Int,
    val login: String,
    val email: String,
    val mobileNumber: String?,
    val phoneNumber: String?,
    val firstName: String?,
    val lastName: String?
)

