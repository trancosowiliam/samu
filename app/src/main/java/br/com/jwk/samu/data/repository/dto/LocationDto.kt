package br.com.jwk.samu.data.repository.dto

import com.google.gson.annotations.SerializedName

data class LocationDto(
        @SerializedName("lat")
        val latitude: Double,

        @SerializedName("lng")
        val longitude: Double
)