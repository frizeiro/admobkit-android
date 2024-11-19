# AdMobKit for Android

Abstraction library of AdMob Ads, User Data Consent (User Messaging Platform - UMP), In App Purchase (Google Play Billing) and Open Bidding (Mediation) for easy integration into your Android app.

## References

* [Google AdMob](https://developers.google.com/admob/android)
* [User Messaging Platform (UMP)](https://developers.google.com/admob/android/privacy)
* [Google Play Billing (In App Purchase)](https://developer.android.com/google/play/billing/integrate)
* [AdMob Open Bidding (Mediation)](https://admob.google.com/home/bidding/what-is-bidding/)

# AdMob Open Bidding (Mediation)

You'll need to create accounts for each platform. Please, take a look at Google AdMob's references:
* [Meta (Facebook)](https://developers.google.com/admob/android/mediation/meta)
* [Pangle (Tik Tok)](https://developers.google.com/admob/android/mediation/pangle)
* [InMobi](https://developers.google.com/admob/android/mediation/inmobi) (coming soon)
* [AppLovin](https://developers.google.com/admob/android/mediation/applovin) (coming soon)
* [Liftoff Monetize (Vungle)](https://developers.google.com/admob/android/mediation/vungle) (coming soon)

# How to install

## gradle

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

Add the dependency
```gradle
dependencies {
    implementation 'com.github.frizeiro:admobkit-android:0.6.1'
}
```

# How to use

See the example project source code for how to implement and use it:
[AdMob Kit Example Project](https://github.com/frizeiro/admobkit-android/tree/master/example/src/main).