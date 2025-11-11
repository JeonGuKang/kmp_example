# Todo KMP 프로젝트

이것은 Android, iOS, Web, Desktop (JVM), Server를 대상으로 하는 Kotlin Multiplatform 프로젝트입니다.

*   [/composeApp](./composeApp/src)은 Compose Multiplatform 애플리케이션 전반에 공유될 코드를 위한 곳입니다.
    여러 하위 폴더를 포함합니다:
    *   [commonMain](./composeApp/src/commonMain/kotlin)은 모든 타겟에 공통적인 코드를 위한 곳입니다.
    *   다른 폴더들은 폴더 이름에 표시된 플랫폼을 위해서만 컴파일될 Kotlin 코드를 위한 곳입니다.
        예를 들어, Kotlin 앱의 iOS 파트를 위해 Apple의 CoreCrypto를 사용하고 싶다면,
        [iosMain](./composeApp/src/iosMain/kotlin) 폴더가 그러한 호출에 적합한 장소일 것입니다.
        마찬가지로, Desktop (JVM) 특정 부분을 편집하고 싶다면, [jvmMain](./composeApp/src/jvmMain/kotlin)
        폴더가 적절한 위치입니다.

*   [/iosApp](./iosApp/iosApp)은 iOS 애플리케이션을 포함합니다. Compose Multiplatform으로 UI를 공유하더라도,
    iOS 앱을 위한 이 진입점이 필요합니다. 프로젝트의 SwiftUI 코드를 추가해야 하는 곳이기도 합니다.

*   [/server](./server/src/main/kotlin)은 Ktor 서버 애플리케이션을 위한 곳입니다.

*   [/shared](./shared/src)는 프로젝트의 모든 타겟 간에 공유될 코드를 위한 곳입니다.
    가장 중요한 하위 폴더는 [commonMain](./shared/src/commonMain/kotlin)입니다. 원한다면,
    여기에 플랫폼별 폴더에 코드를 추가할 수도 있습니다.

### Android 애플리케이션 빌드 및 실행

Android 앱의 개발 버전을 빌드하고 실행하려면, IDE의 툴바에 있는 실행 위젯의 실행 구성을 사용하거나 터미널에서 직접 빌드하세요:
- macOS/Linux에서
  ```shell
  ./gradlew :composeApp:assembleDebug
  ```
- Windows에서
  ```shell
  .\gradlew.bat :composeApp:assembleDebug
  ```

### Desktop (JVM) 애플리케이션 빌드 및 실행

데스크톱 앱의 개발 버전을 빌드하고 실행하려면, IDE의 툴바에 있는 실행 위젯의 실행 구성을 사용하거나 터미널에서 직접 실행하세요:
- macOS/Linux에서
  ```shell
  ./gradlew :composeApp:run
  ```
- Windows에서
  ```shell
  .\gradlew.bat :composeApp:run
  ```

### 서버 빌드 및 실행

서버의 개발 버전을 빌드하고 실행하려면, IDE의 툴바에 있는 실행 위젯의 실행 구성을 사용하거나 터미널에서 직접 실행하세요:
- macOS/Linux에서
  ```shell
  ./gradlew :server:run
  
  ```
- Windows에서
  ```shell
  .\gradlew.bat :server:run
  ```

### 웹 애플리케이션 빌드 및 실행

웹 앱의 개발 버전을 빌드하고 실행하려면, IDE의 툴바에 있는 실행 위젯의 실행 구성을 사용하거나 터미널에서 직접 실행하세요:
- Wasm 타겟용 (더 빠름, 최신 브라우저):
  - macOS/Linux에서
    ```shell
    ./gradlew :composeApp:wasmJsBrowserDevelopmentRun
    ```
  - Windows에서
    ```shell
    .\gradlew.bat :composeApp:wasmJsBrowserDevelopmentRun
    ```
- JS 타겟용 (더 느림, 구형 브라우저 지원):
  - macOS/Linux에서
    ```shell
    ./gradlew :composeApp:jsBrowserDevelopmentRun
    ```
  - Windows에서
    ```shell
    .\gradlew.bat :composeApp:jsBrowserDevelopmentRun
    ```

### iOS 애플리케이션 빌드 및 실행

iOS 앱의 개발 버전을 빌드하고 실행하려면, IDE의 툴바에 있는 실행 위젯의 실행 구성을 사용하거나 Xcode에서 [/iosApp](./iosApp) 디렉토리를 열고 거기서 실행하세요.

---

[Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html),
[Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform/#compose-multiplatform),
[Kotlin/Wasm](https://kotl.in/wasm/)에 대해 더 알아보세요.

Compose/Web과 Kotlin/Wasm에 대한 여러분의 피드백을 공개 Slack 채널 [#compose-web](https://slack-chats.kotlinlang.org/c/compose-web)에서 기다리겠습니다.
문제가 발생하면 [YouTrack](https://youtrack.jetbrains.com/newIssue?project=CMP)에 보고해주세요.
