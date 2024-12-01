package br.com.frizeiro.admobkit.consent

import com.google.android.ump.ConsentDebugSettings.DebugGeography.DEBUG_GEOGRAPHY_DISABLED
import com.google.android.ump.ConsentDebugSettings.DebugGeography.DEBUG_GEOGRAPHY_EEA
import com.google.android.ump.ConsentDebugSettings.DebugGeography.DEBUG_GEOGRAPHY_OTHER
import com.google.android.ump.ConsentDebugSettings.DebugGeography.DEBUG_GEOGRAPHY_REGULATED_US_STATE

/**
 * Created by Felipe Frizeiro on 22/08/20.
 */
enum class ConsentGeography {
    DISABLED,
    EEA,
    REGULATED_US_STATE,
    OTHER,

    @Deprecated("NOT_EEA it's now deprecated. Please, use OTHER instead.", ReplaceWith("OTHER"))
    NOT_EEA;

    // region Public Variables

    val debugGeography by lazy {
        when (this) {
            DISABLED -> DEBUG_GEOGRAPHY_DISABLED
            EEA -> DEBUG_GEOGRAPHY_EEA
            REGULATED_US_STATE -> DEBUG_GEOGRAPHY_REGULATED_US_STATE
            OTHER -> DEBUG_GEOGRAPHY_OTHER
            NOT_EEA -> DEBUG_GEOGRAPHY_OTHER
        }
    }

    // endregion

}