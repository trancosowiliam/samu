package br.com.jwk.samu.data.repository.dto

import com.google.gson.annotations.SerializedName

data class AddressResultsDto(
        @SerializedName("address_components")
        val addressComponents: List<AddressComponentDto>,

        @SerializedName("geometry")
        val geometry: GeometryDto
)