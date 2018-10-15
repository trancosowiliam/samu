package br.com.jwk.samu.extension

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import androidx.core.content.ContextCompat
import br.com.jwk.samu.data.model.Address
import br.com.jwk.samu.data.repository.dto.AddressResultsDto
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

fun MarkerOptions.createDrawableVector(context: Context, vectorResId:Int) {
    val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)

    icon(vectorDrawable?.let { vector ->
        vector.setBounds(0, 0, vector.intrinsicWidth, vector.intrinsicHeight)
        val bitmap = Bitmap.createBitmap(vector.intrinsicWidth, vector.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        vector.draw(canvas)

        BitmapDescriptorFactory.fromBitmap(bitmap)
    })
}

val Location.latLng
    get() = LatLng(latitude, longitude)

fun com.google.android.gms.maps.model.LatLng.toGeoLatLng(): com.google.maps.model.LatLng {
    return com.google.maps.model.LatLng(this.latitude, this.longitude)
}

fun AddressResultsDto?.toAddress(): Address? {
    return this?.addressComponents?.let { addresses ->
        Address(
                country = addresses.firstOrNull { addressComponent -> addressComponent.types.any { it == "country" } }?.longName
                        ?: "",
                countryCode = addresses.firstOrNull { addressComponent -> addressComponent.types.any { it == "country" } }?.shortName
                        ?: "",
                state = addresses.firstOrNull { addressComponent -> addressComponent.types.any { it == "administrative_area_level_1" } }?.longName
                        ?: "",
                county = addresses.firstOrNull { addressComponent -> addressComponent.types.any { it == "administrative_area_level_2" } }?.longName
                        ?: "",
                neighborhood = addresses.firstOrNull { addressComponent -> addressComponent.types.any { it == "sublocality_level_1" } }?.longName
                        ?: "",
                street = addresses.firstOrNull { addressComponent -> addressComponent.types.any { it == "route" } }?.longName
                        ?: "",
                postalCode = addresses.firstOrNull { addressComponent -> addressComponent.types.any { it == "postal_code" } }?.longName
                        ?: "",
                homeNumber = addresses.firstOrNull { addressComponent -> addressComponent.types.any { it == "street_number" } }?.longName
                        ?: "",
                latitude = this.geometry.location.latitude,
                longitude = this.geometry.location.longitude
        )
    }
}