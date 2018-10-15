package br.com.jwk.samu.data.repository.remote

import br.com.jwk.samu.data.repository.dto.GeocodeDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleMapsApi {
    @GET("geocode/json")
    fun latLngAddress(
            @Query("latlng") latLng: String,
            @Query("sensor") sensor: Boolean,
            @Query("key") key: String
    ): Call<GeocodeDto>

    @GET("geocode/json")
    fun nameAddress(
            @Query("address") address: String,
            @Query("sensor") sensor: Boolean,
            @Query("key") key: String
    ): Call<GeocodeDto>
}