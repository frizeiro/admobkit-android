package br.com.frizeiro.admobkitexample

import android.content.Intent
import android.os.Bundle
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

    private val purchasedSku by lazy { getString(R.string.admobkit_purchased_sku) }

    private lateinit var banner: FrameLayout

    private val adsManager: AdsManager = AdsManager(this)
    private val billingManager: BillingManager = BillingManager(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        setupAdMob()
        setupBillingManager()

        // TODO: implement example button's action
    }

    private fun setupAdMob() {
        banner = findViewById(R.id.adContainer)

        val config = AdsConfig(
            listOf("AAE412509C1012E2A5242D58CCE0EB14")
        )

        config.defaultBannerAdId = getString(R.string.admobkit_banner_id)
        config.defaultInterstitialAdId = getString(R.string.admobkit_interstitial_id)
        config.purchaseSkuForRemovingAds = listOf(purchasedSku)
        config.testConsentGeography = NOT_EEA

        AdsManager.initialize(this, config, object : AdsInitializeListener {
            override fun onInitialize() {
                adsManager.loadBanner(banner)
                adsManager.loadInterstitial()
            }
        })
    }

    private fun setupBillingManager() {
        billingManager.purchaseListener = object : PurchaseListener {
            override fun onResult(purchases: List<BillingPurchase>, pending: List<BillingPurchase>) {
                if (purchases.contains(purchasedSku)) {
                    Intent(this@MainActivity, MainActivity::class.java)
                    finish()
                }
            }
        }
    }

}