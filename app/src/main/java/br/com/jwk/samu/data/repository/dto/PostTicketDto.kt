package br.com.jwk.samu.data.repository.dto

import br.com.jwk.samu.data.model.Ticket

data class PostTicketDto(
        val latitude: Double,
        val longitude: Double,
        val note: String,
        val createFrom: String,
        val homeNumber: String,
        val neighborhood: String,
        val county: String,
        val street: String,
        val conditions: List<Long>
) {
    companion object {
        fun from(ticket: Ticket): PostTicketDto {
            return PostTicketDto(
                    ticket.address.latitude,
                    ticket.address.longitude,
                    ticket.note,
                    ticket.createFrom ?: "",
                    ticket.address.homeNumber,
                    ticket.address.neighborhood,
                    ticket.address.county,
                    ticket.address.street,
                    ticket.conditions.map { it.id }
            )
        }
    }
}