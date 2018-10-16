package br.com.jwk.samu.view.main

import br.com.jwk.samu.data.model.Address
import br.com.jwk.samu.data.model.Ticket
import br.com.jwk.samu.data.repository.local.Preferences
import br.com.jwk.samu.data.repository.remote.SamuService
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.android.Main
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch

class MainPresenter(private val service: SamuService, private val preferences: Preferences) : MainContract.Presenter {

    override lateinit var view: MainContract.View

    override fun onLocationLoaded(latLng: LatLng) {
        service.ping()

        if (preferences.statusEnabled) {
            loadLocationPosition(latLng)
            view.enableMap()
        } else {
            view.disableMap()
        }

        view.moveMapCamera(latLng)
    }

    override fun onMapClickListener(latLng: LatLng) {
        if (preferences.statusEnabled)
            loadLocationPosition(latLng)
    }

    private fun loadLocationPosition(latLng: LatLng) {
        view.clearMap()
        view.isShowingLoadAddress(true)
        view.isShowingInfoAddress(true)

        GlobalScope.launch(Dispatchers.Main) {
            async { view.mapAddMarker(latLng) }
            async {
                view.createAddress(latLng) {
                    setSelectedLocation(it)
                }
            }
        }
    }

    override fun searchAddress(nameAddress: String) {
        view.clearMap()
        view.isShowingLoadAddress(true)

        GlobalScope.launch(Dispatchers.Main) {
            async {
                view.createAddress(nameAddress) { address ->
                    address?.latLng?.let {
                        view.mapAddMarker(it)
                        view.moveMapCamera(it)
                    }

                    setSelectedLocation(address)
                }
            }
        }
    }

    override fun refresh() {
        if (preferences.statusEnabled) {
            view.enableMap()
        } else {
            view.disableMap()
        }
    }

    override fun loadLatestTicket() {
        view.showLoadingDialog()
        service.getTicket(preferences.latestTicket, onSuccess = {
            view.hideLoadingDialog()
            it?.let { view.loadTrackerTicket(ticket = it) }
        }, onError = {
            view.hideLoadingDialog()
            view.showErrorMessage(it)
        })
    }


    override fun createTicket(ticket: Ticket) {
        view.createTicket(ticket)
    }

    private fun setSelectedLocation(address: Address?) {
        if (address != null) {
            view.setCurrentAddress(address)
        }

        view.isShowingInfoAddress(address != null)
        view.isShowingLoadAddress(false)

    }

}