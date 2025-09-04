package com.techcognics.procuremasster.presentation.designsystem

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun SupplierDrawer(
    navController: NavHostController,
    closeDrawer: () -> Unit,
//    function: () -> Scaffold

) {
    ModalDrawerSheet {
        Text(
            text = "Supplier Menu",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )

        NavigationDrawerItem(
            label = { Text("Home") },
            selected = false,
            onClick = {
                navController.navigate("supplier_home")
                closeDrawer()
            },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") }
        )
        NavigationDrawerItem(
            label = { Text("Profile") },
            selected = false,
            onClick = {
                navController.navigate("supplier_profile")
                closeDrawer()
            },
            icon = { Icon(Icons.Default.AccountCircle, contentDescription = "Profile") }
        )
        NavigationDrawerItem(
            label = { Text("RFQ") },
            selected = false,
            onClick = {
                navController.navigate("supplier_rfq")
                closeDrawer()
            },
            icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = "RFQ") }
        )
        NavigationDrawerItem(
            label = { Text("Negotiable") },
            selected = false,
            onClick = {
                navController.navigate("supplier_negotiable")
                closeDrawer()
            },
            icon = { Icon(Icons.Default.SwapHoriz, contentDescription = "Negotiable") }
        )
        NavigationDrawerItem(
            label = { Text("Auction") },
            selected = false,
            onClick = {
                navController.navigate("supplier_auction")
                closeDrawer()
            },
            icon = { Icon(Icons.Default.Gavel, contentDescription = "Auction") }
        )
        NavigationDrawerItem(
            label = { Text("Sign Out") },
            selected = false,
            onClick = {
                navController.navigate("login") {
                    popUpTo(0) // clear backstack
                }
                closeDrawer()
            },
            icon = { Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Sign Out") }
        )
    }
}

//package com.techcognics.procuremasster.presentation.designsystem
//
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.automirrored.filled.ExitToApp
//import androidx.compose.material.icons.automirrored.filled.List
//import androidx.compose.material.icons.filled.AccountCircle
//import androidx.compose.material.icons.filled.Gavel
//import androidx.compose.material.icons.filled.Home
//import androidx.compose.material.icons.filled.List
//import androidx.compose.material.icons.filled.Menu
//import androidx.compose.material.icons.filled.SwapHoriz
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavHostController
//import kotlinx.coroutines.launch
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun SupplierDrawer(
//    navController: NavHostController,
//    content: @Composable () -> Unit
//) {
//    val drawerState = rememberDrawerState(DrawerValue.Closed)
//    val scope = rememberCoroutineScope()
//
//    ModalNavigationDrawer(
//        drawerState = drawerState,
//        drawerContent = {
//            ModalDrawerSheet {
//                Text(
//                    text = "Supplier Menu",
//                    style = MaterialTheme.typography.titleLarge,
//                    modifier = Modifier.padding(16.dp)
//                )
//
//                // ✅ Home (Dashboard)
//                NavigationDrawerItem(
//                    label = { Text("Home") },
//                    selected = false,
//                    onClick = { navController.navigate("supplier_dashboard") },
//                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") }
//                )
//
//                // ✅ Profile
//                NavigationDrawerItem(
//                    label = { Text("Profile") },
//                    selected = false,
//                    onClick = { navController.navigate("supplier_profile") },
//                    icon = { Icon(Icons.Default.AccountCircle, contentDescription = "Profile") }
//                )
//
//                // ✅ RFQ
//                NavigationDrawerItem(
//                    label = { Text("RFQ") },
//                    selected = false,
//                    onClick = { navController.navigate("supplier_rfq") },
//                    icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = "RFQ") }
//                )
//
//                // ✅ Negotiable
//                NavigationDrawerItem(
//                    label = { Text("Negotiable") },
//                    selected = false,
//                    onClick = { navController.navigate("supplier_negotiable") },
//                    icon = { Icon(Icons.Default.SwapHoriz, contentDescription = "Negotiable") }
//                )
//
//                // ✅ Auction
//                NavigationDrawerItem(
//                    label = { Text("Auction") },
//                    selected = false,
//                    onClick = { navController.navigate("supplier_auction") },
//                    icon = { Icon(Icons.Default.Gavel, contentDescription = "Auction") }
//                )
//
//                // ✅ Sign Out
//                NavigationDrawerItem(
//                    label = { Text("Sign Out") },
//                    selected = false,
//                    onClick = {
//                        navController.navigate("login") {
//                            popUpTo(0) // clear backstack
//                        }
//                    },
//                    icon = { Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Sign Out") }
//                )
//            }
//        }
//    ) {
//        Scaffold(
//            topBar = {
//                SmallTopAppBar(
//                    title = { Text("Supplier ERP") },
//                    navigationIcon = {
//                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
//                            Icon(Icons.Default.Menu, contentDescription = "Menu")
//                        }
//                    }
//                )
//            },
//            content = { innerPadding ->
//                Box(Modifier.padding(innerPadding)) {
//                    content()
//                }
//            }
//        )
//    }
//}
//
//@Composable
//fun SmallTopAppBar(title: @Composable () -> Unit, navigationIcon: @Composable () -> Unit) {
//    TODO("Not yet implemented")
//}
