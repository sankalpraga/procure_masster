package com.techcognics.procuremasster.presentation.rfqdetails.bid.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.techcognics.procuremasster.presentation.rfqdetails.bid.viewModel.BidViewModel
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun BidTermsAndConditionsScreen(viewModel: BidViewModel) {
    val scrollState = rememberScrollState()
    var expanded by remember { mutableStateOf(false) }

    var packingForwardingText by remember { mutableStateOf("") }
    var freightChargesText by remember { mutableStateOf("") }


    var paymentScheduleTouched by remember { mutableStateOf(false) }
    var freightTermsTouched by remember { mutableStateOf(false) }

    Column(
        Modifier
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {

        OutlinedTextField(
            value = packingForwardingText,
            onValueChange = {
                if (it.isEmpty() || it.matches(Regex("""\d*\.?\d*"""))) {
                    packingForwardingText = it
                    viewModel.packingForwarding = it.toDoubleOrNull()
                }
            },
            label = { Text("Packing & Forwarding (%)") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))
        Box(Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = viewModel.selectedFreightTerm?.name.orEmpty(),
                onValueChange = {},
                readOnly = true,
                label = { Text("Freight Terms*") },
                isError = freightTermsTouched && viewModel.selectedFreightTerm == null,
                supportingText = {
                    if (freightTermsTouched && viewModel.selectedFreightTerm == null)
                        Text("Freight Terms is required.", color = androidx.compose.material3.MaterialTheme.colorScheme.error)
                },
                trailingIcon = {
                    IconButton(onClick = {
                        expanded = !expanded
                        if (!freightTermsTouched) freightTermsTouched = true
                    }) {
                        Icon(Icons.Filled.ArrowDropDown, contentDescription = "Dropdown")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        expanded = true
                        if (!freightTermsTouched) freightTermsTouched = true
                    }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                viewModel.freightTermsOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option.name.toString()) },
                        onClick = {
                            viewModel.selectedFreightTerm = option
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(12.dp))
        // Freight Charges field
        OutlinedTextField(
            value = freightChargesText,
            onValueChange = {
                if (it.isEmpty() || it.matches(Regex("""\d*\.?\d*"""))) {
                    freightChargesText = it
                    viewModel.freightCharges = it.toDoubleOrNull()
                }
            },
            label = { Text("Freight Charges") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))
        // Payment Schedule with error only after touched
        OutlinedTextField(
            value = viewModel.paymentSchedule,
            onValueChange = {
                viewModel.paymentSchedule = it
                if (!paymentScheduleTouched) paymentScheduleTouched = true
            },
            label = { Text("Payment Schedule*") },
            isError = paymentScheduleTouched && viewModel.paymentSchedule.isBlank(),
            supportingText = {
                if (paymentScheduleTouched && viewModel.paymentSchedule.isBlank())
                    Text("Payment Schedule is required.", color = androidx.compose.material3.MaterialTheme.colorScheme.error)
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = viewModel.specialInstructions,
            onValueChange = { viewModel.specialInstructions = it },
            label = { Text("Special Instruction Specific to bid") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = viewModel.comments,
            onValueChange = { viewModel.comments = it },
            label = { Text("Comments If any") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
