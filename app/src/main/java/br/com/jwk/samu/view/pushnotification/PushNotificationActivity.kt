package br.com.jwk.samu.view.pushnotification

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.jwk.samu.R
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage
import kotlinx.android.synthetic.main.activity_push_notification.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class PushNotificationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_push_notification)

        FirebaseMessaging.getInstance().subscribeToTopic("samu")

        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener { instanceIdResult ->
            mainTxtFcmToken.text = instanceIdResult.token
        }
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: RemoteMessage?) {
        mainTxtBody.text = event?.notification?.body ?: "Vazio"
        mainTxtData.text = event?.data?.toString() ?: "Vazio"
    }
}
