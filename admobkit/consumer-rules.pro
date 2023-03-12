-keep class com.google.android.gms.internal.consent_sdk.** { <fields>; }
-keepattributes *Annotation*
-keepattributes Signature

# startup library
-keepnames class androidx.startup.AppInitializer
-keepnames class androidx.startup.InitializationProvider
-keepnames class * extends androidx.startup.Initializer
# These Proguard rules ensures that ComponentInitializers are are neither shrunk nor obfuscated,
# and are a part of the primary dex file. This is because they are discovered and instantiated
# during application startup.
-keep class * extends androidx.startup.Initializer {
    # Keep the public no-argument constructor while allowing other methods to be optimized.
    <init>();
}

# Pangle
-keep class com.bytedance.sdk.** { *; }