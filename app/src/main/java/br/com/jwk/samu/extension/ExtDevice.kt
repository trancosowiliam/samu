package br.com.jwk.samu.extension

import android.annotation.SuppressLint
import android.content.Context
import android.telephony.TelephonyManager

@SuppressLint("MissingPermission", "HardwareIds")
@Suppress("DEPRECATION")
fun Context.getDeviceId() : String{
    val telephonyManager =  getSystemService(Context.TELEPHONY_SERVICE) as? TelephonyManager
    return telephonyManager?.deviceId ?: ""
}