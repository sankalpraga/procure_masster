package com.techcognics.procuremasster.presentation.rfqdetails.bid.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.techcognics.procuremasster.presentation.common.StepperHeader
import com.techcognics.procuremasster.presentation.rfqdetails.bid.components.AttachmentsCard
import com.techcognics.procuremasster.presentation.rfqdetails.bid.components.PricingScreen
import com.techcognics.procuremasster.presentation.rfqdetails.bid.viewModel.BidViewModel
import com.techcognics.procuremasster.ui.theme.BrandBlue
import com.techcognics.procuremasster.ui.theme.StepInactive
import androidx.activity.compose.BackHandler

@Composable
fun BidScreen(
    rfqNumber: String,
    navController: NavHostController,
    viewModel: BidViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        viewModel.loadBidDetails(rfqNumber)
        viewModel.fetchFreightTerms()
    }


    val totalSteps = 3
    var currentStep by remember { mutableStateOf(0) }


    val canProceed = when (currentStep) {
        1 -> !viewModel.paymentSchedule.isNullOrBlank() && viewModel.selectedFreightTerm != null
        else -> true
    }


    Box(Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 24.dp)
        ) {
            StepperHeader(currentStep)
            Spacer(Modifier.height(8.dp))
            when (currentStep) {
                0 -> PricingScreen(
                    items = viewModel.pricingItems,     onItemUpdated = { viewModel.updatePricingItem(it) }
                )
                1 -> BidTermsAndConditionsScreen(viewModel = viewModel)
                2 -> AttachmentsCard(viewModel = viewModel)
            }
            Spacer(Modifier.weight(1f))
        }
        Row(
            Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp),
            horizontalArrangement = Arrangement.End
        ) {
            if (currentStep > 0) {
                Button(
                    onClick = { currentStep-- },
                    colors = ButtonDefaults.buttonColors(containerColor = StepInactive)
                ) {
                    Text("Previous", color = BrandBlue)
                }
                Spacer(Modifier.width(12.dp))
            }
            if (currentStep < totalSteps - 1) {
                Button(
                    onClick = { currentStep++ },
                    colors = ButtonDefaults.buttonColors(containerColor = BrandBlue),
                    enabled = canProceed

                ) {
                    Text("Next", color = Color.White)
                }
            } else {
                Button(
                    onClick = {
                        isLoading = true
                        viewModel.saveBid { success ->
                            isLoading = false
                            if (success) {
                                showSuccessDialog = true
                            } else {
                                Toast.makeText(context, "Save failed", Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = BrandBlue),
                    enabled = !isLoading // Disable while loading
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text("Saving...", color = Color.White)
                    } else {
                        Text("Submit", color = Color.White)
                    }
                }
                }
            }
    }
    if (showSuccessDialog) {
        BackHandler(enabled = true) {
            showSuccessDialog = false
        }
        AlertDialog(
            onDismissRequest = { showSuccessDialog = false }, // user can dismiss by tapping outside or pressing back
            confirmButton = {
                Button(
                    onClick = {
                        showSuccessDialog = false
                        navController.navigate("supplier_rfq") {
                            popUpTo("supplier_home") // Or as fits your flow
                            launchSingleTop = true
                        }
                    }
                ) { Text("Go to RFQ page") }
            },
            title = { Text("Bid Saved!", fontWeight = FontWeight.Bold) },
            text = { Text("Your bid was saved successfully.") }
        )
    }

}


