package br.com.jwk.samu.data.repository.remote

import br.com.jwk.samu.data.model.Ticket
import br.com.jwk.samu.data.repository.dto.PostTicketDto
import br.com.jwk.samu.data.repository.dto.StatusDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class SamuServiceImpl(retrofit: Retrofit) : SamuService {
    private val api by lazy { retrofit.create(SamuApi::class.java) }

    override fun postTicket(ticket: Ticket, onSuccess: (Long) -> Unit, onError: (String) -> Unit) {
        api.postTicket(PostTicketDto.from(ticket)).exec(onSuccess, onError) {
            it?.toTicket()?.idTicket ?: 0
        }
    }

    override fun getTicket(idTicket: Long, onSuccess: (Ticket?) -> Unit, onError: (String) -> Unit) {
        api.getTicket(idTicket).exec(onSuccess, onError) {
            it?.toTicket()
        }
    }

    override fun getTickets(onSuccess: (List<Ticket>) -> Unit, onError: (String) -> Unit) {
        api.getTickets().exec(onSuccess, onError) { dtos ->
            dtos.orEmpty().map { it.toTicket() }
        }
    }

    override fun updateStatus(idTicket: Long, idStatus: Long, onSuccess: (Boolean) -> Unit, onError: (String) -> Unit) {
        api.updateStatus(idTicket, StatusDto(idStatus)).exec(onSuccess, onError) {
            it?.status == true
        }
    }

    override fun blockDevice(idDevice: String, onSuccess: (Boolean) -> Unit, onError: (String) -> Unit) {
        api.blockDevice(idDevice).exec(onSuccess, onError) {
            it?.status == true
        }
    }

    override fun ping() {
        api.ping().enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {}
            override fun onFailure(call: Call<Void>, t: Throwable) {}
        })
    }
}

fun <T, R> Call<T>.exec(onSuccess: (R) -> Unit, onFailure: (String) -> Unit, transform: ((T?) -> R)) {
    this.enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>?, response: Response<T>?) {
            if (response?.isSuccessful == true) {
                onSuccess(transform(response?.body()))
            } else {
                onFailure(response?.errorBody()?.string().takeUnless { it.isNullOrEmpty() }
                        ?: "Erro inesperado!")
            }
        }

        override fun onFailure(call: Call<T>?, t: Throwable?) {
            onFailure("Erro inesperado! Verifique sua conexão")
        }
    })
}