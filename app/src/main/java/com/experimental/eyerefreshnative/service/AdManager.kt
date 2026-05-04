package com.experimental.eyerefreshnative.service

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

/**
 * AdMob広告の初期化・読み込み・表示を管理するクラス。
 * 広告ロジックをシングルトンで管理することで、UI層から疎結合に保つ。
 */
object AdManager {
    private const val TAG = "AdManager"

    // テスト用広告ユニットID（Google公式提供のもの）
    private const val REWARDED_AD_UNIT_ID = "ca-app-pub-3940256099942544/5224354917"
    
    // バナー広告のテストIDもここで管理
    const val BANNER_AD_UNIT_ID = "ca-app-pub-3940256099942544/6300978111"

    private var rewardedAd: RewardedAd? = null
    private var isAdLoading = false

    /**
     * アプリ起動時に呼び出し、Mobile Ads SDKを初期化する。
     * ※メインスレッド以外での初期化を推奨するため、バックグラウンド処理を内部で行う。
     */
    fun initialize(context: Context) {
        MobileAds.initialize(context) { status ->
            Log.d(TAG, "AdMob SDK Initialized: $status")
            // 初期化完了後、最初の報酬広告をプリロードしておく
            loadRewardedAd(context)
        }
    }

    /**
     * 報酬広告をバックグラウンドでロードする。
     * ユーザーが広告を見たいと思った時に即座に表示できるよう、プリロードが重要。
     */
    fun loadRewardedAd(context: Context) {
        if (rewardedAd != null || isAdLoading) return

        isAdLoading = true
        val adRequest = AdRequest.Builder().build()
        RewardedAd.load(context, REWARDED_AD_UNIT_ID, adRequest, object : RewardedAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.e(TAG, "Rewarded ad failed to load: ${adError.message}")
                rewardedAd = null
                isAdLoading = false
            }

            override fun onAdLoaded(ad: RewardedAd) {
                Log.d(TAG, "Rewarded ad loaded successfully.")
                rewardedAd = ad
                isAdLoading = false
            }
        })
    }

    /**
     * 報酬広告を表示する。
     * @param activity 広告を表示するためのベースとなるActivity
     * @param onRewardEarned ユーザーが視聴を完了し、報酬を付与すべきタイミングで呼ばれるコールバック
     */
    fun showRewardedAd(activity: Activity, onRewardEarned: (Int) -> Unit) {
        rewardedAd?.let { ad ->
            ad.show(activity) { rewardItem ->
                // 報酬付与ロジックの実行
                val rewardAmount = rewardItem.amount
                Log.d(TAG, "User earned reward: $rewardAmount")
                onRewardEarned(rewardAmount)
                
                // 次の広告のためにクリアと再ロード
                rewardedAd = null
                loadRewardedAd(activity)
            }
        } ?: run {
            Log.w(TAG, "Rewarded ad was not ready.")
            loadRewardedAd(activity)
        }
    }
}
