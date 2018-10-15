package br.com.jwk.samu.view.tracker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.jwk.samu.R
import br.com.jwk.samu.data.model.Ticket
import br.com.jwk.samu.data.model.TicketStatus
import br.com.jwk.samu.extension.isVisible
import kotlinx.android.synthetic.main.item_tracker.view.*

class TrackerAdapter(val context: Context, val ticket: Ticket) : RecyclerView.Adapter<TrackerAdapter.Holder>() {

    private val inflater by lazy { LayoutInflater.from(context) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(inflater.inflate(R.layout.item_tracker, parent, false))
    }

    override fun getItemCount() = ticket.ticketStatus.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.render(ticket, ticket.ticketStatus[position])
    }

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {

        fun render(ticket: Ticket, status: TicketStatus) {
            val subArea1 = if(status.useAddress) listOfNotNull(
                    ticket.address.neighborhood.takeUnless { it.isNullOrEmpty() },
                    ticket.address.county.takeUnless { it.isNullOrEmpty() }
            ).joinToString(" - ") else "Vitoria - ES"

            val subArea2 = if(status.useAddress) listOfNotNull(
                    ticket.address.street.takeUnless { it.isNullOrEmpty() },
                    ticket.address.homeNumber.takeUnless { it.isNullOrEmpty() }
            ).joinToString(", ") else "Rua Pedro Rocha Dal cim, 1806"

            itemView.itraImgStatus.setBackgroundResource(status.resDrawable)
            itemView.itraTxtStatus.text = status.name
            itemView.itraTxtSubArea1.text = subArea1
            itemView.itraTxtSubArea2.text = subArea2

            itemView.itraGrpArrow.isVisible = ticket.ticketStatus.size > adapterPosition + 1
        }
    }
}