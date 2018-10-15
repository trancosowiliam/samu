package br.com.jwk.samu.data.repository.remote

import br.com.jwk.samu.data.model.Address
import br.com.jwk.samu.data.repository.dto.GeocodeDto
import com.google.android.gms.maps.model.LatLng
import retrofit2.Call

interface GoogleMapsService {
    fun getAddress(latLng: LatLng, callback: ((Address?) -> Unit)): Call<GeocodeDto>
    fun getAddress(address: String, callback: ((Address?) -> Unit)): Call<GeocodeDto>
}