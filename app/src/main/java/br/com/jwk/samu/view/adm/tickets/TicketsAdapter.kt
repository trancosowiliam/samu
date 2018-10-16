package br.com.jwk.samu.view.adm.tickets

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.jwk.samu.R
import br.com.jwk.samu.data.model.Ticket
import kotlinx.android.synthetic.main.item_adm_ticket.view.*

class TicketsAdapter(val context: Context, val tickets: List<Ticket>, val listener: ((Ticket) -> Unit)) : RecyclerView.Adapter<TicketsAdapter.Holder>() {

    private val inflater by lazy { LayoutInflater.from(context) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(inflater.inflate(R.layout.item_adm_ticket, parent, false))
    }

    override fun getItemCount() = tickets.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.render(tickets[position])
    }


    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            itemView.setOnClickListener {
                listener(tickets[adapterPosition])
            }
        }

        fun render(ticket: Ticket) {
            val latestStatus = ticket.ticketStatus.maxBy { it.idStatus }
            itemView.tcksImgStatus.setBackgroundResource(latestStatus?.resDrawable
                    ?: R.drawable.ic_solicitado)
            itemView.tcksTxtId.text = "#${ticket.idTicket.toString().padStart(3, '0')}"
            itemView.tcksTxtStatus.text = latestStatus?.name ?: ""

            itemView.tcksTxtSubArea1.text = listOfNotNull(
                    ticket.address.neighborhood.takeUnless { it.isNullOrEmpty() },
                    ticket.address.county.takeUnless { it.isNullOrEmpty() }
            ).joinToString(" - ")

            itemView.tcksTxtSubArea2.text = listOfNotNull(
                    ticket.address.street.takeUnless { it.isNullOrEmpty() },
                    ticket.address.homeNumber.takeUnless { it.isNullOrEmpty() }
            ).joinToString(", ")
        }
    }
}