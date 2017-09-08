# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/zhengxizhen/Downloads/androidStudio/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-optimizationpasses 5          # 指定代码的压缩级别
-dontusemixedcaseclassnames   # 是否使用大小写混合
-dontpreverify           # 混淆时是否做预校验
-verbose                # 混淆时是否记录日志
-dontwarn com.android.**
-dontwarn com.nostra13.**
-dontwarn com.baidu.**
-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }
-dontwarn com.google.**

#保持com.umeng.**这个包里面的所有类和所有方法不被混淆。(没有友盟的集成时删除此句)
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*  # 混淆时所采用的算法
#程序中用到了gson的new typeToken，结果打包成apk发布时，发现抛出异常
-dontobfuscate
-dontoptimize

-keep public class * extends android.app.Application   # 保持哪些类不被混淆
-keep public class * extends android.app.Service       # 保持哪些类不被混淆
-keep public class * extends android.content.BroadcastReceiver  # 保持哪些类不被混淆
-keep public class * extends android.content.ContentProvider    # 保持哪些类不被混淆
-keep public class * extends android.app.backup.BackupAgentHelper # 保持哪些类不被混淆
-keep public class * extends android.preference.Preference        # 保持哪些类不被混淆
-keep public class com.android.vending.licensing.ILicensingService    # 保持哪些类不被混淆

-keepclasseswithmembernames class * {  # 保持 native 方法不被混淆
    native <methods>;
}
-keepclasseswithmembers class * {   # 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {# 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * extends android.app.Activity {  # 保持自定义控件类不被混淆
    public void *(android.view.View);
}
-keepclassmembers enum * {     # 保持枚举 enum 类不被混淆
 public static **[] values();
 public static ** valueOf(java.lang.String);
}
-keep class * implements android.os.Parcelable { # 保持 Parcelable 不被混淆
 public static final android.os.Parcelable$Creator *;
}
#实体类   保留一个完整的包
-keep class net.sourceforge.**{*;}
-keep class com.nostra13.universalimageloader.**{*;}
-keep class com.google.gson.**{*;}
-keep class com.**{*;}

#第三方引入
#share sdk
-keep class cn.sharesdk.**{*;}
-keep class **.R$* {*;}
-keep class **.R{*;}
-keep class cn.sharesdk.onekeyshare.**{*;}
-dontwarn cn.sharesdk.**
-dontwarn **.R$*

-dontwarn com.tencent.**

-keep class com.google.protobuf.** {*;}
#环信  ↓
-keep class com.easemob.** {*;}
-keep class org.jivesoftware.** {*;}
-keep class org.apache.** {*;}
-dontwarn  com.easemob.**
#另外，demo中发送表情的时候使用到反射，需要keep SmileUtils
#注意前面的包名，如果把这个类复制到自己的项目底下，比如放在com.example.utils底下，应该这么写(实际要去掉#)
#-keep class com.example.utils.SmileUtils {*;}
#如果使用easeui库，需要这么写
#-keep class com.easemob.easeui.utils.EaseSmileUtils {*;}
-keep class com.hyphenate.** {*;}
-dontwarn  com.hyphenate.**

-keep class org.apache.** { *; }
-dontwarn org.apache.**
-dontwarn android.net.**

#retrofit ↓
# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on RoboVM on iOS. Will not be used at runtime.
-dontnote retrofit2.Platform$IOS$MainThreadExecutor
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions

-dontwarn okio.**

-keep class com.lidroid.xutils.**{*;}
-dontwarn com.lidroid.xutils.**

-keep class com.amap.api.location.**{*;}
-keep class com.amap.api.fence.**{*;}
-keep class com.autonavi.aps.amapapi.model.**{*;}

-keep class sun.misc.**{*;}
-dontwarn sun.misc.**

#环信
-keep class com.hyphenate.** {*;}
-dontwarn  com.hyphenate.**
-keep class com.hyphenate.easeui.** {*;}
-dontwarn  com.hyphenate.easeui.**