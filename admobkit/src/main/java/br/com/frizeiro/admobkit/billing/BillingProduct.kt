package br.com.frizeiro.admobkit.billing

import com.android.billingclient.api.SkuDetails

/**
 * Created by Felipe Frizeiro on 31/08/20.
 */
data class BillingProduct(internal val skuDetails: SkuDetails) {

    // TODO: expose other necessary properties.

    val sku: String
        get() = skuDetails.sku

}