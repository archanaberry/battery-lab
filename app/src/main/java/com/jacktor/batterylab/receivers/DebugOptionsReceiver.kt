package com.jacktor.batterylab.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.widget.Toast
import androidx.preference.PreferenceManager
import com.jacktor.batterylab.R
import com.jacktor.batterylab.utilities.Constants.DISABLED_DEBUG_OPTIONS_HOST
import com.jacktor.batterylab.utilities.Constants.ENABLED_DEBUG_OPTIONS_HOST
import com.jacktor.batterylab.utilities.preferences.PreferencesKeys.ENABLED_DEBUG_OPTIONS

class DebugOptionsReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val action = intent.action
        val scheme = intent.data?.scheme
        val host = intent.data?.host

        when {

            action == "android.provider.Telephony.SECRET_CODE" && scheme == "android_secret_code"
                    && (host == ENABLED_DEBUG_OPTIONS_HOST || host == DISABLED_DEBUG_OPTIONS_HOST)
            -> {

                val pref = PreferenceManager.getDefaultSharedPreferences(context)

                val isEnabledDebugOptions = pref.getBoolean(ENABLED_DEBUG_OPTIONS,
                    context.resources.getBoolean(R.bool.enabled_debug_options))

                when(host) {

                    ENABLED_DEBUG_OPTIONS_HOST ->
                        if(!isEnabledDebugOptions) enabledDebugOptions(context, pref)

                    DISABLED_DEBUG_OPTIONS_HOST -> if(isEnabledDebugOptions) disabledDebugOptions(
                        context, pref)
                }
            }
        }
    }

    private fun enabledDebugOptions(context: Context, pref: SharedPreferences) {

        pref.edit().putBoolean(ENABLED_DEBUG_OPTIONS, true).apply()

        Toast.makeText(context, context.getString(R.string.debug_successfully_enabled),
            Toast.LENGTH_LONG).show()
    }

    private fun disabledDebugOptions(context: Context, pref: SharedPreferences) {

        pref.edit().remove(ENABLED_DEBUG_OPTIONS).apply()

        Toast.makeText(context, R.string.debug_successfully_disabled,
            Toast.LENGTH_LONG).show()
    }
}