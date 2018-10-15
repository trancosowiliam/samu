package br.com.jwk.samu.base

import android.app.Application
import br.com.jwk.samu.module.applicationModule
import br.com.jwk.samu.module.retrofitClientModule
import com.google.firebase.messaging.FirebaseMessaging
import org.koin.android.ext.android.startKoin

const val REQUEST_CODE_OPEN_TICKET = 50123

class App : Application() {

    private val modules = listOf(
            applicationModule,
            retrofitClientModule
    )

    override fun onCreate() {
        super.onCreate()
        startKoin(this, modules)

        FirebaseMessaging.getInstance().subscribeToTopic("samu")
    }
}