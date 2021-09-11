package br.com.frizeiro.admobkit.extensions

import android.app.Activity

/**
 * Created by Felipe Frizeiro on 23/08/20.
 */

// region Internal Methods

internal val Activity.firstInstallTime: Long
    get() = try {
        packageManager.getPackageInfo(packageName, 0).firstInstallTime
    } catch (ex: Exception) {
        0L
    }

// endregion
