# Studio Ghibli API for Android

This repository provides an Android library module to consume data from the [Studio Ghibli API](https://ghibliapi.vercel.app). It is designed to be modular and easily integrated into any Android application via **JitPack**.

## 📦 Installation

To use the `studioghibli` module in your Android project:

### 1. Add JitPack to your root `build.gradle` (Project level)

```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

### 2. Add the dependency to your `build.gradle` (Module level)

```groovy
dependencies {
    implementation 'com.github.Agatha886.App:studioghibli-v1.0.1'
}
```

> Make sure to replace `v1.0.1` with the latest version tag available.

### 3. Add Required Permissions to `AndroidManifest.xml`

To allow API calls, include the following permissions in your manifest:

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

## ⚙️ Configuration

To enable dependency injection using **Koin**, add the following to your `AndroidManifest.xml`:

```xml
<application
    android:name="br.com.agatha.monfredini.studio_ghibli_api.di.modules.GhibliApplication"
    ... >
```

This initializes Koin with all the required repositories and view models.

### Application Class

```kotlin
class GhibliApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@GhibliApplication)
            modules(listOf(ghibliApiRepository, ghibliApiViewModel))
        }
    }
}
```

### Required Dependencies

Make sure your project includes the following Koin libraries:

```toml
koin = "3.5.3"
koin-core = { group = "io.insert-koin", name = "koin-core", version.ref = "koin" }
koin-android = { group = "io.insert-koin", name = "koin-android", version.ref = "koin" }
```


## 📚 How to Use

Once added, you can access ViewModels and Repositories provided by the library directly using Koin:

### Example: Injecting a ViewModel

```kotlin
private val viewModel: MoviesViewModel by viewModel()
```

You can then observe data and display it in your UI using LiveData:

```
viewModel.movies.observe(this) { movies ->
    adapter.submitList(movies)
    logInfo("Movies: $movies")
}
```

## 📁 Project Structure

* `studioghibli`: Reusable Android module that handles all API calls and data processing.
* `app`: Example/demo module used only for local testing of the `studioghibli` module. It includes UI screens to test the available API calls and ViewModels.

## 🚀 Features

* Consume Studio Ghibli movie, character, location, species, and vehicle data.
* Built with Retrofit and Kotlin.
* Clean architecture using repository and ViewModel pattern.
* Koin-based dependency injection.

## 🔍 API Reference

All API data is sourced from:
📡 [https://ghibliapi.vercel.app](https://ghibliapi.vercel.app)

You can explore endpoints like:

* `/films`
* `/people`
* `/locations`
* `/species`
* `/vehicles`

### Questions?

For more details, check the [repository](https://github.com/Agatha886/App-Studio-Ghibli-API) or open an issue.
