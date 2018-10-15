package br.com.jwk.samu.view.condition

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import br.com.jwk.samu.R
import br.com.jwk.samu.base.REQUEST_CODE_OPEN_TICKET
import br.com.jwk.samu.data.model.Condition
import br.com.jwk.samu.data.model.Ticket
import br.com.jwk.samu.view.note.NoteActivity
import kotlinx.android.synthetic.main.activity_condition.*
import org.koin.android.ext.android.inject

class ConditionActivity : AppCompatActivity(), ConditionContract.View {
    override val presenter by inject<ConditionContract.Presenter>()
    private val adapter by lazy { ConditionPagerAdapter(this, Condition.all) }
    private val ticket by lazy { intent.getParcelableExtra<Ticket>(EXTRA_TICKET) }

    companion object {
        private const val EXTRA_TICKET = "ticket"

        fun newIntent(context: Context, ticket: Ticket) =
                Intent(context, ConditionActivity::class.java).apply {
                    putExtra(EXTRA_TICKET, ticket)
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_condition)
        presenter(this)

        condEdtSearch.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                presenter.searchCondition(v.text.toString())
                removeEditTextFocus()
            }

            false
        }

        condVPconditions.adapter = adapter
        condCiIndicator.setViewPager(condVPconditions)

        adapter.onButtonClickListener = { condition, selected ->
            if (selected) {
                presenter.addCondition(condition)
            } else {
                presenter.removeCondition(condition)
            }
        }

        condCfdConditions.onButtonClickListener = { conditions ->
            ticket.conditions.clear()
            ticket.conditions.addAll(conditions)

            startActivityForResult(NoteActivity.newIntent(this, ticket), REQUEST_CODE_OPEN_TICKET)
        }

        removeEditTextFocus()
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

    override fun addCondition(condition: Condition) {
        condCfdConditions.addCondition(condition)
    }

    override fun removeCondition(condition: Condition) {
        condCfdConditions.removeCondition(condition)
    }

    override fun searchCondition(query: String) {
        adapter.filter(query)
        condCiIndicator.setViewPager(condVPconditions)
    }


    private fun removeEditTextFocus() {
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
                this.currentFocus?.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS)

        condEdtSearch.isCursorVisible = false
    }

}
