//package com.techcognics.procuremasster.presentation.rfqdetails
//
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.hilt.navigation.compose.hiltViewModel
//import com.techcognics.procuremasster.data.remote.RfqAttachment
//import com.techcognics.procuremasster.presentation.base.UiState
//
//@Composable
//fun RfqDetailsDialogContainer(
//    rfqId: Int,
//    onDismiss: () -> Unit,
//    viewModel: RfqDetailsViewModel = hiltViewModel()
//) {
//    val state by viewModel.uiState.collectAsState()
//
//    LaunchedEffect(rfqId) {
//        viewModel.loadRfqById(rfqId)
//    }
//
//    when (val s = state) {
//        is UiState.Loading -> {
//            AlertDialog(
//                onDismissRequest = onDismiss,
//                confirmButton = {},
//                title = { Text("Loading") },
//                text = { CircularProgressIndicator() }
//            )
//        }
//        is UiState.Success -> {
//            RfqDetailsDialog(
//                rfq = s.data,
//                onDismiss = onDismiss,
//                onDownloadAttachments = { attachments: List<RfqAttachment> ->
//                    // TODO: implement your file downloads (DownloadManager / custom)
//                }
//            )
//        }
//        is UiState.Error -> {
//            AlertDialog(
//                onDismissRequest = onDismiss,
//                confirmButton = {
//                    TextButton(onClick = onDismiss) { Text("Close") }
//                },
//                title = { Text("Error") },
//                text = { Text(s.message) }
//            )
//        }
//        else -> Unit
//    }
//}
