package br.com.jwk.samu.view.tracker

import br.com.jwk.samu.data.model.Ticket
import br.com.jwk.samu.data.repository.remote.SamuService

class TrackerPresenter(private val service: SamuService) : TrackerContract.Presenter {
    override lateinit var view: TrackerContract.View

    lateinit var ticket: Ticket

    override fun init(ticket: Ticket) {
        this.ticket = ticket
        view.showTicket(ticket)
    }

    override fun refresh() {
        service.getTicket(ticket.idTicket!!, onSuccess = { ticket ->
            ticket?.let {
                view.showTicket(it)
            }
        }) {}
    }
}