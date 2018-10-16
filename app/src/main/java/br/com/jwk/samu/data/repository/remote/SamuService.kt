package br.com.jwk.samu.data.repository.remote

import br.com.jwk.samu.data.model.Ticket

interface SamuService {
    fun postTicket(ticket: Ticket, onSuccess: (Long) -> Unit, onError: (String) -> Unit)
    fun getTicket(idTicket: Long, onSuccess: (Ticket?) -> Unit, onError: (String) -> Unit)
    fun getTickets(onSuccess: (List<Ticket>) -> Unit, onError: (String) -> Unit)
    fun updateStatus(idTicket: Long, idStatus: Long, onSuccess: (Boolean) -> Unit, onError: (String) -> Unit)
    fun blockDevice(idDevice: String, onSuccess: (Boolean) -> Unit, onError: (String) -> Unit)
    fun ping()
}