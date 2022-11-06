package com.openclassrooms.realestatemanager.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.auth.AuthResult
import com.openclassrooms.realestatemanager.domain.models.Response
import com.openclassrooms.realestatemanager.presentation.viewModels.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    navigateToMainScreen : () -> Unit,
    navigateToRegisterScreen : () -> Unit
){

    val userViewModel = hiltViewModel<UserViewModel>()

    Log.e("recompose","LoginScreen")

    val context = LocalContext.current

        when (val loginResponse = userViewModel.loginUserResponse) {
            is Response.Empty -> {
                LoginEmailAndPassword(userViewModel,navigateToRegisterScreen = {
                    navigateToRegisterScreen()
                })
            }
            is Response.Failure -> {
                Toast.makeText(context, loginResponse.e.toString(), Toast.LENGTH_SHORT).show()
                LoginEmailAndPassword(userViewModel,navigateToRegisterScreen = {
                    navigateToRegisterScreen()
                })
            }
            is Response.Loading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is Response.Success -> {

                LaunchedEffect(userViewModel.loginUserResponse) {
                    if (userViewModel.loginUserResponse is Response.Success) {
                        Toast.makeText(context, "Connexion Réussi", Toast.LENGTH_SHORT).show()
                        navigateToMainScreen()
                    }
                }
            }

    }


}



