package com.techcognics.procuremasster.data.remote.profile

data class SupplierResponse(
    val id: Long,
    val companyName: String?,
    val gstinNumber: String?,
    val companyAddress: String?,
    val website: String?,
    val currency: String?,
    val country: CountryDto?,
    val state: StateDto?,        // ✅ FIXED
    val city: CityDto?,          // ✅ FIXED
    val pinCode: String?,
    val industry: IndustryDto?,  // (If API sends object)
    val subIndustry: SubIndustryDto?,
    val category: CategoryDto?,
    val subCategory: SubCategoryDto?,
    val itemProcess: List<ItemProcessDto>?,
    val contactPerson: String?,
    val username: String?,
    val user: UserDto?,
    val buyersId: Long?
)

data class StateDto(
    val id: Long,
    val name: String
)

data class CityDto(
    val id: Long,
    val name: String
)

data class IndustryDto(
    val id: Long,
    val industryName: String
)

data class SubIndustryDto(
    val id: Long,
    val subIndustryName: String
)

data class CategoryDto(
    val id: Long,
    val categoryName: String
)

data class SubCategoryDto(
    val id: Long,
    val subCategoryName: String
)

data class ItemProcessDto(
    val id: Long,
    val itemProcessName: String
)



//package com.techcognics.procuremasster.data.remote.profile
//
//
//
//
//data class SupplierResponse(
//    val id: Long,
//    val companyName: String?,
//    val gstinNumber: String?,
//    val companyAddress: String?,
//    val website: String?,
//    val currency: String?,
//    val country: CountryDto?,
//    val state: String?,
//    val city: String?,
//    val pinCode: String?,
//    val industry: String?,
//    val subIndustry: String?,
//    val category: String?,
//    val subCategory: String?,
//    val itemProcess: List<String>?,
//    val contactPerson: String?,
//    val username: String?,
//    val createdBy: String?,
//    val createdDate: String?,
//    val updatedDate: String?,
//    val updatedBy: String?,
//    val user: UserDto?,
//    val buyersId: Long?
//)
//
data class CountryDto(
    val id: Long,
    val code: String,
    val name: String
)
//
data class UserDto(
    val createdBy: String?,
    val createdDate: String?,
    val lastModifiedBy: String?,
    val lastModifiedDate: String?,
    val id: Long,
    val login: String?,
    val password: String?,
    val firstName: String?,
    val lastName: String?,
    val email: String?,
    val mobileNumber: String?,
    val phoneNumber: String?,
    val activated: Boolean,
    val langKey: String?,
    val imageUrl: String?,
    val resetDate: String?,
    val subscriptionDate: String?
)
//
//
//
//
//
//
