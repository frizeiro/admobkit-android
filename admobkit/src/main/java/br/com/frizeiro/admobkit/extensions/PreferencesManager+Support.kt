package br.com.frizeiro.admobkit.extensions

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import br.com.frizeiro.admobkit.support.PreferencesManager

/**
 * Created by Felipe Frizeiro on 02/02/23.
 */

// region Internal Variables

internal val Activity.preferencesManager: PreferencesManager
    get() = PreferencesManager(this)

internal val Fragment.preferencesManager: PreferencesManager
    get() = PreferencesManager(requireContext())

internal val Context.preferencesManager: PreferencesManager
    get() = PreferencesManager(this)

// endregion
