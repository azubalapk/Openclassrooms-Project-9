package com.openclassrooms.realestatemanager.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.openclassrooms.realestatemanager.ui.screens.NewRealEstateScreen
import com.openclassrooms.realestatemanager.ui.ui.theme.Projet_9_OC_RealEstateManagerTheme
import com.openclassrooms.realestatemanager.presentation.viewModels.RealEstateViewModel
import com.openclassrooms.realestatemanager.presentation.viewModels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi

@AndroidEntryPoint
@InternalCoroutinesApi
class NewRealEstateActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Projet_9_OC_RealEstateManagerTheme{
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val realEstateViewModel: RealEstateViewModel = hiltViewModel()
                    val userViewModel : UserViewModel = hiltViewModel()

                        NewRealEstateScreen(realEstateViewModel,userViewModel)

                }
            }
        }
    }
}



