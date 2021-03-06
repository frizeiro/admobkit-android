package br.com.frizeiro.admobkit.ads

import android.app.Activity
import android.widget.FrameLayout
import br.com.frizeiro.admobkit.ads.AdsBannerType.ADAPTIVE
import br.com.frizeiro.admobkit.billing.BillingManager
import br.com.frizeiro.admobkit.billing.BillingPurchase
import br.com.frizeiro.admobkit.billing.PurchaseListener
import br.com.frizeiro.admobkit.billing.extensions.containsAny
import br.com.frizeiro.admobkit.consent.ConsentManager
import br.com.frizeiro.admobkit.extensions.firstInstallTime
import com.google.android.gms.ads.*
import java.lang.ref.WeakReference

/**
 * Created by Felipe Frizeiro on 29/08/20.
 */

class AdsManager(activity: Activity) {

    //region Public Variables

    val isConsentApplicable: Boolean
        get() = consentManager.isApplicable

    val isAllowed: Boolean
        get() = isInitialized && isAllowed(activity.get())

    //endregion

    //region Private Variables

    private var activity: WeakReference<Activity> = WeakReference(activity)

    private val consentManager by lazy { ConsentManager(activity) }

    private var bannerLayoutComplete: Boolean = false
    private var bannerAdView: AdView? = null

    private var interstitialAd: InterstitialAd? = null

    private val adRequest: AdRequest
        get() = AdRequest.Builder().build()

    //endregion

    //region Public Methods

    @JvmOverloads
    fun loadBanner(containerView: FrameLayout, adUnitId: String? = null, type: AdsBannerType = ADAPTIVE) {
        // Reference: https://developers.google.com/admob/android/banner

        if (!isInitialized) {
            return
        }

        val activity = activity.get() ?: return

        bannerAdView = AdView(activity)
        bannerAdView?.adUnitId = adUnitId ?: config?.defaultBannerAdId

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            bannerAdView?.setBackgroundColor(activity.getColor(android.R.color.transparent))
        } else {
            bannerAdView?.setBackgroundColor(activity.resources.getColor(android.R.color.transparent))
        }

        containerView.addView(bannerAdView)

        // wait until containerView is laid out before we can get the width.
        containerView.viewTreeObserver.addOnGlobalLayoutListener {
            if (!bannerLayoutComplete) {
                bannerLayoutComplete = true

                bannerAdView?.adSize = type.size(containerView, activity)
                bannerAdView?.loadAd(adRequest)
            }
        }
    }

    @JvmOverloads
    fun loadInterstitial(adUnitId: String? = null, onLoad: (() -> Unit)? = null) {
        // Reference: https://developers.google.com/admob/android/interstitial

        if (!isInitialized || interstitialAd?.isLoaded == true) {
            return
        }

        val activity = activity.get() ?: return

        interstitialAd = InterstitialAd(activity)
        interstitialAd?.adUnitId = adUnitId ?: config?.defaultInterstitialAdId

        interstitialAd?.adListener = object : AdListener() {
            override fun onAdLoaded() {
                onLoad?.invoke()
            }

            override fun onAdOpened() {
                numberOfInterstitialDisplayed++
            }

            override fun onAdClosed() {
                interstitialAd?.loadAd(adRequest)
            }
        }

        interstitialAd?.loadAd(adRequest)
    }

    fun showInterstitial() {
        if (isInitialized && numberOfInterstitialDisplayed < (config?.maxOfInterstitialPerSession
                ?: 0) && interstitialAd?.isLoaded == true
        ) {
            interstitialAd?.show()
        }
    }

    fun pause() {
        bannerAdView?.pause()
    }

    fun resume() {
        bannerAdView?.resume()
    }

    fun destroy() {
        bannerAdView?.destroy()
    }

    fun resetConsent() {
        pause()
        consentManager.reset()
        consentManager.request {
            if (it) {
                resume()
            }
        }
    }

    //endregion

    //region Companion Object

    companion object {

        internal var config: AdsConfig? = null

        private var isInitialized = false
        private var numberOfInterstitialDisplayed: Int = 0

        @JvmStatic
        fun initialize(activity: Activity, config: AdsConfig, listener: AdsInitializeListener) {
            Companion.config = config
            setDebugConfiguration()

            if (!config.isAllowed || !isAllowed(activity)) {
                listener.onFail("Ads is not allowed.")
                listener.always()
                return
            }

            if (isInitialized) {
                listener.onInitialize()
                listener.always()
                return
            }

            val skus = Companion.config?.purchaseSkuForRemovingAds ?: listOf()
            if (skus.isNotEmpty()) {
                performQueryPurchases(activity, listener)
            } else {
                performConsent(activity, listener)
            }
        }

        private fun performQueryPurchases(activity: Activity, listener: AdsInitializeListener) {
            val billingManager = BillingManager(activity)

            billingManager.purchaseListener = object : PurchaseListener {
                override fun onResult(purchases: List<BillingPurchase>, pending: List<BillingPurchase>) {
                    val skus = config?.purchaseSkuForRemovingAds ?: listOf()
                    if (!purchases.containsAny(skus)) {
                        performConsent(activity, listener)
                    } else {
                        listener.onFail("There are some purchases for removing ads.")
                        listener.always()
                    }
                }

            }

            billingManager.queryPurchases()
        }

        private fun performConsent(activity: Activity, listener: AdsInitializeListener) {
            ConsentManager(activity).request {
                if (it) {
                    performInitializeAds(activity, listener)
                } else {
                    listener.onFail("User data consent could't be requested.")
                    listener.always()
                }
            }
        }

        private fun performInitializeAds(activity: Activity, listener: AdsInitializeListener) {
            MobileAds.initialize(activity) {
                isInitialized = it.adapterStatusMap.entries.any { entry -> entry.value.initializationState.name == "READY" }

                if (isInitialized) {
                    MobileAds.setAppMuted(true)
                    listener.onInitialize()
                } else {
                    val first = it.adapterStatusMap.entries.firstOrNull()?.value
                    listener.onFail(first?.description ?: first?.initializationState?.name ?: "Ads initialization fail.")
                }
                listener.always()
            }
        }

        private fun setDebugConfiguration() {
            val devices = mutableListOf(AdRequest.DEVICE_ID_EMULATOR)

            config?.testDeviceIDs?.let {
                devices.addAll(it)
            }

            MobileAds.setRequestConfiguration(
                RequestConfiguration.Builder()
                    .setTestDeviceIds(devices)
                    .build()
            )
        }

        private fun isAllowed(activity: Activity?): Boolean {
            val postMilliseconds = (config?.postInstallDelayMinutes ?: 0) * 60 * 1000
            val installTime = activity?.firstInstallTime ?: 0

            return (postMilliseconds + installTime) < System.currentTimeMillis()
        }
    }

    //endregion

}