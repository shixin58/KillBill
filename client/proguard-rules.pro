# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# 压缩Shrinking, 默认开启
#-dontshrink

# 优化Optimization，默认开启
#-dontoptimize
#-optimizationpasses 5

# 混淆Obfuscation，默认开启
# -dontobfuscate

# 类名不混淆
-keep class com.bride.client.language.coffee.Coffee

-keep class com.bride.client.reflect.** {*;}

-keep class com.bride.client.observer.* {
    <init>(...);
    <fields>;
    <methods>;
}

# 成员不混淆
-keepclassmembers class * extends android.app.Activity {*;}

# 内部类及其成员不混淆
-keep class com.bride.client.activity.BinderActivity$* {*;}
-keep class com.bride.client.activity.ClassLoaderActivity$* {*;}

# For OkHttp
# JSR 305 annotations are for embedding nullability information.
-dontwarn javax.annotation.**
# A resource is loaded with a relative path so the package of this class must be preserved.
-adaptresourcefilenames okhttp3/internal/publicsuffix/PublicSuffixDatabase.gz
# Animal Sniffer compileOnly dependency to ensure APIs are compatible with older versions of Java.
-dontwarn org.codehaus.mojo.animal_sniffer.*
# OkHttp platform used only on JVM and when Conscrypt and other security providers are available.
-dontwarn okhttp3.internal.platform.**
-dontwarn org.conscrypt.**
-dontwarn org.bouncycastle.**
-dontwarn org.openjsse.**