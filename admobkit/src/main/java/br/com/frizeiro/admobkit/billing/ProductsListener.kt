package br.com.frizeiro.admobkit.billing

/**
 * Created by Felipe Frizeiro on 01/09/20.
 */
interface ProductsListener {

    fun onResult(products: List<BillingProduct>)

}