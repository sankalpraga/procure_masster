package com.techcognics.procuremasster.presentation.supplier

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.techcognics.procuremasster.presentation.auction.auctionscreen
import com.techcognics.procuremasster.presentation.designsystem.SupplierDrawer
import com.techcognics.procuremasster.presentation.rfqdetails.RFQScreen
import com.techcognics.procuremasster.presentation.rfqdetails.view.RfqViewScreenTabbed
import com.techcognics.procuremasster.presentation.rfqdetails.bid.screens.BidScreen
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupplierRootScreen(parentNavController: NavHostController) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val supplierNavController = rememberNavController() // ✅ new inner controller

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            SupplierDrawer(
                navController = supplierNavController,
                closeDrawer = { scope.launch { drawerState.close() } }
            )
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Procure Masster") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { padding ->
            NavHost(
                navController = supplierNavController, // ✅ use inner one
                startDestination = "supplier_home",
                modifier = Modifier.padding(padding)
            ) {
                composable("supplier_home") { SupplierHome(parentNavController) }
                composable("supplier_profile") { SupplierProfileScreen(parentNavController) }
                composable("supplier_rfq") { RFQScreen(supplierNavController) }
                composable("supplier_negotiable") { SupplierNegotiableScreen(parentNavController) }
                composable("supplier_auction") { auctionscreen(parentNavController) }




                composable(
                    route = "rfq_view/{id}",
                    arguments = listOf(navArgument("id") { type = NavType.IntType })
                ) { backStackEntry ->
                    val id = backStackEntry.arguments?.getInt("id") ?: 0
                    RfqViewScreenTabbed(navController = supplierNavController, rfqId = id)
                }

                composable(
                    route = "rfqStepper/{rfqNumber}",
                    arguments = listOf(navArgument("rfqNumber") { type = NavType.StringType })
                ) { backStackEntry ->
                    val rfqNumber = backStackEntry.arguments?.getString("rfqNumber") ?: ""
                    BidScreen(rfqNumber = rfqNumber, navController = supplierNavController)
                }



            }

        }
    }
}
