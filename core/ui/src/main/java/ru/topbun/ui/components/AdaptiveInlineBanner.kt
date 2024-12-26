package ru.topbun.ui.components


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.yandex.mobile.ads.banner.BannerAdSize
import com.yandex.mobile.ads.banner.BannerAdView
import com.yandex.mobile.ads.common.AdRequest
import ru.topbun.ui.BuildConfig

@Composable
fun AdaptiveInlineBanner(
    modifier: Modifier = Modifier,
    maxHeightBanner: Int = 80,
) {
    val context = LocalContext.current
    val width = LocalConfiguration.current.screenWidthDp - 30
    val bannerAdView = BannerAdView(context)

    AndroidView(
        modifier = modifier,
        factory = {
            bannerAdView.apply {
                bannerAdView.setAdUnitId(BuildConfig.BANNER_ID)
                bannerAdView.setAdSize(BannerAdSize.inlineSize(context, width, maxHeightBanner))
                val request = AdRequest.Builder().build()
                bannerAdView.loadAd(request)
            }
        }
    )

}
