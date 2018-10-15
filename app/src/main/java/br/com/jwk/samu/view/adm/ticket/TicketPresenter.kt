package br.com.jwk.samu.view.adm.ticket

import br.com.jwk.samu.data.model.Ticket
import br.com.jwk.samu.data.model.TicketStatus
import br.com.jwk.samu.data.repository.remote.SamuService

class TicketPresenter(val service: SamuService) : TicketContract.Presenter {

    override lateinit var view: TicketContract.View


    override fun init(ticket: Ticket) {
        view.popule(ticket)
    }

    override fun blockDevice(deviceId: String) {
        view.showLoadingDialog()
        service.blockDevice(deviceId, onSuccess = {
            view.hideLoadingDialog()
        }, onError = {
            view.hideLoadingDialog()
            view.showErrorMessage(it)
        })
    }

    override fun updateTicketStatus(ticket: Ticket, idStatus: Long) {
        view.showLoadingDialog()
        if (ticket.idTicket == null) {
            view.showErrorMessage("Ocorrência inválida!")
            return
        }

        service.updateStatus(ticket.idTicket!!, idStatus, onSuccess = {
            view.hideLoadingDialog()
            ticket.ticketStatus.add(TicketStatus.all.first { it.idStatus == idStatus })
            view.popule(ticket)
        }, onError = {
            view.hideLoadingDialog()
            view.showErrorMessage(it)
        })
    }
}