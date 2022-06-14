package com.openclassrooms.realestatemanager.viewmodels

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.models.PhotoWithText
import com.openclassrooms.realestatemanager.models.RealEstate
import com.openclassrooms.realestatemanager.repository.RealEstateRepository

class RealEstateViewModel : ViewModel() {
    private val realEstateRepository : RealEstateRepository = RealEstateRepository()

    val getRealEstates: SnapshotStateList<RealEstate> get() = realEstateRepository.getRealEstates

    fun createRealEstate(type: String, price: Int, area: Int, numberRoom: Int, description: String, address: String, pointOfInterest: String, status: String,listPhotosUri : List<PhotoWithText> ,dateEntry : String ,dateSale :String
    ) {
        realEstateRepository.createRealEstate(type , price , area , numberRoom , description , address , pointOfInterest , status,listPhotosUri,dateEntry,dateSale)
    }

    fun deleteRealEstate(idRealEstate : String){
        realEstateRepository.deleteRealEstate(idRealEstate)
    }

}