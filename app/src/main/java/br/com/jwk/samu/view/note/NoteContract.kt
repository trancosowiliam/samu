package br.com.jwk.samu.view.note

import br.com.jwk.samu.base.BasePresenter
import br.com.jwk.samu.base.BaseView
import br.com.jwk.samu.data.model.Ticket

interface NoteContract {
    interface Presenter : BasePresenter<View> {
        fun saveTicket(ticket: Ticket)
    }

    interface View : BaseView<Presenter> {
        fun showLoadingDialog()
        fun hideLoadingDialog()
        fun onTicketCreated(ticket: Ticket)
        fun showErrorMessage(error: String)
    }
}