package br.com.jwk.samu.view.tracker

import br.com.jwk.samu.base.BasePresenter
import br.com.jwk.samu.base.BaseView
import br.com.jwk.samu.data.model.Ticket

interface TrackerContract {
    interface Presenter : BasePresenter<View> {
        fun refresh()
        fun init(ticket: Ticket)
    }

    interface View : BaseView<Presenter> {
        fun showTicket(ticket: Ticket)
    }
}