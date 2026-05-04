# Eye Refresh Native

視力回復トレーニングをサポートする、Androidネイティブアプリケーションのプロトタイプです。
「目に優しい」ことを最優先に設計されており、長時間の使用でも疲れにくいUI/UXを提供します。

## 🌟 特徴

### 1. 目に優しい設計 (Eye-Friendly Design)
- **ダークテーマ最適化:** 純粋な黒を避け、視覚負担の少ない「深い森の緑（#121B14）」を基調としています。
- **低コントラスト:** テキストや図形には「ソフトホワイト（#E0E0E0）」を採用し、眩しさを抑制。
- **ミニマルなUI:** 十分な余白を確保し、情報密度を抑えた設計。

### 2. 実装されているトレーニング
- **Gabor Patch:** 境界をぼかした縞模様により、脳の視覚皮質を刺激。
- **3D Stereogram:** 平行法トレーニング用のガイドドットを配置。
- **Eye Movement:** 滑らかなイージングを適用したターゲット追従トレーニング。

### 3. テクニカルハイライト
- **Jetpack Compose:** Canvas APIを駆使した精密な描画ロジック。
- **Foreground Service:** トレーニング中もバックグラウンドで安定した動作を維持。
- **ExoPlayer (Media3):** フェードイン/アウトに対応したリラックスサウンド再生。

## 🛠 技術スタック
- **Language:** Kotlin
- **UI Framework:** Jetpack Compose
- **Architecture:** MVVM (Clean Code 基準)
- **Multimedia:** Android Media3 (ExoPlayer)
- **Min SDK:** 30 (Android 11+)

## 🚀 開発の進め方

### 推奨プラグイン
本プロジェクトでは、コード品質と開発効率向上のため、以下のプラグインの使用を推奨しています。
- **SonarLint / Detekt / Ktlint:** コード品質の自動チェック。
- **Rainbow Brackets:** Composeコードの視認性向上。
- **GitToolBox:** Git運用効率化。

### ビルド手順
1. Android Studio (Ladybug 以降推奨) でプロジェクトを開く。
2. `res/raw/awakening.mp3` を配置する。
3. `Run` ボタンをクリック。

## 📄 ライセンス
このプロジェクトは開発用プロトタイプです。
