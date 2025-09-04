//// Top-level build file where you can add configuration options common to all sub-projects/modules.
//plugins {
//    alias(libs.plugins.android.application) apply false
//    alias(libs.plugins.kotlin.android) apply false
//    alias(libs.plugins.kotlin.compose) apply false
//
//}
//buildscript{
//    repositories{
//        mavenCentral()
//    }
//    dependencies{
//        classpath("com.google.dagger:hilt-android-gradle-plugin:2.56.2")
//    }
//}
// Top-level build file
//plugins {
//    id("com.android.application") version "8.5.2" apply false
//    id("com.android.library") version "8.5.2" apply false
//    id("org.jetbrains.kotlin.android") version "1.9.24" apply false
//    id("com.google.dagger.hilt.android") version "2.51.1" apply false
//}
plugins {
    id("com.android.application") version "8.6.0" apply false
    id("com.android.library") version "8.6.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.24" apply false
    id("com.google.dagger.hilt.android") version "2.51.1" apply false
}
