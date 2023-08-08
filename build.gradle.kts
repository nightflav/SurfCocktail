// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories { mavenCentral() }

    dependencies {
        val kotlin_version = "1.9.0"
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        classpath("org.jetbrains.kotlin:kotlin-serialization:$kotlin_version")
    }
}

plugins {
    id("com.android.application") version "8.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.google.devtools.ksp") version "1.9.0-1.0.13" apply false
    kotlin("kapt") version "1.3.70" apply false
}