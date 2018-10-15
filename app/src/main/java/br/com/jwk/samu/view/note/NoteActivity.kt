package br.com.jwk.samu.view.note

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import br.com.jwk.samu.R
import br.com.jwk.samu.base.REQUEST_CODE_OPEN_TICKET
import br.com.jwk.samu.data.model.Ticket
import br.com.jwk.samu.extension.isVisible
import br.com.jwk.samu.view.tracker.TrackerActivity
import kotlinx.android.synthetic.main.activity_note.*
import org.koin.android.ext.android.inject


class NoteActivity : AppCompatActivity(), NoteContract.View {

    override val presenter by inject<NoteContract.Presenter>()

    private val ticket by lazy { intent.getParcelableExtra<Ticket>(NoteActivity.EXTRA_TICKET) }

    companion object {
        private const val EXTRA_TICKET = "ticket"

        fun newIntent(context: Context, ticket: Ticket) =
                Intent(context, NoteActivity::class.java).apply {
                    putExtra(EXTRA_TICKET, ticket)
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        presenter(this)

        noteCvcondition11.isLocked = true
        noteCvcondition12.isLocked = true
        noteCvcondition13.isLocked = true

        noteCvcondition11.populate(ticket.conditions?.getOrNull(0))
        noteCvcondition12.populate(ticket.conditions?.getOrNull(1))
        noteCvcondition13.populate(ticket.conditions?.getOrNull(2))

        noteAfdAction.onButtonClickListener = {
            ticket.note = noteEdtNote.text.toString()
            presenter.saveTicket(ticket)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CODE_OPEN_TICKET -> openTicketResult(resultCode)
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun openTicketResult(resultCode: Int) {
        when (resultCode) {
            RESULT_OK -> finishOpenTicketOk()
        }
    }

    private fun finishOpenTicketOk() {
        setResult(RESULT_OK)
        finish()
    }

    override fun showLoadingDialog() {
        notePbLoading.isVisible = true
    }

    override fun hideLoadingDialog() {
        notePbLoading.isVisible = false
    }

    override fun onTicketCreated(ticket: Ticket) {
        startActivityForResult(TrackerActivity.newIntent(this, ticket), REQUEST_CODE_OPEN_TICKET)
    }

    override fun showErrorMessage(error: String) {
    }
}
