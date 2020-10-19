package br.com.frizeiro.admobkitexample

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import br.com.frizeiro.admobkit.ads.AdsConfig
import br.com.frizeiro.admobkit.ads.AdsInitializeListener
import br.com.frizeiro.admobkit.ads.AdsManager
import br.com.frizeiro.admobkit.billing.BillingManager
import br.com.frizeiro.admobkit.billing.BillingPurchase
import br.com.frizeiro.admobkit.billing.PurchaseListener
import br.com.frizeiro.admobkit.billing.extensions.contains
import br.com.frizeiro.admobkit.consent.ConsentGeography.NOT_EEA

class MainActivity : AppCompatActivity() {

    //region Private Variables

    private val purchasedSku by lazy { getString(R.string.admobkit_purchased_sku) }

    private val adsManager: AdsManager = AdsManager(this)
    private val billingManager: BillingManager = BillingManager(this)

    private var billingPurchases: List<BillingPurchase>? = null
        set(newValue) {
            setupPurchaseButtons(field, newValue)
            field = newValue
        }

    //endregion

    //region Private Views

    private val bannerView: FrameLayout by lazy { findViewById(R.id.ad_banner_view) }

    private val showInterstitialAdButton: Button by lazy { findViewById(R.id.show_interstitial_ad_button) }
    private val revokeConsentButton: Button by lazy { findViewById(R.id.revoke_consent_button) }
    private val removeAdsButton: Button by lazy { findViewById(R.id.remove_ads_button) }
    private val consumePurchaseButton: Button by lazy { findViewById(R.id.consume_purchase_button) }

    //endregion

    //region Life Cycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupButtons()
        setupAdMobKit()
    }

    //endregion

    //region Private Methods

    private fun setupAdMobKit() {
        // purchaseListener
        billingManager.purchaseListener = object : PurchaseListener {
            override fun onResult(purchases: List<BillingPurchase>, pending: List<BillingPurchase>) {
                billingPurchases = purchases
            }
        }

        // configs
        val config = AdsConfig(listOf("AAE412509C1012E2A5242D58CCE0EB14"))
        config.defaultBannerAdId = getString(R.string.admobkit_banner_id)
        config.defaultInterstitialAdId = getString(R.string.admobkit_interstitial_id)
        config.purchaseSkuForRemovingAds = listOf(purchasedSku)
        config.testConsentGeography = NOT_EEA

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

    //endregion

}