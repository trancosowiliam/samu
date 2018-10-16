package br.com.jwk.samu.data.repository.remote

import br.com.jwk.samu.data.repository.dto.GetTicketDto
import br.com.jwk.samu.data.repository.dto.PostTicketDto
import br.com.jwk.samu.data.repository.dto.StatusDto
import br.com.jwk.samu.data.repository.dto.UpdateStatusDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SamuApi {

    @POST("api/ticket")
    fun postTicket(
            @Body body: PostTicketDto
    ): Call<GetTicketDto>

    @GET("api/ticket")
    fun getTickets(): Call<List<GetTicketDto>>

    @GET("api/ticket/{ticketId}")
    fun getTicket(
            @Path("ticketId") ticketId: Long
    ): Call<GetTicketDto>

    @POST("api/ticket/{ticketId}/status")
    fun updateStatus(
            @Path("ticketId") ticketId: Long,
            @Body status: StatusDto
    ): Call<UpdateStatusDto>

    @POST("api/device/{deviceId}/block")
    fun blockDevice(
            @Path("deviceId") deviceId: String
    ): Call<UpdateStatusDto>

    @POST("ping")
    fun ping(): Call<Void>
}