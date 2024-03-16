package com.jacktor.batterylab.helpers

import com.jacktor.batterylab.R

object BatteryLevelHelper {

    fun batteryLevelIcon(batteryLevel: Int?, isCharge: Boolean): Int {
        if(isCharge)
            return when(batteryLevel) {

                in 0..29 -> R.drawable.ic_charge_navigation_20_24dp
                in 30..49 -> R.drawable.ic_charge_navigation_30_24dp
                in 50..59 -> R.drawable.ic_charge_navigation_50_24dp
                in 60..79 -> R.drawable.ic_charge_navigation_60_24dp
                in 80..89 -> R.drawable.ic_charge_navigation_80_24dp
                in 90..95 -> R.drawable.ic_charge_navigation_90_24dp
                else -> R.drawable.ic_charge_navigation_full_24dp
            }

        else return when(batteryLevel) {

            in 0..9 -> R.drawable.ic_discharge_navigation_9_24dp
            in 10..29 -> R.drawable.ic_discharge_navigation_20_24dp
            in 30..49 -> R.drawable.ic_discharge_navigation_30_24dp
            in 50..59 -> R.drawable.ic_discharge_navigation_50_24dp
            in 60..79 -> R.drawable.ic_discharge_navigation_60_24dp
            in 80..89 -> R.drawable.ic_discharge_navigation_80_24dp
            in 90..95 -> R.drawable.ic_discharge_navigation_90_24dp
            else -> R.drawable.ic_discharge_navigation_full_24dp
        }
    }
}