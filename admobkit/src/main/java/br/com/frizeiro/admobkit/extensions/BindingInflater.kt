package br.com.frizeiro.admobkit.extensions

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

/**
 * Created by Felipe Frizeiro on 04/11/2022.
 */

// region Public Alias

internal typealias BindingInflater<T> = (layoutInflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean) -> T

// endregion

// region Public Methods

internal fun <T : ViewBinding> ViewGroup.viewBinding(
    factory: BindingInflater<T>,
    attachToParent: Boolean = true
): T = factory.invoke(LayoutInflater.from(context), this, attachToParent)

internal fun <T : ViewBinding> Context.viewBinding(
    factory: (layoutInflater: LayoutInflater) -> T
): T = factory.invoke(LayoutInflater.from(this))

internal fun <T : ViewBinding> View.viewBinding(
    factory: (View) -> T
) = factory.invoke(this)

// endregion