package br.com.jwk.samu.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ticket(
        val address: Address,
        val conditions: MutableList<Condition> = mutableListOf(),
        var note: String = "",
        val ticketStatus: MutableList<TicketStatus> = mutableListOf(),
        var idTicket: Long? = null,
        var createFrom: String? = null
) : Parcelable