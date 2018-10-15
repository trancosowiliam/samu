package br.com.jwk.samu.data.repository.remote

import br.com.jwk.samu.BuildConfig
import br.com.jwk.samu.data.model.Address
import br.com.jwk.samu.data.repository.dto.GeocodeDto
import br.com.jwk.samu.extension.toAddress
import com.google.android.gms.maps.model.LatLng
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class GoogleMapsServiceImpl(private val retrofit: Retrofit) : GoogleMapsService {
    private val api by lazy { retrofit.create(GoogleMapsApi::class.java) }

    override fun getAddress(latLng: LatLng, callback: ((Address?) -> Unit)): Call<GeocodeDto> {
        return api.latLngAddress("${latLng.latitude},${latLng.longitude}", true, BuildConfig.MAPS_KEY).apply {
            enqueue(object : Callback<GeocodeDto> {
                override fun onResponse(call: Call<GeocodeDto>, response: Response<GeocodeDto>) {
                    callback(response?.body()?.results?.firstOrNull()?.toAddress())
                }

                override fun onFailure(call: Call<GeocodeDto>, t: Throwable) {
                    if (t.message != "Canceled")
                        callback(null)
                }
            })
        }
    }

    override fun getAddress(nameAddress: String, callback: ((Address?) -> Unit)): Call<GeocodeDto> {
        return api.nameAddress(nameAddress, true, BuildConfig.MAPS_KEY).apply {
            enqueue(object : Callback<GeocodeDto> {
                override fun onResponse(call: Call<GeocodeDto>, response: Response<GeocodeDto>) {
                    callback(response?.body()?.results?.firstOrNull()?.toAddress())
                }

                override fun onFailure(call: Call<GeocodeDto>, t: Throwable) {
                    if (t.message != "Canceled")
                        callback(null)
                }
            })
        }
    }
}