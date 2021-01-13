# admobkit-android
Abstraction library of AdMob Ads, User Data Consent (User Messaging Platform, UMP) and In App Purchase (Google Play Billing) for easy integration into your Android app.

# How to install

## gradle

Add it in your root `build.gradle` at the end of repositories:
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

Add the dependency
```
dependencies {
    implementation 'com.github.frizeiro:admobkit-android:$version'
}
```

## maven

```
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

Add the dependency
```
<dependency>
    <groupId>com.github.frizeiro</groupId>
    <artifactId>admobkit-android</artifactId>
    <version>$version</version>
</dependency>
```

## sbt

Add it in your `build.sbt` at the end of resolvers:
```
resolvers += "jitpack" at "https://jitpack.io"
```

Add the dependency
```
libraryDependencies += "com.github.frizeiro" % "admobkit-android" % "$version"
```

## leiningen

Add it in your `project.clj` at the end of repositories:
```
 :repositories [["jitpack" "https://jitpack.io"]]
```

Add the dependency
```
:dependencies [[com.github.frizeiro/admobkit-android "$version"]]
```

# How to use

See the example project source code for how to implement and use it:
[AdMob Kit Example Project](https://github.com/frizeiro/admobkit-android/tree/master/example/src/main).