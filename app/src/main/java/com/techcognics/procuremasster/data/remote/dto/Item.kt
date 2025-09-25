package com.techcognics.procuremasster.data.remote.dto

data class Item(
    val id: Int,
    val number: String,
    val description: String,
    val uom: Uom,              // Full Uom object
    var qty: Int,
    var price: Double,
    var discount: Double?,
    var tax: Double?,
    var moq: Int?,
    var warranty: String?,
    var deliveryTime: String?
)




data class PricingItemRequest(
    val itemId: Int,
    val itemNumber: String,
    val description: String,
    val quantity: Int,
    val price: Double? = 0.0,
    val discount: Double? = 0.0,
    val tax: Double? = 0.0,
    val dueDate: String? = "",
    val moq: Int? = 0,
    val warranty: String? = "",
    val deliveryTime: String? = "",
    val currency: String,
    val rfqId: Int,
    val uom: Uom
)

fun Item.toPricingRequest(rfqId: Int): PricingItemRequest = PricingItemRequest(
    itemId = id,
    itemNumber = number,
    description = description,
    quantity = qty,
    price = price,
    discount = discount,
    tax = tax,
    moq = moq,
    warranty = warranty,
    deliveryTime = deliveryTime,
    currency = "INR",
    rfqId = rfqId,
    uom = uom
)
