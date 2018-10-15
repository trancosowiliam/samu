package br.com.jwk.samu.data.model

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Address(
        val country: String,
        val countryCode: String,
        val state: String,
        val county: String,
        val neighborhood: String,
        val street: String,
        val postalCode: String,
        val homeNumber: String,
        val latitude: Double,
        val longitude: Double
) : Parcelable {
    constructor(
            county: String,
            neighborhood: String,
            street: String,
            homeNumber: String,
            latitude: Double,
            longitude: Double
    ) : this("", "", "", county, neighborhood, street, "", homeNumber, latitude, longitude)

    val latLng: LatLng
        get() = LatLng(latitude, longitude)

}


