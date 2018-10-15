package br.com.jwk.samu.fcm

import br.com.jwk.samu.fcm.event.Event
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseInstanceIDService : FirebaseMessagingService() {

    override fun onNewToken(s: String?) {
        super.onNewToken(s)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)

        Event.getEvent(remoteMessage)?.let {
            it.execute(this)
        }
    }
}