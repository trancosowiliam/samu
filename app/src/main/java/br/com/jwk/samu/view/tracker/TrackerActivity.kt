package br.com.jwk.samu.view.tracker

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.jwk.samu.R
import br.com.jwk.samu.data.model.Ticket
import br.com.jwk.samu.data.model.TicketStatus
import br.com.jwk.samu.data.repository.local.PreferencesImpl
import br.com.jwk.samu.extension.isVisible
import br.com.jwk.samu.fcm.event.UpdateTicketEvent
import kotlinx.android.synthetic.main.activity_tracker.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.android.ext.android.inject

class TrackerActivity : AppCompatActivity(), TrackerContract.View {

    override val presenter by inject<TrackerContract.Presenter>()

    private val ticket by lazy { intent.getParcelableExtra<Ticket>(EXTRA_TICKET) }

    companion object {
        private const val EXTRA_TICKET = "ticket"

        fun newIntent(context: Context, ticket: Ticket) =
                Intent(context, TrackerActivity::class.java).apply {
                    putExtra(EXTRA_TICKET, ticket)
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tracker)

        presenter(this)
        presenter.init(ticket)

        EventBus.getDefault().register(this)
    }

    override fun onBackPressed() {
        setResult(Activity.RESULT_OK)
        super.onBackPressed()
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    override fun showTicket(ticket: Ticket) {
        traRecStatus.layoutManager = LinearLayoutManager(this)
        traRecStatus.adapter = TrackerAdapter(this, ticket)

        val finished = (ticket.ticketStatus.maxBy { it.idStatus }?.idStatus
                ?: 0) >= TicketStatus.finalizado.idStatus
        traAfdAction.isVisible = finished
        traIfdInfo.isVisible = !finished

        traAfdAction.onButtonClickListener = {
            val preferencesImpl = PreferencesImpl(this)
            preferencesImpl.statusEnabled = true
            preferencesImpl.latestTicket = -1
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: UpdateTicketEvent?) {
        if (ticket.idTicket.toString() == event?.idTicket) {
            presenter.refresh()
        }
    }
}
