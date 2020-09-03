package br.com.frizeiro.admobkit.billing

/**
 * Created by Felipe Frizeiro on 31/08/20.
 */
interface PurchaseListener {

    fun onResult(purchases: List<BillingPurchase>, pending: List<BillingPurchase>)

}