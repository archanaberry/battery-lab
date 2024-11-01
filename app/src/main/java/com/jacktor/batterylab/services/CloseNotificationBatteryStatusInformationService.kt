package com.jacktor.batterylab.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.jacktor.batterylab.helpers.ServiceHelper
import com.jacktor.batterylab.interfaces.NotificationInterface

class CloseNotificationBatteryStatusInformationService : Service() {

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

       close()

        ServiceHelper.stopService(this,
            CloseNotificationBatteryStatusInformationService::class.java)

        return START_NOT_STICKY
    }

    private fun close() {

        if(NotificationInterface.isOverheatOvercool) {
            NotificationInterface.notificationManager?.cancel(NotificationInterface
                .NOTIFICATION_BATTERY_OVERHEAT_OVERCOOL_ID)
            return
        }
        
        NotificationInterface.notificationManager?.cancel(NotificationInterface
            .NOTIFICATION_BATTERY_STATUS_ID)
    }
}