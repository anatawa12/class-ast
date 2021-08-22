plugins {
    kotlin("jvm")
    id("com.anatawa12.auto-visitor")
    id("com.anatawa12.auto-tostring")
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.jetbrains.dokka:dokka-core:1.5.0")
    runtimeOnly("org.jetbrains.dokka:dokka-base:1.5.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

sourceSets.main {
    java.srcDirs("src")
    resources.srcDirs("resources")
}

autoVisitor.addLib("")
autoToString.addLib()

tasks.test {
    useJUnitPlatform()
}
