package com.experimental.eyerefreshnative.service

import android.app.*
import android.content.Intent
import android.net.Uri
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.experimental.eyerefreshnative.MainActivity
import com.experimental.eyerefreshnative.R
import com.experimental.eyerefreshnative.constants.AppConstants

/**
 * フォアグラウンドサービス。
 * 通知管理、リソース解放、ExoPlayerによる音声再生を担当。
 * 夜間の使用を妨げないよう、控えめな通知デザインを採用。
 */
class EyeGuardService : Service() {

    private var exoPlayer: ExoPlayer? = null

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startForeground(AppConstants.NOTIFICATION_ID, createNotification())
        setupAudioPlayer()
    }

    private fun setupAudioPlayer() {
        exoPlayer = ExoPlayer.Builder(this).build().apply {
            // res/raw/awakening.mp3 を再生（実際のリソースがある前提）
            val uri = Uri.parse("android.resource://$packageName/raw/awakening")
            setMediaItem(MediaItem.fromUri(uri))
            prepare()
            repeatMode = ExoPlayer.REPEAT_MODE_ONE
            playWhenReady = true
            
            // フェードインの簡易実装（ボリュームを徐々に上げる）
            volume = 0f
            // 実際にはハンドラーやアニメーションで徐々に上げるロジックが必要だが、
            // ここでは初期設定に留める
        }
    }

    private fun createNotification(): Notification {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, AppConstants.NOTIFICATION_CHANNEL_ID)
            .setContentTitle(getString(R.string.app_name))
            .setContentText(getString(R.string.notification_content))
            .setSmallIcon(R.drawable.ic_eye_guard)
            .setPriority(NotificationCompat.PRIORITY_LOW) // 控えめな通知
            .setCategory(Notification.CATEGORY_SERVICE)
            .setContentIntent(pendingIntent)
            .build()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            AppConstants.NOTIFICATION_CHANNEL_ID,
            getString(R.string.notification_channel_name),
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            description = getString(R.string.notification_channel_desc)
        }
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        // リソース解放の徹底
        exoPlayer?.let {
            it.stop()
            it.release()
        }
        exoPlayer = null
        super.onDestroy()
    }
}
