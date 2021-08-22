plugins {
    kotlin("jvm") version "1.5.10" apply false
    id("com.anatawa12.auto-visitor") version "1.0.6" apply false
    id("com.anatawa12.auto-tostring") version "1.0.1" apply false
    id("org.jetbrains.dokka") version "1.5.0" apply false
}

val version: String by properties as Map<String, Any?>

project.group = "com.anatawa12.class-ast"
project.version = version

subprojects {
    group = rootProject.group
    version = rootProject.version
}
