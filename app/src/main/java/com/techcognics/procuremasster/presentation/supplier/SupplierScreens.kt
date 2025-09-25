package com.techcognics.procuremasster.presentation.supplier

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun SupplierHome(navController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("🏠 Supplier Home Screen", style = MaterialTheme.typography.headlineSmall)
    }
}

@Composable
fun SupplierProfileScreen(navController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("👤 Supplier Profile", style = MaterialTheme.typography.headlineSmall)
    }
}



@Composable
fun SupplierNegotiableScreen(navController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("🤝 Supplier Negotiable", style = MaterialTheme.typography.headlineSmall)
    }
}

//@Composable
//fun SupplierAuctionScreen(navController: NavHostController) {
//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        Text("🎯 Supplier Auction", style = MaterialTheme.typography.headlineSmall)
//    }
//}
