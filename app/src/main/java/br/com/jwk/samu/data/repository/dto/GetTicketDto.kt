package br.com.jwk.samu.data.repository.dto

import br.com.jwk.samu.data.model.Address
import br.com.jwk.samu.data.model.Ticket
import br.com.jwk.samu.data.model.TicketStatus
import com.google.gson.annotations.SerializedName

data class GetTicketDto(
        @SerializedName("id") val id: Long,
        val latitude: Double,
        val longitude: Double,
        val note: String,
        val createFrom: String,
        val homeNumber: String,
        val neighborhood: String,
        val county: String,
        val street: String,
        val status: List<Long>?
) {
    fun toTicket(): Ticket {
        return Ticket(
                Address(county, neighborhood, street, homeNumber, latitude, longitude),
                mutableListOf(),
                note,
                (status?.asSequence()?.
                        mapNotNull { statudId -> TicketStatus.all.firstOrNull { it.idStatus == statudId } }?.
                        toMutableList() ?: mutableListOf()).
                        apply { sortBy { it.idStatus } },
                id,
                createFrom)
    }
}