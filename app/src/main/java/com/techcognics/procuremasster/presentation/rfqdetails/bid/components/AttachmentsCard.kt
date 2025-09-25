package com.techcognics.procuremasster.presentation.rfqdetails.bid.components// AttachmentsCard.kt

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.techcognics.procuremasster.data.remote.dto.Attachment
import com.techcognics.procuremasster.presentation.rfqdetails.bid.viewModel.BidViewModel
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns

@Composable
fun AttachmentsCard(viewModel: BidViewModel) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri: Uri? ->
            uri?.let {
                val meta = getFileMeta(context, uri)
                viewModel.addAttachment(Attachment(uri, meta.first, meta.second))
            }
        }
    )

    Card(
        Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Attachments", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(12.dp))
            Button(
                onClick = { launcher.launch(arrayOf("*/*")) },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Select Files...") }
            Spacer(Modifier.height(8.dp))
            Divider()
            if (viewModel.attachments.isEmpty()) {
                Text("No files selected.", color = Color.Gray)
            } else {
                Column {
                    viewModel.attachments.forEach { att ->
                        Row(
                            Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(att.name, Modifier.weight(1f))
                            Text("${att.size / 1024} KB", Modifier.padding(end = 8.dp))
                            IconButton(onClick = { viewModel.removeAttachment(att) }) {
                                Icon(Icons.Default.Close, contentDescription = "Remove file")
                            }
                        }
                    }
                }
            }
            Spacer(Modifier.height(8.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Text(
                    "Clear All",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable { viewModel.clearAttachments() }.padding(8.dp)
                )
            }
        }
    }
}


fun getFileMeta(context: Context, uri: Uri): Pair<String, Long> {
    var name = "unknown"
    var size = 0L
    val cursor = context.contentResolver.query(uri, null, null, null, null)
    cursor?.use {
        val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        val sizeIndex = it.getColumnIndex(OpenableColumns.SIZE)
        if (cursor.moveToFirst()) {
            if (nameIndex != -1) name = cursor.getString(nameIndex)
            if (sizeIndex != -1) size = cursor.getLong(sizeIndex)
        }
    }
    return Pair(name, size)
}
