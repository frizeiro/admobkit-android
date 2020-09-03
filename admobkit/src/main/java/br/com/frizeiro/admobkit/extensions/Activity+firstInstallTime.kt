package br.com.frizeiro.admobkit.extensions

import android.app.Activity

/**
 * Created by Felipe Frizeiro on 23/08/20.
 */

//region Internal Methods

internal val Activity.firstInstallTime: Long
    get() {
        try {
            val info = packageManager.getPackageInfo(packageName, 0)
            return info.firstInstallTime
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        return 0L
    }

//endregion
