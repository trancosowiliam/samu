package br.com.jwk.samu.view.adm.tickets

import br.com.jwk.samu.base.BasePresenter
import br.com.jwk.samu.base.BaseView
import br.com.jwk.samu.data.model.Ticket

interface TicketsContract {
    interface Presenter : BasePresenter<View> {
        fun loadTickets()
        fun refresh()
    }

    interface View : BaseView<Presenter> {
        fun showLoadingDialog()
        fun hideLoadingDialog()
        fun showTickets(tickets: List<Ticket>)
        fun showErrorMessage(message: String)
    }
}