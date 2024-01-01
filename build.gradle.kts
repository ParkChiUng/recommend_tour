// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.0.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
}
buildscript{
    repositories{
        google()
        mavenCentral()
        maven("https://naver.jfrog.io/artifactory/maven/")
    }
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.44.2")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.5.3")
    }
}