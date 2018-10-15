package br.com.jwk.samu.view.note

import br.com.jwk.samu.data.model.Ticket
import br.com.jwk.samu.data.model.TicketStatus
import br.com.jwk.samu.data.repository.local.Preferences
import br.com.jwk.samu.data.repository.remote.SamuService

class NotePresenter(private val service: SamuService, private val preferences: Preferences) : NoteContract.Presenter {

    override lateinit var view: NoteContract.View

    override fun saveTicket(ticket: Ticket) {
        view.showLoadingDialog()
        service.postTicket(ticket, onSuccess = { ticketId ->
            preferences.statusEnabled = false
            preferences.latestTicket = ticketId

            ticket.idTicket = ticketId
            ticket.ticketStatus.add(TicketStatus.solicitado)

            view.hideLoadingDialog()
            view.onTicketCreated(ticket)
        }, onError = { str ->
            preferences.statusEnabled = true
            view.hideLoadingDialog()
            view.showErrorMessage(str)
        })
    }
}