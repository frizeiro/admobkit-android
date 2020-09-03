package br.com.frizeiro.admobkit.billing

import com.android.billingclient.api.Purchase

/**
 * Created by Felipe Frizeiro on 31/08/20.
 */
data class BillingPurchase(internal val purchase: Purchase) {

    // TODO: expose other necessary properties.

    val sku: String
        get() = purchase.sku

}