package br.com.jwk.samu.view.main

import br.com.jwk.samu.base.BasePresenter
import br.com.jwk.samu.base.BaseView
import br.com.jwk.samu.data.model.Address
import br.com.jwk.samu.data.model.Ticket
import com.google.android.gms.maps.model.LatLng

interface MainContract {
    interface Presenter : BasePresenter<View> {
        fun onLocationLoaded(latLng: LatLng)
        fun onMapClickListener(latLng: LatLng)
        fun searchAddress(nameAddress: String)
        fun refresh()
        fun createTicket(ticket: Ticket)
        fun loadLatestTicket()
    }

    interface View : BaseView<Presenter> {
        fun clearMap()
        fun moveMapCamera(latLng: LatLng)
        fun createAddress(latLng: LatLng, callback: ((Address?) -> Unit))
        fun createAddress(nameAddress: String, callback: ((Address?) -> Unit))
        fun setCurrentAddress(address: Address)
        fun mapAddMarker(latLng: LatLng)
        fun isShowingLoadAddress(isLoading: Boolean)
        fun isShowingInfoAddress(isShowing: Boolean)
        fun enableMap()
        fun disableMap()
        fun showLoadingDialog()
        fun hideLoadingDialog()
        fun createTicket(ticket: Ticket)
        fun loadTrackerTicket(ticket: Ticket)
        fun showErrorMessage(message: String)
    }
}