package com.techcognics.procuremasster.data.remote

data class fetchSupplierRecordByUserId(
    val buyersId: Int,
    val category: Category,
    val city: City,
    val companyAddress: String,
    val companyName: String,
    val contactPerson: String,
    val country: Country,
    val createdBy: Any,
    val createdDate: Any,
    val currency: String,
    val gstinNumber: String,
    val id: Int,
    val industry: Industry,
    val itemProcess: List<ItemProces>,
    val pinCode: String,
    val state: State,
    val subCategory: SubCategory,
    val subIndustry: SubIndustry,
    val updatedBy: Any,
    val updatedDate: Any,
    val user: User,
    val username: Any,
    val website: String
)