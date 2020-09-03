package br.com.frizeiro.admobkit.billing

/**
 * Created by Felipe Frizeiro on 31/08/20.
 */
abstract class BillingConnectionListener {

    abstract fun onSuccess()

    fun onDisconnected() {}
    fun response(code: Int) {}

}