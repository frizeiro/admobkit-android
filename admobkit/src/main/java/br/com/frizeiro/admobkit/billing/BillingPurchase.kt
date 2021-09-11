package br.com.frizeiro.admobkit.billing

import com.android.billingclient.api.Purchase

/**
 * Created by Felipe Frizeiro on 31/08/20.
 */
data class BillingPurchase(internal val purchase: Purchase) {

    val skus: List<String>
        get() = purchase.skus

}