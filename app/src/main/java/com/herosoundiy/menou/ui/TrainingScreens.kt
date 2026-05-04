package com.herosoundiy.menou.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import com.herosoundiy.menou.R
import com.herosoundiy.menou.constants.AppConstants
import kotlin.math.sin

/**
 * トレーニング画面のCompose実装。
 * Canvas描画において、縞模様の境界をぼかす（グラデーション）ことで
 * 視覚的な刺激を和らげ、目に馴染むように設計。
 */

@Composable
fun GaborPatchScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppConstants.BACKGROUND_DARK),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(200.dp)) {
            val stripeCount = AppConstants.GABOR_STRIPE_COUNT
            val stripeWidth = size.width / stripeCount

            // 45度回転させてバリエーションを持たせる
            rotate(45f) {
                for (i in 0 until stripeCount) {
                    val x = i * stripeWidth
                    // 境界を滑らかにするため、単純な塗りつぶしではなくグラデーションを使用
                    val brush = Brush.linearGradient(
                        colors = listOf(
                            AppConstants.GABOR_PRIMARY,
                            AppConstants.GABOR_SECONDARY,
                            AppConstants.GABOR_PRIMARY
                        ),
                        start = Offset(x, 0f),
                        end = Offset(x + stripeWidth, 0f)
                    )
                    drawRect(brush = brush, topLeft = Offset(x, 0f), size = size.copy(width = stripeWidth))
                }
            }
        }
        Text(
            text = stringResource(id = R.string.gabor_patch_title),
            color = AppConstants.TEXT_SOFT_WHITE,
            modifier = Modifier.align(Alignment.BottomCenter).padding(32.dp)
        )
    }
}

@Composable
fun StereogramScreen() {
    val density = LocalDensity.current
    val dotDistancePx = with(density) { AppConstants.STEREOGRAM_DOT_DISTANCE_DP.dp.toPx() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppConstants.BACKGROUND_DARK),
        contentAlignment = Alignment.Center
    ) {
        // 平行法（Parallel View）用のガイドドット
        Canvas(modifier = Modifier.fillMaxWidth().height(100.dp)) {
            val centerY = size.height / 2
            val centerX = size.width / 2

            // 左のガイド
            drawCircle(
                color = AppConstants.ACCENT_MINT,
                radius = 8f,
                center = Offset(centerX - dotDistancePx / 2, centerY)
            )
            // 右のガイド
            drawCircle(
                color = AppConstants.ACCENT_MINT,
                radius = 8f,
                center = Offset(centerX + dotDistancePx / 2, centerY)
            )
        }
        Text(
            text = stringResource(id = R.string.stereogram_guide_title),
            color = AppConstants.TEXT_SOFT_WHITE,
            modifier = Modifier.align(Alignment.BottomCenter).padding(32.dp)
        )
    }
}

@Composable
fun EyeMovementScreen() {
    val infiniteTransition = rememberInfiniteTransition(label = "eye_movement")
    
    // 滑らかな移動を実現するため、LinearEasingではなくFastOutSlowInEasing等を検討するが、
    // 一定速度が推奨される場合はLinear、視覚追従を自然にする場合はCubicBezierを使用。
    val animatedOffset by infiniteTransition.animateFloat(
        initialValue = -1f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(AppConstants.EYE_MOVEMENT_DURATION_MS, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "target_x"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppConstants.BACKGROUND_DARK),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val radius = 30.dp.toPx()
            val xPos = center.x + (animatedOffset * (size.width / 2 - radius))
            
            drawCircle(
                color = AppConstants.ACCENT_MINT,
                radius = radius,
                center = Offset(xPos, center.y)
            )
        }
        Text(
            text = stringResource(id = R.string.eye_movement_title),
            color = AppConstants.TEXT_SOFT_WHITE,
            modifier = Modifier.align(Alignment.BottomCenter).padding(32.dp)
        )
    }
}
