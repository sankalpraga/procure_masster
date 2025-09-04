package com.techcognics.procuremasster.presentation.supplier

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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.techcognics.procuremasster.presentation.supplierProfileScreen.SupplierProfileScreen
import com.techcognics.procuremasster.presentation.designsystem.SupplierDrawer
import com.techcognics.procuremasster.presentation.rfqdetails.RFQScreen
import kotlinx.coroutines.launch

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
                composable("supplier_rfq") { RFQScreen(parentNavController) }
                composable("supplier_negotiable") { SupplierNegotiableScreen(parentNavController) }
                composable("supplier_auction") { SupplierAuctionScreen(parentNavController) }
            }
        }
    }
}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun SupplierRootScreen(navController: NavHostController) {
//    val drawerState = rememberDrawerState(DrawerValue.Closed)
//    val scope = rememberCoroutineScope()
//
//    ModalNavigationDrawer(
//        drawerState = drawerState,
//        drawerContent = {
//            SupplierDrawer(
//                navController = navController,
//                closeDrawer = { scope.launch { drawerState.close() } },
////                function = TODO()
//            )
//        }
//    ) {
//        Scaffold(
//            topBar = {
//                TopAppBar(
//                    title = { Text("Supplier ERP") },
//                    navigationIcon = {
//                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
//                            Icon(Icons.Default.Menu, contentDescription = "Menu")
//                        }
//                    }
//                )
//            }
//        ) { padding ->
//            NavHost(
//                navController = navController,
//                startDestination = "supplier_home",
//                modifier = Modifier.padding(padding)
//            ) {
//                composable("supplier_home") { SupplierHome(navController) }
//                composable("supplier_profile") { SupplierProfileScreen(navController) }
//                composable("supplier_rfq") { /* Your RFQ Screen */ }
//                composable("supplier_negotiable") { SupplierNegotiableScreen(navController) }
//                composable("supplier_auction") { SupplierAuctionScreen(navController) }
//            }
//        }
//    }
//}