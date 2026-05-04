package com.experimental.eyerefreshnative

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import com.experimental.eyerefreshnative.R
import com.experimental.eyerefreshnative.constants.AppConstants
import com.experimental.eyerefreshnative.service.AdManager
import com.experimental.eyerefreshnative.service.EyeGuardService
import com.experimental.eyerefreshnative.ui.*

/**
 * メインアクティビティ。
 * トレーニング画面の切り替えと、フォアグラウンドサービスのライフサイクルを制御。
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // --- AdMob Initialization ---
        // アプリ起動直後にSDKを初期化。広告のプリロードを裏で開始させる。
        AdManager.initialize(this)

        // サービスを開始
        startForegroundService(Intent(this, EyeGuardService::class.java))

        setContent {
            var currentScreen by remember { mutableStateOf(0) }

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = AppConstants.BACKGROUND_DARK
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    // トレーニング画面の表示
                    Box(modifier = Modifier.weight(1f)) {
                        when (currentScreen) {
                            0 -> GaborPatchScreen()
                            1 -> StereogramScreen()
                            2 -> EyeMovementScreen()
                        }
                    }

                    // 広告表示エリア（トレーニングとナビゲーションの間）
                    // プレースホルダーとして50dpの高さを確保済み
                    AdBannerView()

                    // ナビゲーションボタン（シンプルかつ余白を持たせた設計）
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = { currentScreen = 0 },
                            colors = ButtonDefaults.buttonColors(containerColor = AppConstants.ACCENT_MINT)
                        ) {
                            Text("Gabor", color = AppConstants.BACKGROUND_DARK)
                        }
                        // 報酬広告の呼び出し例（特別な機能を解放する場合を想定）
                        Button(
                            onClick = { 
                                AdManager.showRewardedAd(this@MainActivity) { amount ->
                                    // 報酬付与ロジックをここに記述
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = AppConstants.ACCENT_MINT)
                        ) {
                            Text("Reward", color = AppConstants.BACKGROUND_DARK)
                        }
                        Button(
                            onClick = { currentScreen = 2 },
                            colors = ButtonDefaults.buttonColors(containerColor = AppConstants.ACCENT_MINT)
                        ) {
                            Text("Move", color = AppConstants.BACKGROUND_DARK)
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        // アクティビティ終了時にサービスを停止（要件により調整）
        stopService(Intent(this, EyeGuardService::class.java))
        super.onDestroy()
    }
}
