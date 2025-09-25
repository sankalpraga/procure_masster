package com.techcognics.procuremasster.presentation.rfqdetails.bid.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AttachmentsPage(rfqNumber: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Title Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Attachments", style = MaterialTheme.typography.titleMedium)
                IconButton(onClick = { /* TODO: Upload File */ }) {
                    Icon(Icons.Default.Edit, contentDescription = "Upload")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Example Attachment
            Row(modifier = Modifier.fillMaxWidth()) {
                Icon(Icons.Default.AttachFile, contentDescription = "File")
                Text("quotation.pdf", modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}