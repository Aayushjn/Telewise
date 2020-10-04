# Telewise

Telewise is a movie and TV guide powered by the [TMDb](https://www.themoviedb.org/) API. 

The purpose of this repository is to help me keep up with the latest (and greatest?) tools available
 in Android development. Another repository ([Filmipedia](https://github.com/Aayushjn/Filmipedia)) 
 exists but the amount of refactoring required would not be worth it. Instead, I can start from the 
 ground up in a new repository.
 
## Tech stack & libraries used
- Minimum SDK level 23
- 100% Kotlin + Coroutines + Flow
- Architecture
    - **MVVM** architecture
    - **Repository** pattern
    - **Dagger** and **Hilt** for dependency injection
- JetPack **Navigation** (following a *single activity, multiple fragments* approach)
- AndroidX **App Startup** library for setting up necessary dependencies at app startup
- **Material Design** (my best attempt at it so far)
- **[Retrofit2](https://square.github.io/retrofit/)** and **[kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization)** for REST API
    - **[OkHttp3](https://square.github.io/okhttp/)** for request interception and logging
    - **[NetworkResponseAdapter](https://github.com/haroldadmin/NetworkResponseAdapter)** for mapping Retrofit2 calls to appropriate network models
- **[Coil](https://coil-kt.github.io/coil/)** for loading images
- **[AndroidVeil](https://github.com/skydoves/AndroidVeil)** library for displaying veiled/skeleton layouts during API calls
- **[Timber](https://github.com/JakeWharton/timber)** for logging
- **[LeakCanary](https://square.github.io/leakcanary/)** for leak detection
