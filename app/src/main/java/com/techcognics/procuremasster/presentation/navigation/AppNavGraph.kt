package com.techcognics.procuremasster.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.techcognics.procuremasster.presentation.login.LoginScreen
import com.techcognics.procuremasster.presentation.rfqdetails.RFQScreen
import com.techcognics.procuremasster.presentation.splash.SplashScreen
import com.techcognics.procuremasster.presentation.splash.SplashViewModel
import com.techcognics.procuremasster.presentation.supplier.SupplierAuctionScreen
import com.techcognics.procuremasster.presentation.supplier.SupplierHome
import com.techcognics.procuremasster.presentation.supplier.SupplierNegotiableScreen
//import com.techcognics.procuremasster.presentation.supplier.SupplierProfileScreen
import com.techcognics.procuremasster.presentation.supplier.SupplierRootScreen

@Composable
fun AppNavGraph(
    navController: NavHostController,
    splashViewModel: SplashViewModel
) {

    val startDestination = splashViewModel.startDestination.collectAsState().value

    NavHost(
        navController = navController,
        startDestination = "splash" // ✅ splash first
    ) {


        composable("splash") { SplashScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("supplier_root") { SupplierRootScreen(navController) }

//        composable("supplier_home") { SupplierHome(navController) }
//        composable("supplier_profile"){ SupplierProfileScreen(navController) }
//        composable("supplier_rfq"){RFQScreen(navController)}
//        composable("supplier_negotiable"){SupplierNegotiableScreen(navController)}
//        composable("supplier_auction"){SupplierAuctionScreen(navController)}
//

    }



}
