package br.com.jwk.samu.data.model

import android.os.Parcelable
import br.com.jwk.samu.R
import kotlinx.android.parcel.Parcelize

@Suppress("SpellCheckingInspection")
@Parcelize
class Condition(val id: Long, val name: String, val resDrawable: Int) : Parcelable {
    companion object {
        val desacordado = Condition(1, "Desacordado", R.drawable.ic_desacordado)
        val membroFraturado = Condition(2, "Membro fraturado", R.drawable.ic_fraturado)
        val infarto = Condition(3, "Infarto", R.drawable.ic_infarto)
        val dorNoPeito = Condition(4, "Dor no peito", R.drawable.ic_dor_no_peito)
        val faltaDeAr = Condition(5, "Falta de ar", R.drawable.ic_falta_de_ar)
        val tontura = Condition(6, "tontura", R.drawable.ic_tontura)
        val queda = Condition(7, "queda", R.drawable.ic_queda)
        val vomito = Condition(8, "Vômito", R.drawable.ic_vomito)
        val convulsao = Condition(9, "Convulsão", R.drawable.ic_convulsao)
        val febre = Condition(10, "febre", R.drawable.ic_febre)
        val erupcoes = Condition(11, "Erupções", R.drawable.ic_erupcao)
        val diarreia = Condition(12, "diarreia", R.drawable.ic_diarreia)
        val afogamento = Condition(13, "afogamento", R.drawable.ic_afogamento)
        val suicidio = Condition(14, "Suicídio", R.drawable.ic_suicidio)
        val intoxicacao = Condition(15, "intoxicacao", R.drawable.ic_intoxicacao)
        val acidenteTransito = Condition(16, "Acidente de trânsito", R.drawable.ic_acidente_transito)

        val all = listOf(
                desacordado,
                membroFraturado,
                infarto,
                dorNoPeito,
                faltaDeAr,
                tontura,
                queda,
                vomito,
                convulsao,
                febre,
                erupcoes,
                diarreia,
                afogamento,
                suicidio,
                intoxicacao,
                acidenteTransito
        )
    }
}