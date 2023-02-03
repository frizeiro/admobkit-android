package br.com.frizeiro.admobkit.extensions

import java.util.*

/**
 * Created by Felipe Frizeiro on 02/02/23.
 */

// region Internal Methods

internal fun Date.add(value: Int, option: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.add(option, value)
    return calendar.time
}

// endregion