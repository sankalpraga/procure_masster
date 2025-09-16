//package com.techcognics.procuremasster.presentation.rfqdetails
//
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.automirrored.filled.ArrowBack
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.material3.TopAppBar
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.navigation.NavHostController
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun RfqBidScreen(
//    navController: NavHostController,
//    rfqNumber: Any,
//    viewModel: RFQViewModel = hiltViewModel()
//){
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Active RFQ | ${rfqNumber}")},
//                navigationIcon = {
//                    IconButton(onClick = { navController.popBackStack()}) {
//                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
//                    }
//                }
//            )
//        }
//    ) { padding ->
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(padding),
//            contentAlignment = Alignment.Center
//
//        ) {
//            Column(
//                modifier = Modifier.padding(16.dp),
//                horizontalAlignment = Alignment.CenterHorizontally,
//            ) { Text(
//                text = "Bid Screen for RFQ Id : $rfqNumber",
//                style = MaterialTheme.typography.titleMedium
//            )
//                Spacer(Modifier.height(12.dp))
//                Text("Here you will add fields like bid amount, delivery, notes, etc")
//            }
//        }
//    }
//}
//
