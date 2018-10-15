package br.com.jwk.samu.fcm.event

import android.content.Context
import org.greenrobot.eventbus.EventBus

class CreateTicketEvent() : Event() {
    companion object {
        const val ACTION = "CREATE_TICKET"
    }

    override fun execute(context: Context) {
        EventBus.getDefault().post(this)
    }
}