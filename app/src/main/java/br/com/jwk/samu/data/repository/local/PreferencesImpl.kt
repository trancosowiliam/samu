package br.com.jwk.samu.data.repository.local

import android.content.Context
import android.preference.PreferenceManager

class PreferencesImpl(context: Context) : Preferences {
    companion object {
        private const val STATUS_ENABLED = "STATUS_ENABLED"
        private const val LATEST_TICKET = "LATEST_TICKET"
    }

    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    override var statusEnabled
        get() = preferences.getBoolean(STATUS_ENABLED, true)
        set(value) = preferences.edit().putBoolean(STATUS_ENABLED, value).apply()

    override var latestTicket
        get() = preferences.getLong(LATEST_TICKET, -1)
        set(value) = preferences.edit().putLong(LATEST_TICKET, value).apply()
}