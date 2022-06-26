package br.com.frizeiro.admobkit.ads.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import br.com.frizeiro.admobkit.R
import com.google.android.gms.ads.AdView
import kotlinx.android.synthetic.main.admobkit_banner_view.view.*

/**
 * Created by Felipe Frizeiro on 26/06/22.
 */
class AdsBannerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    // region Constructors

    init {
        LayoutInflater.from(context).inflate(R.layout.admobkit_banner_view, this)
    }

    // endregion

    // region Public Methods

    fun add(adView: AdView?) = container.addView(adView)

    fun addOnGlobalLayoutListener(Listener: ViewTreeObserver.OnGlobalLayoutListener) =
        container.viewTreeObserver.addOnGlobalLayoutListener(Listener)

    // endregion

}