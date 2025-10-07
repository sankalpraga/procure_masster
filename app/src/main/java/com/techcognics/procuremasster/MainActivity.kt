package com.techcognics.procuremasster

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.techcognics.procuremasster.presentation.designsystem.ProcureMassterTheme
import com.techcognics.procuremasster.presentation.navigation.AppNavGraph
import com.techcognics.procuremasster.presentation.splash.SplashViewModel
//import com.techcognics.procuremasster.presentation.theme.ProcureMassterTheme
import dagger.hilt.android.AndroidEntryPoint
import android.Manifest
import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val splashViewModel: SplashViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestNotificationPermission(this)
        setContent {
            ProcureMassterTheme {
                val navController = rememberNavController()
                AppNavGraph(navController, splashViewModel)
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
}
