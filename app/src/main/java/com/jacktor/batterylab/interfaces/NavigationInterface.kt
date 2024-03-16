package com.jacktor.batterylab.interfaces

import android.os.BatteryManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.jacktor.batterylab.MainActivity
import com.jacktor.batterylab.R
import com.jacktor.batterylab.fragments.AboutFragment
import com.jacktor.batterylab.fragments.BackupSettingsFragment
import com.jacktor.batterylab.fragments.BatteryStatusInformationFragment
import com.jacktor.batterylab.fragments.ChargeDischargeFragment
import com.jacktor.batterylab.fragments.DebugFragment
import com.jacktor.batterylab.fragments.FeedbackFragment
import com.jacktor.batterylab.fragments.HistoryFragment
import com.jacktor.batterylab.fragments.KernelFragment
import com.jacktor.batterylab.fragments.OverlayFragment
import com.jacktor.batterylab.fragments.PowerConnectionSettingsFragment
import com.jacktor.batterylab.fragments.SettingsFragment
import com.jacktor.batterylab.helpers.BatteryLevelHelper
import com.jacktor.batterylab.interfaces.PremiumInterface.Companion.isPremium

interface NavigationInterface : BatteryInfoInterface {

    fun MainActivity.bottomNavigation(status: Int) {

        navigation.menu.findItem(R.id.charge_discharge_navigation).title = getString(
            if (status == BatteryManager.BATTERY_STATUS_CHARGING) R.string.charging
            else R.string.discharge
        )

        navigation.menu.findItem(R.id.charge_discharge_navigation).icon = ContextCompat.getDrawable(
            this,
            BatteryLevelHelper.batteryLevelIcon(
                getBatteryLevel(
                    this
                ),
                status == BatteryManager.BATTERY_STATUS_CHARGING
            )
        )

        navigation.setOnItemSelectedListener {

            when (it.itemId) {

                R.id.charge_discharge_navigation -> {

                    if (fragment !is ChargeDischargeFragment) {

                        fragment = ChargeDischargeFragment()

                        topAppBar.navigationIcon = null

                        MainActivity.isLoadChargeDischarge = true

                        MainActivity.isLoadKernel = false

                        MainActivity.isLoadHistory = false

                        MainActivity.isLoadSettings = false

                        MainActivity.isLoadDebug = false

                        clearMenu()

                        inflateMenu()

                        loadFragment(fragment ?: ChargeDischargeFragment())

                        if (!isPremium) MainActivity.instance?.showAds()
                    }
                }

                R.id.kernel_navigation -> {

                    if (fragment !is KernelFragment) {

                        fragment = KernelFragment()

                        topAppBar.title = getString(R.string.kernel)

                        topAppBar.navigationIcon = null

                        MainActivity.isLoadChargeDischarge = false

                        MainActivity.isLoadKernel = true

                        MainActivity.isLoadHistory = false

                        MainActivity.isLoadSettings = false

                        MainActivity.isLoadDebug = false

                        clearMenu()

                        inflateMenu()

                        loadFragment(fragment ?: KernelFragment())

                        if (!isPremium) MainActivity.instance?.showAds()
                    }
                }

                R.id.history_navigation -> {

                    if (fragment !is HistoryFragment) {

                        fragment = HistoryFragment()

                        topAppBar.title = getString(R.string.history)

                        topAppBar.navigationIcon = null

                        MainActivity.isLoadChargeDischarge = false

                        MainActivity.isLoadKernel = false

                        MainActivity.isLoadHistory = true

                        MainActivity.isLoadSettings = false

                        MainActivity.isLoadDebug = false

                        clearMenu()

                        inflateMenu()

                        loadFragment(fragment ?: HistoryFragment())

                        if (!isPremium) MainActivity.instance?.showAds()
                    }
                }

                R.id.settings_navigation -> {

                    when (fragment) {

                        null, is ChargeDischargeFragment, is KernelFragment, is HistoryFragment -> {

                            fragment = SettingsFragment()

                            topAppBar.title = getString(R.string.settings)

                            topAppBar.navigationIcon = null

                            MainActivity.isLoadChargeDischarge = false

                            MainActivity.isLoadKernel = false

                            MainActivity.isLoadSettings = true

                            MainActivity.isLoadDebug = false

                            clearMenu()

                            loadFragment(fragment ?: SettingsFragment())

                            if (!isPremium) MainActivity.instance?.showAds()
                        }
                    }
                }
            }

            true
        }
    }

    fun MainActivity.loadFragment(fragment: Fragment, isAddToBackStack: Boolean = false) {

        supportFragmentManager.beginTransaction().apply {

            replace(R.id.fragment_container, fragment)
            if (isAddToBackStack) addToBackStack(null)

            if (!MainActivity.isRecreate || fragment is ChargeDischargeFragment || fragment is KernelFragment)
                commit()
        }

        when {

            fragment !is BatteryStatusInformationFragment && fragment !is PowerConnectionSettingsFragment && fragment !is OverlayFragment
                    && fragment !is AboutFragment && fragment !is DebugFragment
                    && fragment !is FeedbackFragment && fragment !is BackupSettingsFragment -> {

                navigation.selectedItemId = when (fragment) {

                    is ChargeDischargeFragment -> R.id.charge_discharge_navigation
                    is KernelFragment -> R.id.kernel_navigation
                    is HistoryFragment -> R.id.history_navigation
                    is SettingsFragment -> R.id.settings_navigation
                    else -> R.id.charge_discharge_navigation
                }
            }

            else -> {

                navigation.selectedItemId = R.id.settings_navigation

                clearMenu()

                topAppBar.navigationIcon = ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_arrow_back_24dp
                )
            }
        }
    }
}