package com.jacktor.batterylab.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.preference.PreferenceManager
import com.jacktor.batterylab.helpers.ServiceHelper
import com.jacktor.batterylab.interfaces.NotificationInterface
import com.jacktor.batterylab.utilities.PreferencesKeys

class DisableNotificationBatteryStatusInformationService : Service() {

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        disable()

        ServiceHelper.stopService(this,
            DisableNotificationBatteryStatusInformationService::class.java)

        return START_NOT_STICKY
    }

    private fun disable() {

        val pref = PreferenceManager.getDefaultSharedPreferences(this)

        with(pref.edit()) {

            when {

                NotificationInterface.isOverheatOvercool -> putBoolean(
                    PreferencesKeys.NOTIFY_OVERHEAT_OVERCOOL, false).apply()

                NotificationInterface.isBatteryFullyCharged -> putBoolean(
                    PreferencesKeys.NOTIFY_BATTERY_IS_FULLY_CHARGED, false).apply()

                NotificationInterface.isBatteryCharged -> putBoolean(
                    PreferencesKeys.NOTIFY_BATTERY_IS_CHARGED, false).apply()

                NotificationInterface.isBatteryDischarged -> putBoolean(
                    PreferencesKeys.NOTIFY_BATTERY_IS_DISCHARGED, false).apply()

                NotificationInterface.isBatteryDischargedVoltage -> putBoolean(
                    PreferencesKeys.NOTIFY_BATTERY_IS_DISCHARGED_VOLTAGE, false).apply()

            }
        }

        if(NotificationInterface.isBatteryFullyCharged)
            NotificationInterface.notificationManager?.cancel(NotificationInterface
                .NOTIFICATION_FULLY_CHARGED_ID)

        if(NotificationInterface.isOverheatOvercool) {
            NotificationInterface.notificationManager?.cancel(NotificationInterface
                .NOTIFICATION_BATTERY_OVERHEAT_OVERCOOL_ID)
            return
        }

        NotificationInterface.notificationManager?.cancel(NotificationInterface
            .NOTIFICATION_BATTERY_STATUS_ID)
    }
}