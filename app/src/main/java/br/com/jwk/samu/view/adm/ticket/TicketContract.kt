package br.com.jwk.samu.view.adm.ticket

import br.com.jwk.samu.base.BasePresenter
import br.com.jwk.samu.base.BaseView
import br.com.jwk.samu.data.model.Ticket

interface TicketContract {
    interface Presenter : BasePresenter<View> {
        fun init(ticket: Ticket)
        fun blockDevice(deviceId: String)
        fun updateTicketStatus(ticket: Ticket, statusId: Long)
    }

    interface View : BaseView<Presenter> {
        fun popule(ticket: Ticket)
        fun showLoadingDialog()
        fun hideLoadingDialog()
        fun showErrorMessage(message: String)
    }
}