package com.techcognics.procuremasster.presentation.supplierprofile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.techcognics.procuremasster.presentation.common.LabeledTextField
import com.techcognics.procuremasster.presentation.supplier.SupplierUiState

@Composable
fun SupplierProfileScreen(
    userId: Long,
    viewModel: SupplierProfileViewModel = hiltViewModel(),
    onCancelClick: () -> Unit = {},
    onSaveClick: () -> Unit = {} // later: { viewModel.saveProfile() }
) {
    LaunchedEffect(userId) {
        viewModel.loadAll(userId)
    }

    val state by viewModel.uiState.collectAsState()

    Scaffold(
        bottomBar = {
            // Fixed bottom bar similar to web Cancel / Save
            Surface(
                tonalElevation = 3.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        onClick = onCancelClick
                    ) {
                        Text("Cancel")
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Button(
                        onClick = onSaveClick
                    ) {
                        Text("Save")
                    }
                }
            }
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(bottom = innerPadding.calculateBottomPadding()) // so form not hidden
        ) {
            when {
                state.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                state.error != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = state.error ?: "Something went wrong",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }

                else -> {
                    SupplierProfileContent(
                        state = state,
                        onCompanyAddressChange = viewModel::onCompanyAddressChange
                    )
                }
            }
        }
    }
}

@Composable
private fun SupplierProfileContent(
    state: SupplierUiState,
    onCompanyAddressChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(Modifier.height(8.dp))

        // Title just below "Procure Masster"
        Text(
            text = "Supplier Profile",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(Modifier.height(16.dp))

        // ---------- Personal Information ----------
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(Modifier.padding(16.dp)) {
                SectionTitle("Personal Information")

                LabeledTextField(
                    label = "Company Name",
                    value = state.companyName,
                    onValueChange = {},
                    isRequired = true,
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(10.dp))

                LabeledTextField(
                    label = "User Name",
                    value = state.userName,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(10.dp))

                LabeledTextField(
                    label = "Contact Person",
                    value = state.contactPerson,
                    onValueChange = {},
                    isRequired = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(10.dp))

                LabeledTextField(
                    label = "Email",
                    value = state.email,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(10.dp))

                LabeledTextField(
                    label = "Mobile Number",
                    value = state.mobileNumber,
                    onValueChange = {},
                    isRequired = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(10.dp))

                LabeledTextField(
                    label = "Phone Number",
                    value = state.phoneNumber,
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(10.dp))

                LabeledTextField(
                    label = "Industry",
                    value = state.industry,
                    onValueChange = {},
                    isRequired = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(10.dp))

                LabeledTextField(
                    label = "Sub Industry",
                    value = state.subIndustry,
                    onValueChange = {},
                    isRequired = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(10.dp))

                LabeledTextField(
                    label = "Currency",
                    value = state.currency,
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(10.dp))

                LabeledTextField(
                    label = "Website",
                    value = state.website,
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(10.dp))

                LabeledTextField(
                    label = "GST Number",
                    value = state.gstNumber,
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        // ---------- Address ----------
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(Modifier.padding(16.dp)) {
                SectionTitle("Address")

                LabeledTextField(
                    label = "Country",
                    value = state.country,
                    onValueChange = {},
                    isRequired = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(10.dp))

                LabeledTextField(
                    label = "State",
                    value = state.state,
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(10.dp))

                LabeledTextField(
                    label = "City",
                    value = state.city,
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(10.dp))

                LabeledTextField(
                    label = "Pincode",
                    value = state.pinCode,
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(10.dp))

                LabeledTextField(
                    label = "Company Address",
                    value = state.companyAddress,
                    onValueChange = onCompanyAddressChange,
                    isRequired = true,
                    singleLine = false,
                    isError = state.companyAddressError,
                    supportingText = if (state.companyAddressError)
                        "Company Address is required."
                    else null,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        // ---------- Category / SubCategory ----------
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(Modifier.padding(16.dp)) {
                SectionTitle("Category / SubCategory")

                LabeledTextField(
                    label = "Category",
                    value = state.category,
                    onValueChange = {},
                    isRequired = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(10.dp))

                LabeledTextField(
                    label = "SubCategory",
                    value = state.subCategory,
                    onValueChange = {},
                    isRequired = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(10.dp))

                LabeledTextField(
                    label = "Item-Process",
                    value = state.itemProcess,
                    onValueChange = {},
                    isRequired = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(Modifier.height(16.dp))
    }
}

@Composable
private fun SectionTitle(text: String) {
    Column {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold
            )
        )
        Spacer(Modifier.height(4.dp))
        Divider()
    }
}
