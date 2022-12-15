package br.com.frizeiro.admobkit.ads.ui

import android.content.Context
import android.util.AttributeSet
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import br.com.frizeiro.admobkit.databinding.AdmobkitBannerViewBinding
import br.com.frizeiro.admobkit.extensions.viewBinding
import com.google.android.gms.ads.AdView

/**
 * Created by Felipe Frizeiro on 26/06/22.
 */
class AdsBannerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // region Private Variables

    private val binding = viewBinding(AdmobkitBannerViewBinding::inflate)

    // endregion

    // region Public Methods

    fun add(adView: AdView?) = binding.container.addView(adView)

    fun addOnGlobalLayoutListener(Listener: ViewTreeObserver.OnGlobalLayoutListener) =
        binding.container.viewTreeObserver.addOnGlobalLayoutListener(Listener)

    // endregion

}