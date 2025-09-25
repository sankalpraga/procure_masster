package com.techcognics.procuremasster.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.techcognics.procuremasster.presentation.login.LoginScreen
import com.techcognics.procuremasster.presentation.splash.SplashScreen
import com.techcognics.procuremasster.presentation.splash.SplashViewModel
import com.techcognics.procuremasster.presentation.supplier.SupplierRootScreen

@RequiresApi(Build.VERSION_CODES.Q)
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

//        // ✅ NEW: RFQ Details Screen
//        composable(
//            route = "rfq_view/{rfqId}",
//            arguments = listOf(navArgument("rfqId") { type = NavType.IntType })
//        ) { backStackEntry ->
//            val rfqId = backStackEntry.arguments?.getInt("rfqId") ?: 0
//            val parentNavController = null
//            RfqViewScreen(navController = parentNavController, rfqId = rfqId)
//        }
//        composable("supplier_home") { SupplierHome(navController) }
//        composable("supplier_profile"){ SupplierProfileScreen(navController) }
//        composable("supplier_rfq"){RFQScreen(navController)}
//        composable("supplier_negotiable"){SupplierNegotiableScreen(navController)}
//        composable("supplier_auction"){SupplierAuctionScreen(navController)}
//

    }



}
