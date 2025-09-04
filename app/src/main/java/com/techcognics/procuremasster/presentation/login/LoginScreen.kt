package com.techcognics.procuremasster.presentation.login


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.techcognics.procuremasster.R
import com.techcognics.procuremasster.presentation.base.UiState
import com.techcognics.procuremasster.presentation.designsystem.*


@Composable
fun LoginScreen(

    navController: NavHostController,
    viewModel: LoginViewModel = hiltViewModel()

) {

    val state by viewModel.uiState.collectAsState()

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    if (state is UiState.Success) {
        LaunchedEffect(state) {
            navController.navigate("supplier_root") {   // 👈 match your graph
                popUpTo("login")
                { inclusive = true }
                launchSingleTop = true
            }
        }
    }

//    if (state is UiState.Success) {
//        LaunchedEffect(Unit) {
//            navController.navigate("supplier_home") {
//                popUpTo("login") { inclusive = true }
//            }
//        }
//    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.procuremasster_logo),
            contentDescription = "App Logo",
            modifier = Modifier
                .size(160.dp)
                .padding(bottom = 32.dp)
        )

        AppTextField(value = username, onValueChange = { username = it }, label = "Username")
        Spacer(Modifier.height(16.dp))
        AppPasswordField(value = password, onValueChange = { password = it })
        Spacer(Modifier.height(24.dp))

        AppButton(
            text = "Sign In",
            onClick = { viewModel.login(username, password) },
            isLoading = state is UiState.Loading,
            enabled = username.isNotBlank() && password.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        )
    }

    if (state is UiState.Error) {
        AppErrorMessage((state as UiState.Error).message)
    }


}

