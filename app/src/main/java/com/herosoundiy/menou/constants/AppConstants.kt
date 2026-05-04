package com.herosoundiy.menou.constants

import androidx.compose.ui.graphics.Color

/**
 * アプリ全体で使用する定数管理クラス。
 * 「目に優しい設計指針」に基づき、コントラストを抑えた配色や滑らかなアニメーション時間を定義。
 */
object AppConstants {

    // --- Color Palette (Eye-Friendly) ---
    // 純粋な黒(#000000)を避け、視覚的な負担を軽減する深い緑を採用
    val BACKGROUND_DARK = Color(0xFF121B14)
    
    // 純粋な白(#FFFFFF)を避け、眩しさを抑えたソフトホワイトを採用
    val TEXT_SOFT_WHITE = Color(0xFFE0E0E0)
    
    // アクセントカラーとして、目に馴染みやすい淡いミントグリーンを採用
    val ACCENT_MINT = Color(0xFFB2D8B2)
    
    // ガボアパッチなどの描画用（低コントラスト）
    val GABOR_PRIMARY = Color(0xFF4A4A4A)
    val GABOR_SECONDARY = Color(0xFF808080)

    // --- Animation & Movement ---
    // ターゲットの移動を滑らかにするための基本期間（ミリ秒）
    const val EYE_MOVEMENT_DURATION_MS = 3000
    
    // 音声のフェードイン・フェードアウト期間
    const val AUDIO_FADE_DURATION_MS = 2000L

    // --- Training Settings ---
    // ガボアパッチの縞模様の数
    const val GABOR_STRIPE_COUNT = 7
    
    // ステレオグラムのガイドドットの距離（dp単位の目安）
    const val STEREOGRAM_DOT_DISTANCE_DP = 64

    // --- Notification & Service ---
    const val NOTIFICATION_ID = 1001
    const val NOTIFICATION_CHANNEL_ID = "eye_refresh_channel"
}
