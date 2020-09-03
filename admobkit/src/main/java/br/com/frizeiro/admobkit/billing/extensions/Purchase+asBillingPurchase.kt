package br.com.frizeiro.admobkit.billing.extensions

import br.com.frizeiro.admobkit.billing.BillingPurchase
import com.android.billingclient.api.Purchase

/**
 * Created by Felipe Frizeiro on 01/09/20.
 */
val Purchase.asBillingPurchase: BillingPurchase
    get() = BillingPurchase(this)

val List<Purchase>.asBillingPurchases: List<BillingPurchase>
    get() = map { it.asBillingPurchase }