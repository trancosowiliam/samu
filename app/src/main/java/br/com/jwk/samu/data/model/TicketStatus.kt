package br.com.jwk.samu.data.model

import android.os.Parcelable
import br.com.jwk.samu.R
import kotlinx.android.parcel.Parcelize

@Parcelize

data class TicketStatus(
        val idStatus: Long,
        val name: String,
        val isFinal: Boolean,
        val resDrawable: Int,
        val useAddress: Boolean
) : Parcelable {
    companion object {

        val solicitado = TicketStatus(1, "Solicitado", false, R.drawable.ic_solicitado, true)
        val ambulanciaSaiu = TicketStatus(2, "Ambulancia saiu", false, R.drawable.ic_ambulancia_saiu, false)
        val ambulanciaLocal = TicketStatus(3, "Ambulancia no local", false, R.drawable.ic_ambulancia_local, true)
        val ambulanciaRetornou = TicketStatus(4, "Ambulancia retornou", false, R.drawable.ic_ambulancia_retornou, true)
        val finalizado = TicketStatus(5, "Finalizado", true, R.drawable.ic_finalizado, false)
        val canceladoSamu = TicketStatus(6, "Canceado pelo samu", true, R.drawable.ic_cancelado_samu, false)
        val canceladoPaciente = TicketStatus(7, "Cancelado pelo paciente", true, R.drawable.ic_cancelado_paciente, true)

        val all = listOf(
                solicitado,
                ambulanciaSaiu,
                ambulanciaLocal,
                ambulanciaRetornou,
                finalizado,
                canceladoSamu,
                canceladoPaciente
        )
    }
}