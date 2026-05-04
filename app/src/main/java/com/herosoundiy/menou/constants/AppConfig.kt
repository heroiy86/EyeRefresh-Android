package com.herosoundiy.menou.constants

/**
 * アプリケーションのグローバル構成を管理するクラス。
 * 宝石の「瑪瑙（メノウ）」を象徴するブランドイメージを技術的側面から支える。
 */
object AppConfig {
    
    // ブランドの基本名称
    const val APP_BRAND_NAME = "MENOU"
    
    // バージョン情報（必要に応じてBuildConfigから取得するように拡張可能）
    const val VERSION_NAME = "1.0.0"
    
    // 開発者・ブランド運営者名
    const val DEVELOPER_BRAND = "herosoundiy"

    /**
     * アプリ内で表示するブランドテキスト。
     * UIの一部（タイトルバーなど）で動的に名称を使用する場合に参照。
     */
    fun getFullBrandTitle(isJapanese: Boolean): String {
        return if (isJapanese) {
            "$APP_BRAND_NAME - 目と脳のコンディショニング -"
        } else {
            "$APP_BRAND_NAME - Eye & Brain Conditioning -"
        }
    }
}
