package br.com.jwk.samu.view.adm.tickets

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.jwk.samu.R
import br.com.jwk.samu.data.model.Ticket
import br.com.jwk.samu.extension.isVisible
import br.com.jwk.samu.fcm.event.CreateTicketEvent
import br.com.jwk.samu.view.adm.ticket.TicketActivity
import kotlinx.android.synthetic.main.activity_tickets.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.android.ext.android.inject

class TicketsActivity : AppCompatActivity(), TicketsContract.View {

    override val presenter by inject<TicketsContract.Presenter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tickets)

        presenter(this)
        presenter.loadTickets()

        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    override fun showLoadingDialog() {
        tcksPbLoading.isVisible = true
    }

    override fun hideLoadingDialog() {
        tcksPbLoading.isVisible = false
    }

    override fun showTickets(tickets: List<Ticket>) {
        tcksRecTickets.layoutManager = LinearLayoutManager(this)
        tcksRecTickets.adapter = TicketsAdapter(this, tickets) {
            onTicketClick(it)
        }
    }

    override fun showErrorMessage(message: String) {
        message.toast()
    }

    fun onTicketClick(ticket: Ticket) {
        startActivity(TicketActivity.newIntent(this, ticket))
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: CreateTicketEvent?) {
        presenter.refresh()
    }
}

fun String.toast() {
    Log.i("SAMU_LOG", this)
}