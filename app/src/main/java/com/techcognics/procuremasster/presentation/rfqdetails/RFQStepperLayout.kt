package com.techcognics.procuremasster.presentation.rfqdetails

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RFQStepperLayout(
    currentStep: MutableIntState,
    steps: List<String> = listOf("Pricing Details", "Bid Terms & Condition", "Attachments"),
    onSubmit: () -> Unit,
    content: @Composable (current: Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        // Step indicators
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            steps.forEachIndexed { index, title ->
                StepBullet(number = index + 1, label = title, active = index == currentStep.intValue)
                if (index != steps.lastIndex) {
                    HorizontalDivider(
                        modifier = Modifier
                            .weight(1f)
                            .height(1.dp)
                            .padding(horizontal = 6.dp),
                        thickness = DividerDefaults.Thickness, color = MaterialTheme.colorScheme.outlineVariant
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // Step content
        Box(modifier = Modifier.weight(1f)) {
            content(currentStep.intValue)
        }

        Spacer(Modifier.height(8.dp))

        // Navigation
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            OutlinedButton(
                enabled = currentStep.intValue > 0,
                onClick = { currentStep.intValue-- }
            ) { Text("Previous") }

            if (currentStep.intValue < steps.lastIndex) {
                Button(onClick = { currentStep.intValue++ }) { Text("Next") }
            } else {
                Button(onClick = onSubmit) { Text("Submit") }
            }
        }
    }
}

@Composable
fun StepBullet(number: Int, label: String, active: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Surface(
            shape = MaterialTheme.shapes.small,
            color = if (active) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.surfaceVariant
        ) {
            Box(Modifier.size(36.dp), contentAlignment = Alignment.Center) {
                Text(
                    text = number.toString(),
                    color = if (active) MaterialTheme.colorScheme.onPrimary
                    else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        Spacer(Modifier.height(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = if (active) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1
        )
    }
}


@Preview(showBackground = true, widthDp = 420, heightDp = 800)
@Composable
fun RFQStepperLayout_Preview() {
    val stepState = remember { mutableIntStateOf(0) }
    MaterialTheme {
        RFQStepperLayout(currentStep = stepState, onSubmit = {}) { step ->
            when (step) {
                0 -> PricingDetailsScreen(
                    rfqNumber = "100920250201",
                    description = "RFQ FOR HARDWARE",
                    currency = "INR",
                    issueDate = "2025-09-10",
                    submitDate = "2025-09-12",
                    items = listOf(
//                        RfqItemUi("I0001", "DISK", "NOS", 200),
//                        RfqItemUi("I0002", "CABLE", "NOS", 20)
                    )
                )
                1 -> BidTermsScreen()
                else -> AttachmentsScreen()
            }
        }
    }
}
