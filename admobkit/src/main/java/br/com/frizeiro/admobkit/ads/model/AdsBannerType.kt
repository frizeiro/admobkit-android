package br.com.frizeiro.admobkit.ads.model

import android.app.Activity
import android.content.res.Resources
import android.widget.FrameLayout
import br.com.frizeiro.admobkit.R
import br.com.frizeiro.admobkit.ads.ui.AdsBannerView
import com.google.android.gms.ads.AdSize

/**
 * Created by Felipe Frizeiro on 23/08/20.
 */
enum class AdsBannerType {

    BANNER,
    ADAPTIVE;

    // region Public Methods

    fun size(adsBannerView: AdsBannerView, activity: Activity): AdSize {
        return when (this) {
            BANNER -> AdSize.BANNER
            ADAPTIVE -> adaptiveSize(adsBannerView, activity)
        }
    }

    // endregion

    // region Private Methods

    private fun adaptiveSize(adsBannerView: AdsBannerView, activity: Activity): AdSize {
        val container = adsBannerView.findViewById<FrameLayout>(R.id.container)
        val metrics = Resources.getSystem().displayMetrics

        var adWidthPixels = container.width.toFloat()
        if (adWidthPixels == 0f) {
            adWidthPixels = metrics.widthPixels.toFloat()
        }

        val adWidth = (adWidthPixels / metrics.density).toInt()

        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, adWidth)
    }

    // endregion

}
