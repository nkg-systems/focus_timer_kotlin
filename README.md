# Focus Timer Kotlin

A minimalist and elegant focus timer Android app built with Kotlin and Jetpack Compose, featuring a dark AMOLED theme and modern Material 3 design.

**By: NKG Systems + NathanGr33n**

## 🎯 Features

- **🍅 Pomodoro Timer**: Customizable focus sessions with preset durations (15, 25, 45 minutes)
- **⏱️ Custom Duration**: Set any custom timer duration up to 999 minutes
- **🔄 Auto-Repeat**: Automatically restart sessions for continuous focus cycles
- **📊 Session Tracking**: Keep track of completed focus sessions
- **🎨 Modern UI**: Clean Material 3 design with pure AMOLED dark theme
- **🔔 Smart Notifications**: Get notified when sessions complete with haptic feedback
- **💡 Always-On Display**: Screen stays on during active sessions to prevent interruptions
- **📱 Portrait Optimized**: Locked to portrait orientation to minimize distractions
- **🚀 Performance Focused**: Lightweight and battery-efficient design

## 🚀 Getting Started

### Prerequisites

- **Android Studio**: Hedgehog (2023.1.1) or later recommended
- **Android SDK**: API level 24 (Android 7.0) minimum, targets API 35 (Android 15)
- **Kotlin**: 1.9.10+
- **Java**: JDK 17 or higher

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/nkg-systems/focus_timer_kotlin.git
   cd focus_timer_kotlin
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Select "Open an existing project"
   - Navigate to the cloned repository and open the `Gh0st_Focus` folder

3. **Build and run**
   - Connect an Android device or start an emulator
   - Click the "Run" button or press `Shift + F10`

### Alternative Installation Methods

**Using Gradle Command Line:**
```bash
cd Gh0st_Focus
./gradlew assembleDebug  # Build APK
./gradlew installDebug   # Install on connected device
```

## 📱 How to Use

1. **Set Timer Duration**
   - Use quick preset buttons (15m, 25m, 45m) for common durations
   - Or enter a custom duration in the "Minutes" field and tap "Set"

2. **Start Focus Session**
   - Tap the "Start" button or the large circular button at the bottom
   - The screen will stay on during active sessions

3. **Control Timer**
   - **Pause**: Temporarily stop the timer while preserving progress
   - **Reset**: Stop the timer and reset to full duration
   - **Auto-repeat**: Toggle to automatically restart sessions

4. **Track Progress**
   - View current session length and completed sessions count
   - Get notifications and vibration when sessions complete

## 🏗️ Architecture

The app follows modern Android development practices:

- **MVVM Architecture**: Clean separation of concerns with ViewModel pattern
- **Jetpack Compose**: Modern declarative UI toolkit
- **StateFlow**: Reactive state management
- **Material 3**: Latest Material Design guidelines
- **Edge-to-Edge**: Full-screen immersive experience

### Project Structure

```
Gh0st_Focus/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/gh0st_focus/
│   │   │   │   ├── MainActivity.kt          # Main UI and navigation
│   │   │   │   ├── TimerViewModel.kt        # Timer logic and state management
│   │   │   │   ├── Notifications.kt         # Notification handling
│   │   │   │   ├── Gh0stFocusApp.kt        # Application class
│   │   │   │   └── ui/theme/               # Theme and styling
│   │   │   └── AndroidManifest.xml
│   │   ├── test/                           # Unit tests
│   │   └── androidTest/                    # Instrumentation tests
│   ├── build.gradle.kts                    # App-level build configuration
│   └── proguard-rules.pro                  # ProGuard rules for release builds
├── build.gradle.kts                        # Project-level build configuration
├── settings.gradle.kts                     # Project settings
├── gradle.properties                       # Gradle properties
└── LICENSE                                 # MIT License
```

## 🛠️ Technologies Used

- **Kotlin 1.9.10**: Primary programming language with latest features
- **Jetpack Compose (2024.08.00 BOM)**: Modern declarative UI framework
- **Material 3**: Latest Material Design system with dynamic theming
- **AndroidX Libraries**:
  - Activity Compose 1.8.2
  - Lifecycle ViewModel Compose 2.7.0
  - Core KTX 1.13.1
  - Core Splash Screen 1.0.1
  - Material Icons Extended
- **Accompanist System UI Controller 0.36.0**: For immersive status/navigation bar theming
- **Android Gradle Plugin 8.1.2**: Latest build tools
- **ProGuard**: Code obfuscation and optimization for release builds
- **JUnit & Espresso**: Testing framework for unit and UI tests

## 🎨 Design Highlights

- **AMOLED Dark Theme**: Pure black backgrounds for OLED displays
- **Material 3 Components**: Modern buttons, chips, and text fields
- **Elevated Surfaces**: Subtle shadows and depth
- **Consistent Typography**: Clear hierarchy and readability
- **Responsive Layout**: Optimized spacing and alignment

## 📋 Permissions

The app requests the following permissions:

- `POST_NOTIFICATIONS`: To show completion notifications (Android 13+)
- `VIBRATE`: To provide haptic feedback on timer completion

## 🔧 Configuration

### Build Variants

- **Debug**: Development build with debugging enabled
- **Release**: Production build with ProGuard obfuscation and optimization

### Customization Options

You can customize the app by modifying:

- **Theme colors**: Edit `ui/theme/Color.kt`
- **Typography**: Modify `ui/theme/Type.kt`
- **Default timer duration**: Change `TimerState` default value
- **Preset durations**: Update the `presets` list in `MainActivity.kt`

## 🧪 Testing

Run tests using Android Studio or command line:

```bash
# Unit tests
./gradlew test

# Instrumentation tests
./gradlew connectedAndroidTest
```

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

```
MIT License

Copyright (c) 2025 NKG_Systems

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request. For major changes, please open an issue first to discuss what you would like to change.

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📞 Support

If you have any questions or run into issues, please:

- Check the existing [issues](https://github.com/nkg-systems/focus_timer_kotlin/issues)
- Create a new issue with detailed information
- Include device information, Android version, and steps to reproduce

---

**Built with ❤️ using Kotlin and Jetpack Compose**
