package br.com.jwk.samu.view.main

import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import br.com.jwk.samu.R
import br.com.jwk.samu.base.MapsActivity
import br.com.jwk.samu.data.model.Address
import br.com.jwk.samu.extension.isVisible
import br.com.jwk.samu.extension.latLng
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_maps.*
import org.koin.android.ext.android.inject
import android.view.inputmethod.InputMethodManager
import br.com.jwk.samu.base.REQUEST_CODE_OPEN_TICKET
import br.com.jwk.samu.data.model.Ticket
import br.com.jwk.samu.extension.getDeviceId
import br.com.jwk.samu.view.condition.ConditionActivity
import br.com.jwk.samu.view.tracker.TrackerActivity


class MainActivity : MapsActivity(), MainContract.View {
    override val presenter by inject<MainContract.Presenter>()
    override val mapFragment: SupportMapFragment by lazy { supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment }
    override val layoutId by lazy { R.layout.activity_maps }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter(this)

        mainEdtSearch.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                presenter.searchAddress(v.text.toString())
                removeEditTextFocus()
            }

            false
        }

        mainEdtSearch.setOnClickListener {
            mainEdtSearch.isCursorVisible = true
        }

        mainFadAddress.onButtonClickListener = {
            mainFadAddress.address?.let { addr ->
                presenter.createTicket(Ticket(addr, createFrom = getDeviceId()))
            }
        }

        mainAfdAction.onButtonClickListener = {
            presenter.loadLatestTicket()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CODE_OPEN_TICKET -> openTicketResult(resultCode)
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun openTicketResult(resultCode: Int) {
        when (resultCode) {
            RESULT_OK -> refreshStatus()
        }
    }

    private fun refreshStatus() {
        presenter.refresh()
    }

    override fun onLocationLoadedCallback(location: Location) {
        presenter.onLocationLoaded(location.latLng)
    }

    override fun onMapClickListener(latLng: LatLng) {
        presenter.onMapClickListener(latLng)
        removeEditTextFocus()
    }

    override fun clearMap() {
        clearMarkers()
    }

    override fun moveMapCamera(latLng: LatLng) {
        moveCamera(latLng)
    }

    override fun createAddress(latLng: LatLng, callback: ((Address?) -> Unit)) {
        getAddress(latLng, callback)
    }

    override fun createAddress(nameAddress: String, callback: (Address?) -> Unit) {
        getAddress(nameAddress, callback)
    }

    override fun setCurrentAddress(address: Address) {
        mainFadAddress.address = address
    }


    override fun mapAddMarker(latLng: LatLng) {
        addMarker(latLng)
    }

    override fun isShowingLoadAddress(isLoading: Boolean) {
        mainFadAddress.isLoading(isLoading)
    }

    override fun isShowingInfoAddress(isShowing: Boolean) {
        mainFadAddress.isVisible = isShowing
    }

    override fun enableMap() {
        mainAfdAction.isVisible = false
        mainFadAddress.isVisible = false
        mainEdtSearch.isVisible = true
    }

    override fun disableMap() {
        mainAfdAction.isVisible = true
        mainFadAddress.isVisible = false
        mainEdtSearch.isVisible = false
    }

    override fun showLoadingDialog() {
        mainPbLoading.isVisible = true
    }

    override fun hideLoadingDialog() {
        mainPbLoading.isVisible = false
    }

    override fun createTicket(ticket: Ticket) {
        startActivityForResult(ConditionActivity.newIntent(this, ticket), REQUEST_CODE_OPEN_TICKET)
    }

    override fun loadTrackerTicket(ticket: Ticket) {
        startActivityForResult(TrackerActivity.newIntent(this, ticket), REQUEST_CODE_OPEN_TICKET)
    }

    override fun showErrorMessage(message: String) {

    }

    private fun removeEditTextFocus() {
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
                this.currentFocus?.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS)

        mainEdtSearch.isCursorVisible = false
    }
}