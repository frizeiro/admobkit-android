package br.com.frizeiro.admobkit.ads

import android.app.Activity
import android.content.res.Resources
import android.widget.FrameLayout
import com.google.android.gms.ads.AdSize

/**
 * Created by Felipe Frizeiro on 23/08/20.
 */
enum class AdsBannerType {
    BANNER,
    ADAPTIVE;

    //region Public Methods

    fun size(containerView: FrameLayout, activity: Activity): AdSize {
        return when (this) {
            BANNER -> AdSize.BANNER
            ADAPTIVE -> adaptiveSize(containerView, activity)
        }
    }

    //endregion

    //region Private Methods

    private fun adaptiveSize(containerView: FrameLayout, activity: Activity): AdSize {
        val metrics = Resources.getSystem().displayMetrics

        var adWidthPixels = containerView.width.toFloat()
        if (adWidthPixels == 0f) {
            adWidthPixels = metrics.widthPixels.toFloat()
        }

        val adWidth = (adWidthPixels / metrics.density).toInt()

        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, adWidth)
    }

    //endregion

}
