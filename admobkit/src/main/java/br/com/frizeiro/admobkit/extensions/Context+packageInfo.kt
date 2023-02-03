package br.com.frizeiro.admobkit.extensions

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build

/**
 * Created by Felipe Frizeiro on 23/08/20.
 */

// region Internal Variables

internal val Context.firstInstallTime: Long
    get() = try {
        packageManager.getPackageInfoCompat(packageName).firstInstallTime
    } catch (ex: Exception) {
        0L
    }

// endregion

// region Private Methods

fun PackageManager.getPackageInfoCompat(packageName: String, flags: Int = 0): PackageInfo =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(flags.toLong()))
    } else {
        @Suppress("DEPRECATION") getPackageInfo(packageName, flags)
    }

// endregion
