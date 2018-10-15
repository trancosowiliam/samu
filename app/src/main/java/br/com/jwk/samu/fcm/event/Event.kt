package br.com.jwk.samu.fcm.event

import android.content.Context
import com.google.firebase.messaging.RemoteMessage

abstract class Event {

    abstract fun execute(context: Context)

    companion object {
        fun getEvent(remoteMessage: RemoteMessage?): Event? {
            if (remoteMessage == null || remoteMessage.data == null)
                return null

            val data = remoteMessage.data

            return when (data["action"]?.toUpperCase()) {
                CreateTicketEvent.ACTION -> CreateTicketEvent()
                UpdateTicketEvent.ACTION -> UpdateTicketEvent(data.opt("value", "-1"))
                else -> null
            }
        }
    }
}

private fun Map<String, String>.opt(key: String, default: String): String {
    return if (this.containsKey(key)) this[key]!! else default
}