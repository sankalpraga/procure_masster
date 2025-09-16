//// RFQDetailResponse.kt
//package com.techcognics.procuremasster.data.remote
//
//import com.techcognics.procuremasster.data.remote.dto.ItemCategory
//import com.techcognics.procuremasster.data.remote.dto.ItemSubCategory
//import com.techcognics.procuremasster.data.remote.dto.RfqItemResponse
//
//data class RFQDetailResponse(
//    val id: Int,
//    val rfqNumber: String?,
//    val rfqDescription: String?,
//    val nameOfBuyer: String?,
//    val companyName: String?,
//    val evaluationCriteria: String?,
//    val offerSubmissionDate: String?,
//    val deliveryAddress: String?,
//    val status: String?,
//    val currency: String?,
//    val category: String?,
//    val createdDate: String?,
//    val itemCategory: ItemCategory?,
//    val itemSubCategory: ItemSubCategory?,
//    val items: List<RfqItemResponse> = emptyList(),
//    val itemProcess: List<ItemProcess> = emptyList(),
//    val rfqsAttachments: List<RfqAttachment> = emptyList()
//)
//
//data class ItemCategory(
//    val id: Int,
//    val categoryName: String?
//)
//
//data class ItemSubCategory(
//    val id: Int,
//    val subCategoryName: String?,
//    val categoryId: Int?
//)
//
//data class ItemProcess(
//    val id: Int,
//    val itemProcessName: String?,
//    val subCategoryId: Int?
//)
//
//data class RfqItemResponse(
//    val id: Int,
//    val lineNumber: String?,
//    val itemNumber: String?,
//    val description: String?,
//    val quantity: Int,
//    val uom: Uom?
//)
//
//data class Uom(
//    val id: Int,
//    val name: String?
//)
//
//data class RfqAttachment(
//    val id: Int? = null,
//    val fileName: String? = null,
//    val url: String? = null
//)
