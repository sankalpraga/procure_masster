package com.techcognics.procuremasster.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.techcognics.procuremasster.presentation.auction.AuctionScreen
import com.techcognics.procuremasster.presentation.auction.biddetails.BiddingScreen
import com.techcognics.procuremasster.presentation.login.LoginScreen
import com.techcognics.procuremasster.presentation.splash.SplashScreen
import com.techcognics.procuremasster.presentation.splash.SplashViewModel
import com.techcognics.procuremasster.presentation.supplier.SupplierRootScreen

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun AppNavGraph(
    navController: NavHostController,
    splashViewModel: SplashViewModel,
    snackbarHostState: SnackbarHostState
) {

    val startDestination = splashViewModel.startDestination.collectAsState().value

    NavHost(
        navController = navController,
        startDestination = "splash" // ✅ splash first
    ) {


        composable("splash") { SplashScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("supplier_root") { SupplierRootScreen(navController) }


        composable("auction_submit/{rfqId}") { backStackEntry ->
            val rfqId = backStackEntry.arguments?.getString("rfqId")?.toIntOrNull()
            if (rfqId != null) {
                BiddingScreen(
                    rfqId = rfqId,
                    navController = navController,
//                    snackbarHostState = snackbarHostState
                )
            } else {
                // If rfqId is missing, navigate away
                LaunchedEffect(Unit) { navController.navigate("supplier_auction") }
            }
        }
//        composable("supplier_auction") { AuctionScreen(
//            navController,
//            auctionNumber = TODO(),
//            snackbarHostState = TODO()
//        ) }

    }



}
