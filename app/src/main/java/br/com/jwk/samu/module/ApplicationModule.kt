package br.com.jwk.samu.module

import br.com.jwk.samu.data.repository.local.Preferences
import br.com.jwk.samu.data.repository.local.PreferencesImpl
import br.com.jwk.samu.data.repository.remote.*
import br.com.jwk.samu.view.adm.ticket.TicketContract
import br.com.jwk.samu.view.adm.ticket.TicketPresenter
import br.com.jwk.samu.view.adm.tickets.TicketsContract
import br.com.jwk.samu.view.adm.tickets.TicketsPresenter
import br.com.jwk.samu.view.condition.ConditionContract
import br.com.jwk.samu.view.condition.ConditionPresenter
import br.com.jwk.samu.view.main.MainContract
import br.com.jwk.samu.view.main.MainPresenter
import br.com.jwk.samu.view.note.NoteContract
import br.com.jwk.samu.view.note.NotePresenter
import br.com.jwk.samu.view.tracker.TrackerContract
import br.com.jwk.samu.view.tracker.TrackerPresenter
import org.koin.dsl.module.applicationContext

val applicationModule = applicationContext {
    factory { MainPresenter(service = get(), preferences = get()) as MainContract.Presenter }
    factory { ConditionPresenter() as ConditionContract.Presenter }
    factory { NotePresenter(service = get(), preferences = get()) as NoteContract.Presenter }
    factory { TicketsPresenter(service = get()) as TicketsContract.Presenter }
    factory { TrackerPresenter(service = get()) as TrackerContract.Presenter }
    factory { TicketPresenter(service = get()) as TicketContract.Presenter }

    factory { PreferencesImpl(context = get()) as Preferences }
    factory { GoogleMapsServiceImpl(retrofit = get(GOOGLE_SERVICE)) as GoogleMapsService }
    factory { SamuServiceImpl(retrofit = get(BASE_SERVICE)) as SamuService }

}