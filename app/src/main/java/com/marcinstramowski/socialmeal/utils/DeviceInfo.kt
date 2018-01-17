package com.marcinstramowski.socialmeal.utils

import android.content.Context
import android.provider.Settings
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Provides information about current device on which our app is installed
 */
@Singleton
data class DeviceInfo(val deviceId: String, val deviceName: String) {

    @Inject constructor  (context: Context) : this(
        deviceId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID),
        deviceName = android.os.Build.MODEL
    )
}