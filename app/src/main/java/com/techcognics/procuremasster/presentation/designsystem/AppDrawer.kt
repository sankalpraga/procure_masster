//package com.techcognics.procuremasster.presentation.designsystem//package com.techcognics.erpapp.presentation.component
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxHeight
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.outlined.Close
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.currentCompositionLocalContext
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalConfiguration
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavHostController
//import com.techcognics.procuremasster.R
////import com.techcognics.procuremasster.data.user_roles.MenuResponseItem
////import com.techcognics.procuremasster.presentation.component.dropdownmenu.ExpandableDrawerMenuItem
////import com.techcognics.procuremasster.util.Constant
//
//@Composable
//fun AppDrawer(
//    modifier: Modifier = Modifier,
//    onClickMenu: () -> Unit,
//    onClickClose: () -> Unit,
//    onClickSignOut: () -> Unit,
//    homeNavController: NavHostController,
//    mainNavController: NavHostController,
////    menuList: List<MenuResponseItem>?
//) {
//    val context = LocalContext.current
//
//    Box(
//        modifier = Modifier
//            .width(LocalConfiguration.current.screenWidthDp.dp * 0.75f)
//            .fillMaxHeight()
//            .background(MaterialTheme.colorScheme.background) // optional background
//    ) {
//        Column {
//            Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
//                IconButton(onClick = onClickClose) {
//                    Icon(
//                        modifier = modifier
//                            .height(27.dp)
//                            .width(27.dp),
//                        imageVector = Icons.Outlined.Close,
//                        contentDescription = "Close"
//                    )
//                }
//            }
//
//            LazyColumn(
//                modifier = Modifier.fillMaxSize()
//            ) {
//                menuList?.size?.let {
//                    items(it) { item ->
//
////                        val iconId = context.resources.getIdentifier((menuList[item].icon ?: "dashboard") as String?, "drawable", context.packageName)
//                        ExpandableDrawerMenuItem(
//                            title = menuList[item].name,
//                            subTitle = menuList[item].description,
//                            icon = (if(iconId != 0){
//                                iconId
//                            }else{
//                                context.resources.getIdentifier(("dashboard") as String?, "drawable", context.packageName)
//                            }) as Int,
//                            children = menuList[item].children
////                        ) { childClicked ->
////                            // Handle click on child item
////                            when (childClicked) {
////                                Constant. dashboardMenu[0].title -> homeNavController.navigate(
////                                    Constant.COMPANY_DASHBOARD_SCREEN
////                                )
////
////                                Constant.dashboardMenu[1].title -> homeNavController.navigate(
////                                    Constant.SALES_DASHBOARD_SCREEN
////                                )
////                                Constant.dashboardMenu[2].title -> homeNavController.navigate(
////                                    Constant.CUSTOMER_SCREEN
////                                )
////                                Constant.dashboardMenu[3].title -> homeNavController.navigate(
////                                    Constant.SALES_FUNNEL_SCREEN
////                                )
////
////                                else -> {}
////                            }
//
//                        }
//                    }
//                }
//
//                /*item {
//                    DrawerTitle(
//                        text = "DASHBOARD",
//                        icon = R.drawable.dashboard_icon,  // replace with your icon
//                        isSelected = true
//                    )*//*
//
//                    ExpandableDrawerMenuItem(
//                        title = "Dashboard",
//                        icon = R.drawable.dashboard_icon,
//                        children = Constant.dashboardMenu
//                    ) { childClicked ->
//                        // Handle click on child item
//                        when (childClicked) {
//                            Constant.dashboardMenu[0].title -> homeNavController.navigate(Constant.COMPANY_DASHBOARD_SCREEN)
//                            Constant.dashboardMenu[1].title -> homeNavController.navigate(Constant.SALES_DASHBOARD_SCREEN)
//                            else -> {}
//                        }
//
//                    }
//                    MenuItem(
//                        modifier = modifier,
//                        icon = R.drawable.admin_icon,
//                        menuTitle = "ADMIN UTILITY"
//                    ) {}
//                    VerticalStageStepper(stages = stagesOne)
//                    Spacer(modifier = modifier.height(10.dp))
//                    MenuItem(
//                        modifier = modifier,
//                        icon = R.drawable.configration_icon,
//                        menuTitle = "CONFIGURATION"
//                    ) {}
//                    VerticalStageStepper(stages = stagesTwo)
//                    Row(
//                        modifier = modifier
//                            .fillMaxWidth()
//                            .padding(start = 20.dp)
//                    ) {
//                        Spacer(
//                            modifier = modifier
//                                .height(0.5.dp)
//                                .width(LocalConfiguration.current.screenWidthDp.dp * 0.35f)
//                                .background(Color.Gray)
//                                .padding(start = 30.dp)
//                        )
//                    }
//                }
//                items(Constant.menuItems.size) { item ->
//                    MenuItem(
//                        modifier = modifier,
//                        icon = Constant.menuItems[item].icon,
//                        menuTitle = Constant.menuItems[item].title
//                    ) {}
//                }
//                item {
//                    Row(
//                        modifier = modifier
//                            .height(30.dp)
//                            .fillMaxWidth()
//                    ) {
//                        Button(
//                            onClick = onClickSignOut,
//                            modifier = Modifier.height(26.dp),
//                            shape = RoundedCornerShape(3.dp),
//                            colors = ButtonDefaults.buttonColors(
//                                containerColor = MaterialTheme.colorScheme.secondary,
//                                contentColor = Color.White
//                            ),
//                            contentPadding = PaddingValues(0.dp)
//                        ) {
//                            Text(
//                                text = "Sign Out", style = TextStyle(
//                                    fontSize = 11.sp, fontWeight = FontWeight.Medium
//                                )
//                            )
//                        }
//                    }
//                }*/
//            }
//
//        }
//    }
//}