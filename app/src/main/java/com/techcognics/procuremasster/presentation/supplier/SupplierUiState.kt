package com.techcognics.procuremasster.presentation.supplier

data class SupplierUiState(
    val isLoading: Boolean = false,
    val error: String? = null,

    val companyName: String = "",
    val userName: String = "",
    val contactPerson: String = "",
    val email: String = "",
    val mobileNumber: String = "",
    val phoneNumber: String = "",

    val industry: String = "",
    val subIndustry: String = "",
    val currency: String = "",

    val website: String = "",
    val gstNumber: String = "",

    val country: String = "",
    val state: String = "",
    val city: String = "",
    val pinCode: String = "",
    val companyAddress: String = "",

    val category: String = "",
    val subCategory: String = "",
    val itemProcess: String = "",   // for now as comma separated text

    val companyAddressError: Boolean = false
)