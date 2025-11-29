package com.techcognics.procuremasster

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.rememberNavController
import com.techcognics.procuremasster.presentation.designsystem.ProcureMassterTheme
import com.techcognics.procuremasster.presentation.navigation.AppNavGraph
import com.techcognics.procuremasster.presentation.splash.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

//private val MainActivity.navController: Any

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val splashViewModel: SplashViewModel by viewModels()

    private var shouldForceLogin = mutableStateOf(false)


    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestNotificationPermission(this)

        setContent {
            ProcureMassterTheme {
                val navController = rememberNavController()
                val snackbarHostState = remember { SnackbarHostState() }
                val context = androidx.compose.ui.platform.LocalContext.current

                // Read intent extras
                val rfqId = intent.getIntExtra("rfqId", -1)
                val timerAlert = intent.getBooleanExtra("timer_alert", false)

                // Scaffold for global snackbar
                Scaffold(
                    snackbarHost = {
                        SnackbarHost(snackbarHostState) { data ->
                            Snackbar(
                                containerColor = Color.Red,
                                contentColor = Color.White,
                                snackbarData = data
                            )
                        }
                    }
                ) {
                    // Notification click/deep-link navigation logic
                    LaunchedEffect(timerAlert, rfqId) {
                        if (timerAlert) {
                            if (rfqId != -1) {
                                navController.navigate("auction_submit/$rfqId")
                            } else {
                                navController.navigate("supplier_auction") // fallback route
                            }
                        }
                    }

                    // Login/session check
                    LaunchedEffect(Unit) {
                        val prefs = context.getSharedPreferences("myapp", Context.MODE_PRIVATE)
                        val isLoggedIn = prefs.getBoolean("isLoggedIn", false)
                        if (!isLoggedIn) {
                            navController.navigate("login") {
                                popUpTo(0)
                                launchSingleTop = true
                            }
                        }
                    }

                    LaunchedEffect(shouldForceLogin.value) {
                        if (shouldForceLogin.value) {
                            navController.navigate("login") {
                                popUpTo(0)
                                launchSingleTop = true
                            }
                            shouldForceLogin.value = false // reset after nav
                        }
                    }

                    // NavGraph: Always pass host state down!
                    AppNavGraph(
                        navController = navController,
                        splashViewModel = splashViewModel,
                        snackbarHostState = snackbarHostState
                    )
                }
            }
        }



    }



    fun requestNotificationPermission(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                110
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.GINGERBREAD)
    override fun onStop() {
        super.onStop()
        // Force logout on app background
        val prefs = getSharedPreferences("myapp", Context.MODE_PRIVATE)
        prefs.edit().putBoolean("isLoggedIn", false).apply()
        prefs.edit().remove("token").apply()
    }



}









