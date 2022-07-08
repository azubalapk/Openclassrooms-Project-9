package com.openclassrooms.realestatemanager.ui.screens

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationServices.getFusedLocationProviderClient
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.openclassrooms.realestatemanager.viewmodels.RealEstateViewModel
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    drawerState: DrawerState,
    scope: CoroutineScope,
    realEstateViewModel: RealEstateViewModel,
    navControllerDrawer: NavController
) {
    val navController = rememberNavController()
    val textState = remember { mutableStateOf(TextFieldValue(""))}
    val listState = remember{ realEstateViewModel.getRealEstates }
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    val activity = LocalContext.current as Activity
    val context = LocalContext.current

    var userPosition by remember {
        mutableStateOf(LatLng(37.422131,-122.084801))
    }

    fun startLocationUpdates() {
        fusedLocationProviderClient = getFusedLocationProviderClient(activity)
        fusedLocationProviderClient.lastLocation.addOnSuccessListener {
            userPosition = LatLng(it.latitude,it.longitude)
        }
    }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {
            if (it) {
                startLocationUpdates()

            } else {
                Toast.makeText(context, "", Toast.LENGTH_SHORT).show()
            }
        },
    )



        when {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                startLocationUpdates()




            }
            ActivityCompat.shouldShowRequestPermissionRationale(activity, "") -> {}
            else -> {
                SideEffect {
                    requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }

            }
        }






    ConstraintLayout {
        val (centerAlignedTopAppBar,map) = createRefs()


        NavHost(
            navController = navController,
            startDestination = "topBarMap",
            modifier = Modifier
                .constrainAs(centerAlignedTopAppBar) {
                    top.linkTo(parent.top, margin = 0.dp)
                    start.linkTo(parent.start, margin = 0.dp)
                    end.linkTo(parent.end, margin = 0.dp)
                }
        ) {
            composable("topBarMap") { TopBar(scope,drawerState,navController,"Map",navControllerDrawer)}
        }


        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(userPosition, 10f)
        }

        val mapProperties by remember { mutableStateOf(MapProperties(isMyLocationEnabled = true)) }
        val uiSettings by remember { mutableStateOf(MapUiSettings(myLocationButtonEnabled = true)) }

        GoogleMap(
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(map) {
                    top.linkTo(centerAlignedTopAppBar.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            cameraPositionState = cameraPositionState,
            properties = mapProperties,
            uiSettings = uiSettings,
        ) {
            //for(realEstate in listState){
                //Marker(
                    //state = MarkerState(position = userPosition),
                //)
            //}

        }


    }

}




