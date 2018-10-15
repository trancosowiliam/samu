package br.com.jwk.samu.data.repository.dto

import com.google.gson.annotations.SerializedName

data class GeometryDto(
        @SerializedName("location")
        val location: LocationDto
)