package com.openclassrooms.realestatemanager.domain.models.resultGeocoding

import java.util.HashMap

data class Location (
    var lat: Double? = null,
    var lng: Double? = null,
    val additionalProperties: MutableMap<String, Any> = HashMap()
)