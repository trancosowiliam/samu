package br.com.jwk.samu.data.repository.dto

import com.google.gson.annotations.SerializedName

data class AddressComponentDto(
        @SerializedName("short_name")
        val shortName: String,

        @SerializedName("long_name")
        val longName: String,

        @SerializedName("types")
        val types: List<String>
)