package br.com.frizeiro.admobkit.ads

import br.com.frizeiro.admobkit.consent.ConsentGeography
import br.com.frizeiro.admobkit.consent.ConsentGeography.DISABLED

/**
 * Created by Felipe Frizeiro on 23/08/20.
 */
data class AdsConfig(
    var testDeviceIDs: List<String>
) {
    var defaultBannerAdId: String? = null
    var defaultInterstitialAdId: String? = null
    var purchaseSkuForRemovingAds: List<String> = listOf()
    var postInstallDelayMinutes: Int = 0
    var maxOfInterstitialPerSession: Int = 999
    var isAllowed: Boolean = true
    var testConsentGeography: ConsentGeography = DISABLED
}