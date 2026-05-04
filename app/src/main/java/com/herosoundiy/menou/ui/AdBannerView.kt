package com.herosoundiy.menou.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.herosoundiy.menou.service.AdManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

/**
 * バナー広告を表示するためのComposableコンポーネント。
 * AndroidViewを使用して、従来のAdViewをCompose内で安全に動作させる。
 */
@Composable
fun AdBannerView() {
    // 広告が表示されるまでのレイアウトのガタつき（Layout Shift）を防ぐため、
    // 標準的なバナーサイズ(50dp)で高さを固定。
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        factory = { context ->
            // Viewの生成
            AdView(context).apply {
                setAdSize(AdSize.BANNER)
                adUnitId = AdManager.BANNER_AD_UNIT_ID
                // 広告リクエストの開始
                loadAd(AdRequest.Builder().build())
            }
        },
        update = { adView ->
            // ライフサイクルに合わせた更新が必要な場合はここで行う
            // Composeの再描画に合わせて広告をロードし直さないよう注意
        },
        onRelease = { adView ->
            // メモリリーク防止のため、リソースの解放
            adView.destroy()
        }
    )
}
