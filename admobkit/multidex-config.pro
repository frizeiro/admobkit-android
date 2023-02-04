# Multidex rules, as ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /dados/home/android-studio/android-sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# startup library
-keep class androidx.startup.AppInitializer
-keep class androidx.startup.InitializationProvider
-keep class * extends androidx.startup.Initializer