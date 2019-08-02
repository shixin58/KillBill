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