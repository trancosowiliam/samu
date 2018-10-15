package br.com.jwk.samu.view.adm.tickets

import br.com.jwk.samu.data.repository.remote.SamuService

class TicketsPresenter(private val service: SamuService) : TicketsContract.Presenter {
    override lateinit var view: TicketsContract.View

    override fun loadTickets() {
        view.showLoadingDialog()

        service.getTickets(onSuccess = { tickets ->
            view.hideLoadingDialog()
            view.showTickets(tickets)
        }, onError = { error ->
            view.hideLoadingDialog()
            view.showErrorMessage(error)
        })
    }

    override fun refresh() {
        service.getTickets(onSuccess = { tickets ->
            view.showTickets(tickets)
        }){}
    }

}