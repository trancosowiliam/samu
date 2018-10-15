package br.com.jwk.samu.view.adm.ticket

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import br.com.jwk.samu.R
import br.com.jwk.samu.data.model.Ticket
import br.com.jwk.samu.data.model.TicketStatus
import br.com.jwk.samu.extension.isVisible
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import kotlinx.android.synthetic.main.activity_ticket.*
import org.koin.android.ext.android.inject

class TicketActivity : AppCompatActivity(), TicketContract.View {
    override val presenter by inject<TicketContract.Presenter>()

    private val ticket by lazy { intent.getParcelableExtra<Ticket>(EXTRA_TICKET) }

    companion object {
        private const val EXTRA_TICKET = "ticket"

        fun newIntent(context: Context, ticket: Ticket) =
                Intent(context, TicketActivity::class.java).apply {
                    putExtra(EXTRA_TICKET, ticket)
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket)
        presenter(this)
        presenter.init(ticket)
    }

    override fun popule(ticket: Ticket) {
        tckTxtId.text = ticket.idTicket.toString()
        tckTxtSubArea1.text = listOfNotNull(
                ticket.address.neighborhood.takeUnless { it.isNullOrEmpty() },
                ticket.address.county.takeUnless { it.isNullOrEmpty() }
        ).joinToString(" - ")

        tckTxtSubArea2.text = listOfNotNull(
                ticket.address.street.takeUnless { it.isNullOrEmpty() },
                ticket.address.homeNumber.takeUnless { it.isNullOrEmpty() }
        ).joinToString(", ")

        tckTxtNote.text = ticket.note

        tckTxtStatus.text = ticket.ticketStatus.joinToString(" > ", transform = {
            it.name
        })

        tckTxtDevice.text = ticket.createFrom

        tckBtnBlock.setOnClickListener {
            ticket.createFrom?.let {
                presenter.blockDevice(it)
            }
        }

        tckBtnStatus.setOnClickListener {
            onStatusClick(ticket)
        }
    }

    override fun showLoadingDialog() {
        tckPbLoading.isVisible = true
    }

    override fun hideLoadingDialog() {
        tckPbLoading.isVisible = false
    }

    override fun showErrorMessage(message: String) {
        MaterialDialog(this)
                .message(text = message)
                .positiveButton(text = "Ok")
                .cancelable(false)
                .show()
    }

    fun onStatusClick(ticket: Ticket) {
        val statusEnabled = TicketStatus
                .all
                .filter {
                    it.idStatus > ticket.ticketStatus.maxBy { it.idStatus }?.idStatus ?: 100}

        MaterialDialog(this)
                .title(text = "Novo Status")
                .listItems(items = statusEnabled.map { it.name }) { _, index, _ ->
                    presenter.updateTicketStatus(ticket, statusEnabled[index].idStatus)
                }
                .show()

    }
}
