package br.com.frizeiro.admobkit.ads.model

import br.com.frizeiro.admobkit.consent.ConsentGeography
import br.com.frizeiro.admobkit.consent.ConsentGeography.NOT_EEA

/**
 * Created by Felipe Frizeiro on 23/08/20.
 */
data class AdsConfig(
    var testDeviceIDs: AdsDeviceIDs,
) {
    var defaultBannerAdId: String? = null
    var defaultInterstitialAdId: String? = null
    var purchaseSkuForRemovingAds: List<String> = listOf()
    var postInstallDelayMinutes: Int = 0
    var maxOfInterstitialPerSession: Int = 999
    var isAllowed: Boolean = true
    var testConsentGeography: ConsentGeography = NOT_EEA
}