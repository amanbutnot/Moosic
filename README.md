# Moosic

![Version](https://img.shields.io/badge/version-1.0-blue)
![Kotlin](https://img.shields.io/badge/Kotlin-2.3.0-purple?logo=kotlin)
![Swift](https://img.shields.io/badge/Swift-5.10-orange?logo=swift)
![Compose Multiplatform](https://img.shields.io/badge/Compose_Multiplatform-1.9.0--alpha03-blue?logo=jetpackcompose)
![Ktor](https://img.shields.io/badge/Ktor-3.3.3-orange?logo=ktor)
![Voyager](https://img.shields.io/badge/Voyager-1.1.0--beta03-red)
![Coil3](https://img.shields.io/badge/Coil3-3.3.0-green)

Moosic is a modern, expressive music client built with **Compose Multiplatform** and **Kotlin Multiplatform (KMP)**. It aims to provide a beautiful and consistent music experience across Android, iOS, Desktop, and Web.

## Features

- **Multiplatform Support:** Shared business logic and UI across Android, iOS, JVM (Desktop), and Wasm/JS (Web).
- **Material 3 Expressive UI:** Leverages the latest Material 3 design principles for a modern, fluid, and personalized look and feel.
- **Subsonic/OpenSubsonic Integration:** Connects to your favorite music server (like Navidrome, Gonic, etc.) to stream your library.
- **Responsive Design:** Adapts seamlessly to different screen sizes and orientations.

## Tech Stack

- **Framework:** [Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform) (1.9.0-alpha03)
- **Language:** [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html) (2.3.0)
- **Navigation:** [Voyager](https://github.com/adrielcafe/voyager) (1.1.0-beta03)
- **Networking:** [Ktor](https://ktor.io/) (3.3.3)
- **Image Loading:** [Coil3](https://github.com/coil-kt/coil) (3.3.0)
- **Dependency Injection:** [Koin](https://insert-koin.io/) (Planned/In-use)
- **Serialization:** [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization) (2.3.0)

## Project Structure

- `composeApp/commonMain`: Shared UI (Compose) and business logic.
- `composeApp/androidMain`: Android-specific implementations and configuration.
- `composeApp/iosMain`: iOS-specific implementations.
- `composeApp/jvmMain`: Desktop-specific implementations.
- `composeApp/wasmJsMain` / `jsMain`: Web-specific implementations.
- `iosApp`: Entry point for the iOS application.

## Getting Started

### Prerequisites

- Android Studio / IntelliJ IDEA
- Kotlin Multiplatform Mobile plugin
- Xcode (for iOS development)
- JDK 17 or higher

### Build and Run

#### Android
```shell
./gradlew :composeApp:assembleDebug
```

#### Desktop (JVM)
```shell
./gradlew :composeApp:run
```

#### Web (Wasm)
```shell
./gradlew :composeApp:wasmJsBrowserDevelopmentRun
```

#### iOS
Open the `iosApp` directory in Xcode or use the run configuration in Android Studio.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
