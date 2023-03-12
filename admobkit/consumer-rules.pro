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

# Avoid Warnings
# This was generated automatically by the Android Gradle plugin.
-dontwarn com.bytedance.JProtect
-dontwarn com.bytedance.component.sdk.annotation.ColorInt
-dontwarn com.bytedance.component.sdk.annotation.RequiresApi
-dontwarn com.bytedance.component.sdk.annotation.UiThread
-dontwarn com.bytedance.frameworks.baselib.network.http.NetworkParams$AddSecurityFactorProcessCallback
-dontwarn com.bytedance.frameworks.baselib.network.http.NetworkParams
-dontwarn com.bytedance.frameworks.baselib.network.http.parser.StreamParser
-dontwarn com.bytedance.framwork.core.sdkmonitor.SDKMonitor$IGetExtendParams
-dontwarn com.bytedance.framwork.core.sdkmonitor.SDKMonitor
-dontwarn com.bytedance.framwork.core.sdkmonitor.SDKMonitorUtils
-dontwarn com.bytedance.retrofit2.Call
-dontwarn com.bytedance.retrofit2.Retrofit
-dontwarn com.bytedance.retrofit2.SsResponse
-dontwarn com.bytedance.retrofit2.client.Header
-dontwarn com.bytedance.retrofit2.client.Request
-dontwarn com.bytedance.retrofit2.intercept.Interceptor$Chain
-dontwarn com.bytedance.retrofit2.intercept.Interceptor
-dontwarn com.bytedance.retrofit2.mime.TypedByteArray
-dontwarn com.bytedance.retrofit2.mime.TypedInput
-dontwarn com.bytedance.sdk.openadsdk.core.model.NetExtParams$RenderType
-dontwarn com.bytedance.sdk.openadsdk.core.settings.TTSdkSettings$FETCH_REQUEST_SOURCE
-dontwarn com.bytedance.ttnet.utils.RetrofitUtils
-dontwarn com.facebook.infer.annotation.Nullsafe$Mode
-dontwarn com.facebook.infer.annotation.Nullsafe