package com.techcognics.procuremasster.presentation.rfqdetails.bid.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.techcognics.procuremasster.data.remote.dto.ItemBidingPricing

@Composable
fun PricingScreen(
    items: List<ItemBidingPricing>,
    onItemUpdated: (ItemBidingPricing) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var currentItem by remember { mutableStateOf<ItemBidingPricing?>(null) }

    Column(
        Modifier.fillMaxWidth().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items.forEach { item ->
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFDDEBFF)),
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Box(Modifier.fillMaxWidth().padding(20.dp)) {
                    Column(Modifier.align(Alignment.CenterStart)) {
                        Text(
                            "Item No: ${item.itemNumber}",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 18.sp,
                            color = Color(0xFF0D47A1)
                        )
                        Spacer(Modifier.height(6.dp))
                        Text(
                            "Description: ${item.description}",
                            fontSize = 15.sp,
                            color = Color(0xFF1A237E)
                        )
                        Spacer(Modifier.height(6.dp))
                        Text(
                            "Quantity: ${item.quantity}",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            color = Color(0xFF1976D2)
                        )
                    }
                    IconButton(
                        onClick = {
                            currentItem = item
                            showDialog = true
                        },
                        modifier = Modifier.align(Alignment.TopEnd)
                    ) {
                        Icon(
                            Icons.Filled.Edit,
                            contentDescription = "Edit item",
                            tint = Color(0xFF0D47A1)
                        )
                    }
                }
            }
        }
    }
    if (showDialog && currentItem != null) {
        PricingDetailsEditDialog(
            item = currentItem!!,
            onDismiss = { showDialog = false },
            onSave = { updatedItem ->
                showDialog = false
                onItemUpdated(updatedItem)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PricingDetailsEditDialog(
    item: ItemBidingPricing,
    onDismiss: () -> Unit,
    onSave: (ItemBidingPricing) -> Unit
) {
    var price by remember { mutableStateOf(item.price?.toString() ?: "") }
    var discount by remember { mutableStateOf(item.discount?.toString() ?: "") }
    var tax by remember { mutableStateOf(item.tax?.toString() ?: "") }
    var moq by remember { mutableStateOf(item.moq?.toString() ?: "") }
    var deliveryTime by remember { mutableStateOf(item.deliveryTime?.toString() ?: "") }
    var warranty by remember { mutableStateOf(item.warranty ?: "NIL") }
    val warrantyOptions = listOf(
        "NIL", "3 months", "6 months", "9 months", "12 months",
        "15 months", "18 months", "21 months", "24 months", "27 months", "30 months"
    )
    val priceValid = price.isNotEmpty() && price.toDoubleOrNull() != null
    val taxValid = tax.isNotEmpty() && tax.toDoubleOrNull() != null
    val canSave = priceValid && taxValid

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Item Details", fontWeight = FontWeight.Bold, fontSize = 18.sp) },
        text = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .heightIn(max = 300.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Column(Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = price,
                        onValueChange = {
                            if (it.isEmpty() || it.matches(Regex("""\d*\.?\d*""")))
                                price = it },
                        label = { Text("Price*") },

                        isError = !priceValid,
                        supportingText = {
                            if (!priceValid) Text("Required...", color = MaterialTheme.colorScheme.error)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

                        )
                    Spacer(Modifier.height(12.dp))
                    OutlinedTextField(
                        value = discount,
                        onValueChange = {
                            if (it.isEmpty() || it.matches(Regex("""\d*\.?\d*""")))
                                discount = it },
                        label = { Text("Discount (%)") },

                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

                    )
                    Spacer(Modifier.height(12.dp))
                    OutlinedTextField(
                        value = tax,
                        onValueChange = {
                            if (it.isEmpty() || it.matches(Regex("""\d*\.?\d*""")))
                                tax = it },
                        label = { Text("Tax (%)*") },
                        isError = !taxValid,
                        supportingText = {
                            if (!taxValid) Text("Required..", color = MaterialTheme.colorScheme.error)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

                    )
                    Spacer(Modifier.height(12.dp))
                    OutlinedTextField(
                        value = moq,
                        onValueChange = { moq = it },
                        label = { Text("MOQ") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    Spacer(Modifier.height(12.dp))
                    OutlinedTextField(
                        value = deliveryTime,
                        onValueChange = { deliveryTime = it },
                        label = { Text("Delivery Time") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    Spacer(Modifier.height(18.dp))
                    WarrantyDropdown(
                        value = warranty.toString(),
                        options = warrantyOptions,
                        onSelect = { warranty = it }
                    )
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                onSave(
                    item.copy(
                        price = price.toDoubleOrNull(),
                        discount = discount.toDoubleOrNull(),
                        tax = tax.toDoubleOrNull(),
                        moq = moq.toInt(),
                        warranty = warranty,
                        deliveryTime = deliveryTime
                    )
                )
            },
                enabled = canSave
            ) { Text("Save", fontWeight = FontWeight.Bold, color = Color.White) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@Composable
fun WarrantyDropdown(
    value: String,
    options: List<String>,
    onSelect: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        TextField(
            value = value,
            onValueChange = {}, // Prevent manual editing
            readOnly = true,
            label = { Text("Warranty") },
            trailingIcon = {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Filled.ArrowDropDown, contentDescription = "Dropdown")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        onSelect(option)
                        expanded = false
                    },
                    text = { Text(option) }
                )
            }
        }
    }
}
