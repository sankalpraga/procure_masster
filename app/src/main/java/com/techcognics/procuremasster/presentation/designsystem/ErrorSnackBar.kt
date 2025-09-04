//package com.techcognics.procuremasster.presentation.designsystem
//
//import androidx.compose.material3.Snackbar
//import androidx.compose.material3.SnackbarData
//import androidx.compose.material3.SnackbarHost
//import androidx.compose.material3.SnackbarHostState
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Modifier
//
//@Composable
//fun ErrorSnackbarHost(
//    snackbarHostState: SnackbarHostState,
//    modifier: Modifier = Modifier
//) {
//    SnackbarHost(
//        hostState = snackbarHostState,
//        modifier = modifier
//    ) { data: SnackbarData ->
//        Snackbar(
//            snackbarData = data
//        ) {
//            Text(text = data.visuals.message)
//        }
//    }
//}
