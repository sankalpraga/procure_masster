package com.techcognics.procuremasster.presentation.supplier

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun SupplierHome(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F4F7)) // light grey
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween // 👈 keeps footer fixed at bottom
    ) {

        // ---- MAIN CENTER CONTENT ----
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(80.dp))

            Text(
                text = "Welcome to Procure masster",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .background(Color(0xFFA6D6A4), shape = RoundedCornerShape(6.dp))
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            ) {
                Text(
                    text = "You are Logged in as Supplier",
                    fontSize = 18.sp,
                    color = Color.Black
                )
            }
        }

        // ---- FIXED FOOTER ----
        Text(
            text = "© Copyright procuremasster.co.in. All rights Reserved",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 10.dp)
        )
    }
}

//@Composable
//fun SupplierProfileScreen(navController: NavHostController) {
//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        Text("👤 Supplier Profile", style = MaterialTheme.typography.headlineSmall)
//    }
//}



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













































































