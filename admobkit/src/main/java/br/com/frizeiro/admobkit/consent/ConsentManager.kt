package br.com.frizeiro.admobkit.consent

import android.app.Activity
import android.util.Log
import br.com.frizeiro.admobkit.ads.AdsManager
import br.com.frizeiro.admobkit.extensions.add
import br.com.frizeiro.admobkit.extensions.firstInstallTime
import br.com.frizeiro.admobkit.extensions.preferencesManager
import br.com.frizeiro.admobkit.support.PreferencesManager
import com.facebook.ads.AdSettings
import com.google.android.gms.ads.AdRequest
import com.google.android.ump.ConsentDebugSettings
import com.google.android.ump.ConsentInformation.ConsentStatus.*
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.UserMessagingPlatform
import java.lang.ref.WeakReference
import java.util.*

/**
 * Created by Felipe Frizeiro on 29/08/20.
 */
class ConsentManager(activity: Activity) {

    // region Companion Object

    companion object {
        private const val LAST_CONSENT_TIME = "PREF_LAST_CONSENT"
    }

    // endregion

    // region Public Variables

    val isApplicable: Boolean
        get() = consentInformation.consentStatus == OBTAINED || consentInformation.consentStatus == REQUIRED

    // endregion

    // region Private Variables

    private var activity: WeakReference<Activity> = WeakReference(activity)

    private val consentInformation by lazy { UserMessagingPlatform.getConsentInformation(activity) }
    private var consentInformationUpdated = false

    private val consentResult: Boolean
        get() = consentInformation.consentStatus == OBTAINED || consentInformation.consentStatus == NOT_REQUIRED

    private val consentRequestParameters: ConsentRequestParameters
        get() = consentBuilder().build()

    private val preferences: PreferencesManager?
        get() = activity.get()?.preferencesManager

    // endregion

    // region Public Methods

    fun request(onConsentResult: (Boolean) -> Unit) {
        if (consentInformationUpdated && consentResult) {
            onConsentResult(consentResult)
            return
        }

        val activity = activity.get() ?: return onConsentResult(consentResult)

        if (endOfConsentTime()) {
            reset()
        }

        consentInformation.requestConsentInfoUpdate(activity, consentRequestParameters, {
            consentInformationUpdated = true

            if (consentInformation.isConsentFormAvailable) {
                loadForm(onConsentResult)
            } else {
                onConsentResult(consentResult)
            }
        }, {
            Log.d("Consent", "requestConsentInfoUpdate.error: ${it.message}")
            onConsentResult(consentResult)
        })
    }

    fun reset() {
        consentInformation.reset()
    }

    // endregion

    // region Private Methods

    private fun loadForm(onConsent: (Boolean) -> Unit) {
        val activity = activity.get() ?: return

        UserMessagingPlatform.loadConsentForm(activity, { consentForm ->
            if (consentInformation.consentStatus == REQUIRED) {
                consentForm.show(activity) { error ->
                    if (error != null) {
                        loadForm(onConsent)
                    } else {
                        updateConsentTime()
                        onConsent(consentResult)
                    }
                }
            } else {
                onConsent(consentResult)
            }
        }, {
            Log.d("Consent", "loadConsentForm.error: ${it.message}")
            onConsent(consentResult)
        })
    }

    private fun consentBuilder(): ConsentRequestParameters.Builder {
        val builder = ConsentRequestParameters.Builder()

        val activity = activity.get() ?: return builder
        val config = AdsManager.config ?: return builder

        val debugBuilder = ConsentDebugSettings.Builder(activity)
            .setDebugGeography(config.testConsentGeography.debugGeography)
            .addTestDeviceHashedId(AdRequest.DEVICE_ID_EMULATOR)

        config.testDeviceIDs.admob.forEach {
            debugBuilder.addTestDeviceHashedId(it)
        }

        config.testDeviceIDs.facebook.forEach {
            AdSettings.addTestDevice(it)
        }

        builder.setConsentDebugSettings(debugBuilder.build())

        return builder
    }

    private fun updateConsentTime() {
        preferences?.save(LAST_CONSENT_TIME, Date().time)
    }

    private fun endOfConsentTime(): Boolean {
        val last = preferences?.getLong(LAST_CONSENT_TIME) ?: activity.get()?.firstInstallTime ?: Date().time
        return last <= Date().add(-1, Calendar.YEAR).time
    }

    // endregion

}