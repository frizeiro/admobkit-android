# AdMobKit for Android

Abstraction library of AdMob Ads, User Data Consent (User Messaging Platform - UMP), In App Purchase (Google Play Billing) and Open Bidding (Mediation) for easy integration into your Android app.

## References

* [Google AdMob](https://developers.google.com/admob/android)
* [User Messaging Platform (UMP)](https://developers.google.com/admob/android/privacy)
* [Google Play Billing (In App Purchase)](https://developer.android.com/google/play/billing/integrate)
* [AdMob Open Bidding (Mediation)](https://admob.google.com/home/bidding/what-is-bidding/)

# How to install

## gradle < 7.0.0

Add these lines in your project's root `build.gradle` at the `repositories` path:

```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
        maven { url 'https://artifact.bytedance.com/repository/pangle' }
    }
}
```

## gradle >= 7.0.0

Add these lines in your project's root `settings.gradle`:

```gradle
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        ...
        maven { url "https://jitpack.io" }
        maven { url 'https://artifact.bytedance.com/repository/pangle' }
    }
}
```

Add the dependency:

```gradle
dependencies {
    ...
    implementation 'com.github.frizeiro:admobkit-android:0.6.2'
}
```

# How to use

See the example project source code for how to implement and use it:
[AdMob Kit Example Project](https://github.com/frizeiro/admobkit-android/tree/master/example/src/main).

# AdMob Open Bidding (Mediation)

You'll need to create accounts for each platform. Please, take a look at Google AdMob's references:

* [Meta (Facebook)](https://developers.google.com/admob/android/mediation/meta)
* [Pangle (Tik Tok)](https://developers.google.com/admob/android/mediation/pangle)
* [InMobi](https://developers.google.com/admob/android/mediation/inmobi) (coming soon)
* [AppLovin](https://developers.google.com/admob/android/mediation/applovin) (coming soon)
* [Liftoff Monetize (Vungle)](https://developers.google.com/admob/android/mediation/vungle) (coming soon)

# TODO: atualizar toda o app de testes, após remoção do Facebook e Pangle.

Manifest merger failed : Attribute property#android.adservices.AD_SERVICES_CONFIG@resource value=(@xml/ga_ad_services_config) from [com.google.android.gms:play-services-measurement-api:22.1.2]
AndroidManifest.xml:32:13-58
is also present at [com.google.android.gms:play-services-ads-lite:23.5.0] AndroidManifest.xml:109:13-59 value=(@xml/gma_ad_services_config).
Suggestion: add 'tools:replace="android:resource"' to <property> element at AndroidManifest.xml to override.

Verificar implementação do consents:
https://github.com/googleads/googleads-mobile-android-examples/blob/main/kotlin/admob/BannerExample/app/src/main/java/com/google/android/gms/example/bannerexample/MainActivity.kt