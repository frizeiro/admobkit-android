package br.com.frizeiro.admobkit.billing.extensions

import br.com.frizeiro.admobkit.billing.BillingPurchase
import com.android.billingclient.api.Purchase

/**
 * Created by Felipe Frizeiro on 01/09/20.
 */

@JvmName("containsPurchase")
fun List<Purchase>.contains(sku: String): Boolean {
    return map { it.sku }.contains(sku)
}

@JvmName("containsAnyPurchase")
fun List<Purchase>.containsAny(skus: List<String>): Boolean {
    return map { it.sku }.any { skus.contains(it) }
}

@JvmName("containsBillingPurchase")
fun List<BillingPurchase>.contains(sku: String): Boolean {
    return map { it.sku }.contains(sku)
}

@JvmName("containsAnyBillingPurchase")
fun List<BillingPurchase>.containsAny(skus: List<String>): Boolean {
    return map { it.sku }.any { skus.contains(it) }
}