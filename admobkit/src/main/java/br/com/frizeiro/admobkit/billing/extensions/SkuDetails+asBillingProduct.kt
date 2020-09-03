package br.com.frizeiro.admobkit.billing.extensions

import br.com.frizeiro.admobkit.billing.BillingProduct
import com.android.billingclient.api.SkuDetails

/**
 * Created by Felipe Frizeiro on 01/09/20.
 */
val SkuDetails.asBillingProduct: BillingProduct
    get() = BillingProduct(this)

val List<SkuDetails>.asBillingProducts: List<BillingProduct>
    get() = map { it.asBillingProduct }