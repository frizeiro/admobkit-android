package br.com.frizeiro.admobkit.ads

/**
 * Created by Felipe Frizeiro on 23/08/20.
 */
abstract class AdsInitializeListener {

    abstract fun onInitialize()

    open fun onFail(message: String) {}
    open fun always() {}

}