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

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProcureMassterTheme {
                val navController = rememberNavController()
                AppNavGraph(navController, splashViewModel)
            }
        }
    }
}
