package br.com.frizeiro.admobkitexample

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import br.com.frizeiro.admobkit.ads.AdsInitializeListener
import br.com.frizeiro.admobkit.ads.AdsManager
import br.com.frizeiro.admobkit.ads.model.AdsConfig
import br.com.frizeiro.admobkit.ads.model.AdsDeviceIDs
import br.com.frizeiro.admobkit.ads.ui.AdsBannerView
import br.com.frizeiro.admobkit.billing.BillingManager
import br.com.frizeiro.admobkit.billing.BillingPurchase
import br.com.frizeiro.admobkit.billing.PurchaseListener
import br.com.frizeiro.admobkit.billing.extensions.contains
import br.com.frizeiro.admobkit.consent.ConsentGeography.EEA

class MainActivity : AppCompatActivity() {

    // region Private Variables

    private val purchasedSku by lazy { getString(R.string.admobkit_purchased_sku) }

    private val adsManager: AdsManager = AdsManager(this)
    private val billingManager: BillingManager = BillingManager(this)

    private var billingPurchases: List<BillingPurchase>? = null
        set(newValue) {
            setupPurchaseButtons(field, newValue)
            field = newValue
        }

    // endregion

    // region Private Views

    private val bannerView by lazy { findViewById<AdsBannerView>(R.id.ad_banner_view) }
    private val showInterstitialAdButton: Button by lazy { findViewById(R.id.show_interstitial_ad_button) }
    private val revokeConsentButton: Button by lazy { findViewById(R.id.revoke_consent_button) }
    private val removeAdsButton: Button by lazy { findViewById(R.id.remove_ads_button) }
    private val consumePurchaseButton: Button by lazy { findViewById(R.id.consume_purchase_button) }

    // endregion

    // region Life Cycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupButtons()
        setupAdMobKit()
    }

    // endregion

    // region Private Methods

    private fun setupAdMobKit() {
        // purchaseListener
        billingManager.purchaseListener = object : PurchaseListener {
            override fun onResult(purchases: List<BillingPurchase>, pending: List<BillingPurchase>) {
                billingPurchases = purchases
            }
        }

        // configs
        val ids = AdsDeviceIDs(
            admob = listOf(
                "0F1B54AE43758E24205DFECFFD517AF5" // Redmi
            ),
            facebook = listOf(
                "0e2b59ed-9ede-45ef-bfe5-1618f9188b12" // Redmi
            )
        )

        val config = AdsConfig(testDeviceIDs = ids)
        config.defaultBannerAdId = getString(R.string.admobkit_banner_id)
        config.defaultInterstitialAdId = getString(R.string.admobkit_interstitial_id)
        config.purchaseSkuForRemovingAds = listOf(purchasedSku)
        config.testConsentGeography = EEA

        // initialize
        AdsManager.initialize(this, config, object : AdsInitializeListener() {
            override fun onInitialize() {
                adsManager.loadBanner(bannerView)

                adsManager.loadInterstitial {
                    showInterstitialAdButton.isEnabled = true
                }
            }

            override fun always() {
                revokeConsentButton.isEnabled = adsManager.isConsentApplicable
                billingManager.queryPurchases()
            }
        })
    }

    private fun setupButtons() {
        showInterstitialAdButton.setOnClickListener {
            adsManager.showInterstitial()
        }

        revokeConsentButton.setOnClickListener {
            adsManager.resetConsent()
        }

        removeAdsButton.setOnClickListener {
            billingManager.launchBillingFlow(purchasedSku)
        }

        consumePurchaseButton.setOnClickListener {
            billingPurchases?.firstOrNull()?.let {
                billingManager.consume(it)
            }
        }
    }

    private fun setupPurchaseButtons(oldPurchases: List<BillingPurchase>?, newPurchases: List<BillingPurchase>?) {
        val adsRemoved = newPurchases?.contains(purchasedSku) ?: false

        oldPurchases?.let {
            val alreadyContains = it.contains(purchasedSku)

            if ((adsRemoved && !alreadyContains) || (!adsRemoved && alreadyContains)) {
                restartActivity()
            }
        }
    }

    private fun restartActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    // endregion

}