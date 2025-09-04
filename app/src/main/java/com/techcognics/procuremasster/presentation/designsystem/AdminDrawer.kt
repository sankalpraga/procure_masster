//package com.techcognics.procuremasster.presentation.designsystem
//
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.automirrored.filled.ExitToApp
//import androidx.compose.material.icons.automirrored.filled.List
//import androidx.compose.material.icons.filled.Home
//import androidx.compose.material.icons.filled.Menu
//import androidx.compose.material.icons.filled.People
//import androidx.compose.material3.DrawerValue
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.ModalDrawerSheet
//import androidx.compose.material3.ModalNavigationDrawer
//import androidx.compose.material3.NavigationDrawerItem
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.SmallTopAppBar
//import androidx.compose.material3.Text
//import androidx.compose.material3.rememberDrawerState
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavHostController
//import kotlinx.coroutines.launch
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun AdminDrawer(
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
//                Text("Admin Menu", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(16.dp))
//
//                NavigationDrawerItem(
//                    label = { Text("Dashboard") },
//                    selected = false,
//                    onClick = { navController.navigate("admin_dashboard") },
//                    icon = { Icon(Icons.Default.Home, contentDescription = null) }
//                )
//                NavigationDrawerItem(
//                    label = { Text("Users") },
//                    selected = false,
//                    onClick = { /* TODO */ },
//                    icon = { Icon(Icons.Default.People, contentDescription = null) }
//                )
//                NavigationDrawerItem(
//                    label = { Text("Feedback") },
//                    selected = false,
//                    onClick = { navController.navigate("feedback") },
//                    icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = null) }
//                )
//                NavigationDrawerItem(
//                    label = { Text("Sign Out") },
//                    selected = false,
//                    onClick = { navController.navigate("login") { popUpTo(0) } },
//                    icon = { Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null) }
//                )
//            }
//        }
//    ) {
//        Scaffold(
//            topBar = {
//                SmallTopAppBar(
//                    title = { Text("Admin ERP") },
//                    navigationIcon = {
//                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
//                            Icon(Icons.Default.Menu, contentDescription = "Menu")
//                        }
//                    }
//                )
//            },
//            content = { innerPadding ->
//                Box(Modifier.padding(innerPadding)) { content() }
//            }
//        )
//    }
//}
